package generator.tasks

import generator.domain.MapperContext
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.createDirectories

object ModelWriter {
    private val descriptorCommonSourcePath = Paths.get("webgpu-ktypes-descriptors").resolve("src").resolve("commonMain").resolve("kotlin")
    private val commonSourcePath = Paths.get("webgpu-ktypes").resolve("src").resolve("commonMain").resolve("kotlin")
    private val commonWebSourcePath = Paths.get("webgpu-ktypes").resolve("src").resolve("webMain").resolve("kotlin")
    private val commonNativeSourcePath = Paths.get("webgpu-ktypes").resolve("src").resolve("commonNativeMain").resolve("kotlin")
    private val webSourcePath = Paths.get("webgpu-ktypes-web").resolve("src").resolve("commonMain").resolve("kotlin")

    fun write(context: MapperContext) {

        commonSourcePath.createSourceFile("bitflags.kt") {
            appendText(context.bitflagEnumerations.joinToString("\n"))
        }

        commonSourcePath.createSourceFile("enumerations.kt") {
            appendText(context.commonEnumerations.joinToString("\n"))
        }

        commonWebSourcePath.createSourceFile("enumerations.kt") {
            appendText(context.commonWebEnumerations.joinToString("\n"))
        }

        commonNativeSourcePath.createSourceFile("enumerations.kt") {
            appendText(context.commonNativeEnumerations.joinToString("\n"))
        }

        commonSourcePath.createSourceFile("typealiases.kt") {
            appendText(context.typeAliases.joinToString("\n"))
        }

        commonSourcePath.createSourceFile("interfaces.kt") {
            appendText(context.interfaces.joinToString("\n"))
        }

        descriptorCommonSourcePath.createSourceFile("descriptor.kt") {
            appendText(context.descriptors.joinToString("\n"))
        }

        webSourcePath.createWebSourceFile("types.kt") {
            appendText("import kotlin.js.ExperimentalWasmJsInterop\n")
            appendText("import kotlin.js.JsAny\n")
            appendText("import kotlin.js.Promise\n")
            appendText("\n")
            appendText(context.webTypeAlias.joinToString("\n"))
            appendText("\n")
            appendText(context.webInterfaces.joinToString("\n"))
        }
    }

    private fun Path.createSourceFile(fileName: String, block: File.() -> Unit) {
        createDirectories()
        resolve(fileName).toFile().apply {
            delete()
            createNewFile()

            appendText("@file:Suppress(\"unused\")\n")
            appendText("// This file has been generated DO NO EDIT\n")
            appendText("package io.ygdrasil.webgpu\n\n")
            block()
        }
    }

    private fun Path.createWebSourceFile(fileName: String, block: File.() -> Unit) {
        createDirectories()
        resolve(fileName).toFile().apply {
            delete()
            createNewFile()

            appendText("@file:Suppress(\"unused\")\n")
            appendText("@file:OptIn(ExperimentalWasmJsInterop::class)")
            appendText("// This file has been generated DO NO EDIT\n")
            appendText("package io.ygdrasil.webgpu\n\n")
            block()
        }
    }
}