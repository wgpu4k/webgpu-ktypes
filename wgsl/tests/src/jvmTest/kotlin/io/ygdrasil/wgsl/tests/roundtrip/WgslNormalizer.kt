package io.ygdrasil.wgsl.tests.roundtrip

import java.util.regex.Pattern

object WgslNormalizer {

    private val COMMENT_PATTERN = Pattern.compile("//.*|/\\*.*?\\*/", Pattern.DOTALL)
    private val WHITESPACE_PATTERN = Pattern.compile("\\s+")
    private val NUMBER_PATTERN = Pattern.compile("(\\d+\\.\\d*|\\.\\d+)([fFhH]?)")

    /**
     * Normalizes WGSL code for semantic comparison.
     */
    fun normalize(code: String): String {
        var normalized = code

        // 1. Remove comments
        normalized = COMMENT_PATTERN.matcher(normalized).replaceAll("")

        // 2. Normalize whitespace (replace any sequence of whitespace with a single space)
        normalized = WHITESPACE_PATTERN.matcher(normalized).replaceAll(" ").trim()

        // 3. Normalize numbers (e.g., 1.0 -> 1. , 1.0f -> 1.f)
        // This is tricky but let's try a simple approach first
        normalized = normalizeNumbers(normalized)

        // 4. Normalize separators around punctuation
        normalized = normalized
            .replace(" {", "{")
            .replace("{ ", "{")
            .replace(" }", "}")
            .replace("} ", "}")
            .replace(" (", "(")
            .replace("( ", "(")
            .replace(" )", ")")
            .replace(") ", ")")
            .replace(" [", "[")
            .replace("[ ", "[")
            .replace(" ]", "]")
            .replace("] ", "]")
            .replace(" ,", ",")
            .replace(", ", ",")
            .replace(" :", ":")
            .replace(": ", ":")
            .replace(" ;", ";")
            .replace("; ", ";")

        return normalized
    }

    private fun normalizeNumbers(code: String): String {
        val matcher = NUMBER_PATTERN.matcher(code)
        val sb = StringBuilder()
        while (matcher.find()) {
            val numStr = matcher.group(1)
            val suffix = matcher.group(2)
            
            // Normalize "1.0" to "1."
            var normalizedNum = numStr
            if (normalizedNum.contains(".") && normalizedNum.endsWith("0") && !normalizedNum.endsWith(".0")) {
                 // Might be risky, let's just strip trailing zeros after decimal point
                 while (normalizedNum.endsWith("0") && normalizedNum.contains(".") && !normalizedNum.endsWith(".")) {
                     normalizedNum = normalizedNum.substring(0, normalizedNum.length - 1)
                 }
            }
            if (normalizedNum.endsWith(".0")) {
                normalizedNum = normalizedNum.substring(0, normalizedNum.length - 1)
            }

            matcher.appendReplacement(sb, normalizedNum + suffix)
        }
        matcher.appendTail(sb)
        return sb.toString()
    }
}
