package io.ygdrasil.wgsl.lexer

/**
 * Enumeration of all possible token kinds in the WGSL language.
 * 
 * This represents the lexical categories that the lexer can produce.
 * Each token has a kind, a span (position in source), and optionally a literal value.
 */
enum class TokenKind {
    // End of file
    EOF,

    // Whitespace and comments (typically skipped but available for full fidelity parsing)
    WHITESPACE,
    SINGLE_LINE_COMMENT,
    MULTI_LINE_COMMENT,
    DOC_COMMENT,

    // Literals
    IDENTIFIER,
    INT_LITERAL,
    UINT_LITERAL,
    FLOAT_LITERAL,
    BOOL_LITERAL,
    STRING_LITERAL,

    // Keywords - Control flow
    IF,
    ELSE,
    SWITCH,
    CASE,
    DEFAULT,
    LOOP,
    WHILE,
    FOR,
    BREAK,
    CONTINUE,
    RETURN,
    DISCARD,

    // Keywords - Function and variable declarations
    ABSTRACT,
    FN,
    LET,
    CONST,
    VAR,
    TYPE,
    STRUCT,
    ENUM,
    CONST_ASSERT,
    ALIAS,
    DIAGNOSTIC,
    STATIC_ASSERT,

    // Keywords - Type constructors
    ARRAY,
    MAT,
    VEC,
    PTR,

    // Keywords - Storage classes
    UNIFORM,
    STORAGE,
    WORKGROUP,
    PRIVATE,
    FUNCTION,

    // Keywords - Access modes
    READ,
    WRITE,
    READ_WRITE,

    // Keywords - Attribute-related
    AT,
    LOCATION,
    BUILTIN,
    ENABLE,
    REQUIRES,
    INTERPOLATE,
    INVARIANT,
    MUST_USE,
    OVERRIDE,
    BITCAST,
    COMPUTE,
    FRAGMENT,
    VERTEX,

    // Keywords - Layout annotations
    PACKED,
    ALIGNED,

    // Keywords - Template constraints
    WHERE,

    // Keywords - Built-in scalar types
    BOOL,
    F16,
    F32,
    F64,
    FLOAT,
    I8,
    U8,
    I16,
    U16,
    I32,
    U32,
    I64,
    U64,
    INT,

    // Keywords - Built-in texture and sampler types
    SAMPLER,
    SAMPLER_COMPARISON,
    TEXTURE_1D,
    TEXTURE_1D_ARRAY,
    TEXTURE_2D,
    TEXTURE_2D_ARRAY,
    TEXTURE_3D,
    TEXTURE_CUBE,
    TEXTURE_CUBE_ARRAY,
    TEXTURE_MULTISAMPLED_2D,
    TEXTURE_DEPTH_2D,
    TEXTURE_DEPTH_2D_ARRAY,
    TEXTURE_DEPTH_CUBE,
    TEXTURE_DEPTH_CUBE_ARRAY,
    TEXTURE_DEPTH_MULTISAMPLED_2D,
    TEXTURE_EXTERNAL,
    TEXTURE_STORAGE_1D,
    TEXTURE_STORAGE_2D,
    TEXTURE_STORAGE_2D_ARRAY,
    TEXTURE_STORAGE_3D,
    ATOMIC,
    RAY_QUERY,

    // Keywords - Address spaces
    HANDLE,

    // Operators - Arithmetic
    PLUS,
    MINUS,
    STAR,
    SLASH,
    PERCENT,

    // Operators - Bitwise
    AMPERSAND,
    PIPE,
    CARET,
    TILDE,
    LEFT_SHIFT,
    RIGHT_SHIFT,

    // Operators - Comparison
    EQ,
    NEQ,
    LTE,
    GTE,

    // Operators - Logical
    AND,
    OR,
    NOT,

    // Keywords - Control flow additions
    CONTINUING,

    // Operators - Assignment
    ASSIGN,
    PLUS_ASSIGN,
    MINUS_ASSIGN,
    STAR_ASSIGN,
    SLASH_ASSIGN,
    PERCENT_ASSIGN,
    AND_ASSIGN,
    OR_ASSIGN,
    XOR_ASSIGN,
    LEFT_SHIFT_ASSIGN,
    RIGHT_SHIFT_ASSIGN,

    // Operators - Increment/Decrement
    INCREMENT,
    DECREMENT,

    // Punctuation - Single character
    LEFT_PAREN,
    RIGHT_PAREN,
    LEFT_BRACE,
    RIGHT_BRACE,
    LEFT_BRACKET,
    RIGHT_BRACKET,
    COMMA,
    DOT,
    COLON,
    SEMICOLON,

    // Punctuation - Multi-character
    COLON_COLON,
    DOT_STAR,
    ARROW,
    FAT_ARROW,

    // Template-related
    LEFT_ANGLE,
    RIGHT_ANGLE,
    LEFT_ANGLE_RIGHT_ANGLE,

    // Special identifiers
    UNDERSCORE,

    // Built-in boolean values
    TRUE,
    FALSE,

    // Built-in values (used in @builtin attribute)
    POSITION,
    VERTEX_INDEX,
    INSTANCE_INDEX,
    DRAW_INDEX,
    FRONT_FACING,
    PRIMITIVE_INDEX,
    SAMPLE_INDEX,
    SAMPLE_MASK,
    VIEWPORT_INDEX,
    POINTSIZE,
    CLIP_DISTANCES,
    CULL_DISTANCES,
    DEVICE_INDEX,
    VIEW_INDEX,
    WORKGROUP_ID,
    NUM_WORKGROUPS,
    GLOBAL_INVOCATION_ID,
    LOCAL_INVOCATION_ID,
    LOCAL_INVOCATION_INDEX,

    // Ternary conditional operator
    QUESTION,

    // Unknown character
    UNKNOWN,
}

/**
 * Checks if this token kind is a keyword.
 */
val TokenKind.isKeyword: Boolean
    get() = when (this) {
        // Control flow
        TokenKind.IF, TokenKind.ELSE, TokenKind.SWITCH, TokenKind.CASE, TokenKind.DEFAULT,
        TokenKind.LOOP, TokenKind.WHILE, TokenKind.FOR, TokenKind.BREAK, TokenKind.CONTINUE,
        TokenKind.RETURN, TokenKind.DISCARD, TokenKind.CONTINUING -> true
        // Declarations
        TokenKind.ABSTRACT, TokenKind.FN, TokenKind.LET, TokenKind.CONST, TokenKind.VAR, TokenKind.TYPE, TokenKind.STRUCT, TokenKind.ENUM, TokenKind.CONST_ASSERT, TokenKind.STATIC_ASSERT, TokenKind.ALIAS, TokenKind.DIAGNOSTIC -> true
        // Type constructors
        TokenKind.ARRAY, TokenKind.MAT, TokenKind.VEC, TokenKind.PTR -> true
        // Storage classes
        TokenKind.UNIFORM, TokenKind.STORAGE, TokenKind.WORKGROUP, TokenKind.PRIVATE, TokenKind.FUNCTION -> true
        // Access modes
        TokenKind.READ, TokenKind.WRITE, TokenKind.READ_WRITE -> true
        // Attributes
        TokenKind.AT, TokenKind.LOCATION, TokenKind.BUILTIN, TokenKind.ENABLE, TokenKind.REQUIRES,
        TokenKind.INTERPOLATE, TokenKind.INVARIANT, TokenKind.MUST_USE, TokenKind.OVERRIDE,
        TokenKind.BITCAST, TokenKind.COMPUTE, TokenKind.FRAGMENT, TokenKind.VERTEX -> true
        // Layout annotations
        TokenKind.PACKED, TokenKind.ALIGNED -> true
        // Template constraints
        TokenKind.WHERE -> true
        // Built-in types
        TokenKind.BOOL, TokenKind.F16, TokenKind.F32, TokenKind.F64, TokenKind.FLOAT,
        TokenKind.I8, TokenKind.U8, TokenKind.I16, TokenKind.U16, TokenKind.I32,
        TokenKind.U32, TokenKind.I64, TokenKind.U64, TokenKind.INT -> true

        TokenKind.TEXTURE_1D, TokenKind.TEXTURE_1D_ARRAY, TokenKind.TEXTURE_2D, TokenKind.TEXTURE_2D_ARRAY, TokenKind.TEXTURE_3D,
        TokenKind.TEXTURE_CUBE, TokenKind.TEXTURE_CUBE_ARRAY, TokenKind.TEXTURE_MULTISAMPLED_2D,
        TokenKind.TEXTURE_DEPTH_2D, TokenKind.TEXTURE_DEPTH_2D_ARRAY, TokenKind.TEXTURE_DEPTH_CUBE,
        TokenKind.TEXTURE_DEPTH_CUBE_ARRAY, TokenKind.TEXTURE_DEPTH_MULTISAMPLED_2D,
        TokenKind.TEXTURE_EXTERNAL, TokenKind.TEXTURE_STORAGE_1D, TokenKind.TEXTURE_STORAGE_2D,
        TokenKind.TEXTURE_STORAGE_2D_ARRAY, TokenKind.TEXTURE_STORAGE_3D,
        TokenKind.SAMPLER, TokenKind.SAMPLER_COMPARISON, TokenKind.ATOMIC, TokenKind.RAY_QUERY, TokenKind.HANDLE -> true
        // Built-in values
        TokenKind.TRUE, TokenKind.FALSE, TokenKind.POSITION, TokenKind.VERTEX_INDEX,
        TokenKind.INSTANCE_INDEX, TokenKind.DRAW_INDEX, TokenKind.FRONT_FACING, TokenKind.PRIMITIVE_INDEX,
        TokenKind.SAMPLE_INDEX, TokenKind.SAMPLE_MASK, TokenKind.VIEWPORT_INDEX,
        TokenKind.POINTSIZE, TokenKind.CLIP_DISTANCES, TokenKind.CULL_DISTANCES,
        TokenKind.DEVICE_INDEX, TokenKind.VIEW_INDEX, TokenKind.WORKGROUP_ID,
        TokenKind.NUM_WORKGROUPS, TokenKind.GLOBAL_INVOCATION_ID, TokenKind.LOCAL_INVOCATION_ID,
        TokenKind.LOCAL_INVOCATION_INDEX -> true

        else -> false
    }

/**
 * Checks if this token kind is a literal.
 */
val TokenKind.isLiteral: Boolean
    get() = when (this) {
        TokenKind.IDENTIFIER, TokenKind.INT_LITERAL, TokenKind.UINT_LITERAL,
        TokenKind.FLOAT_LITERAL, TokenKind.BOOL_LITERAL, TokenKind.STRING_LITERAL -> true

        else -> false
    }

/**
 * Checks if this token kind is an operator.
 */
val TokenKind.isOperator: Boolean
    get() = when (this) {
        // Arithmetic
        TokenKind.PLUS, TokenKind.MINUS, TokenKind.STAR, TokenKind.SLASH, TokenKind.PERCENT -> true
        // Bitwise
        TokenKind.AMPERSAND, TokenKind.PIPE, TokenKind.CARET, TokenKind.TILDE,
        TokenKind.LEFT_SHIFT, TokenKind.RIGHT_SHIFT -> true
        // Comparison
        TokenKind.EQ, TokenKind.NEQ, TokenKind.LEFT_ANGLE, TokenKind.LTE, TokenKind.RIGHT_ANGLE, TokenKind.GTE -> true
        // Logical
        TokenKind.AND, TokenKind.OR, TokenKind.NOT -> true
        // Assignment
        TokenKind.ASSIGN, TokenKind.PLUS_ASSIGN, TokenKind.MINUS_ASSIGN, TokenKind.STAR_ASSIGN,
        TokenKind.SLASH_ASSIGN, TokenKind.PERCENT_ASSIGN, TokenKind.AND_ASSIGN,
        TokenKind.OR_ASSIGN, TokenKind.XOR_ASSIGN, TokenKind.LEFT_SHIFT_ASSIGN,
        TokenKind.RIGHT_SHIFT_ASSIGN -> true
        // Increment/Decrement
        TokenKind.INCREMENT, TokenKind.DECREMENT -> true
        // Special
        TokenKind.ARROW, TokenKind.FAT_ARROW, TokenKind.DOT_STAR, TokenKind.COLON_COLON -> true
        // Ternary
        TokenKind.QUESTION -> true
        else -> false
    }

/**
 * Checks if this token kind is punctuation.
 */
val TokenKind.isPunctuation: Boolean
    get() = when (this) {
        TokenKind.LEFT_PAREN, TokenKind.RIGHT_PAREN, TokenKind.LEFT_BRACE, TokenKind.RIGHT_BRACE,
        TokenKind.LEFT_BRACKET, TokenKind.RIGHT_BRACKET, TokenKind.COMMA, TokenKind.DOT,
        TokenKind.COLON, TokenKind.SEMICOLON,
        TokenKind.COLON_COLON, TokenKind.DOT_STAR, TokenKind.ARROW, TokenKind.FAT_ARROW,
        TokenKind.LEFT_ANGLE, TokenKind.RIGHT_ANGLE, TokenKind.LEFT_ANGLE_RIGHT_ANGLE -> true

        else -> false
    }

/**
 * Returns the precedence level for this operator token kind.
 * Higher values have higher precedence.
 */
fun TokenKind.precedence(): Int = when (this) {
    // Primary expressions (highest precedence)
    TokenKind.LEFT_PAREN, TokenKind.DOT, TokenKind.LEFT_BRACKET -> 15

    // Postfix operators
    TokenKind.INCREMENT, TokenKind.DECREMENT -> 14

    // Unary operators
    TokenKind.NOT, TokenKind.TILDE, TokenKind.MINUS, TokenKind.PLUS -> 13

    // Multiplicative
    TokenKind.STAR, TokenKind.SLASH, TokenKind.PERCENT -> 12

    // Additive
    TokenKind.PLUS, TokenKind.MINUS -> 11

    // Shift
    TokenKind.LEFT_SHIFT, TokenKind.RIGHT_SHIFT -> 10

    // Relational
    TokenKind.LEFT_ANGLE, TokenKind.LTE, TokenKind.RIGHT_ANGLE, TokenKind.GTE -> 9

    // Equality
    TokenKind.EQ, TokenKind.NEQ -> 8

    // Bitwise AND
    TokenKind.AMPERSAND -> 7

    // Bitwise XOR
    TokenKind.CARET -> 6

    // Bitwise OR
    TokenKind.PIPE -> 5

    // Logical AND
    TokenKind.AND -> 4

    // Logical OR
    TokenKind.OR -> 3

    // Ternary conditional
    TokenKind.QUESTION -> 2

    // Assignment (lowest precedence)
    TokenKind.ASSIGN, TokenKind.PLUS_ASSIGN, TokenKind.MINUS_ASSIGN, TokenKind.STAR_ASSIGN, TokenKind.SLASH_ASSIGN,
    TokenKind.PERCENT_ASSIGN, TokenKind.AND_ASSIGN, TokenKind.OR_ASSIGN, TokenKind.XOR_ASSIGN,
    TokenKind.LEFT_SHIFT_ASSIGN, TokenKind.RIGHT_SHIFT_ASSIGN -> 1

    // Non-operators
    else -> 0
}

/**
 * Checks if this token kind is a binary operator.
 */
val TokenKind.isBinaryOperator: Boolean
    get() = when (this) {
        TokenKind.PLUS, TokenKind.MINUS, TokenKind.STAR, TokenKind.SLASH, TokenKind.PERCENT,
        TokenKind.AMPERSAND, TokenKind.PIPE, TokenKind.CARET,
        TokenKind.LEFT_SHIFT, TokenKind.RIGHT_SHIFT,
        TokenKind.EQ, TokenKind.NEQ, TokenKind.LEFT_ANGLE, TokenKind.LTE, TokenKind.RIGHT_ANGLE, TokenKind.GTE,
        TokenKind.AND, TokenKind.OR,
        TokenKind.ASSIGN, TokenKind.PLUS_ASSIGN, TokenKind.MINUS_ASSIGN, TokenKind.STAR_ASSIGN, TokenKind.SLASH_ASSIGN,
        TokenKind.PERCENT_ASSIGN, TokenKind.AND_ASSIGN, TokenKind.OR_ASSIGN, TokenKind.XOR_ASSIGN,
        TokenKind.LEFT_SHIFT_ASSIGN, TokenKind.RIGHT_SHIFT_ASSIGN -> true

        else -> false
    }

/**
 * Checks if this token kind is a unary operator.
 */
val TokenKind.isUnaryOperator: Boolean
    get() = when (this) {
        TokenKind.NOT, TokenKind.TILDE, TokenKind.MINUS, TokenKind.PLUS, TokenKind.STAR, TokenKind.AMPERSAND -> true
        else -> false
    }

/**
 * Checks if this token kind is right-associative.
 */
val TokenKind.isRightAssociative: Boolean
    get() = when (this) {
        TokenKind.ASSIGN, TokenKind.PLUS_ASSIGN, TokenKind.MINUS_ASSIGN, TokenKind.STAR_ASSIGN, TokenKind.SLASH_ASSIGN,
        TokenKind.PERCENT_ASSIGN, TokenKind.AND_ASSIGN, TokenKind.OR_ASSIGN, TokenKind.XOR_ASSIGN,
        TokenKind.LEFT_SHIFT_ASSIGN, TokenKind.RIGHT_SHIFT_ASSIGN,
        TokenKind.INCREMENT, TokenKind.DECREMENT,
        TokenKind.QUESTION -> true

        else -> false
    }

/**
 * Returns true if this token is a builtin value keyword that can be used as a struct member name.
 * These keywords should be accepted after a DOT token to form MemberAccessExpr.
 */
val TokenKind.isBuiltinValue: Boolean
    get() = this in listOf(
        TokenKind.POSITION,
        TokenKind.VERTEX_INDEX,
        TokenKind.INSTANCE_INDEX,
        TokenKind.DRAW_INDEX,
        TokenKind.FRONT_FACING,
        TokenKind.PRIMITIVE_INDEX,
        TokenKind.SAMPLE_INDEX,
        TokenKind.SAMPLE_MASK,
        TokenKind.VIEWPORT_INDEX,
        TokenKind.POINTSIZE,
        TokenKind.CLIP_DISTANCES,
        TokenKind.CULL_DISTANCES,
        TokenKind.DEVICE_INDEX,
        TokenKind.VIEW_INDEX,
        TokenKind.WORKGROUP_ID,
        TokenKind.NUM_WORKGROUPS,
        TokenKind.GLOBAL_INVOCATION_ID,
        TokenKind.LOCAL_INVOCATION_ID,
        TokenKind.LOCAL_INVOCATION_INDEX,
    )
