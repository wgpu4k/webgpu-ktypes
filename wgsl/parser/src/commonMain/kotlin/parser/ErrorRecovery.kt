package io.ygdrasil.wgsl.parser

import io.ygdrasil.wgsl.ast.BlockStatement
import io.ygdrasil.wgsl.ast.BoolLiteral
import io.ygdrasil.wgsl.ast.Expression
import io.ygdrasil.wgsl.ast.ScalarKind
import io.ygdrasil.wgsl.ast.ScalarType
import io.ygdrasil.wgsl.ast.Statement
import io.ygdrasil.wgsl.ast.TypeDecl
import io.ygdrasil.wgsl.ir.Span
import io.ygdrasil.wgsl.lexer.Token
import io.ygdrasil.wgsl.lexer.TokenKind

/**
 * Error recovery strategies for the WGSL parser.
 * 
 * This class provides methods for recovering from parse errors and continuing
 * to parse the rest of the source file, enabling better error reporting.
 */
class ErrorRecovery {

    companion object {
        /** Maximum number of errors before stopping. */
        const val DEFAULT_MAX_ERRORS = 100

        /** Tokens that can start a new statement. */
        private val STATEMENT_START_TOKENS = setOf(
            TokenKind.LET,
            TokenKind.CONST,
            TokenKind.VAR,
            TokenKind.IF,
            TokenKind.SWITCH,
            TokenKind.LOOP,
            TokenKind.WHILE,
            TokenKind.FOR,
            TokenKind.BREAK,
            TokenKind.CONTINUE,
            TokenKind.RETURN,
            TokenKind.DISCARD,
            TokenKind.LEFT_BRACE,
            TokenKind.SEMICOLON
        )

        /** Tokens that can start a new top-level declaration. */
        private val DECLARATION_START_TOKENS = setOf(
            TokenKind.FN,
            TokenKind.STRUCT,
            TokenKind.TYPE,
            TokenKind.LET,
            TokenKind.CONST,
            TokenKind.VAR,
            TokenKind.OVERRIDE,
            TokenKind.AT
        )

        /** Tokens that can end a statement. */
        private val STATEMENT_END_TOKENS = setOf(
            TokenKind.SEMICOLON,
            TokenKind.RIGHT_BRACE
        )
    }

    /**
     * State for error recovery.
     */
    data class RecoveryState(
        /** Whether we are currently recovering from an error. */
        var isRecovering: Boolean = false,
        /** Number of errors encountered. */
        var errorCount: Int = 0,
        /** Maximum number of errors before stopping. */
        val maxErrors: Int = DEFAULT_MAX_ERRORS,
        /** Collection of diagnostics. */
        val diagnostics: DiagnosticCollection = DiagnosticCollection(),
        /** Stack of expected tokens for recovery. */
        val expectedTokens: MutableList<TokenKind> = mutableListOf()
    ) {
        /** Check if we should stop parsing. */
        fun shouldStop(): Boolean = errorCount >= maxErrors

        /** Check if we are at a synchronization point. */
        fun isAtSynchronizationPoint(token: Token): Boolean {
            return STATEMENT_START_TOKENS.contains(token.kind) ||
                    STATEMENT_END_TOKENS.contains(token.kind) ||
                    token.isEof
        }
    }

    // ========== Recovery Strategies ==========

    /**
     * Recover to the next statement.
     * 
     * This skips tokens until we find a statement start token or end token.
     * 
     * @param state The recovery state
     * @param currentToken The current token
     * @param advance Function to advance to the next token
     * @return The token where recovery stopped
     */
    fun recoverToNextStatement(
        state: RecoveryState,
        currentToken: Token,
        advance: () -> Token
    ): Token {
        state.isRecovering = true
        var token = currentToken

        while (!token.isEof && !state.shouldStop()) {
            if (STATEMENT_START_TOKENS.contains(token.kind)) {
                // Found the start of a new statement
                state.isRecovering = false
                return token
            }

            if (STATEMENT_END_TOKENS.contains(token.kind)) {
                // Found the end of a statement
                token = advance()
                state.isRecovering = false
                return token
            }

            // Skip this token
            token = advance()
        }

        state.isRecovering = false
        return token
    }

    /**
     * Recover to the next top-level declaration.
     * 
     * This skips tokens until we find a declaration start token.
     * 
     * @param state The recovery state
     * @param currentToken The current token
     * @param advance Function to advance to the next token
     * @return The token where recovery stopped
     */
    fun recoverToNextDeclaration(
        state: RecoveryState,
        currentToken: Token,
        advance: () -> Token
    ): Token {
        state.isRecovering = true
        var token = currentToken

        while (!token.isEof && !state.shouldStop()) {
            if (DECLARATION_START_TOKENS.contains(token.kind)) {
                state.isRecovering = false
                return token
            }

            token = advance()
        }

        state.isRecovering = false
        return token
    }

    /**
     * Recover to a specific token kind.
     * 
     * @param state The recovery state
     * @param currentToken The current token
     * @param advance Function to advance to the next token
     * @param targetKind The token kind to recover to
     * @return The token where recovery stopped
     */
    fun recoverTo(
        state: RecoveryState,
        currentToken: Token,
        advance: () -> Token,
        targetKind: TokenKind
    ): Token {
        state.isRecovering = true
        var token = currentToken

        while (!token.isEof && !state.shouldStop()) {
            if (token.kind == targetKind) {
                state.isRecovering = false
                return token
            }

            token = advance()
        }

        state.isRecovering = false
        return token
    }

    /**
     * Recover to one of several token kinds.
     * 
     * @param state The recovery state
     * @param currentToken The current token
     * @param advance Function to advance to the next token
     * @param targetKinds The token kinds to recover to
     * @return The token where recovery stopped
     */
    fun recoverToAny(
        state: RecoveryState,
        currentToken: Token,
        advance: () -> Token,
        vararg targetKinds: TokenKind
    ): Token {
        state.isRecovering = true
        var token = currentToken
        val targets = targetKinds.toSet()

        while (!token.isEof && !state.shouldStop()) {
            if (targets.contains(token.kind)) {
                state.isRecovering = false
                return token
            }

            token = advance()
        }

        state.isRecovering = false
        return token
    }

    // ========== Token Insertion/Replacement ==========

    /**
     * Data class for virtual token insertion.
     * 
     * This allows the parser to pretend a token exists without actually inserting it
     * into the token stream.
     */
    data class VirtualToken(
        val kind: TokenKind,
        val text: String,
        val span: Span
    )

    /**
     * Try to insert a virtual token and continue parsing.
     * 
     * @param state The recovery state
     * @param kind The token kind to insert
     * @param text The token text
     * @param span The span for the virtual token
     * @return The virtual token, or null if insertion is not possible
     */
    fun tryInsertToken(
        state: RecoveryState,
        kind: TokenKind,
        text: String,
        span: Span
    ): VirtualToken? {
        if (state.isRecovering) {
            return VirtualToken(kind, text, span)
        }
        return null
    }

    /**
     * Try to replace the current token with a different kind.
     * 
     * @param state The recovery state
     * @param currentToken The current token
     * @param newKind The new token kind
     * @return The replaced token, or null if replacement is not possible
     */
    fun tryReplaceToken(
        state: RecoveryState,
        currentToken: Token,
        newKind: TokenKind
    ): Token? {
        if (state.isRecovering) {
            return currentToken.copy(kind = newKind)
        }
        return null
    }

    // ========== Helper Methods ==========

    /**
     * Skip tokens until we find a token of the given kind.
     * 
     * @param currentToken The current token
     * @param advance Function to advance to the next token
     * @param targetKind The token kind to find
     * @return The found token, or the EOF token if not found
     */
    fun skipUntil(
        currentToken: Token,
        advance: () -> Token,
        targetKind: TokenKind
    ): Token {
        var token = currentToken

        while (!token.isEof) {
            if (token.kind == targetKind) {
                return token
            }
            token = advance()
        }

        return token
    }

    /**
     * Skip tokens until we find one of the given kinds.
     * 
     * @param currentToken The current token
     * @param advance Function to advance to the next token
     * @param targetKinds The token kinds to find
     * @return The found token, or the EOF token if not found
     */
    fun skipUntilAny(
        currentToken: Token,
        advance: () -> Token,
        vararg targetKinds: TokenKind
    ): Token {
        var token = currentToken
        val targets = targetKinds.toSet()

        while (!token.isEof) {
            if (targets.contains(token.kind)) {
                return token
            }
            token = advance()
        }

        return token
    }

    /**
     * Check if we should attempt recovery after an error.
     * 
     * @param state The recovery state
     * @return true if recovery should be attempted
     */
    fun shouldAttemptRecovery(state: RecoveryState): Boolean {
        return !state.shouldStop() && !state.isRecovering
    }

    /**
     * Create a dummy statement for error recovery.
     * 
     * This creates a placeholder statement that can be used when
     * a statement cannot be parsed.
     */
    fun createDummyStatement(span: Span): Statement {
        // Return an empty block as a dummy
        return BlockStatement(emptyList(), span)
    }

    /**
     * Create a dummy expression for error recovery.
     * 
     * This creates a placeholder expression that can be used when
     * an expression cannot be parsed.
     */
    fun createDummyExpression(span: Span): Expression {
        // Return a boolean literal as a dummy
        return BoolLiteral(false, span)
    }

    /**
     * Create a dummy type for error recovery.
     */
    fun createDummyType(span: Span): TypeDecl {
        // Return a bool type as a dummy
        return ScalarType(ScalarKind.BOOL, span)
    }

    // ========== Recovery Context ==========

    /**
     * Context for error recovery that tracks what we're currently parsing.
     */
    enum class RecoveryContext {
        /** Parsing a top-level declaration. */
        DECLARATION,

        /** Parsing a function. */
        FUNCTION,

        /** Parsing a struct. */
        STRUCT,

        /** Parsing a statement. */
        STATEMENT,

        /** Parsing an expression. */
        EXPRESSION,

        /** Parsing a type. */
        TYPE
    }

    /**
     * Get the appropriate recovery strategy for a context.
     */
    fun getRecoveryStrategy(context: RecoveryContext): RecoveryStrategy {
        return when (context) {
            RecoveryContext.DECLARATION -> RecoveryStrategy.DECLARATION
            RecoveryContext.FUNCTION -> RecoveryStrategy.STATEMENT
            RecoveryContext.STRUCT -> RecoveryStrategy.STATEMENT
            RecoveryContext.STATEMENT -> RecoveryStrategy.STATEMENT
            RecoveryContext.EXPRESSION -> RecoveryStrategy.EXPRESSION
            RecoveryContext.TYPE -> RecoveryStrategy.TYPE
        }
    }

    /**
     * Recovery strategy to use in different contexts.
     */
    enum class RecoveryStrategy {
        /** Recover to the next declaration. */
        DECLARATION,

        /** Recover to the next statement. */
        STATEMENT,

        /** Recover to the next expression boundary. */
        EXPRESSION,

        /** Recover to the next type boundary. */
        TYPE
    }
}
