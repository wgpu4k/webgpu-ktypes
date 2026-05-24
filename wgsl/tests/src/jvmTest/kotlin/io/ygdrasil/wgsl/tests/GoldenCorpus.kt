package io.ygdrasil.wgsl.tests

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.extension
import kotlin.io.path.nameWithoutExtension

data class GoldenBackend(
    val name: String,
    val extension: String,
)

object GoldenCorpus {
    val backends = listOf(
        GoldenBackend("wgsl", "wgsl"),
        GoldenBackend("glsl", "glsl"),
        GoldenBackend("hlsl", "hlsl"),
        GoldenBackend("msl", "metal"),
        GoldenBackend("ir", "json"),
    )

    val suiteNames = backends.map { it.name }.toSet() + "roundtrip"

    fun findProjectRoot(): Path {
        var current = Paths.get(".").toAbsolutePath()
        while (current != null) {
            if (Files.exists(current.resolve("settings.gradle.kts"))) {
                return current
            }
            current = current.parent
        }
        return Paths.get(".").toAbsolutePath()
    }

    fun inputNames(rootDir: Path = findProjectRoot()): Set<String> {
        return Files.list(rootDir.resolve("tests/golden/inputs")).use { paths ->
            paths
                .filter { it.extension == "wgsl" }
                .map { it.nameWithoutExtension }
                .toList()
                .toSortedSet()
        }
    }

    fun inputFileNames(rootDir: Path = findProjectRoot()): Set<String> {
        return Files.list(rootDir.resolve("tests/golden/inputs")).use { paths ->
            paths
                .filter { it.extension == "wgsl" }
                .map { it.fileName.toString() }
                .toList()
                .toSortedSet()
        }
    }

    fun outputNames(backend: GoldenBackend, rootDir: Path = findProjectRoot()): Set<String> {
        val outputDir = rootDir.resolve("tests/golden/outputs").resolve(backend.name)
        if (!Files.exists(outputDir)) return emptySet()

        return Files.list(outputDir).use { paths ->
            paths
                .filter { it.extension == backend.extension }
                .map { it.nameWithoutExtension }
                .toList()
                .toSortedSet()
        }
    }

    fun missingOutputs(backend: GoldenBackend, rootDir: Path = findProjectRoot()): Set<String> {
        return inputNames(rootDir) - outputNames(backend, rootDir)
    }

    fun orphanOutputs(backend: GoldenBackend, rootDir: Path = findProjectRoot()): Set<String> {
        return outputNames(backend, rootDir) - inputNames(rootDir)
    }

    fun documentedMissingOutputs(rootDir: Path = findProjectRoot()): Set<String> {
        val document = rootDir.resolve("wgsl/tests/docs/golden-missing-outputs.md")
        if (!Files.exists(document)) return emptySet()

        val pattern = Regex("""^- `([^`]+)\.wgsl`""")
        return Files.readAllLines(document)
            .mapNotNull { line -> pattern.find(line)?.groupValues?.get(1) }
            .toSortedSet()
    }

    fun outputPath(rootDir: Path, backendName: String, inputFileName: String): Path {
        return outputPath(rootDir, backendName, inputFileName, getExtension(backendName))
    }

    fun outputPath(rootDir: Path, backendName: String, inputFileName: String, extension: String): Path {
        return rootDir
            .resolve("tests/golden/outputs")
            .resolve(backendName)
            .resolve(inputFileName.replace(".wgsl", ".$extension"))
    }

    fun getExtension(backend: String): String {
        return backends.firstOrNull { it.name == backend.lowercase() }?.extension ?: "txt"
    }
}
