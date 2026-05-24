package io.ygdrasil.wgsl.cli

import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.core.CliktError
import com.github.ajalt.clikt.core.parse
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.io.path.createTempFile

class ConvertCommandTest : FunSpec({
    test("convert reports parse errors with non-zero exit status") {
        val input = createTempFile(suffix = ".wgsl").toFile()
        input.writeText("@location(0)")

        val error = shouldThrow<CliktError> {
            WgslKTypes()
                .subcommands(ConvertCommand())
                .parse(listOf("convert", input.absolutePath, "--format", "wgsl"))
        }

        error.statusCode shouldBe 1
        error.message shouldBe "Parsing failed"
    }
})
