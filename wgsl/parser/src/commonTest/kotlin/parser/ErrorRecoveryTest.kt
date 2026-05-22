package io.ygdrasil.wgsl.parser

import io.kotest.core.annotation.Ignored
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.ygdrasil.wgsl.ir.Span
import io.ygdrasil.wgsl.lexer.Token
import io.ygdrasil.wgsl.lexer.TokenKind

class ErrorRecoveryTest : FunSpec({
    test("recovery state initial values") {
        val state = ErrorRecovery.RecoveryState()
        state.isRecovering shouldBe false
        state.errorCount shouldBe 0
        state.maxErrors shouldBe ErrorRecovery.DEFAULT_MAX_ERRORS
    }

    test("recovery state should stop at max errors") {
        val state = ErrorRecovery.RecoveryState(maxErrors = 5)
        repeat(5) { state.errorCount++ }
        state.shouldStop() shouldBe true
    }

    test("recovery state not stopped under max") {
        val state = ErrorRecovery.RecoveryState(maxErrors = 5)
        repeat(4) { state.errorCount++ }
        state.shouldStop() shouldBe false
    }

    test("diagnostic collection starts empty") {
        val state = ErrorRecovery.RecoveryState()
        state.diagnostics.size shouldBe 0
    }

    test("is at synchronization point for statement start tokens") {
        val state = ErrorRecovery.RecoveryState()
        val token = Token(TokenKind.LET, Span.UNDEFINED, "let")
        state.isAtSynchronizationPoint(token) shouldBe true
    }

    test("is at synchronization point for semicolon") {
        val state = ErrorRecovery.RecoveryState()
        val token = Token(TokenKind.SEMICOLON, Span.UNDEFINED, ";")
        state.isAtSynchronizationPoint(token) shouldBe true
    }

    test("is at synchronization point for EOF") {
        val state = ErrorRecovery.RecoveryState()
        val token = Token.eof(Span.UNDEFINED)
        state.isAtSynchronizationPoint(token) shouldBe true
    }

    test("is not at synchronization point for arbitrary token") {
        val state = ErrorRecovery.RecoveryState()
        val token = Token(TokenKind.IDENTIFIER, Span.UNDEFINED, "foo")
        state.isAtSynchronizationPoint(token) shouldBe false
    }
})
