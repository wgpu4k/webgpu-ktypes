package io.ygdrasil.wgsl.lexer

import io.ygdrasil.wgsl.ir.Span

/**
 * Represents a lexical token in the WGSL source code.
 * 
 * A token has a kind (category), a span (position in source), and optionally
 * a literal text value for identifier and literal tokens.
 */
data class Token(
    /** The kind of this token. */
    val kind: TokenKind,
    /** The span (position) of this token in the source. */
    val span: Span,
    /** The literal text value for IDENTIFIER and literal tokens. */
    val literal: String? = null,
) {
    companion object {
        /**
         * Creates a token with the given kind, span, and optional literal.
         * 
         * @param kind The token kind
         * @param span The span in source
         * @param literal The literal text (for IDENTIFIER, INT_LITERAL, etc.)
         * @return A new token
         */
        fun of(
            kind: TokenKind,
            span: Span,
            literal: String? = null,
        ): Token {
            // Validate that literal is only set for appropriate token kinds
            check(literal == null || kind.isLiteral || kind.isKeyword) {
                "Literal can only be set for literal or keyword token kinds, got ${kind.name}"
            }
            return Token(kind, span, literal)
        }

        /**
         * Creates an end-of-file token.
         * 
         * @param span The span (typically the end of the source)
         * @return An EOF token
         */
        fun eof(span: Span): Token = Token(TokenKind.EOF, span, null)

        /**
         * Creates an identifier token.
         * 
         * @param text The identifier text
         * @param span The span in source
         * @return An identifier token
         */
        fun identifier(text: String, span: Span): Token = Token(TokenKind.IDENTIFIER, span, text)

        /**
         * Creates an integer literal token.
         * 
         * @param text The integer literal text (e.g., "42", "0x2a")
         * @param span The span in source
         * @return An integer literal token
         */
        fun intLiteral(text: String, span: Span): Token = Token(TokenKind.INT_LITERAL, span, text)

        /**
         * Creates an unsigned integer literal token.
         * 
         * @param text The unsigned integer literal text (e.g., "42u", "0x2au")
         * @param span The span in source
         * @return An unsigned integer literal token
         */
        fun uintLiteral(text: String, span: Span): Token = Token(TokenKind.UINT_LITERAL, span, text)

        /**
         * Creates a float literal token.
         * 
         * @param text The float literal text (e.g., "3.14", "1.0e-5", "0x1.8p1")
         * @param span The span in source
         * @return A float literal token
         */
        fun floatLiteral(text: String, span: Span): Token = Token(TokenKind.FLOAT_LITERAL, span, text)

        /**
         * Creates a boolean literal token.
         * 
         * @param value The boolean value
         * @param span The span in source
         * @return A boolean literal token
         */
        fun boolLiteral(value: Boolean, span: Span): Token =
            Token(TokenKind.BOOL_LITERAL, span, value.toString())

        /**
         * Creates a string literal token.
         * 
         * @param text The string literal text (including quotes)
         * @param span The span in source
         * @return A string literal token
         */
        fun stringLiteral(text: String, span: Span): Token = Token(TokenKind.STRING_LITERAL, span, text)

        /**
         * Creates a simple operator or punctuation token.
         * 
         * @param kind The token kind
         * @param span The span in source
         * @return A token
         */
        fun simple(kind: TokenKind, span: Span): Token = Token(kind, span, null)
    }

    /** Returns true if this is an end-of-file token. */
    val isEof: Boolean get() = kind == TokenKind.EOF

    /** Returns true if this is a whitespace token. */
    val isWhitespace: Boolean get() = kind == TokenKind.WHITESPACE

    /** Returns true if this is a comment token. */
    val isComment: Boolean
        get() =
            kind == TokenKind.SINGLE_LINE_COMMENT ||
                    kind == TokenKind.MULTI_LINE_COMMENT ||
                    kind == TokenKind.DOC_COMMENT

    /** Returns true if this is a keyword token. */
    val isKeyword: Boolean get() = kind.isKeyword

    /** Returns true if this is a literal token (identifier or literal value). */
    val isLiteral: Boolean get() = kind.isLiteral

    /** Returns true if this is an operator token. */
    val isOperator: Boolean get() = kind.isOperator

    /** Returns true if this is a punctuation token. */
    val isPunctuation: Boolean get() = kind.isPunctuation

    /**
     * Returns the display representation of this token for debugging.
     */
    override fun toString(): String = buildString {
        append(kind.name)
        if (literal != null) {
            append("(")
            append(literal)
            append(")")
        }
        append(" at ")
        append(span)
    }
}

/**
 * A sequence of tokens produced by the lexer.
 * 
 * This interface provides an iterator-like API for consuming tokens.
 */
interface TokenStream {
    /** Returns the next token, or null if at end of stream. */
    fun next(): Token?

    /** Returns the next token without consuming it, or null if at end of stream. */
    fun peek(): Token?

    /** Returns the current position in the source. */
    fun position(): SourcePosition

    /** Returns true if we've reached the end of the stream. */
    fun isEof(): Boolean = peek()?.isEof ?: true
}

/**
 * Represents a position in the source code.
 */
data class SourcePosition(
    /** The 0-based line number. */
    val line: Int,
    /** The 0-based column number. */
    val column: Int,
    /** The 0-based offset from the start of the source. */
    val offset: Int,
) {
    companion object {
        /** The initial position (line 0, column 0, offset 0). */
        val START = SourcePosition(0, 0, 0)

        /**
         * Creates a source position.
         */
        fun of(line: Int, column: Int, offset: Int): SourcePosition =
            SourcePosition(line, column, offset)
    }

    /**
     * Returns a new position advanced by the given character.
     * 
     * @param char The character consumed
     * @return The new position
     */
    fun advance(char: Char): SourcePosition = when (char) {
        '\n' -> SourcePosition(line + 1, 0, offset + 1)
        '\r' -> SourcePosition(line, column, offset + 1) // Handle \r\n separately
        else -> SourcePosition(line, column + 1, offset + 1)
    }

    /**
     * Returns a new position advanced by the given number of columns.
     */
    fun advanceColumns(count: Int): SourcePosition =
        SourcePosition(line, column + count, offset + count)

    override fun toString(): String = "${line + 1}:${column + 1}"
}
