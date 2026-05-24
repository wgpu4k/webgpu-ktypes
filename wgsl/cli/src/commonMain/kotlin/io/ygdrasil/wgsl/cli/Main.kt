package io.ygdrasil.wgsl.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.CliktError
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.enum
import com.github.ajalt.clikt.parameters.types.file
import io.ygdrasil.wgsl.generator.glsl.GlslWriterFactory
import io.ygdrasil.wgsl.generator.hlsl.HlslWriterFactory
import io.ygdrasil.wgsl.generator.msl.MslWriterFactory
import io.ygdrasil.wgsl.wgsl.WgslWriterFactory

enum class OutputFormat {
    glsl, hlsl, msl, wgsl
}

class ConvertCommand : CliktCommand(name = "convert") {
    val input by argument().file(mustExist = true, canBeDir = false)
    val format by option().enum<OutputFormat>().required()
    val output by option().file(canBeDir = false)

    override fun run() {
        val source = input.readText()
        val parseResult = io.ygdrasil.wgsl.parser.parseWgslResult(source)

        if (!parseResult.isSuccess) {
            echo("Errors during parsing:", err = true)
            parseResult.errors.forEach { echo("${it.message} at ${it.span}", err = true) }
            throw CliktError("Parsing failed", statusCode = 1)
        }

        val resolver = io.ygdrasil.wgsl.parser.TypeResolver()
        val resolutionResult = resolver.resolve(parseResult.translationUnit)
        
        if (!resolutionResult.isSuccess) {
            echo("Errors during type resolution:", err = true)
            resolutionResult.unresolvedReferences.forEach { echo(it, err = true) }
            return
        }

        val lowerer = io.ygdrasil.wgsl.parser.Lowerer()
        val loweredModule = lowerer.lower(resolutionResult.resolvedUnit)

        val validator = io.ygdrasil.wgsl.proc.Validator()
        val validationErrors = validator.validate(loweredModule)
        if (validationErrors.isNotEmpty()) {
            echo("Validation errors:", err = true)
            validationErrors.forEach { echo(it.message, err = true) }
            // We continue anyway, or should we stop?
        }
        
        val writerFactory = when (format) {
            OutputFormat.glsl -> GlslWriterFactory()
            OutputFormat.hlsl -> HlslWriterFactory()
            OutputFormat.msl -> MslWriterFactory()
            OutputFormat.wgsl -> WgslWriterFactory()
        }

        val writer = writerFactory.create()
        val moduleInfo = io.ygdrasil.wgsl.valid.ModuleInfo.empty()
        val result = writer.write(loweredModule, moduleInfo)

        if (output != null) {
            output!!.writeText(result)
            echo("Written to ${output!!.path}")
        } else {
            echo(result)
        }
    }
}

class WgslKTypes : CliktCommand() {
    override fun run() = Unit
}

fun main(args: Array<String>) = WgslKTypes()
    .subcommands(ConvertCommand())
    .main(args)
