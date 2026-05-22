package io.ygdrasil.wgsl.generator.msl

/**
 * Mots-clés réservés pour Metal Shading Language (MSL).
 */
object Keywords {
    val MSL_RESERVED = setOf(
        "alignas", "alignof", "as_type", "auto", "break", "case", "char",
        "class", "const", "constexpr", "continue", "decltype", "default",
        "delete", "do", "double", "dynamic_cast", "else", "enum", "explicit",
        "export", "extern", "false", "float", "for", "friend", "goto", "if",
        "inline", "int", "long", "mutable", "namespace", "new", "noexcept",
        "nullptr", "operator", "private", "protected", "public", "register",
        "reinterpret_cast", "return", "short", "signed", "sizeof", "static",
        "static_assert", "static_cast", "struct", "switch", "template",
        "this", "thread_local", "throw", "true", "try", "typedef", "typeid",
        "typename", "union", "unsigned", "using", "virtual", "void",
        "volatile", "wchar_t", "while", "device", "constant", "thread",
        "threadgroup", "threadgroup_imageblock"
    )
}
