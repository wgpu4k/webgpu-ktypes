package io.ygdrasil.wgsl.tests

import java.nio.file.Files
import java.nio.file.Path

data class GoldenExpectedFailure(
    val suite: String,
    val input: String,
    val phase: String,
)

class GoldenExpectedFailures private constructor(
    private val failures: Map<Pair<String, String>, GoldenExpectedFailure>,
) {
    val size: Int
        get() = failures.size

    fun find(suite: String, input: String): GoldenExpectedFailure? {
        return failures[suite to input]
    }

    fun verify(
        suite: String,
        input: String,
        failure: Throwable?,
        skippedPhases: Set<String> = emptySet(),
    ) {
        val expected = find(suite, input)
        if (expected == null) {
            if (failure != null) throw failure
            return
        }

        if (failure == null) {
            if (expected.phase in skippedPhases) return

            throw GoldenTestException(
                input,
                suite,
                "unexpected-pass",
                "Expected failure at phase '${expected.phase}' no longer occurs. " +
                    "Remove this case from golden-expected-failures.md after reviewing the output."
            )
        }

        val actualPhase = failure.actualGoldenPhase()
        if (actualPhase != expected.phase) {
            throw GoldenTestException(
                input,
                suite,
                "unexpected-failure",
                "Expected phase '${expected.phase}', got '$actualPhase': ${failure.message}",
                failure
            )
        }
    }

    companion object {
        val phases = setOf(
            "parse",
            "type-resolution",
            "lowering",
            "backend-lookup",
            "code-generation",
            "missing-golden",
            "comparison",
            "roundtrip-type-resolution",
            "roundtrip-semantic-isomorphism",
            "validation",
            "native-validation",
        )

        fun load(rootDir: Path = GoldenCorpus.findProjectRoot()): GoldenExpectedFailures {
            val document = rootDir.resolve("wgsl/tests/docs/golden-expected-failures.md")
            if (!Files.exists(document)) return GoldenExpectedFailures(emptyMap())

            val suiteNames = GoldenCorpus.suiteNames
            val inputNames = GoldenCorpus.inputFileNames(rootDir)
            val row = Regex("""^\| `([^`]+)` \| `([^`]+)` \| `([^`]+)` \| .+ \| .+ \|$""")
            val failures = linkedMapOf<Pair<String, String>, GoldenExpectedFailure>()

            Files.readAllLines(document).forEachIndexed { index, line ->
                val location = "${document.fileName}:${index + 1}"
                if (!line.startsWith("|")) return@forEachIndexed
                if (line.startsWith("| Suite ") || line.startsWith("|-------")) return@forEachIndexed

                val match = row.find(line)
                require(match != null) {
                    "$location contains a malformed expected-failure table row"
                }
                val suite = match.groupValues[1]
                val input = match.groupValues[2]
                val phase = match.groupValues[3]

                require(suite in suiteNames) {
                    "$location references unknown golden suite '$suite'"
                }
                require(input in inputNames) {
                    "$location references unknown golden input '$input'"
                }
                require(phase in phases) {
                    "$location references unknown golden phase '$phase'"
                }

                val key = suite to input
                require(key !in failures) {
                    "$location duplicates expected failure for $suite/$input"
                }
                failures[key] = GoldenExpectedFailure(suite, input, phase)
            }

            return GoldenExpectedFailures(failures)
        }
    }
}

private fun Throwable.actualGoldenPhase(): String {
    return when (this) {
        is GoldenTestException -> phase
        is AssertionError -> {
            if (stackTrace.any { it.methodName.startsWith("assertModulesEquivalent") }) {
                "roundtrip-semantic-isomorphism"
            } else {
                "comparison"
            }
        }
        else -> "unknown"
    }
}
