package io.ygdrasil.wgsl.parser

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class ComplianceCorpusTest : FunSpec({
    context("parse corpus") {
        parseCases.forEach { case ->
            test(case.name) {
                val result = parseWgslResult(case.source)
                result.isSuccess shouldBe case.expectSuccess
            }
        }
    }

    context("resolve corpus") {
        resolveCases.forEach { case ->
            test(case.name) {
                val parseResult = parseWgslResult(case.source)
                parseResult.isSuccess shouldBe true

                val result = TypeResolver().resolve(parseResult.translationUnit)
                result.isSuccess shouldBe case.expectSuccess
            }
        }
    }

    context("lowering corpus") {
        loweringCases.forEach { case ->
            test(case.name) {
                val unit = parseWgslResult(case.source).translationUnit
                if (case.expectSuccess) {
                    val module = shouldNotThrowAny {
                        Lowerer().lower(unit)
                    }
                    module.functions.toList().firstOrNull() shouldNotBe null
                    module.types.toList().firstOrNull() shouldNotBe null
                } else {
                    shouldThrowAny {
                        Lowerer().lower(unit)
                    }
                }
            }
        }
    }
})

private data class ComplianceCase(
    val name: String,
    val source: String,
    val expectSuccess: Boolean,
)

private val parseCases = listOf(
    ComplianceCase(
        name = "valid function declaration",
        source = "fn main() {}",
        expectSuccess = true,
    ),
    ComplianceCase(
        name = "valid struct and alias declarations",
        source = """
            struct Vertex { position: vec4<f32> }
            alias Position = vec4<f32>;
        """.trimIndent(),
        expectSuccess = true,
    ),
    ComplianceCase(
        name = "invalid orphan attribute",
        source = "@location(0)",
        expectSuccess = false,
    ),
)

private val resolveCases = listOf(
    ComplianceCase(
        name = "valid forward type reference",
        source = """
            fn make() -> Payload { return Payload(1); }
            struct Payload { value: i32 }
        """.trimIndent(),
        expectSuccess = true,
    ),
    ComplianceCase(
        name = "invalid unknown parameter type",
        source = "fn use_missing(value: MissingType) {}",
        expectSuccess = false,
    ),
)

private val loweringCases = listOf(
    ComplianceCase(
        name = "valid scalar return lowering",
        source = "fn main() -> i32 { return 1; }",
        expectSuccess = true,
    ),
    ComplianceCase(
        name = "invalid unknown type lowering",
        source = "type Bad = definitely_unknown;",
        expectSuccess = false,
    ),
    ComplianceCase(
        name = "invalid empty array constructor lowering",
        source = "fn main() { let values = array(); }",
        expectSuccess = false,
    ),
)
