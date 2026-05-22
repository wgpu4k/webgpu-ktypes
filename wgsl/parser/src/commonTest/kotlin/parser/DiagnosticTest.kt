package io.ygdrasil.wgsl.parser

import io.kotest.core.annotation.Ignored
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.ygdrasil.wgsl.ir.Span

class DiagnosticTest : FunSpec({
    test("create error diagnostic") {
        val diagnostic = Diagnostic(
            severity = DiagnosticSeverity.ERROR,
            code = ErrorCode.UNKNOWN_TYPE,
            span = Span.UNDEFINED,
            message = "Type not found",
            suggestions = listOf("Did you mean 'i32'?")
        )
        diagnostic.isError shouldBe true
        diagnostic.isWarning shouldBe false
        diagnostic.severity shouldBe DiagnosticSeverity.ERROR
        diagnostic.code shouldBe ErrorCode.UNKNOWN_TYPE
        diagnostic.message shouldBe "Type not found"
    }

    test("create warning diagnostic") {
        val diagnostic = Diagnostic(
            severity = DiagnosticSeverity.WARNING,
            code = ErrorCode.INVALID_CAST,
            span = Span.UNDEFINED,
            message = "Potential precision loss"
        )
        diagnostic.isError shouldBe false
        diagnostic.isWarning shouldBe true
        diagnostic.severity shouldBe DiagnosticSeverity.WARNING
    }

    test("diagnostic toString format") {
        val diagnostic = Diagnostic(
            severity = DiagnosticSeverity.ERROR,
            code = ErrorCode.UNEXPECTED_TOKEN,
            span = Span(10u, 15u),
            message = "Unexpected token"
        )
        val str = diagnostic.toString()
        str shouldBe "[error:E0010] Unexpected token at Span(start=10, end=15)"
    }

    test("error codes have correct string representation") {
        ErrorCode.UNEXPECTED_TOKEN.toString() shouldBe "E0010"
        ErrorCode.UNKNOWN_TYPE.toString() shouldBe "E0100"
        ErrorCode.UNDECLARED_IDENTIFIER.toString() shouldBe "E0200"
    }

    test("diagnostic with related information") {
        val related = Diagnostic(
            severity = DiagnosticSeverity.INFO,
            code = ErrorCode.INTERNAL_ERROR,
            span = Span.UNDEFINED,
            message = "See also"
        )
        val diagnostic = Diagnostic(
            severity = DiagnosticSeverity.ERROR,
            code = ErrorCode.UNKNOWN_TYPE,
            span = Span.UNDEFINED,
            message = "Type not found",
            related = listOf(related)
        )
        diagnostic.related shouldBe listOf(related)
    }
})
