package io.ygdrasil.wgsl.lexer

import io.ygdrasil.wgsl.ir.Span

/**
 * Lexical analyzer for the WGSL shader language.
 * 
 * The lexer converts a WGSL source string into a stream of tokens.
 * It handles:
 * - Whitespace (spaces, tabs, newlines)
 * - Single-line comments (// ...)
 * - Multi-line comments (/* ... */)
 * - Documentation comments (/// ...)
 * - Identifiers and keywords
 * - Numeric literals (integer, unsigned integer, float)
 * - String literals
 * - Boolean literals (true, false)
 * - Operators and punctuation
 * 
 * The lexer maintains the current position in the source and produces
 * tokens with accurate span information.
 */
class Lexer(
    /** The source code to lex. */
    private val source: String,
) : TokenStream {
    /** Current position in the source. */
    private var position: SourcePosition = SourcePosition.START

    /** Current index in the source string. */
    private var index: Int = 0

    /** The next token, cached for peek(). */
    private var peeked: Token? = null

    /** true if we've reached the end of the source. */
    private val isAtEnd: Boolean get() = index >= source.length

    override fun position(): SourcePosition = position

    override fun peek(): Token? {
        if (peeked == null && !isAtEnd) {
            peeked = nextToken()
        }
        return peeked
    }

    override fun next(): Token? {
        val result = peeked ?: if (!isAtEnd) nextToken() else Token.eof(spanFrom(position))
        peeked = null
        return result
    }

    override fun isEof(): Boolean = peek()?.isEof ?: true

    /**
     * Returns the current character without consuming it, or null if at end.
     */
    private fun peekChar(): Char? = if (isAtEnd) null else source[index]

    /**
     * Returns the next character without consuming it, or null if at end.
     */
    private fun peekChar(ahead: Int = 1): Char? {
        val lookaheadIndex = index + ahead
        return if (lookaheadIndex >= source.length) null else source[lookaheadIndex]
    }

    /**
     * Consumes and returns the current character, advancing the position.
     */
    private fun consume(): Char? {
        if (isAtEnd) return null
        val char = source[index]
        position = position.advance(char)
        index++
        return char
    }

    /**
     * Consumes the current character if it matches the expected character.
     * 
     * @param expected The expected character
     * @return true if the character matched and was consumed
     */
    private fun expect(expected: Char): Boolean {
        if (peekChar() == expected) {
            consume()
            return true
        }
        return false
    }

    /**
     * Consumes characters while the predicate is true.
     * 
     * @param predicate The condition for continuing to consume
     * @return The consumed string
     */
    private fun consumeWhile(predicate: (Char) -> Boolean): String {
        val start = index
        while (!isAtEnd && predicate(peekChar()!!)) {
            consume()
        }
        return source.substring(start, index)
    }

    private fun consumeDigits(predicate: (Char) -> Boolean) {
        while (!isAtEnd) {
            val c = peekChar()!!
            if (predicate(c)) {
                consume()
            } else if (c == '_') {
                val next = peekChar(1)
                if (next != null && predicate(next)) {
                    consume()
                    consume()
                } else {
                    break
                }
            } else {
                break
            }
        }
    }

    /**
     * Consumes a specific string if it matches at the current position.
     * 
     * @param expected The expected string
     * @return true if the string matched and was consumed
     */
    private fun expectString(expected: String): Boolean {
        if (source.substring(index).startsWith(expected)) {
            repeat(expected.length) { consume() }
            return true
        }
        return false
    }

    /**
     * Creates a span from the start position to the current position.
     * Span uses start (inclusive) and end (exclusive) byte indices.
     */
    private fun spanFrom(start: SourcePosition): Span =
        Span(start.offset.toUInt(), position.offset.toUInt())

    /**
     * Creates a span from the start index to the current index.
     * Span uses start (inclusive) and end (exclusive) byte indices.
     */
    private fun spanFrom(startIndex: Int): Span =
        Span(startIndex.toUInt(), index.toUInt())

    /**
     * Produces the next token from the source.
     */
    private fun nextToken(): Token {
        // Skip whitespace
        skipWhitespace()

        if (isAtEnd) {
            return Token.eof(spanFrom(position))
        }

        val start = position
        val startIndex = index

        return when (val char = peekChar()!!) {
            // Single-line comment
            '/' -> when (peekChar(1)) {
                '/' -> lexSingleLineComment(start)
                '*' -> lexMultiLineComment(start)
                '=' -> {
                    consume(); consume()
                    Token.simple(TokenKind.SLASH_ASSIGN, spanFrom(start))
                }
                else -> {
                    consume()
                    Token.simple(TokenKind.SLASH, spanFrom(start))
                }
            }
            // Special handling for underscore
            '_' -> {
                if (peekChar(1)?.isIdentifierContinue() == true) {
                    lexIdentifierOrKeyword(start)
                } else {
                    consume()
                    Token.simple(TokenKind.UNDERSCORE, spanFrom(start))
                }
            }

            // Numeric literals
            in '0'..'9' -> lexNumericLiteral(start)

            // String literals
            '"' -> lexStringLiteral(start)

            // Operators and punctuation
            '+', '-', '*', '%', '&', '|', '^', '~', '<', '>', '!', '=', ':', '.' ->
                lexOperatorOrPunctuation(char, start)

            // Single-character punctuation
            '(', ')', '{', '}', '[', ']', ',', ';', '?', '#' -> {
                consume()
                val kind = when (char) {
                    '(' -> TokenKind.LEFT_PAREN
                    ')' -> TokenKind.RIGHT_PAREN
                    '{' -> TokenKind.LEFT_BRACE
                    '}' -> TokenKind.RIGHT_BRACE
                    '[' -> TokenKind.LEFT_BRACKET
                    ']' -> TokenKind.RIGHT_BRACKET
                    ',' -> TokenKind.COMMA
                    ';' -> TokenKind.SEMICOLON
                    '?' -> TokenKind.QUESTION
                    '#' -> TokenKind.UNKNOWN
                    else -> error("Unexpected character: $char")
                }
                Token.simple(kind, spanFrom(start))
            }

            // At symbol
            '@' -> {
                consume()
                Token.simple(TokenKind.AT, spanFrom(start))
            }

            // Identifiers and keywords
            else -> if (char.isIdentifierStart()) {
                lexIdentifierOrKeyword(start)
            } else {
                // Unknown character - consume and return as error
                consume()
                Token.simple(TokenKind.UNKNOWN, spanFrom(start))
            }
        }
    }

    /**
     * Skips whitespace characters.
     */
    private fun skipWhitespace() {
        while (!isAtEnd) {
            when (peekChar()!!) {
                ' ', '\t', '\r', '\n', '\u000C' -> consume()
                else -> break
            }
        }
    }

    /**
     * Lexes a single-line comment.
     */
    private fun lexSingleLineComment(start: SourcePosition): Token {
        // Consume //
        consume()
        consume()

        val isDocComment = expect('/')

        // Consume until newline or end
        consumeWhile { it != '\n' && it != '\r' }

        val kind = if (isDocComment) TokenKind.DOC_COMMENT else TokenKind.SINGLE_LINE_COMMENT
        return Token.simple(kind, spanFrom(start))
    }

    /**
     * Lexes a multi-line comment.
     */
    private fun lexMultiLineComment(start: SourcePosition): Token {
        // Consume /*
        consume()
        consume()

        // Doc comments start with /** (one or more stars after /)
        val isDocComment = peekChar() == '*'
        if (isDocComment) {
            consume() // consume the extra *
            // Optionally consume more stars
            while (peekChar() == '*') {
                consume()
            }
        }

        var depth = 1
        // Consume until matched */ or end
        while (!isAtEnd && depth > 0) {
            if (peekChar() == '/' && peekChar(1) == '*') {
                consume()
                consume()
                depth++
            } else if (peekChar() == '*' && peekChar(1) == '/') {
                consume()
                consume()
                depth--
            } else {
                consume()
            }
        }

        val kind = if (isDocComment) TokenKind.DOC_COMMENT else TokenKind.MULTI_LINE_COMMENT
        return Token.simple(kind, spanFrom(start))
    }

    /**
     * Lexes an identifier or keyword.
     */
    private fun lexIdentifierOrKeyword(start: SourcePosition): Token {
        val startIndex = index

        // Consume the first character (letter or underscore)
        consume()

        // Consume the rest of the identifier
        consumeWhile { it.isIdentifierContinue() }

        val text = source.substring(startIndex, index)
        val kind = keywordFor(text) ?: TokenKind.IDENTIFIER

        return Token.of(kind, spanFrom(start), text)
    }

    /**
     * Lexes a numeric literal (integer, unsigned integer, or float).
     */
    private fun lexNumericLiteral(start: SourcePosition): Token {
        val startIndex = index
        val firstChar = peekChar()!!

        // Check for hexadecimal
        val isHex = firstChar == '0' && peekChar(1)?.let { it in "xX" } ?: false

        if (isHex) {
            return lexHexLiteral(start, startIndex)
        }

        // Reject decimal integer starting with 0 followed by other digits (e.g. 052)
        val isMalformedOctal = firstChar == '0' && peekChar(1)?.let { it in '0'..'9' } ?: false
        if (isMalformedOctal) {
            consumeDigits { it in '0'..'9' }
            return Token.simple(TokenKind.UNKNOWN, spanFrom(start))
        }

        // Decimal literal
        return lexDecimalLiteral(start, startIndex)
    }

    /**
     * Lexes a hexadecimal literal.
     */
    private fun lexHexLiteral(start: SourcePosition, startIndex: Int): Token {
        // Consume 0x or 0X
        consume()
        consume()

        val hexDigitsStart = index
        // Consume hex digits only if it does not start with a digit separator '_'
        if (peekChar() != '_') {
            consumeDigits { it in '0'..'9' || it in 'a'..'f' || it in 'A'..'F' }
        }
        val hexDigitsCount = index - hexDigitsStart

        var isFloat = false
        var hasFractionDigits = false

        // Optional fractional part
        if (peekChar() == '.') {
            isFloat = true
            consume()
            val fracStart = index
            if (peekChar() != '_') {
                consumeDigits { it in '0'..'9' || it in 'a'..'f' || it in 'A'..'F' }
            }
            hasFractionDigits = (index - fracStart) > 0
        }

        // Optional exponent part
        var hasExponent = false
        var hasExponentDigits = false
        if (peekChar()?.let { it in "pP" } == true) {
            isFloat = true
            hasExponent = true
            consume()
            if (peekChar()?.let { it in "+-" } == true) {
                consume()
            }
            val expDigitsStart = index
            if (peekChar() != '_') {
                consumeDigits { it in '0'..'9' }
            }
            hasExponentDigits = (index - expDigitsStart) > 0
        }

        // Check validity
        val hasMantisse = hexDigitsCount > 0 || hasFractionDigits
        val isFloatValid = !isFloat || (hasExponent && hasExponentDigits)

        if (!hasMantisse || !isFloatValid) {
            // Suffixes check to consume completely
            if (isFloat) {
                if (peekChar()?.let { it in "fFhH" } == true) {
                    consume()
                }
            } else {
                if (peekChar()?.let { it in "iIuU" } == true) {
                    consume()
                }
            }
            return Token.simple(TokenKind.UNKNOWN, spanFrom(start))
        }

        // Suffixes (standard WGSL only: f, F, h, H for floats; i, I, u, U for integers)
        if (isFloat) {
            if (peekChar()?.let { it in "fFhH" } == true) {
                consume()
            }
        } else {
            if (peekChar()?.let { it in "iIuU" } == true) {
                consume()
            }
        }

        val text = source.substring(startIndex, index)
        return if (isFloat) {
            Token.floatLiteral(text, spanFrom(start))
        } else if (text.endsWith('u', ignoreCase = true)) {
            Token.uintLiteral(text, spanFrom(start))
        } else {
            Token.intLiteral(text, spanFrom(start))
        }
    }

    /**
     * Lexes a decimal literal.
     */
    private fun lexDecimalLiteral(start: SourcePosition, startIndex: Int): Token {
        // Consume digits
        consumeDigits { it in '0'..'9' }

        // Check for float parts
        val hasDot = if (peekChar() == '.') {
            val next = peekChar(1)
            if (next == null || (next !in 'a'..'z' && next !in 'A'..'Z' && next != '_')) {
                true
            } else if (next == 'e' || next == 'E') {
                val nextNext = peekChar(2)
                if (nextNext != null && nextNext in '0'..'9') {
                    true
                } else if ((nextNext == '+' || nextNext == '-') && peekChar(3)?.let { it in '0'..'9' } == true) {
                    true
                } else {
                    false
                }
            } else if (next == 'f' || next == 'F' || next == 'h' || next == 'H') {
                val nextNext = peekChar(2)
                if (nextNext == null || (nextNext !in 'a'..'z' && nextNext !in 'A'..'Z' && nextNext !in '0'..'9' && nextNext != '_')) {
                    true
                } else {
                    false
                }
            } else {
                false
            }
        } else {
            false
        }
        val hasExp = peekChar()?.let { it in "eE" } ?: false

        if (hasDot || hasExp) {
            return lexFloatLiteral(start, startIndex)
        }

        // Check for integer suffixes (standard WGSL: i, u)
        val nextChar = peekChar()?.lowercaseChar()
        if (nextChar == 'u' || nextChar == 'i') {
            consume()
            val text = source.substring(startIndex, index)
            return if (nextChar == 'u') Token.uintLiteral(text, spanFrom(start)) else Token.intLiteral(
                text,
                spanFrom(start)
            )
        }

        // Check for float suffixes without dot/exp (standard WGSL: f, h)
        if (nextChar == 'f' || nextChar == 'h') {
            consume()
            val text = source.substring(startIndex, index)
            return Token.floatLiteral(text, spanFrom(start))
        }

        val text = source.substring(startIndex, index)
        return Token.intLiteral(text, spanFrom(start))
    }

    /**
     * Lexes a float literal.
     */
    private fun lexFloatLiteral(start: SourcePosition, startIndex: Int): Token {
        // We've already consumed the integer part

        // Consume fractional part
        if (peekChar() == '.') {
            consume()
            if (peekChar() != '_') {
                consumeDigits { it in '0'..'9' }
            }
        }

        // Consume exponent part
        var hasExponent = false
        var hasExponentDigits = false
        if (peekChar()?.let { it in "eE" } == true) {
            hasExponent = true
            consume()
            if (peekChar()?.let { it in "+-" } == true) {
                consume()
            }
            val expDigitsStart = index
            if (peekChar() != '_') {
                consumeDigits { it in '0'..'9' }
            }
            hasExponentDigits = (index - expDigitsStart) > 0
        }

        if (hasExponent && !hasExponentDigits) {
            // Suffixes check to consume completely
            val nextChar = peekChar()?.lowercaseChar()
            if (nextChar == 'f' || nextChar == 'h') {
                consume()
            }
            return Token.simple(TokenKind.UNKNOWN, spanFrom(start))
        }

        // Check for float suffixes
        val nextChar = peekChar()?.lowercaseChar()
        if (nextChar == 'f' || nextChar == 'h') {
            consume()
        }

        val text = source.substring(startIndex, index)
        return Token.floatLiteral(text, spanFrom(start))
    }

    /**
     * Lexes a string literal.
     */
    private fun lexStringLiteral(start: SourcePosition): Token {
        // Consume opening quote
        consume()

        val content = StringBuilder()
        var isValid = true

        // Consume until closing quote or end
        while (!isAtEnd) {
            val char = peekChar()!!
            when (char) {
                '"' -> {
                    consume()
                    return Token.stringLiteral(if (isValid) content.toString() else "", spanFrom(start))
                }

                '\\' -> {
                    consume() // consume \
                    if (isAtEnd) {
                        isValid = false
                        break
                    }
                    val escapeChar = consume()!!
                    when (escapeChar) {
                        'n' -> content.append('\n')
                        'r' -> content.append('\r')
                        't' -> content.append('\t')
                        '\\' -> content.append('\\')
                        '"' -> content.append('"')
                        '\'' -> content.append('\'')
                        '0' -> content.append('\u0000')
                        'x' -> {
                            val hex = StringBuilder()
                            for (i in 0 until 2) {
                                if (!isAtEnd && peekChar()!!.isHexDigit()) {
                                    hex.append(consume())
                                }
                            }
                            if (hex.length == 2) {
                                content.append(hex.toString().toInt(16).toChar())
                            } else {
                                isValid = false
                            }
                        }
                        'u' -> {
                            if (peekChar() == '{') {
                                consume() // {
                                val hex = StringBuilder()
                                while (!isAtEnd && peekChar() != '}') {
                                    val h = peekChar()!!
                                    if (h.isHexDigit()) {
                                        hex.append(consume())
                                    } else {
                                        break
                                    }
                                }
                                if (peekChar() == '}') {
                                    consume() // }
                                    if (hex.isNotEmpty()) {
                                        try {
                                            val codePoint = hex.toString().toInt(16)
                                            if (codePoint <= 0x10FFFF) {
                                                content.append(
                                                    if (codePoint <= 0xFFFF) {
                                                        codePoint.toChar().toString()
                                                    } else {
                                                        val high = 0xD800 + ((codePoint - 0x10000) ushr 10)
                                                        val low = 0xDC00 + ((codePoint - 0x10000) and 0x3FF)
                                                        charArrayOf(high.toChar(), low.toChar()).concatToString()
                                                    }
                                                )
                                            } else {
                                                isValid = false
                                            }
                                        } catch (e: Exception) {
                                            isValid = false
                                        }
                                    } else {
                                        isValid = false
                                    }
                                } else {
                                    isValid = false
                                }
                            } else {
                                isValid = false
                            }
                        }
                        else -> content.append(escapeChar)
                    }
                }

                '\n', '\r' -> {
                    // Unterminated string literal
                    break
                }

                else -> content.append(consume())
            }
        }

        return Token.stringLiteral(if (isValid) content.toString() else "", spanFrom(start))
    }

    private fun Char.isHexDigit(): Boolean = this in '0'..'9' || this in 'a'..'f' || this in 'A'..'F'

    /**
     * Lexes an operator or punctuation token.
     */
    private fun lexOperatorOrPunctuation(firstChar: Char, start: SourcePosition): Token {
        val startIndex = index

        return when (firstChar) {
            '+' -> when (peekChar(1)) {
                '+' -> {
                    consume(); consume(); Token.simple(TokenKind.INCREMENT, spanFrom(start))
                }

                '=' -> {
                    consume(); consume(); Token.simple(TokenKind.PLUS_ASSIGN, spanFrom(start))
                }

                else -> {
                    consume(); Token.simple(TokenKind.PLUS, spanFrom(start))
                }
            }

            '-' -> when (peekChar(1)) {
                '-' -> {
                    consume(); consume(); Token.simple(TokenKind.DECREMENT, spanFrom(start))
                }

                '=' -> {
                    consume(); consume(); Token.simple(TokenKind.MINUS_ASSIGN, spanFrom(start))
                }

                '>' -> {
                    consume(); consume(); Token.simple(TokenKind.ARROW, spanFrom(start))
                }

                else -> {
                    consume(); Token.simple(TokenKind.MINUS, spanFrom(start))
                }
            }

            '*' -> when (peekChar(1)) {
                '=' -> {
                    consume(); consume(); Token.simple(TokenKind.STAR_ASSIGN, spanFrom(start))
                }

                '.' -> {
                    consume(); consume(); Token.simple(TokenKind.DOT_STAR, spanFrom(start))
                }

                else -> {
                    consume(); Token.simple(TokenKind.STAR, spanFrom(start))
                }
            }

            '%' -> when (peekChar(1)) {
                '=' -> {
                    consume(); consume(); Token.simple(TokenKind.PERCENT_ASSIGN, spanFrom(start))
                }

                else -> {
                    consume(); Token.simple(TokenKind.PERCENT, spanFrom(start))
                }
            }

            '&' -> when (peekChar(1)) {
                '&' -> {
                    consume(); consume(); Token.simple(TokenKind.AND, spanFrom(start))
                }

                '=' -> {
                    consume(); consume(); Token.simple(TokenKind.AND_ASSIGN, spanFrom(start))
                }

                else -> {
                    consume(); Token.simple(TokenKind.AMPERSAND, spanFrom(start))
                }
            }

            '|' -> when (peekChar(1)) {
                '|' -> {
                    consume(); consume(); Token.simple(TokenKind.OR, spanFrom(start))
                }

                '=' -> {
                    consume(); consume(); Token.simple(TokenKind.OR_ASSIGN, spanFrom(start))
                }

                else -> {
                    consume(); Token.simple(TokenKind.PIPE, spanFrom(start))
                }
            }

            '^' -> when (peekChar(1)) {
                '=' -> {
                    consume(); consume(); Token.simple(TokenKind.XOR_ASSIGN, spanFrom(start))
                }

                else -> {
                    consume(); Token.simple(TokenKind.CARET, spanFrom(start))
                }
            }

            '~' -> {
                consume(); Token.simple(TokenKind.TILDE, spanFrom(start))
            }

            '<' -> when (peekChar(1)) {
                '<' -> when (peekChar(2)) {
                    '=' -> {
                        consume(); consume(); consume(); Token.simple(TokenKind.LEFT_SHIFT_ASSIGN, spanFrom(start))
                    }

                    else -> {
                        consume(); consume(); Token.simple(TokenKind.LEFT_SHIFT, spanFrom(start))
                    }
                }

                '=' -> {
                    consume(); consume(); Token.simple(TokenKind.LTE, spanFrom(start))
                }

                '>' -> {
                    consume(); consume(); Token.simple(TokenKind.LEFT_ANGLE_RIGHT_ANGLE, spanFrom(start))
                }

                else -> {
                    consume(); Token.simple(TokenKind.LEFT_ANGLE, spanFrom(start))
                }
            }

            '>' -> when (peekChar(1)) {
                '>' -> when (peekChar(2)) {
                    '=' -> {
                        consume(); consume(); consume(); Token.simple(TokenKind.RIGHT_SHIFT_ASSIGN, spanFrom(start))
                    }

                    else -> {
                        consume(); consume(); Token.simple(TokenKind.RIGHT_SHIFT, spanFrom(start))
                    }
                }

                '=' -> {
                    consume(); consume(); Token.simple(TokenKind.GTE, spanFrom(start))
                }

                else -> {
                    consume(); Token.simple(TokenKind.RIGHT_ANGLE, spanFrom(start))
                }
            }

            '!' -> when (peekChar(1)) {
                '=' -> {
                    consume(); consume(); Token.simple(TokenKind.NEQ, spanFrom(start))
                }

                else -> {
                    consume(); Token.simple(TokenKind.NOT, spanFrom(start))
                }
            }

            '=' -> when (peekChar(1)) {
                '>' -> {
                    consume(); consume(); Token.simple(TokenKind.FAT_ARROW, spanFrom(start))
                }

                '=' -> {
                    consume(); consume(); Token.simple(TokenKind.EQ, spanFrom(start))
                }

                else -> {
                    consume(); Token.simple(TokenKind.ASSIGN, spanFrom(start))
                }
            }

            ':' -> when (peekChar(1)) {
                ':' -> {
                    consume(); consume(); Token.simple(TokenKind.COLON_COLON, spanFrom(start))
                }

                else -> {
                    consume(); Token.simple(TokenKind.COLON, spanFrom(start))
                }
            }

            '.' -> when (peekChar(1)) {
                '*' -> {
                    consume(); consume(); Token.simple(TokenKind.DOT_STAR, spanFrom(start))
                }

                in '0'..'9' -> {
                    // This is a float literal starting with .
                    // Don't consume the '.' - let lexFloatLiteral handle it
                    lexFloatLiteral(start, startIndex)
                }

                else -> {
                    consume(); Token.simple(TokenKind.DOT, spanFrom(start))
                }
            }

            else -> {
                consume()
                Token.simple(TokenKind.IDENTIFIER, spanFrom(start))
            }
        }
    }

    /**
     * Returns the keyword token kind for the given text, or null if it's not a keyword.
     */
    private fun keywordFor(text: String): TokenKind? = when (text) {
        // Control flow
        "if" -> TokenKind.IF
        "else" -> TokenKind.ELSE
        "switch" -> TokenKind.SWITCH
        "case" -> TokenKind.CASE
        "default" -> TokenKind.DEFAULT
        "loop" -> TokenKind.LOOP
        "while" -> TokenKind.WHILE
        "for" -> TokenKind.FOR
        "break" -> TokenKind.BREAK
        "continue" -> TokenKind.CONTINUE
        "return" -> TokenKind.RETURN
        "discard" -> TokenKind.DISCARD
        "continuing" -> TokenKind.CONTINUING

        // Declarations
        "abstract" -> TokenKind.ABSTRACT
        "fn" -> TokenKind.FN
        "let" -> TokenKind.LET
        "const" -> TokenKind.CONST
        "var" -> TokenKind.VAR
        "type" -> TokenKind.TYPE
        "enum" -> TokenKind.ENUM
        "alias" -> TokenKind.ALIAS
        "struct" -> TokenKind.STRUCT
        "const_assert" -> TokenKind.CONST_ASSERT
        "static_assert" -> TokenKind.STATIC_ASSERT
        "diagnostic" -> TokenKind.DIAGNOSTIC

        // Type constructors
        "array" -> TokenKind.ARRAY
        "ptr" -> TokenKind.PTR
        "mat" -> TokenKind.MAT
        "mat2x2" -> TokenKind.MAT
        "mat2x3" -> TokenKind.MAT
        "mat2x4" -> TokenKind.MAT
        "mat3x2" -> TokenKind.MAT
        "mat3x3" -> TokenKind.MAT
        "mat3x4" -> TokenKind.MAT
        "mat4x2" -> TokenKind.MAT
        "mat4x3" -> TokenKind.MAT
        "mat4x4" -> TokenKind.MAT
        "vec" -> TokenKind.VEC
        "vec2" -> TokenKind.VEC
        "vec3" -> TokenKind.VEC
        "vec4" -> TokenKind.VEC

        // Storage classes
        "uniform" -> TokenKind.UNIFORM
        "storage" -> TokenKind.STORAGE
        "workgroup" -> TokenKind.WORKGROUP
        "private" -> TokenKind.PRIVATE
        "function" -> TokenKind.FUNCTION

        // Access modes
        "read" -> TokenKind.READ
        "write" -> TokenKind.WRITE
        "read_write" -> TokenKind.READ_WRITE

        // Attributes
        "location" -> TokenKind.LOCATION
        "builtin" -> TokenKind.BUILTIN
        "enable" -> TokenKind.ENABLE
        "requires" -> TokenKind.REQUIRES
        "interpolate" -> TokenKind.INTERPOLATE
        "invariant" -> TokenKind.INVARIANT
        "must_use" -> TokenKind.MUST_USE
        "override" -> TokenKind.OVERRIDE
        "bitcast" -> TokenKind.BITCAST
        "compute" -> TokenKind.COMPUTE
        "fragment" -> TokenKind.FRAGMENT
        "vertex" -> TokenKind.VERTEX

        // Layout annotations
        "packed" -> TokenKind.PACKED
        "aligned" -> TokenKind.ALIGNED

        // Template constraints
        "where" -> TokenKind.WHERE

        // Built-in types
        "bool" -> TokenKind.BOOL
        "f16" -> TokenKind.F16
        "f32" -> TokenKind.F32
        "f64" -> TokenKind.F64
        "float" -> TokenKind.FLOAT
        "i8" -> TokenKind.I8
        "u8" -> TokenKind.U8
        "i16" -> TokenKind.I16
        "u16" -> TokenKind.U16
        "i32" -> TokenKind.I32
        "u32" -> TokenKind.U32
        "i64" -> TokenKind.I64
        "u64" -> TokenKind.U64
        "int" -> TokenKind.INT
        "sampler" -> TokenKind.SAMPLER
        "sampler_comparison" -> TokenKind.SAMPLER_COMPARISON
        "texture_1d" -> TokenKind.TEXTURE_1D
        "texture_1d_array" -> TokenKind.TEXTURE_1D_ARRAY
        "texture_2d" -> TokenKind.TEXTURE_2D
        "texture_2d_array" -> TokenKind.TEXTURE_2D_ARRAY
        "texture_3d" -> TokenKind.TEXTURE_3D
        "texture_cube" -> TokenKind.TEXTURE_CUBE
        "texture_cube_array" -> TokenKind.TEXTURE_CUBE_ARRAY
        "texture_multisampled_2d" -> TokenKind.TEXTURE_MULTISAMPLED_2D
        "texture_depth_2d" -> TokenKind.TEXTURE_DEPTH_2D
        "texture_depth_2d_array" -> TokenKind.TEXTURE_DEPTH_2D_ARRAY
        "texture_depth_cube" -> TokenKind.TEXTURE_DEPTH_CUBE
        "texture_depth_cube_array" -> TokenKind.TEXTURE_DEPTH_CUBE_ARRAY
        "texture_depth_multisampled_2d" -> TokenKind.TEXTURE_DEPTH_MULTISAMPLED_2D
        "texture_external" -> TokenKind.TEXTURE_EXTERNAL
        "texture_storage_1d" -> TokenKind.TEXTURE_STORAGE_1D
        "texture_storage_2d" -> TokenKind.TEXTURE_STORAGE_2D
        "texture_storage_2d_array" -> TokenKind.TEXTURE_STORAGE_2D_ARRAY
        "texture_storage_3d" -> TokenKind.TEXTURE_STORAGE_3D
        "atomic" -> TokenKind.ATOMIC
        "ray_query" -> TokenKind.RAY_QUERY
        "handle" -> TokenKind.HANDLE

        // Built-in values
        "true" -> TokenKind.TRUE
        "false" -> TokenKind.FALSE
        "position" -> TokenKind.POSITION
        "vertex_index" -> TokenKind.VERTEX_INDEX
        "instance_index" -> TokenKind.INSTANCE_INDEX
        "draw_index" -> TokenKind.DRAW_INDEX
        "front_facing" -> TokenKind.FRONT_FACING
        "primitive_index" -> TokenKind.PRIMITIVE_INDEX
        "sample_index" -> TokenKind.SAMPLE_INDEX
        "sample_mask" -> TokenKind.SAMPLE_MASK
        "viewport_index" -> TokenKind.VIEWPORT_INDEX
        "pointsize" -> TokenKind.POINTSIZE
        "clip_distances" -> TokenKind.CLIP_DISTANCES
        "cull_distances" -> TokenKind.CULL_DISTANCES
        "device_index" -> TokenKind.DEVICE_INDEX
        "view_index" -> TokenKind.VIEW_INDEX
        "workgroup_id" -> TokenKind.WORKGROUP_ID
        "num_workgroups" -> TokenKind.NUM_WORKGROUPS
        "global_invocation_id" -> TokenKind.GLOBAL_INVOCATION_ID
        "local_invocation_id" -> TokenKind.LOCAL_INVOCATION_ID
        "local_invocation_index" -> TokenKind.LOCAL_INVOCATION_INDEX

        else -> null
    }

    /**
     * Creates a lexer for the given source and returns all tokens.
     * 
     * This is a convenience function for testing and debugging.
     */
    fun tokenize(): List<Token> {
        val tokens = mutableListOf<Token>()
        var token: Token?
        while (true) {
            token = this.next() ?: break
            if (token.isEof) break
            tokens.add(token)
        }
        return tokens
    }

    /**
     * Creates a lexer for the given source and returns all non-whitespace, non-comment tokens.
     * 
     * This is a convenience function for getting only the significant tokens.
     */
    fun tokenizeSignificant(): List<Token> {
        val tokens = mutableListOf<Token>()
        var token: Token?
        while (true) {
            token = this.next() ?: break
            if (token.isEof) break
            if (!token.isWhitespace && !token.isComment) {
                tokens.add(token)
            }
        }
        return tokens
    }
}

/**
 * Convenience function to create a lexer and tokenize a source string.
 */
fun tokenize(source: String): List<Token> = Lexer(source).tokenize()

/**
 * Convenience function to create a lexer and tokenize a source string,
 * returning only significant tokens.
 */
fun tokenizeSignificant(source: String): List<Token> = Lexer(source).tokenizeSignificant()

private fun Char.isIdentifierStart(): Boolean {
    // Kotlin common does not expose the WGSL XID_Start table; keep this as a Unicode letter subset.
    return this == '_' || this in 'a'..'z' || this in 'A'..'Z' || isLetter()
}

private fun Char.isIdentifierContinue(): Boolean {
    return isIdentifierStart() || this in '0'..'9' || isDigit()
}
