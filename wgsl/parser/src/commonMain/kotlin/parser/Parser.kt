package io.ygdrasil.wgsl.parser

import io.ygdrasil.wgsl.ast.AbstractFloatType
import io.ygdrasil.wgsl.ast.AbstractIntType
import io.ygdrasil.wgsl.ast.ArrayType
import io.ygdrasil.wgsl.ast.AssignmentStatement
import io.ygdrasil.wgsl.ast.AtomicType
import io.ygdrasil.wgsl.ast.Attribute
import io.ygdrasil.wgsl.ast.PredeclaredEnumerantExpr
import io.ygdrasil.wgsl.ast.getPredeclaredEnumerant
import io.ygdrasil.wgsl.ast.getPredeclaredEnumerantValues
import io.ygdrasil.wgsl.ast.BinaryExpr
import io.ygdrasil.wgsl.ast.BinaryOperator
import io.ygdrasil.wgsl.ast.BitcastExpr
import io.ygdrasil.wgsl.ast.BlockStatement
import io.ygdrasil.wgsl.ast.BoolLiteral
import io.ygdrasil.wgsl.ast.BreakIfStatement
import io.ygdrasil.wgsl.ast.BreakStatement
import io.ygdrasil.wgsl.ast.BuiltinValue
import io.ygdrasil.wgsl.ast.CallExpr
import io.ygdrasil.wgsl.ast.Case
import io.ygdrasil.wgsl.ast.ConstAssertDecl
import io.ygdrasil.wgsl.ast.ConstAssertStatement
import io.ygdrasil.wgsl.ast.ConstantType
import io.ygdrasil.wgsl.ast.ContinueStatement
import io.ygdrasil.wgsl.ast.DefaultCase
import io.ygdrasil.wgsl.ast.DiagnosticDirective
import io.ygdrasil.wgsl.ast.DiagnosticStatement
import io.ygdrasil.wgsl.ast.DiscardStatement
import io.ygdrasil.wgsl.ast.EmptyStatement
import io.ygdrasil.wgsl.ast.EnableDirective
import io.ygdrasil.wgsl.ast.EnumDecl
import io.ygdrasil.wgsl.ast.EnumMember
import io.ygdrasil.wgsl.ast.EntryPointAttribute
import io.ygdrasil.wgsl.ast.Expression
import io.ygdrasil.wgsl.ast.ExpressionStatement
import io.ygdrasil.wgsl.ast.FloatLiteral
import io.ygdrasil.wgsl.ast.ForStatement
import io.ygdrasil.wgsl.ast.FragmentInput
import io.ygdrasil.wgsl.ast.FunctionDecl
import io.ygdrasil.wgsl.ast.GlobalDecl
import io.ygdrasil.wgsl.ast.IdentExpr
import io.ygdrasil.wgsl.ast.IfStatement
import io.ygdrasil.wgsl.ast.IncDecStatement
import io.ygdrasil.wgsl.ast.IndexExpr
import io.ygdrasil.wgsl.ast.IntLiteral
import io.ygdrasil.wgsl.ast.LoopStatement
import io.ygdrasil.wgsl.ast.MatrixType
import io.ygdrasil.wgsl.ast.MemberAccessExpr
import io.ygdrasil.wgsl.ast.NamedType
import io.ygdrasil.wgsl.ast.OverrideDecl
import io.ygdrasil.wgsl.ast.Param
import io.ygdrasil.wgsl.ast.PhonyAssignmentStatement
import io.ygdrasil.wgsl.ast.PointerType
import io.ygdrasil.wgsl.ast.RayQueryType
import io.ygdrasil.wgsl.ast.RequiresDirective
import io.ygdrasil.wgsl.ast.ReturnStatement
import io.ygdrasil.wgsl.ast.SamplerType
import io.ygdrasil.wgsl.ast.ScalarKind
import io.ygdrasil.wgsl.ast.ScalarType
import io.ygdrasil.wgsl.ast.Statement
import io.ygdrasil.wgsl.ast.StorageClass
import io.ygdrasil.wgsl.ast.StringLiteral
import io.ygdrasil.wgsl.ast.StructDecl
import io.ygdrasil.wgsl.ast.StructMember
import io.ygdrasil.wgsl.ast.SwitchBody
import io.ygdrasil.wgsl.ast.SwitchCase
import io.ygdrasil.wgsl.ast.SwitchStatement
import io.ygdrasil.wgsl.ast.TemplateParam
import io.ygdrasil.wgsl.ast.TemplateType
import io.ygdrasil.wgsl.ast.TernaryExpr
import io.ygdrasil.wgsl.ast.TextureKind
import io.ygdrasil.wgsl.ast.TextureType
import io.ygdrasil.wgsl.ast.TranslationUnit
import io.ygdrasil.wgsl.ast.TypeAliasDecl
import io.ygdrasil.wgsl.ast.TypeDecl
import io.ygdrasil.wgsl.ast.UnaryExpr
import io.ygdrasil.wgsl.ast.UnaryOperator
import io.ygdrasil.wgsl.ast.VariableDecl
import io.ygdrasil.wgsl.ast.VariableDeclKind
import io.ygdrasil.wgsl.ast.VariableDeclStatement
import io.ygdrasil.wgsl.ast.VectorType
import io.ygdrasil.wgsl.ast.VertexOutput
import io.ygdrasil.wgsl.ast.WhileStatement
import io.ygdrasil.wgsl.ast.ExpressionTransformer
import io.ygdrasil.wgsl.ast.tryResolveToEnumExpr
import io.ygdrasil.wgsl.ir.Span
import io.ygdrasil.wgsl.lexer.Lexer
import io.ygdrasil.wgsl.lexer.Token
import io.ygdrasil.wgsl.lexer.TokenKind
import io.ygdrasil.wgsl.lexer.isBuiltinValue
import io.ygdrasil.wgsl.lexer.isKeyword

/**
 * Parser for the WGSL shader language.
 * 
 * The parser converts a stream of tokens from the lexer into an Abstract Syntax Tree (AST).
 * It uses recursive descent parsing with operator precedence for expressions.
 * 
 * The parser maintains the current token and provides methods for parsing
 * different language constructs (expressions, statements, declarations, etc.).
 */
class Parser(
    /** The lexer providing tokens. */
    private val lexer: Lexer,
) {
    private fun nextNonCommentToken(): Token {
        var token = lexer.next() ?: Token.eof(Span.UNDEFINED)
        while (token.kind == TokenKind.SINGLE_LINE_COMMENT ||
               token.kind == TokenKind.MULTI_LINE_COMMENT ||
               token.kind == TokenKind.DOC_COMMENT) {
            token = lexer.next() ?: Token.eof(Span.UNDEFINED)
        }
        return token
    }

    /** The current token. */
    private var currentToken: Token = Token.eof(Span.UNDEFINED)

    init {
        currentToken = nextNonCommentToken()
    }

    /** The previous token (for error recovery). */
    private var previousToken: Token? = null

    /** true if we've seen an error during parsing. */
    private var hasError: Boolean = false

    /** true if we are currently parsing inside a template (angle brackets). */
    private var templateDepth: Int = 0
    private var isInsideTemplate: Boolean
        get() = templateDepth > 0
        set(value) {
            templateDepth = if (value) 1 else 0
        }

    /** The list of errors encountered during parsing. */
    val errors: MutableList<ParseError> = mutableListOf()

    /** Returns true if we've reached the end of the token stream. */
    private fun isAtEnd(): Boolean = currentToken.isEof

    /** Returns the kind of the current token. */
    private fun currentKind(): TokenKind {
        val kind = currentToken.kind
        if (kind == TokenKind.RIGHT_SHIFT && templateDepth > 0) {
            return TokenKind.RIGHT_ANGLE
        }
        return kind
    }

    /**
     * Advances to the next token.
     */
    private fun advance(): Token {
        previousToken = currentToken
        if (currentToken.kind == TokenKind.RIGHT_SHIFT && templateDepth > 0) {
            val span1 = Span(currentToken.span.start, currentToken.span.start + 1u)
            val span2 = Span(currentToken.span.start + 1u, currentToken.span.end)
            previousToken = Token(TokenKind.RIGHT_ANGLE, span1)
            currentToken = Token(TokenKind.RIGHT_ANGLE, span2)
            return previousToken!!
        }
        currentToken = nextNonCommentToken()
        return previousToken!!
    }

    /**
     * Returns the current token without consuming it.
     */
    private fun peek(): Token = currentToken

    /**
     * Consumes the current token if it matches the expected kind.
     * 
     * @param kind The expected token kind
     * @return true if the token matched and was consumed
     */
    private fun expect(kind: TokenKind): Boolean {
        if (currentKind() == kind) {
            advance()
            return true
        }
        return false
    }

    /**
     * Consumes the current token if it matches any of the expected kinds.
     * 
     * @param kinds The expected token kinds
     * @return true if the token matched and was consumed
     */
    private fun expectAny(vararg kinds: TokenKind): Boolean {
        if (kinds.contains(currentKind())) {
            advance()
            return true
        }
        return false
    }

    /**
     * Reports a parse error at the current position.
     */
    private fun error(message: String) {
        val span = currentToken.span
        val error = ParseError(message, span)
        errors.add(error)
        hasError = true
    }

    /**
     * Reports a parse error at the previous token position.
     */
    private fun errorAtPrevious(message: String) {
        val span = previousToken?.span ?: currentToken.span
        errors.add(ParseError(message, span))
        hasError = true
    }

    /**
     * Expects the current token to be of the given kind, and reports an error if not.
     */
    private fun expectOrError(kind: TokenKind, message: String): Boolean {
        if (expect(kind)) {
            return true
        }
        error("Expected ${kind}, found ${currentKind()}")
        return false
    }

    /**
     * Parses the entire source and returns the translation unit.
     */
    fun parse(): TranslationUnit {
        val declarations = mutableListOf<GlobalDecl>()

        while (!isAtEnd()) {
            if (expect(TokenKind.SEMICOLON)) continue

            val startToken = currentToken
            declarations.add(parseTopLevelDecl())
            if (startToken == currentToken) {
                advance()
            }
        }

        val start = declarations.firstOrNull()?.span?.start ?: 0u
        val end = declarations.lastOrNull()?.span?.end ?: 0u
        val span = Span(start, end)

        val unit = TranslationUnit(declarations, span)
        
        // Resolve user-defined enum member accesses to EnumMemberExpr
        // Note: This is done after parsing to allow forward references
        return resolveEnumAccesses(unit)
    }

    /**
     * Resolves MemberAccessExpr to EnumMemberExpr for user-defined enum member accesses.
     * 
     * This pass converts MemberAccessExpr nodes that reference user-defined enum members
     * into EnumMemberExpr nodes, providing a unified representation for both user-defined
     * and predeclared enum value references.
     */
    private fun resolveEnumAccesses(unit: TranslationUnit): TranslationUnit {
        val transformer = EnumAccessResolver(unit)
        return transformer.transform(unit)
    }

    /**
     * Resolves MemberAccessExpr to EnumMemberExpr for user-defined enum member accesses.
     */
    private class EnumAccessResolver(private val unit: TranslationUnit) : ExpressionTransformer() {
        override fun transform(expr: MemberAccessExpr): Expression {
            return expr.tryResolveToEnumExpr(unit)
        }
    }

    /**
     * Parses a top-level declaration.
     */
    private fun parseTopLevelDecl(): GlobalDecl {
        val attributes = mutableListOf<Attribute>()
        while (currentKind() == TokenKind.AT) {
            attributes.add(parseAttribute())
        }

        return when (currentKind()) {
            TokenKind.ENABLE -> parseEnableDirective()
            TokenKind.REQUIRES -> parseRequiresDirective()
            TokenKind.DIAGNOSTIC -> parseDiagnosticDirective()
            TokenKind.FN -> parseFunctionDecl(attributes)
            TokenKind.STRUCT -> parseStructDecl(attributes)
            TokenKind.ENUM -> parseEnumDecl(attributes)
            TokenKind.LET, TokenKind.CONST, TokenKind.VAR -> parseVariableDecl(attributes)
            TokenKind.TYPE, TokenKind.ALIAS -> parseTypeAliasDecl(attributes)
            TokenKind.OVERRIDE -> parseOverrideDecl(attributes)
            TokenKind.CONST_ASSERT, TokenKind.STATIC_ASSERT -> parseConstAssertDecl()
            else -> {
                if (attributes.isNotEmpty()) {
                    error("Attributes must be followed by a declaration")
                    return ConstAssertDecl(IdentExpr("", currentToken.span), Span(attributes.first().span.start, currentToken.span.end))
                }
                // Try to recover
                error("Unexpected token ${currentKind()} at top level")
                advance()
                if (isAtEnd()) {
                    return ConstAssertDecl(IdentExpr("", currentToken.span), currentToken.span)
                }
                parseTopLevelDecl()
            }
        }
    }

    /**
     * Parses a function declaration.
     */
    private fun parseFunctionDecl(attributes: List<Attribute> = emptyList()): FunctionDecl {
        val start = attributes.firstOrNull()?.span ?: currentToken.span

        // Consume 'fn'
        expectOrError(TokenKind.FN, "Expected 'fn'")

        // Parse name
        val name = if (currentKind() == TokenKind.IDENTIFIER || currentKind().isBuiltinValue || currentKind().isKeyword) {
            val nameToken = advance()
            nameToken.literal ?: nameToken.kind.toString().lowercase()
        } else {
            error("Expected function name")
            ""
        }

        // Parse template parameters (if any)
        val templateParams = mutableListOf<TemplateParam>()
        if (currentKind() == TokenKind.LEFT_ANGLE) {
            templateParams.addAll(parseTemplateParamList())
        }

        // Parse parameters
        expectOrError(TokenKind.LEFT_PAREN, "Expected '('")
        val parameters = parseParameterList()
        expectOrError(TokenKind.RIGHT_PAREN, "Expected ')'")

        // Parse return type (if any)
        val returnAttributes = mutableListOf<Attribute>()
        val returnType = if (currentKind() == TokenKind.ARROW) {
            advance() // consume ->
            while (currentKind() == TokenKind.AT) {
                returnAttributes.add(parseAttribute())
            }
            parseTypeDecl()
        } else {
            null
        }

        // Parse body (if any)
        val body = if (currentKind() == TokenKind.LEFT_BRACE) {
            parseBlockStatement()
        } else {
            null
        }

        val end = previousToken?.span?.end ?: currentToken.span.end
        return FunctionDecl(
            attributes = attributes,
            name = name,
            templateParams = templateParams,
            parameters = parameters,
            returnAttributes = returnAttributes,
            returnType = returnType,
            body = body,
            span = Span(start.start, end)
        )
    }

    /**
     * Parses a struct declaration.
     */
    private fun parseStructDecl(attributes: List<Attribute> = emptyList()): StructDecl {
        val start = attributes.firstOrNull()?.span ?: currentToken.span

        // Consume 'struct'
        expectOrError(TokenKind.STRUCT, "Expected 'struct'")

        // Parse name
        val name = if (currentKind() == TokenKind.IDENTIFIER) {
            val nameToken = advance()
            nameToken.literal ?: ""
        } else {
            error("Expected struct name")
            ""
        }

        // Parse template parameters (if any)
        val templateParams = mutableListOf<TemplateParam>()
        if (currentKind() == TokenKind.LEFT_ANGLE) {
            templateParams.addAll(parseTemplateParamList())
        }

        // Parse members
        expectOrError(TokenKind.LEFT_BRACE, "Expected '{'")
        val members = mutableListOf<StructMember>()
        while (currentKind() != TokenKind.RIGHT_BRACE && !isAtEnd()) {
            if (expect(TokenKind.SEMICOLON)) continue
            if (expect(TokenKind.COMMA)) continue
            members.add(parseStructMember())
        }
        expectOrError(TokenKind.RIGHT_BRACE, "Expected '}'")

        val end = previousToken?.span?.end ?: currentToken.span.end
        return StructDecl(
            attributes = attributes,
            name = name,
            templateParams = templateParams,
            members = members,
            span = Span(start.start, end)
        )
    }

    /**
     * Parses a struct member.
     */
    private fun parseStructMember(): StructMember {
        val start = currentToken.span

        // Parse attributes (if any)
        val attributes = mutableListOf<Attribute>()
        while (currentKind() == TokenKind.AT) {
            attributes.add(parseAttribute())
        }

        // Parse name
        var name = if (currentKind() == TokenKind.IDENTIFIER || currentKind().isBuiltinValue || currentKind().isKeyword) {
            val nameToken = advance()
            nameToken.literal ?: nameToken.kind.toString().lowercase()
        } else {
            // T023 fix: If name is missing, try to get it from @builtin attribute
            val builtinAttr = attributes.find { it.name == "builtin" }
            val builtinName = builtinAttr?.args?.firstOrNull()?.let { arg ->
                (arg as? IdentExpr)?.name
            }
            builtinName ?: run {
                error("Expected member name")
                ""
            }
        }

        // Parse type
        expectOrError(TokenKind.COLON, "Expected ':'")
        val type = parseTypeDecl()

        // Parse default value (if any)
        val defaultValue = if (currentKind() == TokenKind.ASSIGN) {
            advance()
            parseExpression()
        } else {
            null
        }

        if (!expectAny(TokenKind.SEMICOLON, TokenKind.COMMA) && currentKind() != TokenKind.RIGHT_BRACE) {
            error("Expected ';' or ',' after struct member")
        }

        val end = previousToken?.span?.end ?: currentToken.span.end
        return StructMember(
            attributes = attributes,
            name = name,
            type = type,
            defaultValue = defaultValue,
            span = Span(start.start, end)
        )
    }

    /**
     * Parses an enum declaration.
     */
    private fun parseEnumDecl(attributes: List<Attribute> = emptyList()): EnumDecl {
        val start = attributes.firstOrNull()?.span ?: currentToken.span

        // Consume 'enum'
        expectOrError(TokenKind.ENUM, "Expected 'enum'")

        // Parse name
        val name = if (currentKind() == TokenKind.IDENTIFIER) {
            val nameToken = advance()
            nameToken.literal ?: ""
        } else {
            error("Expected enum name")
            ""
        }

        // Parse members
        expectOrError(TokenKind.LEFT_BRACE, "Expected '{'")
        val members = mutableListOf<EnumMember>()
        while (currentKind() != TokenKind.RIGHT_BRACE && !isAtEnd()) {
            if (expect(TokenKind.SEMICOLON)) continue
            if (expect(TokenKind.COMMA)) continue
            members.add(parseEnumMember())
        }
        expectOrError(TokenKind.RIGHT_BRACE, "Expected '}'")

        val end = previousToken?.span?.end ?: currentToken.span.end
        return EnumDecl(
            attributes = attributes,
            name = name,
            members = members,
            span = Span(start.start, end)
        )
    }

    /**
     * Parses an enum member.
     */
    private fun parseEnumMember(): EnumMember {
        val start = currentToken.span

        // Parse attributes (if any)
        val attributes = mutableListOf<Attribute>()
        while (currentKind() == TokenKind.AT) {
            attributes.add(parseAttribute())
        }

        // Parse name
        val name = if (currentKind() == TokenKind.IDENTIFIER) {
            val nameToken = advance()
            nameToken.literal ?: ""
        } else {
            error("Expected enum member name")
            ""
        }

        // Parse value (if any)
        val value = if (currentKind() == TokenKind.ASSIGN) {
            advance()
            parseExpression()
        } else {
            null
        }

        val end = previousToken?.span?.end ?: currentToken.span.end
        return EnumMember(
            attributes = attributes,
            name = name,
            value = value,
            span = Span(start.start, end)
        )
    }

    /**
     * Parses a variable declaration (global or local).
     */
    private fun parseVariableDecl(attributes: List<Attribute> = emptyList()): VariableDecl {
        val start = attributes.firstOrNull()?.span ?: currentToken.span

        // Parse kind (let, const, var)
        val kind = when (currentKind()) {
            TokenKind.LET -> VariableDeclKind.LET
            TokenKind.CONST -> VariableDeclKind.CONST
            TokenKind.VAR -> VariableDeclKind.VAR
            else -> {
                error("Expected 'let', 'const', or 'var'")
                VariableDeclKind.LET
            }
        }
        advance()

        // Parse storage class and access mode
        var storageClass: String? = null
        var accessMode: String? = null
        if (kind == VariableDeclKind.VAR && currentKind() == TokenKind.LEFT_ANGLE) {
            advance() // <
            if (currentKind() == TokenKind.IDENTIFIER || currentKind().isKeyword) {
                val token = advance()
                storageClass = token.literal ?: token.kind.name.lowercase()
            }
            if (currentKind() == TokenKind.COMMA) {
                advance() // ,
                if (currentKind() == TokenKind.IDENTIFIER || currentKind().isKeyword) {
                    val token = advance()
                    accessMode = token.literal ?: token.kind.name.lowercase()
                }
            }
            expectOrError(TokenKind.RIGHT_ANGLE, "Expected '>'")
        }

        // Parse name
        val name = if (currentKind() == TokenKind.IDENTIFIER || currentKind().isBuiltinValue || currentKind().isKeyword) {
            val nameToken = advance()
            nameToken.literal ?: nameToken.kind.toString().lowercase()
        } else {
            error("Expected variable name")
            ""
        }

        // Parse type annotation (if any)
        val type = if (currentKind() == TokenKind.COLON) {
            advance()
            parseTypeDecl()
        } else {
            null
        }

        // Parse initializer (required for const)
        val initializer = if (currentKind() == TokenKind.ASSIGN) {
            advance()
            parseExpression()
        } else {
            null
        }

        // For const, initializer is required
        if (kind == VariableDeclKind.CONST && initializer == null) {
            error("Const declarations must have an initializer")
        }

        expectOrError(TokenKind.SEMICOLON, "Expected ';'")

        val end = previousToken?.span?.end ?: currentToken.span.end
        return VariableDecl(
            kind = kind,
            attributes = attributes,
            name = name,
            storageClass = storageClass,
            accessMode = accessMode,
            type = type,
            initializer = initializer,
            span = Span(start.start, end)
        )
    }

    /**
     * Parses a type alias declaration.
     */
    private fun parseTypeAliasDecl(attributes: List<Attribute> = emptyList()): TypeAliasDecl {
        val start = attributes.firstOrNull()?.span ?: currentToken.span

        // Consume 'type' or 'alias'
        if (currentKind() == TokenKind.TYPE || currentKind() == TokenKind.ALIAS) {
            advance()
        } else {
            error("Expected 'type' or 'alias'")
        }

        // Parse name
        val name = if (currentKind() == TokenKind.IDENTIFIER) {
            val nameToken = advance()
            nameToken.literal ?: ""
        } else {
            error("Expected type alias name")
            ""
        }

        // Parse template parameters (if any)
        val templateParams = mutableListOf<TemplateParam>()
        if (currentKind() == TokenKind.LEFT_ANGLE) {
            templateParams.addAll(parseTemplateParamList())
        }

        // Parse '=' and type
        expectOrError(TokenKind.ASSIGN, "Expected '='")
        val type = parseTypeDecl()

        expectOrError(TokenKind.SEMICOLON, "Expected ';'")

        val end = previousToken?.span?.end ?: currentToken.span.end
        return TypeAliasDecl(
            attributes = attributes,
            name = name,
            templateParams = templateParams,
            type = type,
            span = Span(start.start, end)
        )
    }

    /**
     * Parses an override declaration.
     */
    private fun parseOverrideDecl(attributes: List<Attribute> = emptyList()): OverrideDecl {
        val start = attributes.firstOrNull()?.span ?: currentToken.span

        // Consume 'override'
        expectOrError(TokenKind.OVERRIDE, "Expected 'override'")

        // Parse name
        val name = parseIdentifier()

        // Parse type annotation (if any)
        val type = if (currentKind() == TokenKind.COLON) {
            advance()
            parseTypeDecl()
        } else {
            null
        }

        // Parse initializer (optional)
        val initializer = if (currentKind() == TokenKind.ASSIGN) {
            advance()
            parseExpression()
        } else {
            null
        }

        expectOrError(TokenKind.SEMICOLON, "Expected ';'")

        val end = previousToken?.span?.end ?: currentToken.span.end
        return OverrideDecl(
            attributes = attributes,
            name = name,
            type = type,
            initializer = initializer,
            span = Span(start.start, end)
        )
    }

    private fun parseEnableDirective(): EnableDirective {
        val start = currentToken.span
        advance() // consume 'enable'
        val extensions = mutableListOf<String>()
        extensions.add(parseIdentifier())
        while (expect(TokenKind.COMMA)) {
            extensions.add(parseIdentifier())
        }
        expectOrError(TokenKind.SEMICOLON, "Expected ';' after enable directive")
        return EnableDirective(extensions, Span(start.start, previousToken!!.span.end))
    }

    private fun parseRequiresDirective(): RequiresDirective {
        val start = currentToken.span
        advance() // consume 'requires'
        val features = mutableListOf<String>()
        features.add(parseIdentifier())
        while (expect(TokenKind.COMMA)) {
            features.add(parseIdentifier())
        }
        expectOrError(TokenKind.SEMICOLON, "Expected ';' after requires directive")
        return RequiresDirective(features, Span(start.start, previousToken!!.span.end))
    }

    private fun parseDiagnosticDirective(): DiagnosticDirective {
        val start = currentToken.span
        advance() // consume 'diagnostic'
        expectOrError(TokenKind.LEFT_PAREN, "Expected '(' after diagnostic")
        val severity = parseIdentifier()
        expectOrError(TokenKind.COMMA, "Expected ',' after diagnostic severity")
        val rule = parseIdentifier()
        expectOrError(TokenKind.RIGHT_PAREN, "Expected ')' after diagnostic rule")
        expectOrError(TokenKind.SEMICOLON, "Expected ';' after diagnostic directive")
        return DiagnosticDirective(severity, rule, Span(start.start, previousToken!!.span.end))
    }

    private fun parseIdentifier(): String {
        if (currentKind() == TokenKind.IDENTIFIER) {
            return advance().literal ?: ""
        }
        if (currentToken.isKeyword) {
            return advance().literal ?: ""
        }
        error("Expected identifier, found ${currentKind()}")
        return ""
    }

    /**
     * Parses a const assert declaration.
     */
    private fun parseConstAssertDecl(): ConstAssertDecl {
        val start = currentToken.span

        // Consume 'const_assert' or 'static_assert'
        if (currentKind() == TokenKind.CONST_ASSERT || currentKind() == TokenKind.STATIC_ASSERT) {
            advance()
        } else {
            error("Expected 'const_assert' or 'static_assert'")
        }
        val hasParen = expect(TokenKind.LEFT_PAREN)

        val expression = parseExpression()

        if (hasParen) {
            expectOrError(TokenKind.RIGHT_PAREN, "Expected ')'")
        }
        expectOrError(TokenKind.SEMICOLON, "Expected ';'")

        val end = previousToken?.span?.end ?: currentToken.span.end
        return ConstAssertDecl(
            expression = expression,
            span = Span(start.start, end)
        )
    }

    /**
     * Parses a const assert statement (for use inside function bodies).
     */
    private fun parseConstAssertStatement(): ConstAssertStatement {
        val start = currentToken.span

        // Consume 'const_assert' or 'static_assert'
        if (currentKind() == TokenKind.CONST_ASSERT || currentKind() == TokenKind.STATIC_ASSERT) {
            advance()
        } else {
            error("Expected 'const_assert' or 'static_assert'")
        }
        val hasParen = expect(TokenKind.LEFT_PAREN)

        val expression = parseExpression()

        if (hasParen) {
            expectOrError(TokenKind.RIGHT_PAREN, "Expected ')'")
        }
        expectOrError(TokenKind.SEMICOLON, "Expected ';'")

        val end = previousToken?.span?.end ?: currentToken.span.end
        return ConstAssertStatement(
            expression = expression,
            span = Span(start.start, end)
        )
    }

    /**
     * Parses an attribute.
     */
    private fun parseAttribute(): Attribute {
        val start = currentToken.span

        // Consume '@'
        expectOrError(TokenKind.AT, "Expected '@'")

        // Parse attribute name
        val token = advance()
        val name = token.literal ?: token.kind.name.lowercase()

        // Parse arguments (if any)
        val args = mutableListOf<Expression>()
        if (currentKind() == TokenKind.LEFT_PAREN) {
            advance()
            while (currentKind() != TokenKind.RIGHT_PAREN && !isAtEnd()) {
                args.add(parseExpression())
                if (currentKind() == TokenKind.COMMA) {
                    advance()
                }
            }
            expectOrError(TokenKind.RIGHT_PAREN, "Expected ')'")
        }

        val end = previousToken?.span?.end ?: currentToken.span.end
        return Attribute(
            name = name,
            args = args,
            span = Span(start.start, end)
        )
    }


    /**
     * Parses a list of template parameters.
     */
    private fun parseTemplateParamList(): List<TemplateParam> {
        val params = mutableListOf<TemplateParam>()

        expectOrError(TokenKind.LEFT_ANGLE, "Expected '<'")
        val oldInsideTemplate = isInsideTemplate
        isInsideTemplate = true

        while (currentKind() != TokenKind.RIGHT_ANGLE && !isAtEnd()) {
            params.add(parseTemplateParam())
            if (currentKind() == TokenKind.COMMA) {
                advance()
            } else if (currentKind() != TokenKind.RIGHT_ANGLE) {
                // If not followed by comma or end of list, it's an error, but let's be lenient for now
            }
        }

        isInsideTemplate = oldInsideTemplate
        expectOrError(TokenKind.RIGHT_ANGLE, "Expected '>'")

        return params
    }

    /**
     * Parses a template parameter.
     */
    private fun parseTemplateParam(): TemplateParam {
        val start = currentToken.span

        // Parse name
        val name = if (currentKind() == TokenKind.IDENTIFIER) {
            val nameToken = advance()
            nameToken.literal ?: ""
        } else {
            error("Expected template parameter name")
            ""
        }

        // Parse constraint (if any)
        val constraint = if (currentKind() == TokenKind.COLON) {
            advance()
            parseTypeDecl()
        } else {
            null
        }

        val end = previousToken?.span?.end ?: currentToken.span.end
        return TemplateParam(
            name = name,
            constraint = constraint,
            span = Span(start.start, end)
        )
    }

    /**
     * Parses a parameter list.
     */
    private fun parseParameterList(): List<Param> {
        val params = mutableListOf<Param>()

        while (currentKind() != TokenKind.RIGHT_PAREN && !isAtEnd()) {
            params.add(parseParameter())
            if (currentKind() == TokenKind.COMMA) {
                advance()
            }
        }

        return params
    }

    /**
     * Parses a parameter.
     */
    private fun parseParameter(): Param {
        val start = currentToken.span

        // Parse attributes (if any)
        val attributes = mutableListOf<Attribute>()
        while (currentKind() == TokenKind.AT) {
            attributes.add(parseAttribute())
        }

        // Parse name
        val name = if (currentKind() == TokenKind.IDENTIFIER || currentKind().isBuiltinValue || currentKind().isKeyword) {
            val nameToken = advance()
            nameToken.literal ?: nameToken.kind.toString().lowercase()
        } else {
            error("Expected parameter name")
            ""
        }

        // Parse type
        expectOrError(TokenKind.COLON, "Expected ':'")
        val type = parseTypeDecl()

        // Parse default value (if any)
        val defaultValue = if (currentKind() == TokenKind.ASSIGN) {
            advance()
            parseExpression()
        } else {
            null
        }

        val end = previousToken?.span?.end ?: currentToken.span.end
        return Param(
            attributes = attributes,
            name = name,
            type = type,
            defaultValue = defaultValue,
            span = Span(start.start, end)
        )
    }

    /**
     * Parses a type declaration.
     */
    internal fun parseTypeDecl(): TypeDecl {
        val start = currentToken.span

        when (currentKind()) {
            TokenKind.ABSTRACT -> {
                advance() // consume 'abstract'
                when (currentKind()) {
                    TokenKind.INT -> {
                        advance() // consume 'int'
                        return AbstractIntType(Span(start.start, previousToken?.span?.end ?: start.end))
                    }
                    TokenKind.FLOAT -> {
                        advance() // consume 'float'
                        return AbstractFloatType(Span(start.start, previousToken?.span?.end ?: start.end))
                    }
                    else -> {
                        error("Expected 'int' or 'float' after 'abstract'")
                        // Return a dummy type to avoid NPE
                        return AbstractIntType(Span(start.start, start.end))
                    }
                }
            }

            TokenKind.BOOL -> {
                advance()
                return ScalarType(ScalarKind.BOOL, Span(start.start, previousToken?.span?.end ?: start.end))
            }

            TokenKind.I8 -> {
                advance()
                return ScalarType(ScalarKind.I8, Span(start.start, previousToken?.span?.end ?: start.end))
            }

            TokenKind.U8 -> {
                advance()
                return ScalarType(ScalarKind.U8, Span(start.start, previousToken?.span?.end ?: start.end))
            }

            TokenKind.I16 -> {
                advance()
                return ScalarType(ScalarKind.I16, Span(start.start, previousToken?.span?.end ?: start.end))
            }

            TokenKind.U16 -> {
                advance()
                return ScalarType(ScalarKind.U16, Span(start.start, previousToken?.span?.end ?: start.end))
            }

            TokenKind.I32 -> {
                advance()
                return ScalarType(ScalarKind.I32, Span(start.start, previousToken?.span?.end ?: start.end))
            }

            TokenKind.U32 -> {
                advance()
                return ScalarType(ScalarKind.U32, Span(start.start, previousToken?.span?.end ?: start.end))
            }

            TokenKind.I64 -> {
                advance()
                return ScalarType(ScalarKind.I64, Span(start.start, previousToken?.span?.end ?: start.end))
            }

            TokenKind.U64 -> {
                advance()
                return ScalarType(ScalarKind.U64, Span(start.start, previousToken?.span?.end ?: start.end))
            }

            TokenKind.F16 -> {
                advance()
                return ScalarType(ScalarKind.F16, Span(start.start, previousToken?.span?.end ?: start.end))
            }

            TokenKind.F32 -> {
                advance()
                return ScalarType(ScalarKind.F32, Span(start.start, previousToken?.span?.end ?: start.end))
            }

            TokenKind.F64 -> {
                advance()
                return ScalarType(ScalarKind.F64, Span(start.start, previousToken?.span?.end ?: start.end))
            }

            TokenKind.VEC -> {
                val vecToken = advance()
                val start = vecToken.span
                // Check if size is part of keyword (vec2, vec3, vec4)
                val text = vecToken.literal ?: ""
                val size = if (text.length > 3) {
                    text.substring(3).toIntOrNull() ?: 2
                } else if (currentKind() == TokenKind.INT_LITERAL) {
                    val sizeToken = advance()
                    sizeToken.literal?.toIntOrNull() ?: 2
                } else {
                    2
                }
                return parseVectorType(start, size)
            }

            TokenKind.MAT -> {
                val matToken = advance()
                val start = matToken.span
                val text = matToken.literal ?: ""
                // mat2x3
                if (text.length > 3) {
                    val dims = text.substring(3).split('x')
                    val cols = dims.getOrNull(0)?.toIntOrNull() ?: 2
                    val rows = dims.getOrNull(1)?.toIntOrNull() ?: 2
                    return parseMatrixType(start, cols, rows)
                }
                return parseMatrixType(start)
            }

            TokenKind.ARRAY -> {
                advance()
                return parseArrayType(start)
            }

            TokenKind.PTR -> {
                advance()
                return parsePointerType(start)
            }

            TokenKind.ATOMIC -> {
                advance()
                return parseAtomicType(start)
            }

            TokenKind.SAMPLER -> {
                advance()
                return SamplerType(false, Span(start.start, previousToken?.span?.end ?: start.end))
            }

            TokenKind.SAMPLER_COMPARISON -> {
                advance()
                return SamplerType(true, Span(start.start, previousToken?.span?.end ?: start.end))
            }

            TokenKind.RAY_QUERY -> {
                advance()
                return RayQueryType(Span(start.start, previousToken?.span?.end ?: start.end))
            }

            TokenKind.TEXTURE_1D -> {
                advance()
                return parseTextureType(start, TextureKind.TEXTURE_1D)
            }

            TokenKind.TEXTURE_1D_ARRAY -> {
                advance()
                return parseTextureType(start, TextureKind.TEXTURE_1D_ARRAY)
            }

            TokenKind.TEXTURE_2D -> {
                advance()
                return parseTextureType(start, TextureKind.TEXTURE_2D)
            }

            TokenKind.TEXTURE_2D_ARRAY -> {
                advance()
                return parseTextureType(start, TextureKind.TEXTURE_2D_ARRAY)
            }

            TokenKind.TEXTURE_3D -> {
                advance()
                return parseTextureType(start, TextureKind.TEXTURE_3D)
            }

            TokenKind.TEXTURE_CUBE -> {
                advance()
                return parseTextureType(start, TextureKind.TEXTURE_CUBE)
            }

            TokenKind.TEXTURE_CUBE_ARRAY -> {
                advance()
                return parseTextureType(start, TextureKind.TEXTURE_CUBE_ARRAY)
            }

            TokenKind.TEXTURE_MULTISAMPLED_2D -> {
                advance()
                return parseTextureType(start, TextureKind.TEXTURE_MULTISAMPLED_2D)
            }

            TokenKind.TEXTURE_DEPTH_2D -> {
                advance()
                return parseTextureType(start, TextureKind.TEXTURE_DEPTH_2D)
            }

            TokenKind.TEXTURE_DEPTH_2D_ARRAY -> {
                advance()
                return parseTextureType(start, TextureKind.TEXTURE_DEPTH_2D_ARRAY)
            }

            TokenKind.TEXTURE_DEPTH_CUBE -> {
                advance()
                return parseTextureType(start, TextureKind.TEXTURE_DEPTH_CUBE)
            }

            TokenKind.TEXTURE_DEPTH_CUBE_ARRAY -> {
                advance()
                return parseTextureType(start, TextureKind.TEXTURE_DEPTH_CUBE_ARRAY)
            }

            TokenKind.TEXTURE_DEPTH_MULTISAMPLED_2D -> {
                advance()
                return parseTextureType(start, TextureKind.TEXTURE_DEPTH_MULTISAMPLED_2D)
            }

            TokenKind.TEXTURE_EXTERNAL -> {
                advance()
                return parseTextureType(start, TextureKind.TEXTURE_EXTERNAL)
            }

            TokenKind.TEXTURE_STORAGE_1D -> {
                advance()
                return parseTextureType(start, TextureKind.TEXTURE_STORAGE_1D)
            }

            TokenKind.TEXTURE_STORAGE_2D -> {
                advance()
                return parseTextureType(start, TextureKind.TEXTURE_STORAGE_2D)
            }

            TokenKind.TEXTURE_STORAGE_2D_ARRAY -> {
                advance()
                return parseTextureType(start, TextureKind.TEXTURE_STORAGE_2D_ARRAY)
            }

            TokenKind.TEXTURE_STORAGE_3D -> {
                advance()
                return parseTextureType(start, TextureKind.TEXTURE_STORAGE_3D)
            }

            TokenKind.INT_LITERAL, TokenKind.FLOAT_LITERAL, TokenKind.TRUE, TokenKind.FALSE -> {
                val expr = parseExpression()
                return ConstantType(expr, expr.span)
            }

            TokenKind.IDENTIFIER -> {
                val nameToken = advance()
                val name = nameToken.literal ?: ""
                if (currentKind() == TokenKind.LEFT_ANGLE) {
                    advance()
                    val args = mutableListOf<TypeDecl>()
                    val oldInsideTemplate = isInsideTemplate
                    isInsideTemplate = true
                    try {
                        while (currentKind() != TokenKind.RIGHT_ANGLE && !isAtEnd()) {
                            args.add(parseTypeDecl())
                            if (currentKind() == TokenKind.COMMA) {
                                advance()
                            } else if (currentKind() != TokenKind.RIGHT_ANGLE) {
                                break
                            }
                        }
                    } finally {
                        isInsideTemplate = oldInsideTemplate
                    }
                    expectOrError(TokenKind.RIGHT_ANGLE, "Expected '>'")
                    val end = previousToken?.span?.end ?: currentToken.span.end
                    return TemplateType(name, args, Span(start.start, end))
                }
                return NamedType(name, nameToken.span)
            }

            else -> {
                error("Expected a type, found ${currentKind()}")
                val dummy = NamedType("error_type", start)
                advance()
                return dummy
            }
        }
    }

    /**
     * Parses a vector type like `vec2<f32>`.
     */
    private fun parseVectorType(start: Span, size: Int = 2): VectorType {
        var finalSize = size
        if (currentKind() == TokenKind.INT_LITERAL) {
            val sizeToken = advance()
            finalSize = sizeToken.literal?.toIntOrNull() ?: size
        }

        expectOrError(TokenKind.LEFT_ANGLE, "Expected '<'")
        val oldInsideTemplate = isInsideTemplate
        isInsideTemplate = true
        val elementType = parseTypeDecl()
        if (currentKind() == TokenKind.COMMA) advance()
        isInsideTemplate = oldInsideTemplate
        expectOrError(TokenKind.RIGHT_ANGLE, "Expected '>'")

        val end = previousToken?.span?.end ?: currentToken.span.end
        return VectorType(finalSize, elementType, Span(start.start, end))
    }

    /**
     * Parses a matrix type like `mat2x3<f32>`.
     */
    private fun parseMatrixType(start: Span, cols: Int = 2, rows: Int = 2): MatrixType {
        var finalCols = cols
        var finalRows = rows

        if (currentKind() == TokenKind.INT_LITERAL) {
            val colToken = advance()
            finalCols = colToken.literal?.toIntOrNull() ?: cols

            expectOrError(TokenKind.IDENTIFIER, "Expected 'x'")

            if (currentKind() == TokenKind.INT_LITERAL) {
                val rowToken = advance()
                finalRows = rowToken.literal?.toIntOrNull() ?: rows
            }
        }

        expectOrError(TokenKind.LEFT_ANGLE, "Expected '<'")
        val oldInsideTemplate = isInsideTemplate
        isInsideTemplate = true
        val elementType = parseTypeDecl()
        if (currentKind() == TokenKind.COMMA) advance()
        isInsideTemplate = oldInsideTemplate
        expectOrError(TokenKind.RIGHT_ANGLE, "Expected '>'")

        val end = previousToken?.span?.end ?: currentToken.span.end
        return MatrixType(finalCols, finalRows, elementType, Span(start.start, end))
    }

    /**
     * Parses an array type like `array<i32, 4>`.
     */
    private fun parseArrayType(start: Span): ArrayType {
        expectOrError(TokenKind.LEFT_ANGLE, "Expected '<'")
        val oldInsideTemplate = isInsideTemplate
        isInsideTemplate = true

        val elementType = parseTypeDecl()

        // Parse length (if present)
        var length: Expression? = null
        var stride: Int? = null

        if (currentKind() == TokenKind.COMMA) {
            advance()
            if (currentKind() != TokenKind.RIGHT_ANGLE) {
                length = parseExpression()

                if (currentKind() == TokenKind.COMMA) {
                    advance()
                    if (currentKind() == TokenKind.INT_LITERAL) {
                        val strideToken = advance()
                        stride = strideToken.literal?.toIntOrNull()
                    }
                }
                if (currentKind() == TokenKind.COMMA) advance()
            }
        }

        isInsideTemplate = oldInsideTemplate
        expectOrError(TokenKind.RIGHT_ANGLE, "Expected '>'")

        val end = previousToken?.span?.end ?: currentToken.span.end
        return ArrayType(elementType, length, stride, Span(start.start, end))
    }

    /**
     * Parses a pointer type like `ptr<function, i32>`.
     */
    private fun parsePointerType(start: Span): PointerType {
        expectOrError(TokenKind.LEFT_ANGLE, "Expected '<'")
        val oldInsideTemplate = isInsideTemplate
        isInsideTemplate = true

        // Storage class
        val storageClassToken = advance()
        val storageClass = when (storageClassToken.literal ?: storageClassToken.kind.name.lowercase()) {
            "function" -> StorageClass.FUNCTION
            "private" -> StorageClass.PRIVATE
            "workgroup" -> StorageClass.WORKGROUP
            "uniform" -> StorageClass.UNIFORM
            "storage" -> StorageClass.STORAGE
            "handle" -> StorageClass.HANDLE
            "push_constant" -> StorageClass.PUSH_CONSTANT
            else -> {
                error("Unknown storage class: ${storageClassToken.literal}")
                StorageClass.PRIVATE
            }
        }

        expectOrError(TokenKind.COMMA, "Expected ','")

        // Element type
        val elementType = parseTypeDecl()

        // Optional access mode
        var accessMode: String? = null
        if (currentKind() == TokenKind.COMMA) {
            advance()
            val accessModeToken = advance()
            accessMode = accessModeToken.literal ?: accessModeToken.kind.name.lowercase()
        }

        isInsideTemplate = oldInsideTemplate
        expectOrError(TokenKind.RIGHT_ANGLE, "Expected '>'")

        val end = previousToken?.span?.end ?: currentToken.span.end
        return PointerType(storageClass, elementType, accessMode, Span(start.start, end))
    }

    /**
     * Parses an atomic type like `atomic<i32>`.
     */
    private fun parseAtomicType(start: Span): AtomicType {
        expectOrError(TokenKind.LEFT_ANGLE, "Expected '<'")
        val oldInsideTemplate = isInsideTemplate
        isInsideTemplate = true
        val elementType = parseTypeDecl()
        if (currentKind() == TokenKind.COMMA) {
            advance()
        }
        isInsideTemplate = oldInsideTemplate
        expectOrError(TokenKind.RIGHT_ANGLE, "Expected '>'")
        val end = previousToken?.span?.end ?: currentToken.span.end
        return AtomicType(elementType, Span(start.start, end))
    }

    /**
     * Parses a texture type.
     */
    private fun parseTextureType(start: Span, kind: TextureKind): TextureType {
        var elementType: TypeDecl? = null
        var accessMode: String? = null

        if (currentKind() == TokenKind.LEFT_ANGLE) {
            advance()
            val oldInsideTemplate = isInsideTemplate
            isInsideTemplate = true
            elementType = parseTypeDecl()

            if (currentKind() == TokenKind.COMMA) {
                advance()
                if (currentKind() == TokenKind.READ || currentKind() == TokenKind.WRITE || currentKind() == TokenKind.READ_WRITE) {
                    accessMode = advance().literal ?: previousToken?.kind?.name?.lowercase()
                } else if (currentKind() == TokenKind.IDENTIFIER) {
                    accessMode = advance().literal
                }
            }

            isInsideTemplate = oldInsideTemplate
            expectOrError(TokenKind.RIGHT_ANGLE, "Expected '>'")
        }
        val end = previousToken?.span?.end ?: currentToken.span.end
        return TextureType(kind, elementType, accessMode, Span(start.start, end))
    }

    /**
     * Parses a fragment input.
     */
    private fun parseFragmentInput(): FragmentInput {
        var location: Int? = null
        var builtin: BuiltinValue? = null

        if (currentKind() == TokenKind.AT) {
            advance()
            if (currentKind() == TokenKind.LOCATION) {
                advance()
                if (currentKind() == TokenKind.LEFT_PAREN) {
                    advance()
                    if (currentKind() == TokenKind.INT_LITERAL) {
                        val locToken = advance()
                        location = locToken.literal?.toIntOrNull()
                    }
                    expectOrError(TokenKind.RIGHT_PAREN, "Expected ')'")
                }
            } else if (currentKind() == TokenKind.BUILTIN) {
                advance()
                if (currentKind() == TokenKind.LEFT_PAREN) {
                    advance()
                    // Parse builtin value
                    builtin = parseBuiltinValue()
                    expectOrError(TokenKind.RIGHT_PAREN, "Expected ')'")
                }
            }
        }

        expectOrError(TokenKind.COLON, "Expected ':'")
        val type = parseTypeDecl()

        return FragmentInput(location, builtin, type)
    }

    /**
     * Parses a vertex output.
     */
    private fun parseVertexOutput(): VertexOutput {
        var location: Int? = null
        var builtin: BuiltinValue? = null

        if (currentKind() == TokenKind.AT) {
            advance()
            if (currentKind() == TokenKind.LOCATION) {
                advance()
                if (currentKind() == TokenKind.LEFT_PAREN) {
                    advance()
                    if (currentKind() == TokenKind.INT_LITERAL) {
                        val locToken = advance()
                        location = locToken.literal?.toIntOrNull()
                    }
                    expectOrError(TokenKind.RIGHT_PAREN, "Expected ')'")
                }
            } else if (currentKind() == TokenKind.BUILTIN) {
                advance()
                if (currentKind() == TokenKind.LEFT_PAREN) {
                    advance()
                    // Parse builtin value
                    builtin = parseBuiltinValue()
                    expectOrError(TokenKind.RIGHT_PAREN, "Expected ')'")
                }
            }
        }

        expectOrError(TokenKind.COLON, "Expected ':'")
        val type = parseTypeDecl()

        return VertexOutput(location, builtin, type)
    }

    /**
     * Parses a builtin value.
     */
    private fun parseBuiltinValue(): BuiltinValue {
        when (currentKind()) {
            TokenKind.POSITION -> {
                advance(); return BuiltinValue.POSITION
            }

            TokenKind.VERTEX_INDEX -> {
                advance(); return BuiltinValue.VERTEX_INDEX
            }

            TokenKind.INSTANCE_INDEX -> {
                advance(); return BuiltinValue.INSTANCE_INDEX
            }

            TokenKind.DRAW_INDEX -> {
                advance(); return BuiltinValue.DRAW_INDEX
            }

            TokenKind.FRONT_FACING -> {
                advance(); return BuiltinValue.FRONT_FACING
            }

            TokenKind.PRIMITIVE_INDEX -> {
                advance(); return BuiltinValue.PRIMITIVE_INDEX
            }

            TokenKind.SAMPLE_INDEX -> {
                advance(); return BuiltinValue.SAMPLE_INDEX
            }

            TokenKind.SAMPLE_MASK -> {
                advance(); return BuiltinValue.SAMPLE_MASK
            }

            TokenKind.VIEWPORT_INDEX -> {
                advance(); return BuiltinValue.VIEWPORT_INDEX
            }

            TokenKind.POINTSIZE -> {
                advance(); return BuiltinValue.POINTSIZE
            }

            TokenKind.CLIP_DISTANCES -> {
                advance(); return BuiltinValue.CLIP_DISTANCES
            }

            TokenKind.CULL_DISTANCES -> {
                advance(); return BuiltinValue.CULL_DISTANCES
            }

            TokenKind.DEVICE_INDEX -> {
                advance(); return BuiltinValue.DEVICE_INDEX
            }

            TokenKind.VIEW_INDEX -> {
                advance(); return BuiltinValue.VIEW_INDEX
            }

            TokenKind.WORKGROUP_ID -> {
                advance(); return BuiltinValue.WORKGROUP_ID
            }

            TokenKind.NUM_WORKGROUPS -> {
                advance(); return BuiltinValue.NUM_WORKGROUPS
            }

            TokenKind.GLOBAL_INVOCATION_ID -> {
                advance(); return BuiltinValue.GLOBAL_INVOCATION_ID
            }

            TokenKind.LOCAL_INVOCATION_ID -> {
                advance(); return BuiltinValue.LOCAL_INVOCATION_ID
            }

            TokenKind.LOCAL_INVOCATION_INDEX -> {
                advance(); return BuiltinValue.LOCAL_INVOCATION_INDEX
            }

            else -> {
                error("Expected builtin value")
                return BuiltinValue.POSITION
            }
        }
    }

    // ========================================================================
    // Expression parsing
    // ========================================================================

    /**
     * Parses an expression with the lowest precedence.
     */
    private fun parseExpression(): Expression {
        return parseTernaryExpression()
    }

    /**
     * Parses a ternary (conditional) expression.
     */
    private fun parseTernaryExpression(): Expression {
        val left = parseLogicalOrExpression()

        if (currentKind() == TokenKind.QUESTION) {
            advance()
            val trueExpr = parseExpression()
            expectOrError(TokenKind.COLON, "Expected ':' after ternary true expression")
            val falseExpr = parseExpression()

            val start = left.span.start
            val end = previousToken?.span?.end ?: currentToken.span.end
            return TernaryExpr(left, trueExpr, falseExpr, Span(start, end))
        }

        return left
    }

    /**
     * Parses a logical OR expression.
     */
    private fun parseLogicalOrExpression(): Expression {
        var left = parseLogicalAndExpression()

        while (currentKind() == TokenKind.OR) {
            advance()
            val right = parseLogicalAndExpression()

            val start = left.span.start
            val end = right.span.end
            left = BinaryExpr(left, BinaryOperator.LOGICAL_OR, right, Span(start, end))
        }

        return left
    }

    /**
     * Parses a logical AND expression.
     */
    private fun parseLogicalAndExpression(): Expression {
        var left = parseBitwiseOrExpression()

        while (currentKind() == TokenKind.AND) {
            advance()
            val right = parseBitwiseOrExpression()

            val start = left.span.start
            val end = right.span.end
            left = BinaryExpr(left, BinaryOperator.LOGICAL_AND, right, Span(start, end))
        }

        return left
    }

    /**
     * Parses a bitwise OR expression.
     */
    private fun parseBitwiseOrExpression(): Expression {
        var left = parseBitwiseXorExpression()

        while (currentKind() == TokenKind.PIPE) {
            advance()
            val right = parseBitwiseXorExpression()

            val start = left.span.start
            val end = right.span.end
            left = BinaryExpr(left, BinaryOperator.BITWISE_OR, right, Span(start, end))
        }

        return left
    }

    /**
     * Parses a bitwise XOR expression.
     */
    private fun parseBitwiseXorExpression(): Expression {
        var left = parseBitwiseAndExpression()

        while (currentKind() == TokenKind.CARET) {
            advance()
            val right = parseBitwiseAndExpression()

            val start = left.span.start
            val end = right.span.end
            left = BinaryExpr(left, BinaryOperator.BITWISE_XOR, right, Span(start, end))
        }

        return left
    }

    /**
     * Parses a bitwise AND expression.
     */
    private fun parseBitwiseAndExpression(): Expression {
        var left = parseEqualityExpression()

        while (currentKind() == TokenKind.AMPERSAND) {
            advance()
            val right = parseEqualityExpression()

            val start = left.span.start
            val end = right.span.end
            left = BinaryExpr(left, BinaryOperator.BITWISE_AND, right, Span(start, end))
        }

        return left
    }

    /**
     * Parses an equality expression.
     */
    private fun parseEqualityExpression(): Expression {
        var left = parseRelationalExpression()

        while (currentKind() == TokenKind.EQ || currentKind() == TokenKind.NEQ) {
            val op = if (currentKind() == TokenKind.EQ) BinaryOperator.EQ else BinaryOperator.NEQ
            advance()
            val right = parseRelationalExpression()

            val start = left.span.start
            val end = right.span.end
            left = BinaryExpr(left, op, right, Span(start, end))
        }

        return left
    }

    /**
     * Parses a relational expression.
     */
    private fun parseRelationalExpression(): Expression {
        var left = parseShiftExpression()

        while (currentKind() == TokenKind.LEFT_ANGLE || currentKind() == TokenKind.LTE ||
            (currentKind() == TokenKind.RIGHT_ANGLE && !isInsideTemplate) || currentKind() == TokenKind.GTE
        ) {
            val op = when (currentKind()) {
                TokenKind.LEFT_ANGLE -> BinaryOperator.LT
                TokenKind.LTE -> BinaryOperator.LTE
                TokenKind.RIGHT_ANGLE -> BinaryOperator.GT
                TokenKind.GTE -> BinaryOperator.GTE
                else -> BinaryOperator.LT
            }
            advance()
            val right = parseShiftExpression()

            val start = left.span.start
            val end = right.span.end
            left = BinaryExpr(left, op, right, Span(start, end))
        }

        return left
    }

    /**
     * Parses a shift expression.
     */
    private fun parseShiftExpression(): Expression {
        var left = parseAdditiveExpression()

        while (currentKind() == TokenKind.LEFT_SHIFT || currentKind() == TokenKind.RIGHT_SHIFT) {
            val op =
                if (currentKind() == TokenKind.LEFT_SHIFT) BinaryOperator.LEFT_SHIFT else BinaryOperator.RIGHT_SHIFT
            advance()
            val right = parseAdditiveExpression()

            val start = left.span.start
            val end = right.span.end
            left = BinaryExpr(left, op, right, Span(start, end))
        }

        return left
    }

    /**
     * Parses an additive expression.
     */
    private fun parseAdditiveExpression(): Expression {
        var left = parseMultiplicativeExpression()

        while (currentKind() == TokenKind.PLUS || currentKind() == TokenKind.MINUS) {
            val op = if (currentKind() == TokenKind.PLUS) BinaryOperator.ADD else BinaryOperator.SUBTRACT
            advance()
            val right = parseMultiplicativeExpression()

            val start = left.span.start
            val end = right.span.end
            left = BinaryExpr(left, op, right, Span(start, end))
        }

        return left
    }

    /**
     * Parses a multiplicative expression.
     */
    private fun parseMultiplicativeExpression(): Expression {
        var left = parseUnaryExpression()

        while (currentKind() == TokenKind.STAR || currentKind() == TokenKind.SLASH || currentKind() == TokenKind.PERCENT) {
            val op = when (currentKind()) {
                TokenKind.STAR -> BinaryOperator.MULTIPLY
                TokenKind.SLASH -> BinaryOperator.DIVIDE
                TokenKind.PERCENT -> BinaryOperator.MODULO
                else -> BinaryOperator.MULTIPLY
            }
            advance()
            val right = parseUnaryExpression()

            val start = left.span.start
            val end = right.span.end
            left = BinaryExpr(left, op, right, Span(start, end))
        }

        return left
    }

    /**
     * Parses a unary expression.
     */
    private fun parseUnaryExpression(): Expression {
        when (currentKind()) {
            TokenKind.MINUS -> {
                advance()
                val operand = parseUnaryExpression()
                return UnaryExpr(UnaryOperator.MINUS, operand, operand.span)
            }

            TokenKind.PLUS -> {
                advance()
                val operand = parseUnaryExpression()
                return UnaryExpr(UnaryOperator.PLUS, operand, operand.span)
            }

            TokenKind.NOT -> {
                advance()
                val operand = parseUnaryExpression()
                return UnaryExpr(UnaryOperator.NOT, operand, operand.span)
            }

            TokenKind.TILDE -> {
                advance()
                val operand = parseUnaryExpression()
                return UnaryExpr(UnaryOperator.BITWISE_NOT, operand, operand.span)
            }

            TokenKind.STAR -> {
                advance()
                val operand = parseUnaryExpression()
                return UnaryExpr(UnaryOperator.DEREF, operand, operand.span)
            }

            TokenKind.AMPERSAND -> {
                advance()
                val operand = parseUnaryExpression()
                return UnaryExpr(UnaryOperator.ADDRESS_OF, operand, operand.span)
            }

            else -> return parsePostfixExpression()
        }
    }

    /**
     * Parses a postfix expression.
     */
    private fun parsePostfixExpression(): Expression {
        var left = parsePrimaryExpression()

        while (true) {
            when (currentKind()) {
                TokenKind.LEFT_PAREN -> {
                    advance()
                    val args = mutableListOf<Expression>()
                    while (currentKind() != TokenKind.RIGHT_PAREN && !isAtEnd()) {
                        val startToken = currentToken
                        args.add(parseExpression())
                        if (currentKind() == TokenKind.COMMA) {
                            advance()
                        }
                        if (startToken == currentToken) advance()
                    }
                    expectOrError(TokenKind.RIGHT_PAREN, "Expected ')'")
                    val start = left.span.start
                    val end = previousToken?.span?.end ?: currentToken.span.end
                    left = CallExpr(left, args, null, Span(start, end))
                }

                TokenKind.LEFT_ANGLE -> {
                    // This could be a comparison, but in postfix position it's likely template args
                    // e.g. vec4<f32>(...)
                    // For now, simple heuristic: if there's a '(' later, it's a call with template args
                    val isTemplate = if (left is IdentExpr) {
                        val name = left.name
                        name == "vec" || name == "vec2" || name == "vec3" || name == "vec4" ||
                        name == "mat" || name.startsWith("mat") ||
                        name == "array" || name == "atomic" || name == "ptr" ||
                        name.startsWith("texture_")
                    } else {
                        false
                    }

                    if (!isTemplate) {
                        break
                    }
                    advance()
                    val templateArgs = mutableListOf<TypeDecl>()
                    val oldInsideTemplate = isInsideTemplate
                    isInsideTemplate = true
                    try {
                        while (currentKind() != TokenKind.RIGHT_ANGLE && !isAtEnd()) {
                            val startToken = currentToken
                            templateArgs.add(parseTypeDecl())
                            if (currentKind() == TokenKind.COMMA) {
                                advance()
                            }
                            if (startToken == currentToken) advance()
                        }
                    } finally {
                        isInsideTemplate = oldInsideTemplate
                    }
                    expectOrError(TokenKind.RIGHT_ANGLE, "Expected '>'")

                    if (currentKind() == TokenKind.LEFT_PAREN) {
                        advance()
                        val args = mutableListOf<Expression>()
                        while (currentKind() != TokenKind.RIGHT_PAREN && !isAtEnd()) {
                            val startToken = currentToken
                            args.add(parseExpression())
                            if (currentKind() == TokenKind.COMMA) {
                                advance()
                            }
                            if (startToken == currentToken) advance()
                        }
                        expectOrError(TokenKind.RIGHT_PAREN, "Expected ')'")
                        val start = left.span.start
                        val end = previousToken?.span?.end ?: currentToken.span.end
                        left = CallExpr(left, args, templateArgs, Span(start, end))
                    } else {
                        // It was just template args (not possible in expressions except for calls, but let's be robust)
                        val start = left.span.start
                        val end = previousToken?.span?.end ?: currentToken.span.end
                        left = CallExpr(left, emptyList(), templateArgs, Span(start, end))
                    }
                }

                TokenKind.DOT -> {
                    advance()
                    // ✅ ACCEPTER : IDENTIFIER + builtin keywords + standard keywords
                    if (currentKind() == TokenKind.IDENTIFIER || currentKind().isBuiltinValue || currentKind().isKeyword) {
                        val memberToken = advance()
                        // Pour les builtins, literal est null → utiliser le nom du TokenKind
                        val member = memberToken.literal ?: memberToken.kind.toString().lowercase()
                        val start = left.span.start
                        val end = memberToken.span.end
                        
                        // Check if this is a predeclared enumerant reference
                        // e.g., AddressMode.clamp_to_edge
                        val objectName = when (left) {
                            is IdentExpr -> left.name
                            else -> null
                        }
                        
                        if (objectName != null) {
                            // Check if the object is a known predeclared enumerant category
                            val category = objectName
                            val enumerant = getPredeclaredEnumerant(category, member, Span(start, end))
                            if (enumerant != null) {
                                left = PredeclaredEnumerantExpr(category, enumerant, Span(start, end))
                            } else {
                                // Check if this is a known category with invalid value
                                val validValues = getPredeclaredEnumerantValues(category)
                                if (validValues.isNotEmpty()) {
                                    // This is a known category but invalid value
                                    error("Invalid ${category} value '$member'. Valid values are: ${validValues.joinToString(", ")}")
                                }
                                // Fallback to MemberAccessExpr for unknown categories or invalid values
                                // Note: User-defined enum member access will be resolved to EnumMemberExpr in semantic analysis
                                left = MemberAccessExpr(left, member, Span(start, end))
                            }
                        } else {
                            left = MemberAccessExpr(left, member, Span(start, end))
                        }
                    } else {
                        error("Expected member name after '.'")
                        break
                    }
                }

                TokenKind.LEFT_BRACKET -> {
                    advance()
                    val index = parseExpression()
                    expectOrError(TokenKind.RIGHT_BRACKET, "Expected ']'")
                    val start = left.span.start
                    val end = previousToken?.span?.end ?: currentToken.span.end
                    left = IndexExpr(left, index, Span(start, end))
                }


                else -> break
            }
        }

        return left
    }

    /**
     * Parses a primary expression.
     */
    private fun parsePrimaryExpression(): Expression {
        val start = currentToken.span

        return when (currentKind()) {
            TokenKind.INT_LITERAL -> {
                val token = advance()
                val suffix = token.literal?.takeLastWhile { it.isLetter() }?.takeIf { it.isNotEmpty() }
                val cleanLiteral = token.literal?.dropLast(suffix?.length ?: 0)
                IntLiteral(parseIntegerLiteralValue(cleanLiteral), suffix, token.span)
            }

            TokenKind.UINT_LITERAL -> {
                val token = advance()
                val suffix = token.literal?.takeLastWhile { it.isLetter() }?.takeIf { it.isNotEmpty() } ?: "u"
                val cleanLiteral = token.literal?.dropLast(suffix.length)
                IntLiteral(parseIntegerLiteralValue(cleanLiteral), suffix, token.span)
            }

            TokenKind.FLOAT_LITERAL -> {
                val token = advance()
                val cleanLiteral = token.literal?.dropLastWhile { it.lowercaseChar() in setOf('u', 'i', 'f', 'h') }
                val suffix = token.literal?.takeLastWhile { it.lowercaseChar() in setOf('u', 'i', 'f', 'h') }?.takeIf { it.isNotEmpty() }
                FloatLiteral(cleanLiteral?.toDoubleOrNull() ?: 0.0, suffix, token.span)
            }

            TokenKind.TRUE -> {
                advance()
                BoolLiteral(true, Span(start.start, previousToken?.span?.end ?: start.end))
            }

            TokenKind.FALSE -> {
                advance()
                BoolLiteral(false, Span(start.start, previousToken?.span?.end ?: start.end))
            }

            TokenKind.STRING_LITERAL -> {
                val token = advance()
                // Remove quotes from literal
                val value = token.literal?.removeSurrounding("\"") ?: ""
                StringLiteral(value, token.span)
            }

            TokenKind.IDENTIFIER,
            TokenKind.BOOL,
            TokenKind.I8, TokenKind.U8,
            TokenKind.I16, TokenKind.U16,
            TokenKind.I32, TokenKind.U32,
            TokenKind.I64, TokenKind.U64,
            TokenKind.F16, TokenKind.F32, TokenKind.F64,
            TokenKind.VEC, TokenKind.MAT, TokenKind.ARRAY,
            TokenKind.SAMPLER, TokenKind.SAMPLER_COMPARISON, TokenKind.RAY_QUERY,
            TokenKind.TEXTURE_1D, TokenKind.TEXTURE_2D, TokenKind.TEXTURE_3D,
            TokenKind.TEXTURE_CUBE, TokenKind.TEXTURE_EXTERNAL,
            TokenKind.BUILTIN, TokenKind.COMPUTE, TokenKind.POSITION, TokenKind.VERTEX_INDEX, TokenKind.INSTANCE_INDEX,
            TokenKind.DRAW_INDEX,
            TokenKind.FRONT_FACING, TokenKind.PRIMITIVE_INDEX, TokenKind.SAMPLE_INDEX,
            TokenKind.SAMPLE_MASK, TokenKind.VIEWPORT_INDEX, TokenKind.POINTSIZE,
            TokenKind.CLIP_DISTANCES, TokenKind.CULL_DISTANCES, TokenKind.DEVICE_INDEX,
            TokenKind.VIEW_INDEX, TokenKind.WORKGROUP_ID, TokenKind.NUM_WORKGROUPS,
            TokenKind.GLOBAL_INVOCATION_ID, TokenKind.LOCAL_INVOCATION_ID, TokenKind.LOCAL_INVOCATION_INDEX -> {
                val token = advance()
                IdentExpr(token.literal ?: token.kind.name.lowercase(), token.span)
            }

            TokenKind.LEFT_PAREN -> {
                advance()
                val expr = parseExpression()
                expectOrError(TokenKind.RIGHT_PAREN, "Expected ')'")
                val end = previousToken?.span?.end ?: currentToken.span.end
                expr // Return the expression inside parentheses
            }

            TokenKind.BITCAST -> {
                advance()
                expectOrError(TokenKind.LEFT_ANGLE, "Expected '<' after 'bitcast'")
                val oldInsideTemplate = isInsideTemplate
                isInsideTemplate = true
                val type = parseTypeDecl()
                isInsideTemplate = oldInsideTemplate
                expectOrError(TokenKind.RIGHT_ANGLE, "Expected '>' after 'bitcast' type")
                expectOrError(TokenKind.LEFT_PAREN, "Expected '(' after 'bitcast'")
                val expr = parseExpression()
                expectOrError(TokenKind.RIGHT_PAREN, "Expected ')' after 'bitcast' expression")
                val end = previousToken?.span?.end ?: currentToken.span.end
                BitcastExpr(expr, type, Span(start.start, end))
            }

            else -> {
                error("Expected a primary expression, found ${currentKind()}")
                val dummy = IdentExpr("", start)
                advance()
                dummy
            }
        }
    }

    private fun parseIntegerLiteralValue(literal: String?): Long {
        val cleaned = literal?.replace("_", "") ?: return 0
        return if (cleaned.startsWith("0x", ignoreCase = true)) {
            cleaned.drop(2).toLongOrNull(16) ?: 0
        } else {
            cleaned.toLongOrNull() ?: 0
        }
    }

    // ========================================================================
    // Statement parsing
    // ========================================================================

    /**
     * Parses a block statement.
     */
    private fun parseBlockStatement(): BlockStatement {
        val start = currentToken.span

        expectOrError(TokenKind.LEFT_BRACE, "Expected '{'")

        val statements = mutableListOf<Statement>()
        while (currentKind() != TokenKind.RIGHT_BRACE && !isAtEnd()) {
            val startToken = currentToken
            statements.add(parseStatement())
            if (startToken == currentToken) {
                // Safety break to avoid infinite loop
                error("Stuck at token ${currentKind()} at top of statement loop")
                advance()
            }
        }

        expectOrError(TokenKind.RIGHT_BRACE, "Expected '}'")

        val end = previousToken?.span?.end ?: currentToken.span.end
        return BlockStatement(statements, Span(start.start, end))
    }

    /**
     * Parses a statement.
     */
    private fun parseStatement(): Statement {
        val start = currentToken.span

        if (expect(TokenKind.SEMICOLON)) {
            return EmptyStatement(Span(start.start, previousToken?.span?.end ?: start.end))
        }

        return when (currentKind()) {
            TokenKind.LEFT_BRACE -> parseBlockStatement()
            TokenKind.IF -> parseIfStatement()
            TokenKind.SWITCH -> parseSwitchStatement()
            TokenKind.LOOP -> parseLoopStatement()
            TokenKind.WHILE -> parseWhileStatement()
            TokenKind.FOR -> parseForStatement()
            TokenKind.DIAGNOSTIC -> {
                val directive = parseDiagnosticDirective()
                DiagnosticStatement(directive.severity, directive.rule, directive.span)
            }
            TokenKind.BREAK -> {
                advance()
                if (currentKind() == TokenKind.IF) {
                    advance() // consume 'if'
                    val hasParen = expect(TokenKind.LEFT_PAREN)
                    val condition = parseExpression()
                    if (hasParen) {
                        expectOrError(TokenKind.RIGHT_PAREN, "Expected ')'")
                    }
                    expectOrError(TokenKind.SEMICOLON, "Expected ';'")
                    BreakIfStatement(condition, Span(start.start, previousToken?.span?.end ?: start.end))
                } else {
                    expectOrError(TokenKind.SEMICOLON, "Expected ';'")
                    BreakStatement(Span(start.start, previousToken?.span?.end ?: start.end))
                }
            }

            TokenKind.CONTINUE -> {
                advance()
                expectOrError(TokenKind.SEMICOLON, "Expected ';'")
                ContinueStatement(Span(start.start, previousToken?.span?.end ?: start.end))
            }

            TokenKind.RETURN -> {
                advance()
                val value = if (currentKind() != TokenKind.SEMICOLON) {
                    parseExpression()
                } else {
                    null
                }
                expectOrError(TokenKind.SEMICOLON, "Expected ';'")
                ReturnStatement(value, Span(start.start, previousToken?.span?.end ?: start.end))
            }

            TokenKind.DISCARD -> {
                advance()
                expectOrError(TokenKind.SEMICOLON, "Expected ';'")
                DiscardStatement(Span(start.start, previousToken?.span?.end ?: start.end))
            }

            TokenKind.LET, TokenKind.CONST, TokenKind.VAR -> parseVariableDeclStatement()
            TokenKind.CONST_ASSERT, TokenKind.STATIC_ASSERT -> parseConstAssertStatement()
            TokenKind.UNDERSCORE -> {
                advance()
                expectOrError(TokenKind.ASSIGN, "Expected '=' after '_'")
                val expr = parseExpression()
                expectOrError(TokenKind.SEMICOLON, "Expected ';'")
                PhonyAssignmentStatement(expr, Span(start.start, previousToken?.span?.end ?: start.end))
            }
            else -> {
                // Expression or assignment or increment/decrement
                val expr = parseExpression()

                val statement = when (currentKind()) {
                    TokenKind.ASSIGN, TokenKind.PLUS_ASSIGN, TokenKind.MINUS_ASSIGN,
                    TokenKind.STAR_ASSIGN, TokenKind.SLASH_ASSIGN, TokenKind.PERCENT_ASSIGN,
                    TokenKind.AND_ASSIGN, TokenKind.OR_ASSIGN, TokenKind.XOR_ASSIGN,
                    TokenKind.LEFT_SHIFT_ASSIGN, TokenKind.RIGHT_SHIFT_ASSIGN -> {
                        val op = currentKind()
                        advance()
                        val rhs = parseExpression()
                        expectOrError(TokenKind.SEMICOLON, "Expected ';'")
                        AssignmentStatement(expr, rhs, mapAssignmentOp(op), Span(start.start, previousToken?.span?.end ?: start.end))
                    }

                    TokenKind.INCREMENT -> {
                        advance()
                        expectOrError(TokenKind.SEMICOLON, "Expected ';'")
                        IncDecStatement(expr, true, Span(start.start, previousToken?.span?.end ?: start.end))
                    }

                    TokenKind.DECREMENT -> {
                        advance()
                        expectOrError(TokenKind.SEMICOLON, "Expected ';'")
                        IncDecStatement(expr, false, Span(start.start, previousToken?.span?.end ?: start.end))
                    }

                    else -> {
                        expectOrError(TokenKind.SEMICOLON, "Expected ';'")
                        ExpressionStatement(expr, Span(start.start, previousToken?.span?.end ?: start.end))
                    }
                }
                statement
            }
        }
    }

    private fun mapAssignmentOp(kind: TokenKind): BinaryOperator? = when (kind) {
        TokenKind.ASSIGN -> null
        TokenKind.PLUS_ASSIGN -> BinaryOperator.ADD
        TokenKind.MINUS_ASSIGN -> BinaryOperator.SUBTRACT
        TokenKind.STAR_ASSIGN -> BinaryOperator.MULTIPLY
        TokenKind.SLASH_ASSIGN -> BinaryOperator.DIVIDE
        TokenKind.PERCENT_ASSIGN -> BinaryOperator.MODULO
        TokenKind.AND_ASSIGN -> BinaryOperator.BITWISE_AND
        TokenKind.OR_ASSIGN -> BinaryOperator.BITWISE_OR
        TokenKind.XOR_ASSIGN -> BinaryOperator.BITWISE_XOR
        TokenKind.LEFT_SHIFT_ASSIGN -> BinaryOperator.LEFT_SHIFT
        TokenKind.RIGHT_SHIFT_ASSIGN -> BinaryOperator.RIGHT_SHIFT
        else -> null
    }

    /**
     * Parses an if statement.
     */
    private fun parseIfStatement(): IfStatement {
        val start = currentToken.span

        expectOrError(TokenKind.IF, "Expected 'if'")
        val hasParen = expect(TokenKind.LEFT_PAREN)
        val condition = parseExpression()
        if (hasParen) {
            expectOrError(TokenKind.RIGHT_PAREN, "Expected ')'")
        }

        val thenBranch = parseStatement()

        val elseBranch = if (currentKind() == TokenKind.ELSE) {
            advance()
            parseStatement()
        } else {
            null
        }

        val end = elseBranch?.span?.end ?: thenBranch.span.end
        return IfStatement(condition, thenBranch, elseBranch, Span(start.start, end))
    }

    /**
     * Parses a switch statement.
     */
    private fun parseSwitchStatement(): SwitchStatement {
        val start = currentToken.span

        expectOrError(TokenKind.SWITCH, "Expected 'switch'")
        val hasParen = expect(TokenKind.LEFT_PAREN)
        val expression = parseExpression()
        if (hasParen) {
            expectOrError(TokenKind.RIGHT_PAREN, "Expected ')'")
        }

        val body = parseSwitchBody()

        val end = body.span.end
        return SwitchStatement(expression, body, Span(start.start, end))
    }

    /**
     * Parses a switch body.
     */
    private fun parseSwitchBody(): SwitchBody {
        val start = currentToken.span

        expectOrError(TokenKind.LEFT_BRACE, "Expected '{'")

        val cases = mutableListOf<SwitchCase>()
        while (currentKind() != TokenKind.RIGHT_BRACE && !isAtEnd()) {
            cases.add(parseSwitchCase())
        }

        expectOrError(TokenKind.RIGHT_BRACE, "Expected '}'")

        val end = previousToken?.span?.end ?: currentToken.span.end
        return SwitchBody(cases, Span(start.start, end))
    }

    /**
     * Parses a switch case.
     */
    private fun parseSwitchCase(): SwitchCase {
        val selectors = mutableListOf<Expression>()
        var isDefault = false

        if (currentKind() == TokenKind.CASE) {
            advance()
            // First selector
            if (currentKind() == TokenKind.DEFAULT) {
                advance()
                isDefault = true
            } else {
                selectors.add(parseExpression())
            }

            // Additional selectors
            while (expect(TokenKind.COMMA)) {
                if (currentKind() == TokenKind.DEFAULT) {
                    advance()
                    isDefault = true
                } else if (currentKind() == TokenKind.COLON || currentKind() == TokenKind.LEFT_BRACE) {
                    // Trailing comma
                    break
                } else {
                    selectors.add(parseExpression())
                }
            }
        } else if (currentKind() == TokenKind.DEFAULT) {
            advance()
            isDefault = true
        } else {
            error("Expected 'case' or 'default'")
        }

        expect(TokenKind.COLON) // Colon is optional after case selectors in some WGSL versions but usually present

        val body = parseBlockStatement()
        return Case(selectors, isDefault, body, body.span)
    }

    /**
     * Parses a loop statement.
     */
    private fun parseLoopStatement(): LoopStatement {
        val start = currentToken.span

        expectOrError(TokenKind.LOOP, "Expected 'loop'")
        expectOrError(TokenKind.LEFT_BRACE, "Expected '{'")

        val bodyStatements = mutableListOf<Statement>()
        var continuing: BlockStatement? = null

        while (currentKind() != TokenKind.RIGHT_BRACE && !isAtEnd()) {
            if (expect(TokenKind.SEMICOLON)) continue

            if (currentKind() == TokenKind.CONTINUING) {
                continuing = parseContinuingStatement()
                break
            }

            bodyStatements.add(parseStatement())
        }

        expectOrError(TokenKind.RIGHT_BRACE, "Expected '}'")

        val end = previousToken?.span?.end ?: currentToken.span.end
        val body = BlockStatement(bodyStatements, Span(start.start, end))

        return LoopStatement(body, continuing, Span(start.start, end))
    }

    private fun parseContinuingStatement(): BlockStatement {
        expectOrError(TokenKind.CONTINUING, "Expected 'continuing'")
        return parseBlockStatement()
    }

    /**
     * Parses a while statement.
     */
    private fun parseWhileStatement(): WhileStatement {
        val start = currentToken.span

        expectOrError(TokenKind.WHILE, "Expected 'while'")
        val hasParen = expect(TokenKind.LEFT_PAREN)
        val condition = parseExpression()
        if (hasParen) {
            expectOrError(TokenKind.RIGHT_PAREN, "Expected ')'")
        }

        val body = parseBlockStatement()

        val continuing = if (currentKind() == TokenKind.CONTINUING) {
            advance()
            parseBlockStatement()
        } else {
            null
        }

        val end = continuing?.span?.end ?: body.span.end
        return WhileStatement(condition, body, continuing, Span(start.start, end))
    }

    /**
     * Parses a for statement.
     */
    private fun parseForStatement(): ForStatement {
        val start = currentToken.span

        expectOrError(TokenKind.FOR, "Expected 'for'")
        val hasParen = expect(TokenKind.LEFT_PAREN)

        // Parse init
        val init = if (currentKind() != TokenKind.SEMICOLON) {
            val stmt = parseVariableDeclStatement()
            expectOrError(TokenKind.SEMICOLON, "Expected ';'")
            stmt
        } else {
            advance()
            null
        }

        // Parse condition
        val condition = if (currentKind() != TokenKind.SEMICOLON) {
            parseExpression()
        } else {
            null
        }
        expectOrError(TokenKind.SEMICOLON, "Expected ';'")

        // Parse update
        val update = if (currentKind() != TokenKind.RIGHT_PAREN && currentKind() != TokenKind.LEFT_BRACE) {
            val expr = parseExpression()
            when (currentKind()) {
                TokenKind.ASSIGN, TokenKind.PLUS_ASSIGN, TokenKind.MINUS_ASSIGN,
                TokenKind.STAR_ASSIGN, TokenKind.SLASH_ASSIGN, TokenKind.PERCENT_ASSIGN,
                TokenKind.AND_ASSIGN, TokenKind.OR_ASSIGN, TokenKind.XOR_ASSIGN,
                TokenKind.LEFT_SHIFT_ASSIGN, TokenKind.RIGHT_SHIFT_ASSIGN -> {
                    val op = currentKind()
                    advance()
                    val rhs = parseExpression()
                    AssignmentStatement(expr, rhs, mapAssignmentOp(op), Span(expr.span.start, previousToken?.span?.end ?: expr.span.end))
                }
                TokenKind.INCREMENT -> {
                    advance()
                    IncDecStatement(expr, true, Span(expr.span.start, previousToken?.span?.end ?: expr.span.end))
                }
                TokenKind.DECREMENT -> {
                    advance()
                    IncDecStatement(expr, false, Span(expr.span.start, previousToken?.span?.end ?: expr.span.end))
                }
                else -> {
                    ExpressionStatement(expr, Span(expr.span.start, expr.span.end))
                }
            }
        } else {
            null
        }
        
        if (hasParen) {
            expectOrError(TokenKind.RIGHT_PAREN, "Expected ')'")
        }

        val body = parseBlockStatement()

        val end = body.span.end
        return ForStatement(init, condition, update, body, Span(start.start, end))
    }

    /**
     * Parses a local variable declaration statement.
     */
    private fun parseVariableDeclStatement(): VariableDeclStatement {
        val start = currentToken.span

        // Parse kind (let, const, var)
        val kind = when (currentKind()) {
            TokenKind.LET -> VariableDeclKind.LET
            TokenKind.CONST -> VariableDeclKind.CONST
            TokenKind.VAR -> VariableDeclKind.VAR
            else -> {
                error("Expected 'let', 'const', or 'var'")
                VariableDeclKind.LET
            }
        }
        advance()

        // Parse name
        val name = if (currentKind() == TokenKind.IDENTIFIER || currentKind().isBuiltinValue || currentKind().isKeyword) {
            val nameToken = advance()
            nameToken.literal ?: nameToken.kind.toString().lowercase()
        } else {
            error("Expected variable name")
            ""
        }

        // Parse type annotation (if any)
        val type = if (currentKind() == TokenKind.COLON) {
            advance()
            parseTypeDecl()
        } else {
            null
        }

        // Parse initializer
        val initializer = if (currentKind() == TokenKind.ASSIGN) {
            advance()
            parseExpression()
        } else {
            null
        }

        expectOrError(TokenKind.SEMICOLON, "Expected ';'")

        val end = previousToken?.span?.end ?: currentToken.span.end
        return VariableDeclStatement(kind, name, null, null, type, initializer, Span(start.start, end))
    }
}

/**
 * A parse error.
 */
data class ParseError(
    /** The error message. */
    val message: String,
    /** The span where the error occurred. */
    val span: Span,
)

/**
 * Result of parsing a WGSL source string.
 */
data class ParseResult(
    /** The parsed translation unit. It may be partial when [errors] is not empty. */
    val translationUnit: TranslationUnit,
    /** Parse errors collected while building the translation unit. */
    val errors: List<ParseError>,
) {
    /** Whether parsing completed without syntax errors. */
    val isSuccess: Boolean get() = errors.isEmpty()
}

/**
 * Parses a WGSL source string and returns the AST.
 */
fun parseWgsl(source: String): TranslationUnit {
    return parseWgslResult(source).translationUnit
}

/**
 * Parses a WGSL source string and returns both the AST and parse errors.
 */
fun parseWgslResult(source: String): ParseResult {
    val lexer = Lexer(source)
    val parser = Parser(lexer)
    val unit = parser.parse()
    return ParseResult(unit, parser.errors.toList())
}
