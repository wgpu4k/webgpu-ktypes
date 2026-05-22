package io.ygdrasil.wgsl.parser

import io.ygdrasil.wgsl.ir.Span

/**
 * Severity levels for diagnostics.
 */
enum class DiagnosticSeverity {
    /** An error that prevents compilation. */
    ERROR,

    /** A warning that doesn't prevent compilation but indicates a potential issue. */
    WARNING,

    /** An informational message. */
    INFO,

    /** A debug message. */
    DEBUG
}

/**
 * Error codes for WGSL parsing and validation.
 */
enum class ErrorCode(val code: String, val description: String) {
    // Lexer errors
    UNEXPECTED_CHARACTER("E0001", "Unexpected character"),
    INVALID_NUMBER("E0002", "Invalid number format"),
    UNTERMINATED_STRING("E0003", "Unterminated string literal"),
    UNTERMINATED_COMMENT("E0004", "Unterminated comment"),
    INVALID_ESCAPE("E0005", "Invalid escape sequence"),

    // Parser errors
    UNEXPECTED_TOKEN("E0010", "Unexpected token"),
    EXPECTED_TOKEN("E0011", "Expected token"),
    EXPECTED_IDENTIFIER("E0012", "Expected identifier"),
    EXPECTED_TYPE("E0013", "Expected type"),
    EXPECTED_EXPRESSION("E0014", "Expected expression"),
    EXPECTED_STATEMENT("E0015", "Expected statement"),
    EXPECTED_DECLARATION("E0016", "Expected declaration"),
    MISSING_SEMICOLON("E0017", "Missing semicolon"),
    MISSING_BRACE("E0018", "Missing brace"),
    MISSING_PAREN("E0019", "Missing parenthesis"),
    MISSING_BRACKET("E0020", "Missing bracket"),
    INVALID_SYNTAX("E0021", "Invalid syntax"),

    // Type errors
    UNKNOWN_TYPE("E0100", "Unknown type"),
    TYPE_MISMATCH("E0101", "Type mismatch"),
    INVALID_TYPE_ARGUMENT("E0102", "Invalid type argument"),
    INVALID_CAST("E0103", "Invalid type cast"),

    // Semantic errors
    UNDECLARED_IDENTIFIER("E0200", "Undeclared identifier"),
    DUPLICATE_DECLARATION("E0201", "Duplicate declaration"),
    INVALID_ASSIGNMENT("E0202", "Invalid assignment"),
    INVALID_CALL("E0203", "Invalid function call"),
    INVALID_MEMBER_ACCESS("E0204", "Invalid member access"),
    INVALID_INDEX("E0205", "Invalid index"),

    // Forward reference errors
    FORWARD_REFERENCE("E0300", "Forward reference not allowed"),
    CYCLIC_DEPENDENCY("E0301", "Cyclic dependency detected"),

    // Generic errors
    INTERNAL_ERROR("E9999", "Internal compiler error");

    override fun toString(): String = code
}

/**
 * A diagnostic message with severity, code, location, and message.
 */
data class Diagnostic(
    /** The severity of this diagnostic. */
    val severity: DiagnosticSeverity,
    /** The error code. */
    val code: ErrorCode,
    /** The span where this diagnostic occurred. */
    val span: Span,
    /** The message describing the issue. */
    val message: String,
    /** Optional suggestions for fixing the issue. */
    val suggestions: List<String> = emptyList(),
    /** Optional related information. */
    val related: List<Diagnostic> = emptyList()
) {
    /** Check if this is an error. */
    val isError: Boolean get() = severity == DiagnosticSeverity.ERROR

    /** Check if this is a warning. */
    val isWarning: Boolean get() = severity == DiagnosticSeverity.WARNING

    override fun toString(): String {
        val severityStr = when (severity) {
            DiagnosticSeverity.ERROR -> "error"
            DiagnosticSeverity.WARNING -> "warning"
            DiagnosticSeverity.INFO -> "info"
            DiagnosticSeverity.DEBUG -> "debug"
        }
        return "[$severityStr:${code.code}] ${message} at ${span}"
    }

    companion object {
        /** Create an error diagnostic. */
        fun error(
            code: ErrorCode,
            span: Span,
            message: String,
            suggestions: List<String> = emptyList()
        ): Diagnostic {
            return Diagnostic(DiagnosticSeverity.ERROR, code, span, message, suggestions)
        }

        /** Create a warning diagnostic. */
        fun warning(
            code: ErrorCode,
            span: Span,
            message: String,
            suggestions: List<String> = emptyList()
        ): Diagnostic {
            return Diagnostic(DiagnosticSeverity.WARNING, code, span, message, suggestions)
        }

        /** Create an info diagnostic. */
        fun info(
            code: ErrorCode,
            span: Span,
            message: String
        ): Diagnostic {
            return Diagnostic(DiagnosticSeverity.INFO, code, span, message)
        }
    }
}

/**
 * Collection of diagnostics.
 */
class DiagnosticCollection {
    private val diagnostics: MutableList<Diagnostic> = mutableListOf()

    /** Number of diagnostics in this collection. */
    val size: Int get() = diagnostics.size

    /** Whether this collection is empty. */
    val isEmpty: Boolean get() = diagnostics.isEmpty()

    /** Whether this collection contains any errors. */
    val hasErrors: Boolean get() = diagnostics.any { it.isError }

    /** Whether this collection contains any warnings. */
    val hasWarnings: Boolean get() = diagnostics.any { it.isWarning }

    /** Number of errors in this collection. */
    val errorCount: Int get() = diagnostics.count { it.isError }

    /** Number of warnings in this collection. */
    val warningCount: Int get() = diagnostics.count { it.isWarning }

    /** Add a diagnostic to this collection. */
    fun add(diagnostic: Diagnostic) {
        diagnostics.add(diagnostic)
    }

    /** Add all diagnostics from another collection. */
    fun addAll(other: DiagnosticCollection) {
        diagnostics.addAll(other.diagnostics)
    }

    /** Add a diagnostic with builder syntax. */
    fun diagnostic(
        severity: DiagnosticSeverity,
        code: ErrorCode,
        span: Span,
        message: String,
        suggestions: List<String> = emptyList()
    ) {
        diagnostics.add(Diagnostic(severity, code, span, message, suggestions))
    }

    /** Add an error diagnostic. */
    fun error(
        code: ErrorCode,
        span: Span,
        message: String,
        suggestions: List<String> = emptyList()
    ) {
        diagnostics.add(Diagnostic.error(code, span, message, suggestions))
    }

    /** Add a warning diagnostic. */
    fun warning(
        code: ErrorCode,
        span: Span,
        message: String,
        suggestions: List<String> = emptyList()
    ) {
        diagnostics.add(Diagnostic.warning(code, span, message, suggestions))
    }

    /** Get all diagnostics. */
    fun getAll(): List<Diagnostic> = diagnostics.toList()

    /** Get all errors. */
    fun getErrors(): List<Diagnostic> = diagnostics.filter { it.isError }

    /** Get all warnings. */
    fun getWarnings(): List<Diagnostic> = diagnostics.filter { it.isWarning }

    /** Get diagnostics for a specific span. */
    fun getForSpan(span: Span): List<Diagnostic> = diagnostics.filter { it.span == span }

    /** Sort diagnostics by span (position in source). */
    fun sortBySpan(): DiagnosticCollection {
        val sorted = diagnostics.sortedBy { it.span.start }
        return DiagnosticCollection().apply { diagnostics.addAll(sorted) }
    }

    /** Group diagnostics by severity. */
    fun groupBySeverity(): Map<DiagnosticSeverity, List<Diagnostic>> {
        return diagnostics.groupBy { it.severity }
    }

    /** Clear all diagnostics. */
    fun clear() {
        diagnostics.clear()
    }

    /** Check if this collection contains a diagnostic with the given code. */
    fun hasCode(code: ErrorCode): Boolean = diagnostics.any { it.code == code }

    /** Merge with another collection. */
    fun merge(other: DiagnosticCollection): DiagnosticCollection {
        return DiagnosticCollection().apply {
            diagnostics.addAll(this@DiagnosticCollection.diagnostics)
            diagnostics.addAll(other.diagnostics)
        }
    }

    override fun toString(): String {
        return "DiagnosticCollection($errorCount errors, $warningCount warnings)"
    }

    companion object {
        /** Create an empty diagnostic collection. */
        fun empty(): DiagnosticCollection = DiagnosticCollection()

        /** Create a collection with a single diagnostic. */
        fun of(diagnostic: Diagnostic): DiagnosticCollection {
            return DiagnosticCollection().apply { add(diagnostic) }
        }

        /** Create a collection with multiple diagnostics. */
        fun of(vararg diagnostics: Diagnostic): DiagnosticCollection {
            return DiagnosticCollection().apply {
                this.diagnostics.addAll(diagnostics)
            }
        }
    }
}

/**
 * Exception thrown when too many errors are encountered.
 */
class TooManyErrorsException(val maxErrors: Int, diagnostics: DiagnosticCollection) : Exception(
    "Too many errors (${diagnostics.errorCount} > $maxErrors)"
) {
    val diagnostics: DiagnosticCollection = diagnostics
}
