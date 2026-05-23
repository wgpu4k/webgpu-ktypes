package io.ygdrasil.wgsl.tests

import io.github.oshai.kotlinlogging.KotlinLogging
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.ygdrasil.wgsl.back.BackendOptions
import io.ygdrasil.wgsl.back.BackendRegistry
import io.ygdrasil.wgsl.parser.Lowerer
import io.ygdrasil.wgsl.parser.TypeResolver
import io.ygdrasil.wgsl.parser.parseWgsl
import io.ygdrasil.wgsl.tests.validator.BackendType
import io.ygdrasil.wgsl.tests.validator.ValidatorFactory
import io.ygdrasil.wgsl.tests.roundtrip.WgslNormalizer
import io.ygdrasil.wgsl.ir.*
import io.ygdrasil.wgsl.ir.Function
import io.ygdrasil.wgsl.arena.Handle
import io.kotest.matchers.shouldNotBe
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

private val logger = KotlinLogging.logger {}

/**
 * Custom exception for golden test failures with clean error messages.
 * Stack trace is logged separately to keep stdout clean.
 */
class GoldenTestException(
    val fileName: String,
    val backend: String,
    val phase: String,
    message: String,
    cause: Throwable? = null
) : RuntimeException("[$backend] $phase failed for $fileName: $message", cause)

/**
 * Centralized error handler that logs the full exception but throws a clean error.
 */
private fun handleGoldenError(fileName: String, backend: String, phase: String, e: Throwable): Nothing {
    val simpleMessage = "[$backend] $phase failed for $fileName: ${e.message}"
    logger.error(e) { simpleMessage }
    throw GoldenTestException(fileName, backend, phase, e.message ?: "Unknown error", e)
}

abstract class GoldenTestBase(val backendName: String) : FunSpec({

    registerAllBackends()
    val goldenUpdate = System.getenv("GOLDEN_UPDATE")?.toBoolean() ?: false
    val goldenFilter = System.getenv("GOLDEN_FILTER")?.takeIf { it.isNotEmpty() }
    val rootDir = findProjectRoot()
    val inputDir = rootDir.resolve("tests/golden/inputs")
    val outputBaseDir = rootDir.resolve("tests/golden/outputs")

    context("$backendName Golden Tests") {
        val inputFiles = Files.list(inputDir)
            .filter { it.toString().endsWith(".wgsl") }
            .filter {
                if (goldenFilter == "starter") {
                    Files.size(it) < 200
                } else {
                    goldenFilter == null || (if (goldenFilter.endsWith(".wgsl")) it.fileName.toString() == goldenFilter else it.fileName.toString().contains(goldenFilter))
                }
            }
            .toList()

        inputFiles.forEach { inputFile ->
            val fileName = inputFile.fileName.toString()
            test("Golden test: $fileName") {
                logger.debug { "Testing $fileName" }
                val source = Files.readString(inputFile)
                
                // 1. Parse
                logger.debug { "Parsing..." }
                val unit = try {
                    parseWgsl(source)
                } catch (e: Exception) {
                    handleGoldenError(fileName, backendName, "parse", e)
                }
                
                // 2. Resolve types
                logger.debug { "Resolving types..." }
                val resolver = TypeResolver()
                val resolutionResult = try {
                    resolver.resolve(unit)
                } catch (e: Exception) {
                    handleGoldenError(fileName, backendName, "type-resolution", e)
                }
                if (!resolutionResult.isSuccess) {
                    throw GoldenTestException(
                        fileName, backendName, "type-resolution",
                        "Unresolved references: ${resolutionResult.unresolvedReferences}"
                    )
                }
                
                // 3. Lower to IR
                logger.debug { "Lowering to IR..." }
                val lowerer = Lowerer()
                val module = try {
                    lowerer.lower(resolutionResult.resolvedUnit)
                } catch (e: Exception) {
                    handleGoldenError(fileName, backendName, "lowering", e)
                }
                
                // 4. Generate backend code
                logger.debug { "Generating backend code for $backendName..." }
                val writer = BackendRegistry.DEFAULT.get(backendName)
                    ?: throw GoldenTestException(fileName, backendName, "backend-lookup", "Backend $backendName not found")
                val output = try {
                    writer.write(module, io.ygdrasil.wgsl.valid.ModuleInfo())
                } catch (e: Exception) {
                    handleGoldenError(fileName, backendName, "code-generation", e)
                }
                
                // 5. Compare or Update
                val outputFile = outputBaseDir.resolve(backendName).resolve(fileName.replace(".wgsl", getExtension(backendName)))
                Files.createDirectories(outputFile.parent)
                
                if (goldenUpdate || !Files.exists(outputFile)) {
                    Files.writeString(outputFile, output)
                } else {
                    val expected = Files.readString(outputFile)
                    if (backendName.lowercase() == "wgsl") {
                        val normalizedActual = WgslNormalizer.normalize(output)
                        val normalizedExpected = WgslNormalizer.normalize(expected)
                        try {
                            normalizedActual shouldBe normalizedExpected
                        } catch (e: AssertionError) {
                            handleGoldenError(fileName, backendName, "comparison", e)
                        }

                        // Proposition 3 : Validation par Round-Trip Sémantique d'Isomorphisme d'IR
                        try {
                            val unitRoundtrip = io.ygdrasil.wgsl.parser.parseWgsl(output)
                            val resolverRoundtrip = io.ygdrasil.wgsl.parser.TypeResolver()
                            val resolutionResultRoundtrip = resolverRoundtrip.resolve(unitRoundtrip)
                            if (!resolutionResultRoundtrip.isSuccess) {
                                println("ROUNDTRIP FAILURE FOR $fileName!")
                                println("OUTPUT:\n$output")
                                println("UNRESOLVED: ${resolutionResultRoundtrip.unresolvedReferences}")
                                throw GoldenTestException(
                                    fileName, backendName, "roundtrip-type-resolution",
                                    "Unresolved references in roundtrip AST: ${resolutionResultRoundtrip.unresolvedReferences}"
                                )
                            }
                            val lowererRoundtrip = io.ygdrasil.wgsl.parser.Lowerer()
                            val moduleRoundtrip = lowererRoundtrip.lower(resolutionResultRoundtrip.resolvedUnit)

                            assertModulesEquivalent(module, moduleRoundtrip)
                        } catch (e: Exception) {
                            handleGoldenError(fileName, backendName, "roundtrip-semantic-isomorphism", e)
                        }
                    } else {
                        try {
                            output shouldBe expected
                        } catch (e: AssertionError) {
                            handleGoldenError(fileName, backendName, "comparison", e)
                        }
                    }
                }

                // 6. Native Validation (if available)
                val type = when (backendName.lowercase()) {
                    "msl" -> BackendType.MSL
                    "glsl" -> BackendType.GLSL
                    "hlsl" -> BackendType.HLSL
                    "spirv" -> BackendType.SPIRV
                    else -> null
                }

                if (type != null && ValidatorFactory.isAvailable(type)) {
                    logger.debug { "Native validation for $backendName..." }
                    val stage = module.entryPoints.firstOrNull()?.stage?.let {
                        when (it) {
                            io.ygdrasil.wgsl.ir.ShaderStage.Vertex -> io.ygdrasil.wgsl.tests.validator.ShaderStage.VERTEX
                            io.ygdrasil.wgsl.ir.ShaderStage.Fragment -> io.ygdrasil.wgsl.tests.validator.ShaderStage.FRAGMENT
                            io.ygdrasil.wgsl.ir.ShaderStage.Compute -> io.ygdrasil.wgsl.tests.validator.ShaderStage.COMPUTE
                        }
                    }

                    val validator = ValidatorFactory.getValidator(type)!!
                    val validationResult = try {
                        validator.validate(output, stage = stage)
                    } catch (e: Exception) {
                        handleGoldenError(fileName, backendName, "validation", e)
                    }
                    if (validationResult.isFailure) {
                        logger.debug { "Native validation FAILED for $fileName ($backendName): ${validationResult.output}" }
                        throw GoldenTestException(
                            fileName, backendName, "native-validation",
                            "Validation failed: ${validationResult.output}"
                        )
                    } else {
                        logger.debug { "Native validation SUCCESS for $fileName ($backendName)" }
                    }
                }
            }
        }
    }
})

private fun findProjectRoot(): java.nio.file.Path {
    var current = Paths.get(".").toAbsolutePath()
    while (current != null) {
        if (Files.exists(current.resolve("settings.gradle.kts"))) {
            return current
        }
        current = current.parent
    }
    return Paths.get(".")
}

private fun getExtension(backend: String): String = when (backend.lowercase()) {
    "msl" -> ".metal"
    "hlsl" -> ".hlsl"
    "glsl" -> ".glsl"
    "wgsl" -> ".wgsl"
    "ir" -> ".json"
    else -> ".txt"
}

private fun assertModulesEquivalent(original: Module, roundtrip: Module) {
    original.entryPoints.size shouldBe roundtrip.entryPoints.size
    val epOriginalMap = original.entryPoints.associateBy { it.name }
    val epRoundtripMap = roundtrip.entryPoints.associateBy { it.name }
    epOriginalMap.keys shouldBe epRoundtripMap.keys

    val gOriginalMap = original.globalVariables.toList().associateBy { it.name }
    val gRoundtripMap = roundtrip.globalVariables.toList().associateBy { it.name }
    gOriginalMap.keys shouldBe gRoundtripMap.keys

    val fOriginalMap = original.functions.toList().associateBy { it.name }
    val fRoundtripMap = roundtrip.functions.toList().associateBy { it.name }
    fOriginalMap.keys shouldBe fRoundtripMap.keys

    for (name in fOriginalMap.keys) {
        val fOrig = fOriginalMap[name]!!
        val fRound = fRoundtripMap[name]!!

        fOrig.parameters.size shouldBe fRound.parameters.size
        fOrig.parameters.forEachIndexed { i, pOrig ->
            val pRound = fRound.parameters[i]
            pOrig.name shouldBe pRound.name
            assertTypesEquivalent(pOrig.type, original, pRound.type, roundtrip)
        }

        val origRet = fOrig.returnType
        val roundRet = fRound.returnType
        if (origRet == null) {
            roundRet shouldBe null
        } else {
            roundRet shouldNotBe null
            assertTypesEquivalent(origRet, original, roundRet!!, roundtrip)
        }

        val lvOrig = fOrig.localVariables.toList().associateBy { it.name }
        val lvRound = fRound.localVariables.toList().associateBy { it.name }
        lvOrig.keys shouldBe lvRound.keys
        for (lvName in lvOrig.keys) {
            assertTypesEquivalent(lvOrig[lvName]!!.type, original, lvRound[lvName]!!.type, roundtrip)
        }

        assertBlocksEquivalent(
            fOrig.body, fOrig, original,
            fRound.body, fRound, roundtrip
        )
    }
}

private fun assertBlocksEquivalent(
    origBlockHandle: Handle<Block>, origFunc: Function, origMod: Module,
    roundBlockHandle: Handle<Block>, roundFunc: Function, roundMod: Module
) {
    val origBlock = origFunc.blocks[origBlockHandle]
    val roundBlock = roundFunc.blocks[roundBlockHandle]

    val origStmts = origBlock.statements.filter { it !is Statement.Nop }
    val roundStmts = roundBlock.statements.filter { it !is Statement.Nop }

    origStmts.size shouldBe roundStmts.size
    origStmts.forEachIndexed { i, origStmt ->
        val roundStmt = roundStmts[i]
        assertStatementsEquivalent(origStmt, origFunc, origMod, roundStmt, roundFunc, roundMod)
    }
}

private fun assertStatementsEquivalent(
    origStmt: Statement, origFunc: Function, origMod: Module,
    roundStmt: Statement, roundFunc: Function, roundMod: Module
) {
    origStmt::class shouldBe roundStmt::class

    when (origStmt) {
        is Statement.Block -> {
            val roundBlock = roundStmt as Statement.Block
            assertBlocksEquivalent(origStmt.block, origFunc, origMod, roundBlock.block, roundFunc, roundMod)
        }
        is Statement.Declare -> {
            val roundDecl = roundStmt as Statement.Declare
            val varOrig = origFunc.localVariables[origStmt.variable]
            val varRound = roundFunc.localVariables[roundDecl.variable]
            varOrig.name shouldBe varRound.name
        }
        is Statement.Init -> {
            val roundInit = roundStmt as Statement.Init
            val varOrig = origFunc.localVariables[origStmt.variable]
            val varRound = roundFunc.localVariables[roundInit.variable]
            varOrig.name shouldBe varRound.name
            val initOrig = varOrig.init!!
            val initRound = varRound.init!!
            assertExpressionsEquivalent(initOrig, origFunc, origMod, initRound, roundFunc, roundMod)
        }
        is Statement.Assign -> {
            val roundAssign = roundStmt as Statement.Assign
            assertExpressionsEquivalent(origStmt.pointer, origFunc, origMod, roundAssign.pointer, roundFunc, roundMod)
            assertExpressionsEquivalent(origStmt.value, origFunc, origMod, roundAssign.value, roundFunc, roundMod)
        }
        is Statement.If -> {
            val roundIf = roundStmt as Statement.If
            assertExpressionsEquivalent(origStmt.condition, origFunc, origMod, roundIf.condition, roundFunc, roundMod)
            assertBlocksEquivalent(origStmt.accept, origFunc, origMod, roundIf.accept, roundFunc, roundMod)
            val origReject = origStmt.reject
            val roundReject = roundIf.reject
            if (origReject == null) {
                roundReject shouldBe null
            } else {
                roundReject shouldNotBe null
                assertBlocksEquivalent(origReject, origFunc, origMod, roundReject!!, roundFunc, roundMod)
            }
        }
        is Statement.Switch -> {
            val roundSwitch = roundStmt as Statement.Switch
            assertExpressionsEquivalent(origStmt.selector, origFunc, origMod, roundSwitch.selector, roundFunc, roundMod)
            assertBlocksEquivalent(origStmt.body, origFunc, origMod, roundSwitch.body, roundFunc, roundMod)
            val origDefault = origStmt.default
            val roundDefault = roundSwitch.default
            if (origDefault == null) {
                roundDefault shouldBe null
            } else {
                roundDefault shouldNotBe null
                assertBlocksEquivalent(origDefault, origFunc, origMod, roundDefault!!, roundFunc, roundMod)
            }
            origStmt.cases.size shouldBe roundSwitch.cases.size
            origStmt.cases.forEachIndexed { i, origCase ->
                val roundCase = roundSwitch.cases[i]
                origCase.selector::class shouldBe roundCase.selector::class
                if (origCase.selector is CaseSelector.Value) {
                    val valOrig = (origCase.selector as CaseSelector.Value).value
                    val valRound = (roundCase.selector as CaseSelector.Value).value
                    valOrig shouldBe valRound
                }
                assertBlocksEquivalent(origCase.body, origFunc, origMod, roundCase.body, roundFunc, roundMod)
            }
        }
        is Statement.Loop -> {
            val roundLoop = roundStmt as Statement.Loop
            assertBlocksEquivalent(origStmt.body, origFunc, origMod, roundLoop.body, roundFunc, roundMod)
            val origContinuing = origStmt.continuing
            val roundContinuing = roundLoop.continuing
            if (origContinuing == null) {
                roundContinuing shouldBe null
            } else {
                roundContinuing shouldNotBe null
                assertBlocksEquivalent(origContinuing, origFunc, origMod, roundContinuing!!, roundFunc, roundMod)
            }
        }
        is Statement.Return -> {
            val roundReturn = roundStmt as Statement.Return
            val origVal = origStmt.value
            val roundVal = roundReturn.value
            if (origVal == null) {
                roundVal shouldBe null
            } else {
                roundVal shouldNotBe null
                assertExpressionsEquivalent(origVal, origFunc, origMod, roundVal!!, roundFunc, roundMod)
            }
        }
        is Statement.Break -> {}
        is Statement.Continue -> {}
        is Statement.Kill -> {}
        is Statement.Discard -> {}
        is Statement.Nop -> {}
        is Statement.Emit -> {}
        else -> {
            origStmt.toString() shouldBe roundStmt.toString()
        }
    }
}

private fun assertExpressionsEquivalent(
    origExprHandle: Handle<Expression>, origFunc: Function, origMod: Module,
    roundExprHandle: Handle<Expression>, roundFunc: Function, roundMod: Module
) {
    val origExpr = origFunc.expressions[origExprHandle]
    val roundExpr = roundFunc.expressions[roundExprHandle]

    origExpr.kind::class shouldBe roundExpr.kind::class

    when (val origKind = origExpr.kind) {
        is ExpressionKind.Literal -> {
            val roundKind = roundExpr.kind as ExpressionKind.Literal
            origKind.value shouldBe roundKind.value
        }
        is ExpressionKind.GlobalVar -> {
            val roundKind = roundExpr.kind as ExpressionKind.GlobalVar
            val varOrig = origMod.globalVariables[origKind.handle]
            val varRound = roundMod.globalVariables[roundKind.handle]
            varOrig.name shouldBe varRound.name
        }
        is ExpressionKind.LocalVar -> {
            val roundKind = roundExpr.kind as ExpressionKind.LocalVar
            val varOrig = origFunc.localVariables[origKind.handle]
            val varRound = roundFunc.localVariables[roundKind.handle]
            varOrig.name shouldBe varRound.name
        }
        is ExpressionKind.FunctionArgument -> {
            val roundKind = roundExpr.kind as ExpressionKind.FunctionArgument
            origKind.index shouldBe roundKind.index
        }
        is ExpressionKind.ConstantExpr -> {
            val roundKind = roundExpr.kind as ExpressionKind.ConstantExpr
            assertConstantsEquivalent(origKind.handle, origMod, roundKind.handle, roundMod)
        }
        is ExpressionKind.Binary -> {
            val roundKind = roundExpr.kind as ExpressionKind.Binary
            origKind.operator shouldBe roundKind.operator
            assertExpressionsEquivalent(origKind.left, origFunc, origMod, roundKind.left, roundFunc, roundMod)
            assertExpressionsEquivalent(origKind.right, origFunc, origMod, roundKind.right, roundFunc, roundMod)
        }
        is ExpressionKind.Unary -> {
            val roundKind = roundExpr.kind as ExpressionKind.Unary
            origKind.operator shouldBe roundKind.operator
            assertExpressionsEquivalent(origKind.expr, origFunc, origMod, roundKind.expr, roundFunc, roundMod)
        }
        is ExpressionKind.Select -> {
            val roundKind = roundExpr.kind as ExpressionKind.Select
            assertExpressionsEquivalent(origKind.condition, origFunc, origMod, roundKind.condition, roundFunc, roundMod)
            assertExpressionsEquivalent(origKind.accept, origFunc, origMod, roundKind.accept, roundFunc, roundMod)
            assertExpressionsEquivalent(origKind.reject, origFunc, origMod, roundKind.reject, roundFunc, roundMod)
        }
        is ExpressionKind.Call -> {
            val roundKind = roundExpr.kind as ExpressionKind.Call
            val funcOrig = origMod.functions[origKind.function]
            val funcRound = roundMod.functions[roundKind.function]
            funcOrig.name shouldBe funcRound.name
            origKind.arguments.size shouldBe roundKind.arguments.size
            origKind.arguments.forEachIndexed { i, argOrig ->
                assertExpressionsEquivalent(argOrig, origFunc, origMod, roundKind.arguments[i], roundFunc, roundMod)
            }
        }
        is ExpressionKind.BuiltinCall -> {
            val roundKind = roundExpr.kind as ExpressionKind.BuiltinCall
            origKind.function shouldBe roundKind.function
            origKind.arguments.size shouldBe roundKind.arguments.size
            origKind.arguments.forEachIndexed { i, argOrig ->
                assertExpressionsEquivalent(argOrig, origFunc, origMod, roundKind.arguments[i], roundFunc, roundMod)
            }
        }
        is ExpressionKind.AccessIndex -> {
            val roundKind = roundExpr.kind as ExpressionKind.AccessIndex
            origKind.index shouldBe roundKind.index
            assertExpressionsEquivalent(origKind.expr, origFunc, origMod, roundKind.expr, roundFunc, roundMod)
        }
        is ExpressionKind.Access -> {
            val roundKind = roundExpr.kind as ExpressionKind.Access
            assertExpressionsEquivalent(origKind.expr, origFunc, origMod, roundKind.expr, roundFunc, roundMod)
            assertExpressionsEquivalent(origKind.index, origFunc, origMod, roundKind.index, roundFunc, roundMod)
        }
        is ExpressionKind.Swizzle -> {
            val roundKind = roundExpr.kind as ExpressionKind.Swizzle
            origKind.size shouldBe roundKind.size
            origKind.pattern shouldBe roundKind.pattern
            assertExpressionsEquivalent(origKind.vector, origFunc, origMod, roundKind.vector, roundFunc, roundMod)
        }
        is ExpressionKind.Splat -> {
            val roundKind = roundExpr.kind as ExpressionKind.Splat
            origKind.size shouldBe roundKind.size
            assertExpressionsEquivalent(origKind.value, origFunc, origMod, roundKind.value, roundFunc, roundMod)
        }
        is ExpressionKind.Load -> {
            val roundKind = roundExpr.kind as ExpressionKind.Load
            assertExpressionsEquivalent(origKind.pointer, origFunc, origMod, roundKind.pointer, roundFunc, roundMod)
        }
        is ExpressionKind.Store -> {
            val roundKind = roundExpr.kind as ExpressionKind.Store
            assertExpressionsEquivalent(origKind.pointer, origFunc, origMod, roundKind.pointer, roundFunc, roundMod)
            assertExpressionsEquivalent(origKind.value, origFunc, origMod, roundKind.value, roundFunc, roundMod)
        }
        is ExpressionKind.As -> {
            val roundKind = roundExpr.kind as ExpressionKind.As
            assertExpressionsEquivalent(origKind.expr, origFunc, origMod, roundKind.expr, roundFunc, roundMod)
            assertTypesEquivalent(origKind.target, origMod, roundKind.target, roundMod)
        }
        is ExpressionKind.TypeConstructor -> {
            val roundKind = roundExpr.kind as ExpressionKind.TypeConstructor
            assertTypesEquivalent(origKind.type, origMod, roundKind.type, roundMod)
            origKind.arguments.size shouldBe roundKind.arguments.size
            origKind.arguments.forEachIndexed { i, argOrig ->
                assertExpressionsEquivalent(argOrig, origFunc, origMod, roundKind.arguments[i], roundFunc, roundMod)
            }
        }
        is ExpressionKind.ArrayLength -> {
            val roundKind = roundExpr.kind as ExpressionKind.ArrayLength
            assertExpressionsEquivalent(origKind.expr, origFunc, origMod, roundKind.expr, roundFunc, roundMod)
        }
        is ExpressionKind.Sample -> {
            val roundKind = roundExpr.kind as ExpressionKind.Sample
            assertExpressionsEquivalent(origKind.texture, origFunc, origMod, roundKind.texture, roundFunc, roundMod)
            val origSampler = origKind.sampler
            val roundSampler = roundKind.sampler
            if (origSampler == null) {
                roundSampler shouldBe null
            } else {
                roundSampler shouldNotBe null
                assertExpressionsEquivalent(origSampler, origFunc, origMod, roundSampler!!, roundFunc, roundMod)
            }
            assertExpressionsEquivalent(origKind.coordinate, origFunc, origMod, roundKind.coordinate, roundFunc, roundMod)
            origKind.level shouldBe roundKind.level
            val origDepth = origKind.depthRef
            val roundDepth = roundKind.depthRef
            if (origDepth == null) {
                roundDepth shouldBe null
            } else {
                roundDepth shouldNotBe null
                assertExpressionsEquivalent(origDepth, origFunc, origMod, roundDepth!!, roundFunc, roundMod)
            }
        }
        is ExpressionKind.TextureQuery -> {
            val roundKind = roundExpr.kind as ExpressionKind.TextureQuery
            origKind.query shouldBe roundKind.query
            assertExpressionsEquivalent(origKind.texture, origFunc, origMod, roundKind.texture, roundFunc, roundMod)
        }
        is ExpressionKind.Atomic -> {
            val roundKind = roundExpr.kind as ExpressionKind.Atomic
            origKind.fun_ shouldBe roundKind.fun_
            assertExpressionsEquivalent(origKind.pointer, origFunc, origMod, roundKind.pointer, roundFunc, roundMod)
            origKind.arguments.size shouldBe roundKind.arguments.size
            origKind.arguments.forEachIndexed { i, argOrig ->
                assertExpressionsEquivalent(argOrig, origFunc, origMod, roundKind.arguments[i], roundFunc, roundMod)
            }
        }
        is ExpressionKind.Relational -> {
            val roundKind = roundExpr.kind as ExpressionKind.Relational
            origKind.fun_ shouldBe roundKind.fun_
            origKind.arguments.size shouldBe roundKind.arguments.size
            origKind.arguments.forEachIndexed { i, argOrig ->
                assertExpressionsEquivalent(argOrig, origFunc, origMod, roundKind.arguments[i], roundFunc, roundMod)
            }
        }
        is ExpressionKind.Bitcast -> {
            val roundKind = roundExpr.kind as ExpressionKind.Bitcast
            assertExpressionsEquivalent(origKind.expr, origFunc, origMod, roundKind.expr, roundFunc, roundMod)
        }
        is ExpressionKind.ValuePointer -> {
            val roundKind = roundExpr.kind as ExpressionKind.ValuePointer
            assertExpressionsEquivalent(origKind.base, origFunc, origMod, roundKind.base, roundFunc, roundMod)
        }
        else -> {
            origKind.toString() shouldBe roundExpr.kind.toString()
        }
    }
}

private fun assertConstantsEquivalent(
    constOrigHandle: Handle<Constant>, origMod: Module,
    constRoundHandle: Handle<Constant>, roundMod: Module
) {
    val constOrig = origMod.constants[constOrigHandle]
    val constRound = roundMod.constants[constRoundHandle]
    assertTypesEquivalent(constOrig.type, origMod, constRound.type, roundMod)
    constOrig.specialization shouldBe constRound.specialization
    assertConstantInnersEquivalent(constOrig.inner, origMod, constRound.inner, roundMod)
}

private fun assertConstantInnersEquivalent(
    origInner: ConstantInner, origMod: Module,
    roundInner: ConstantInner, roundMod: Module
) {
    origInner::class shouldBe roundInner::class
    when (origInner) {
        is ConstantInner.Scalar -> {
            val roundScalar = roundInner as ConstantInner.Scalar
            origInner.value shouldBe roundScalar.value
        }
        is ConstantInner.Vector -> {
            val roundVector = roundInner as ConstantInner.Vector
            origInner.components shouldBe roundVector.components
        }
        is ConstantInner.Matrix -> {
            val roundMatrix = roundInner as ConstantInner.Matrix
            origInner.columns shouldBe roundMatrix.columns
        }
        is ConstantInner.Zero -> {
            val roundZero = roundInner as ConstantInner.Zero
            assertTypesEquivalent(origInner.type, origMod, roundZero.type, roundMod)
        }
        is ConstantInner.Composite -> {
            val roundComposite = roundInner as ConstantInner.Composite
            assertTypesEquivalent(origInner.type, origMod, roundComposite.type, roundMod)
            origInner.components.size shouldBe roundComposite.components.size
            origInner.components.forEachIndexed { i, cOrig ->
                assertConstantsEquivalent(cOrig, origMod, roundComposite.components[i], roundMod)
            }
        }
        is ConstantInner.Expression -> {
            val roundExpr = roundInner as ConstantInner.Expression
            assertGlobalExpressionsEquivalent(origInner.expr, origMod, roundExpr.expr, roundMod)
        }
        else -> {
            origInner.toString() shouldBe roundInner.toString()
        }
    }
}

private fun assertGlobalExpressionsEquivalent(
    origExprHandle: Handle<Expression>, origMod: Module,
    roundExprHandle: Handle<Expression>, roundMod: Module
) {
    val origExpr = origMod.globalExpressions.getOrNull(origExprHandle)
    val roundExpr = roundMod.globalExpressions.getOrNull(roundExprHandle)

    if (origExpr == null || roundExpr == null) {
        origExpr shouldBe roundExpr
        return
    }

    origExpr.kind::class shouldBe roundExpr.kind::class
    when (val origKind = origExpr.kind) {
        is ExpressionKind.Literal -> {
            val roundKind = roundExpr.kind as ExpressionKind.Literal
            origKind.value shouldBe roundKind.value
        }
        is ExpressionKind.ConstantExpr -> {
            val roundKind = roundExpr.kind as ExpressionKind.ConstantExpr
            assertConstantsEquivalent(origKind.handle, origMod, roundKind.handle, roundMod)
        }
        else -> {
            origExpr.kind.toString() shouldBe roundExpr.kind.toString()
        }
    }
}

private fun assertArraySizesEquivalent(
    origSize: ArraySize, origMod: Module,
    roundSize: ArraySize, roundMod: Module
) {
    origSize::class shouldBe roundSize::class
    when (origSize) {
        is ArraySize.Constant -> {
            val roundConst = roundSize as ArraySize.Constant
            origSize.value shouldBe roundConst.value
        }
        is ArraySize.Dynamic -> {
            val roundDyn = roundSize as ArraySize.Dynamic
            val origIndex = origSize.expression.index
            val roundIndex = roundDyn.expression.index
            if (origIndex == 0 && roundIndex == 0 && (origMod.globalExpressions.isEmpty() || roundMod.globalExpressions.isEmpty())) {
                // both are dummy Handle(0) sizes
            } else {
                assertGlobalExpressionsEquivalent(origSize.expression, origMod, roundDyn.expression, roundMod)
            }
        }
    }
}

private fun assertTypesEquivalent(
    typeOrigHandle: Handle<Type>, origMod: Module,
    typeRoundHandle: Handle<Type>, roundMod: Module
) {
    val typeOrig = origMod.types[typeOrigHandle]
    val typeRound = roundMod.types[typeRoundHandle]

    typeOrig.inner::class shouldBe typeRound.inner::class

    when (val innerOrig = typeOrig.inner) {
        is TypeInner.Scalar -> {
            val innerRound = typeRound.inner as TypeInner.Scalar
            innerOrig shouldBe innerRound
        }
        is TypeInner.Vector -> {
            val innerRound = typeRound.inner as TypeInner.Vector
            innerOrig.size shouldBe innerRound.size
            assertTypesEquivalent(innerOrig.scalar, origMod, innerRound.scalar, roundMod)
        }
        is TypeInner.Matrix -> {
            val innerRound = typeRound.inner as TypeInner.Matrix
            innerOrig.columns shouldBe innerRound.columns
            innerOrig.rows shouldBe innerRound.rows
            assertTypesEquivalent(innerOrig.scalar, origMod, innerRound.scalar, roundMod)
        }
        is TypeInner.Struct -> {
            val innerRound = typeRound.inner as TypeInner.Struct
            innerOrig.members.size shouldBe innerRound.members.size
            innerOrig.members.forEachIndexed { i, mOrig ->
                val mRound = innerRound.members[i]
                mOrig.name shouldBe mRound.name
                assertTypesEquivalent(mOrig.type, origMod, mRound.type, roundMod)
                mOrig.binding shouldBe mRound.binding
            }
        }
        is TypeInner.Pointer -> {
            val innerRound = typeRound.inner as TypeInner.Pointer
            innerOrig.addressSpace shouldBe innerRound.addressSpace
            innerOrig.accessMode shouldBe innerRound.accessMode
            assertTypesEquivalent(innerOrig.base, origMod, innerRound.base, roundMod)
        }
        is TypeInner.ValuePointer -> {
            val innerRound = typeRound.inner as TypeInner.ValuePointer
            assertTypesEquivalent(innerOrig.base, origMod, innerRound.base, roundMod)
        }
        is TypeInner.Array -> {
            val innerRound = typeRound.inner as TypeInner.Array
            assertTypesEquivalent(innerOrig.element, origMod, innerRound.element, roundMod)
            assertArraySizesEquivalent(innerOrig.size, origMod, innerRound.size, roundMod)
        }
        is TypeInner.Opaque -> {
            val innerRound = typeRound.inner as TypeInner.Opaque
            innerOrig.name shouldBe innerRound.name
        }
        is TypeInner.Abstract -> {
            val innerRound = typeRound.inner as TypeInner.Abstract
            innerOrig.scalar shouldBe innerRound.scalar
        }
        TypeInner.Error -> {}
        else -> {
            innerOrig.toString() shouldBe typeRound.inner.toString()
        }
    }
}
