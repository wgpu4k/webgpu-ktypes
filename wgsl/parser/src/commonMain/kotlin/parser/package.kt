package io.ygdrasil.wgsl.parser

/**
 * WGSL Parser module.
 * 
 * This module provides the complete parsing pipeline for WGSL shaders:
 * - Lexer: Tokenizes WGSL source code
 * - Parser: Parses tokens into an AST
 * - AstBuilder: Helper for creating AST nodes
 * - TypeIndex: Index of all declared types and values
 * - ModuleIndexer: Handles forward references via topological sorting
 * - TypeResolver: Resolves type and identifier references
 * - Error handling: Diagnostic, ErrorRecovery, PrettyPrintError
 */
