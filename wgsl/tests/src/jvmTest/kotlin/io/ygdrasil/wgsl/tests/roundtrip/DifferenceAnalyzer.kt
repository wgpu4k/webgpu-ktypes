package io.ygdrasil.wgsl.tests.roundtrip

/**
 * Analyzes differences between normalized WGSL codes.
 */
class DifferenceAnalyzer {

    data class Difference(
        val type: DifferenceType,
        val message: String,
        val expected: String? = null,
        val actual: String? = null
    )

    enum class DifferenceType {
        MISSING_DECLARATION,
        EXTRA_DECLARATION,
        SEMANTIC_MISMATCH,
        FORMATTING_ONLY
    }

    /**
     * Compare two normalized strings.
     * Returns a list of differences found.
     */
    fun analyze(expected: String, actual: String): List<Difference> {
        if (expected == actual) return emptyList()

        // For now, if they don't match exactly after normalization, it's a semantic mismatch
        // In the future, we could parse both into AST and compare ASTs
        return listOf(
            Difference(
                DifferenceType.SEMANTIC_MISMATCH,
                "Codes do not match after normalization",
                expected,
                actual
            )
        )
    }
}
