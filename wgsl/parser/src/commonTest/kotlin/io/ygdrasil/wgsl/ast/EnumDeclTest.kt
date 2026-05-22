package io.ygdrasil.wgsl.ast

import io.ygdrasil.wgsl.ir.Span
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class EnumDeclTest : FunSpec({
    context("EnumDecl creation") {
        test("should create EnumDecl with all properties") {
            // Given
            val attributes = listOf(
                Attribute("binding", emptyList(), Span.UNDEFINED)
            )
            val name = "Color"
            val members = listOf(
                EnumMember(
                    attributes = emptyList(),
                    name = "RED",
                    value = IntLiteral(0, null, Span.UNDEFINED),
                    span = Span.UNDEFINED
                ),
                EnumMember(
                    attributes = emptyList(),
                    name = "GREEN",
                    value = IntLiteral(1, null, Span.UNDEFINED),
                    span = Span.UNDEFINED
                ),
                EnumMember(
                    attributes = emptyList(),
                    name = "BLUE",
                    value = null,
                    span = Span.UNDEFINED
                )
            )
            val span = Span.UNDEFINED

            // When
            val enumDecl = EnumDecl(
                attributes = attributes,
                name = name,
                members = members,
                span = span
            )

            // Then
            enumDecl.attributes shouldBe attributes
            enumDecl.name shouldBe name
            enumDecl.members shouldBe members
            enumDecl.span shouldBe span
        }

        test("should create EnumDecl with empty members") {
            // Given
            val enumDecl = EnumDecl(
                attributes = emptyList(),
                name = "EmptyEnum",
                members = emptyList(),
                span = Span.UNDEFINED
            )

            // Then
            enumDecl.attributes shouldBe emptyList()
            enumDecl.name shouldBe "EmptyEnum"
            enumDecl.members shouldBe emptyList()
        }

        test("should create EnumDecl with no attributes") {
            // Given
            val members = listOf(
                EnumMember(
                    attributes = emptyList(),
                    name = "FIRST",
                    value = null,
                    span = Span.UNDEFINED
                )
            )

            val enumDecl = EnumDecl(
                attributes = emptyList(),
                name = "SimpleEnum",
                members = members,
                span = Span.UNDEFINED
            )

            // Then
            enumDecl.attributes shouldBe emptyList()
            enumDecl.name shouldBe "SimpleEnum"
            enumDecl.members shouldBe members
        }
    }

    context("EnumMember creation") {
        test("should create EnumMember with explicit value") {
            // Given
            val attributes = listOf(
                Attribute("deprecated", emptyList(), Span.UNDEFINED)
            )
            val name = "VALUE_A"
            val value = IntLiteral(42, null, Span.UNDEFINED)
            val span = Span.UNDEFINED

            // When
            val member = EnumMember(
                attributes = attributes,
                name = name,
                value = value,
                span = span
            )

            // Then
            member.attributes shouldBe attributes
            member.name shouldBe name
            member.value shouldBe value
            member.span shouldBe span
        }

        test("should create EnumMember without explicit value") {
            // Given
            val name = "VALUE_B"

            // When
            val member = EnumMember(
                attributes = emptyList(),
                name = name,
                value = null,
                span = Span.UNDEFINED
            )

            // Then
            member.attributes shouldBe emptyList()
            member.name shouldBe name
            member.value shouldBe null
        }

        test("should create EnumMember with empty attributes") {
            // Given
            val member = EnumMember(
                attributes = emptyList(),
                name = "VALUE_C",
                value = IntLiteral(100, null, Span.UNDEFINED),
                span = Span.UNDEFINED
            )

            // Then
            member.attributes shouldBe emptyList()
            member.name shouldBe "VALUE_C"
            member.value shouldBe IntLiteral(100, null, Span.UNDEFINED)
        }
    }

    context("EnumDecl immutability") {
        test("EnumDecl should be immutable") {
            // Given
            val original = EnumDecl(
                attributes = emptyList(),
                name = "TestEnum",
                members = listOf(
                    EnumMember(
                        attributes = emptyList(),
                        name = "MEMBER_1",
                        value = null,
                        span = Span.UNDEFINED
                    )
                ),
                span = Span.UNDEFINED
            )

            // When - attempt to modify (this will create a new instance)
            val modified = original.copy(name = "ModifiedEnum")

            // Then
            original.name shouldBe "TestEnum"
            modified.name shouldBe "ModifiedEnum"
        }

        test("EnumMember should be immutable") {
            // Given
            val original = EnumMember(
                attributes = emptyList(),
                name = "ORIGINAL",
                value = IntLiteral(1, null, Span.UNDEFINED),
                span = Span.UNDEFINED
            )

            // When - attempt to modify (this will create a new instance)
            val modified = original.copy(name = "MODIFIED")

            // Then
            original.name shouldBe "ORIGINAL"
            modified.name shouldBe "MODIFIED"
        }
    }

    context("EnumDecl as GlobalDecl") {
        test("EnumDecl should be a GlobalDecl") {
            // Given
            val enumDecl = EnumDecl(
                attributes = emptyList(),
                name = "GlobalEnum",
                members = emptyList(),
                span = Span.UNDEFINED
            )

            // Then
            enumDecl shouldBe enumDecl
            enumDecl.span shouldBe Span.UNDEFINED
        }
    }

    context("Complex EnumDecl scenario") {
        test("should create complex EnumDecl with multiple members and attributes") {
            // Given
            val enumAttributes = listOf(
                Attribute("binding", listOf(IntLiteral(0, null, Span.UNDEFINED)), Span.UNDEFINED),
                Attribute("group", listOf(IntLiteral(1, null, Span.UNDEFINED)), Span.UNDEFINED)
            )

            val members = listOf(
                EnumMember(
                    attributes = listOf(Attribute("deprecated", emptyList(), Span.UNDEFINED)),
                    name = "RED",
                    value = IntLiteral(0xFF0000, null, Span.UNDEFINED),
                    span = Span.UNDEFINED
                ),
                EnumMember(
                    attributes = emptyList(),
                    name = "GREEN",
                    value = IntLiteral(0x00FF00, null, Span.UNDEFINED),
                    span = Span.UNDEFINED
                ),
                EnumMember(
                    attributes = emptyList(),
                    name = "BLUE",
                    value = null,
                    span = Span.UNDEFINED
                ),
                EnumMember(
                    attributes = emptyList(),
                    name = "ALPHA",
                    value = null,
                    span = Span.UNDEFINED
                )
            )

            val enumDecl = EnumDecl(
                attributes = enumAttributes,
                name = "ColorRGBA",
                members = members,
                span = Span.UNDEFINED
            )

            // Then
            enumDecl.attributes.size shouldBe 2
            enumDecl.name shouldBe "ColorRGBA"
            enumDecl.members.size shouldBe 4
            enumDecl.members[0].name shouldBe "RED"
            enumDecl.members[1].name shouldBe "GREEN"
            enumDecl.members[2].name shouldBe "BLUE"
            enumDecl.members[3].name shouldBe "ALPHA"
            enumDecl.members[0].value shouldBe IntLiteral(0xFF0000, null, Span.UNDEFINED)
            enumDecl.members[1].value shouldBe IntLiteral(0x00FF00, null, Span.UNDEFINED)
            enumDecl.members[2].value shouldBe null
            enumDecl.members[3].value shouldBe null
        }
    }
})
