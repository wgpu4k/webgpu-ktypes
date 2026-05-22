package io.ygdrasil.wgsl.parser

import io.ygdrasil.wgsl.ir.Span

/**
 * Formats diagnostics for human-readable output.
 * 
 * This class provides methods for formatting error messages with
 * source context, line numbers, and caret indicators.
 */
class PrettyPrintError {

    companion object {
        /** Default number of context lines to show before and after the error. */
        private const val DEFAULT_CONTEXT_LINES = 2

        /** Maximum line length for source display. */
        private const val MAX_LINE_LENGTH = 120
    }

    /**
     * Source code for formatting.
     */
    data class SourceInfo(
        val source: String,
        val lines: List<String>
    ) {
        constructor(source: String) : this(source, source.split("\n"))

        fun getLine(lineNumber: Int): String? {
            if (lineNumber >= 0 && lineNumber < lines.size) {
                return lines[lineNumber]
            }
            return null
        }
    }

    // ========== Main Formatting Methods ==========

    /**
     * Format a single diagnostic with source context.
     * 
     * @param diagnostic The diagnostic to format
     * @param source The source code
     * @param contextLines Number of context lines to show before and after
     * @return Formatted string
     */
    @Suppress("UNNECESSARY_SAFE_CALL", "CONSTANT_CONDITION")
    fun formatDiagnostic(
        diagnostic: Diagnostic,
        source: String,
        contextLines: Int = DEFAULT_CONTEXT_LINES
    ): String {
        val span = diagnostic.span
        val location = span.location(source)

        // Build the output
        val output = StringBuilder()

        // Add severity and code
        val severityStr = when (diagnostic.severity) {
            DiagnosticSeverity.ERROR -> "error"
            DiagnosticSeverity.WARNING -> "warning"
            DiagnosticSeverity.INFO -> "info"
            DiagnosticSeverity.DEBUG -> "debug"
        }

        output.appendLine("${severityStr.uppercase()}: ${diagnostic.code.code}: ${diagnostic.message}")

        // Add location and source context with caret
        location?.let { loc ->
            output.appendLine("   --> line ${loc.lineNumber}:${loc.linePosition}")
            val lineNumber = loc.lineNumber.toInt() - 1  // Convert to 0-based
            val column = loc.linePosition.toInt() - 1  // Convert to 0-based
            val endOffset = span.end.toInt()
            val startOffset = span.start.toInt()

            // Find the line in the source
            val lines = source.split("\n")
            val startLine = minOf(lineNumber, lines.size - 1)
            val endLine = minOf(startLine + contextLines * 2, lines.size - 1)

            // Calculate the width needed for line numbers
            val lineNumberWidth = (endLine + 1).toString().length

            // Calculate the start and end columns in the line
            val line = lines.getOrNull(startLine) ?: ""
            val lineStartOffset = getLineStartOffset(lines, startLine)
            val startCol = startOffset - lineStartOffset
            val endCol = endOffset - lineStartOffset

            // Display context lines
            for (lineNum in maxOf(0, startLine - contextLines)..minOf(lines.size - 1, startLine + contextLines)) {
                val currentLine = lines[lineNum]
                val displayLineNum = lineNum + 1
                val paddedLineNum = displayLineNum.toString().padStart(lineNumberWidth)

                // Truncate long lines
                val displayLine = if (currentLine.length > MAX_LINE_LENGTH) {
                    currentLine.substring(0, MAX_LINE_LENGTH - 3) + "..."
                } else {
                    currentLine
                }

                output.appendLine("   | ${paddedLineNum} | ${displayLine}")

                // Add caret for the error line
                if (lineNum == startLine) {
                    val caretLine = buildCaretLine(
                        currentLine.length,
                        startCol,
                        endCol,
                        lineNumberWidth
                    )
                    output.appendLine("   | ${caretLine}")
                }
            }
        }

        // Add suggestions
        if (diagnostic.suggestions.isNotEmpty()) {
            output.appendLine()
            output.appendLine("   note: ${diagnostic.suggestions.joinToString(" or ")}")
        }

        return output.toString()
    }

    /**
     * Format multiple diagnostics.
     * 
     * @param diagnostics The diagnostics to format
     * @param source The source code
     * @param contextLines Number of context lines to show
     * @return Formatted string
     */
    fun formatDiagnostics(
        diagnostics: List<Diagnostic>,
        source: String,
        contextLines: Int = DEFAULT_CONTEXT_LINES
    ): String {
        if (diagnostics.isEmpty()) {
            return ""
        }

        val output = StringBuilder()

        for ((index, diagnostic) in diagnostics.withIndex()) {
            output.appendLine(formatDiagnostic(diagnostic, source, contextLines))
            if (index < diagnostics.size - 1) {
                output.appendLine()
            }
        }

        // Add summary
        val errorCount = diagnostics.count { it.isError }
        val warningCount = diagnostics.count { it.isWarning }

        if (errorCount > 0 || warningCount > 0) {
            output.appendLine()
            output.appendLine("Found $errorCount error${if (errorCount != 1) "s" else ""} and $warningCount warning${if (warningCount != 1) "s" else ""}")
        }

        return output.toString().trimEnd()
    }

    /**
     * Format a diagnostic collection.
     */
    fun formatDiagnosticCollection(
        collection: DiagnosticCollection,
        source: String,
        contextLines: Int = DEFAULT_CONTEXT_LINES
    ): String {
        return formatDiagnostics(collection.getAll(), source, contextLines)
    }

    // ========== Helper Methods ==========

    /**
     * Build a caret line for highlighting the error span.
     * 
     * @param lineLength The length of the source line
     * @param startColumn The start column (0-based)
     * @param endColumn The end column (0-based)
     * @param lineNumberWidth The width of the line number column
     * @return The caret line string
     */
    private fun buildCaretLine(
        lineLength: Int,
        startColumn: Int,
        endColumn: Int,
        lineNumberWidth: Int
    ): String {
        val caret = StringBuilder()

        // Add padding for line number
        caret.append(" ".repeat(lineNumberWidth + 3))

        // Add spaces before the caret
        val safeStart = maxOf(0, minOf(startColumn, lineLength))
        caret.append(" ".repeat(safeStart))

        // Add carets for the error span
        val caretLength = maxOf(1, minOf(endColumn, lineLength) - safeStart)
        caret.append("^".repeat(caretLength))

        return caret.toString()
    }

    /**
     * Format a location string.
     */
    fun formatLocation(span: Span): String {
        if (span == Span.UNDEFINED) {
            return "<unknown location>"
        }

        // Without source, we can only show byte offsets
        return "bytes ${span.start}-${span.end}"
    }

    /**
     * Format source context for a span.
     */
    fun formatContext(
        span: Span,
        source: String,
        contextLines: Int = DEFAULT_CONTEXT_LINES
    ): String {
        val location = span.location(source)

        val lines = source.split("\n")
        val lineNumber = location.lineNumber.toInt() - 1

        val output = StringBuilder()
        val startLine = maxOf(0, lineNumber - contextLines)
        val endLine = minOf(lines.size - 1, lineNumber + contextLines)

        for (lineNum in startLine..endLine) {
            val line = lines[lineNum]
            val displayLineNum = lineNum + 1

            output.appendLine("${displayLineNum.toString().padStart(4)} | $line")
        }

        return output.toString()
    }

    // ========== Helper Functions ==========

    /**
     * Get the byte offset of the start of a line.
     */
    private fun getLineStartOffset(lines: List<String>, lineNumber: Int): Int {
        var offset = 0
        for (i in 0 until lineNumber) {
            offset += lines[i].length + 1 // +1 for newline
        }
        return offset
    }
}

/**
 * Formatter for diagnostics with consistent styling.
 */
interface DiagnosticFormatter {
    fun format(diagnostic: Diagnostic, source: String): String
    fun formatAll(diagnostics: List<Diagnostic>, source: String): String
}

/**
 * Default diagnostic formatter.
 */
class DefaultDiagnosticFormatter : DiagnosticFormatter {
    private val prettyPrinter = PrettyPrintError()

    override fun format(diagnostic: Diagnostic, source: String): String {
        return prettyPrinter.formatDiagnostic(diagnostic, source)
    }

    override fun formatAll(diagnostics: List<Diagnostic>, source: String): String {
        return prettyPrinter.formatDiagnostics(diagnostics, source)
    }
}

/**
 * Compact diagnostic formatter (one line per diagnostic).
 */
class CompactDiagnosticFormatter : DiagnosticFormatter {
    override fun format(diagnostic: Diagnostic, source: String): String {
        val severity = when (diagnostic.severity) {
            DiagnosticSeverity.ERROR -> "error"
            DiagnosticSeverity.WARNING -> "warning"
            else -> "note"
        }
        val location = PrettyPrintError().formatLocation(diagnostic.span)
        return "[$severity] ${diagnostic.code.code} at $location: ${diagnostic.message}"
    }

    override fun formatAll(diagnostics: List<Diagnostic>, source: String): String {
        return diagnostics.joinToString("\n") { format(it, source) }
    }
}
