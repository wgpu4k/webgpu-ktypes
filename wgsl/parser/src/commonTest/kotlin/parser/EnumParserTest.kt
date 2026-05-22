package io.ygdrasil.wgsl.parser

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ygdrasil.wgsl.ast.EnumDecl
import io.ygdrasil.wgsl.ast.EnumMember
import io.ygdrasil.wgsl.lexer.Lexer

/**
 * Tests for parsing enum declarations.
 */
class EnumParserTest : FunSpec({

    test("basic enum parsing") {
        val source = "enum Color { RED, GREEN, BLUE }"
        val result = Parser(Lexer(source)).parse()
        
        result.declarations shouldHaveSize 1
        val enumDecl = result.declarations[0].shouldBeInstanceOf<EnumDecl>()
        
        enumDecl.name shouldBe "Color"
        enumDecl.members shouldHaveSize 3
        
        enumDecl.members[0].name shouldBe "RED"
        enumDecl.members[0].value shouldBe null
        
        enumDecl.members[1].name shouldBe "GREEN"
        enumDecl.members[1].value shouldBe null
        
        enumDecl.members[2].name shouldBe "BLUE"
        enumDecl.members[2].value shouldBe null
    }

    test("enum with explicit values") {
        val source = "enum Color { RED, GREEN, BLUE = 3 }"
        val result = Parser(Lexer(source)).parse()
        
        result.declarations shouldHaveSize 1
        val enumDecl = result.declarations[0].shouldBeInstanceOf<EnumDecl>()
        
        enumDecl.name shouldBe "Color"
        enumDecl.members shouldHaveSize 3
        
        enumDecl.members[0].name shouldBe "RED"
        enumDecl.members[0].value shouldBe null
        
        enumDecl.members[1].name shouldBe "GREEN"
        enumDecl.members[1].value shouldBe null
        
        enumDecl.members[2].name shouldBe "BLUE"
        enumDecl.members[2].value.shouldNotBeNull()
    }

    test("enum with trailing comma") {
        val source = "enum Color { RED, GREEN, BLUE = 3, }"
        val result = Parser(Lexer(source)).parse()
        
        result.declarations shouldHaveSize 1
        val enumDecl = result.declarations[0].shouldBeInstanceOf<EnumDecl>()
        
        enumDecl.name shouldBe "Color"
        enumDecl.members shouldHaveSize 3
        
        enumDecl.members[0].name shouldBe "RED"
        enumDecl.members[1].name shouldBe "GREEN"
        enumDecl.members[2].name shouldBe "BLUE"
        enumDecl.members[2].value.shouldNotBeNull()
    }

    test("enum Status with explicit first value") {
        val source = "enum Status { ACTIVE = 1, INACTIVE }"
        val result = Parser(Lexer(source)).parse()
        
        result.declarations shouldHaveSize 1
        val enumDecl = result.declarations[0].shouldBeInstanceOf<EnumDecl>()
        
        enumDecl.name shouldBe "Status"
        enumDecl.members shouldHaveSize 2
        
        enumDecl.members[0].name shouldBe "ACTIVE"
        enumDecl.members[0].value.shouldNotBeNull()
        
        enumDecl.members[1].name shouldBe "INACTIVE"
        enumDecl.members[1].value shouldBe null
    }

    test("enum with attributes on enum") {
        val source = "@packed enum MyEnum { A, B }"
        val result = Parser(Lexer(source)).parse()
        
        result.declarations shouldHaveSize 1
        val enumDecl = result.declarations[0].shouldBeInstanceOf<EnumDecl>()
        
        enumDecl.name shouldBe "MyEnum"
        enumDecl.attributes shouldHaveSize 1
        enumDecl.attributes[0].name shouldBe "packed"
        enumDecl.members shouldHaveSize 2
    }

    test("enum with attributes on members") {
        val source = "enum MyEnum { @attr1 A, @attr2 B }"
        val result = Parser(Lexer(source)).parse()
        
        result.declarations shouldHaveSize 1
        val enumDecl = result.declarations[0].shouldBeInstanceOf<EnumDecl>()
        
        enumDecl.name shouldBe "MyEnum"
        enumDecl.members shouldHaveSize 2
        
        enumDecl.members[0].name shouldBe "A"
        enumDecl.members[0].attributes shouldHaveSize 1
        enumDecl.members[0].attributes[0].name shouldBe "attr1"
        
        enumDecl.members[1].name shouldBe "B"
        enumDecl.members[1].attributes shouldHaveSize 1
        enumDecl.members[1].attributes[0].name shouldBe "attr2"
    }

    test("enum with attributes on both enum and members") {
        val source = "@interpolate(linear) enum TextureSampleType { @sample(0) SAMPLE_0, @sample(1) SAMPLE_1 = 1 }"
        val result = Parser(Lexer(source)).parse()
        
        result.declarations shouldHaveSize 1
        val enumDecl = result.declarations[0].shouldBeInstanceOf<EnumDecl>()
        
        enumDecl.name shouldBe "TextureSampleType"
        enumDecl.attributes shouldHaveSize 1
        enumDecl.attributes[0].name shouldBe "interpolate"
        
        enumDecl.members shouldHaveSize 2
        
        enumDecl.members[0].name shouldBe "SAMPLE_0"
        enumDecl.members[0].attributes shouldHaveSize 1
        enumDecl.members[0].attributes[0].name shouldBe "sample"
        enumDecl.members[0].value shouldBe null
        
        enumDecl.members[1].name shouldBe "SAMPLE_1"
        enumDecl.members[1].attributes shouldHaveSize 1
        enumDecl.members[1].attributes[0].name shouldBe "sample"
        enumDecl.members[1].value.shouldNotBeNull()
    }

    test("enum with various value expressions") {
        val source = """
            enum MyEnum {
                A,
                B = 10,
                C = 0xFF,
                D = -5
            }
        """
        val result = Parser(Lexer(source)).parse()
        
        result.declarations shouldHaveSize 1
        val enumDecl = result.declarations[0].shouldBeInstanceOf<EnumDecl>()
        
        enumDecl.name shouldBe "MyEnum"
        enumDecl.members shouldHaveSize 4
        
        enumDecl.members[0].name shouldBe "A"
        enumDecl.members[0].value shouldBe null
        
        enumDecl.members[1].name shouldBe "B"
        enumDecl.members[1].value.shouldNotBeNull()
        
        enumDecl.members[2].name shouldBe "C"
        enumDecl.members[2].value.shouldNotBeNull()
        
        enumDecl.members[3].name shouldBe "D"
        enumDecl.members[3].value.shouldNotBeNull()
    }

    test("multiple enums in same file") {
        val source = """
            enum Color { RED, GREEN, BLUE }
            enum Status { ACTIVE = 1, INACTIVE }
        """
        val result = Parser(Lexer(source)).parse()
        
        result.declarations shouldHaveSize 2
        
        val colorDecl = result.declarations[0].shouldBeInstanceOf<EnumDecl>()
        colorDecl.name shouldBe "Color"
        colorDecl.members shouldHaveSize 3
        
        val statusDecl = result.declarations[1].shouldBeInstanceOf<EnumDecl>()
        statusDecl.name shouldBe "Status"
        statusDecl.members shouldHaveSize 2
    }

    test("enum with semicolon after closing brace") {
        val source = "enum Color { RED, GREEN, BLUE = 3, };"
        val result = Parser(Lexer(source)).parse()
        
        result.declarations shouldHaveSize 1
        val enumDecl = result.declarations[0].shouldBeInstanceOf<EnumDecl>()
        
        enumDecl.name shouldBe "Color"
        enumDecl.members shouldHaveSize 3
    }

    test("enum with explicit values and trailing comma") {
        val source = "enum Status { ACTIVE = 1, INACTIVE, }"
        val result = Parser(Lexer(source)).parse()
        
        result.declarations shouldHaveSize 1
        val enumDecl = result.declarations[0].shouldBeInstanceOf<EnumDecl>()
        
        enumDecl.name shouldBe "Status"
        enumDecl.members shouldHaveSize 2
        
        enumDecl.members[0].name shouldBe "ACTIVE"
        enumDecl.members[0].value.shouldNotBeNull()
        
        enumDecl.members[1].name shouldBe "INACTIVE"
        enumDecl.members[1].value shouldBe null
    }

    test("empty enum") {
        val source = "enum Empty { }"
        val result = Parser(Lexer(source)).parse()
        
        result.declarations shouldHaveSize 1
        val enumDecl = result.declarations[0].shouldBeInstanceOf<EnumDecl>()
        
        enumDecl.name shouldBe "Empty"
        enumDecl.members shouldHaveSize 0
    }

    test("single member enum") {
        val source = "enum Single { ONLY }"
        val result = Parser(Lexer(source)).parse()
        
        result.declarations shouldHaveSize 1
        val enumDecl = result.declarations[0].shouldBeInstanceOf<EnumDecl>()
        
        enumDecl.name shouldBe "Single"
        enumDecl.members shouldHaveSize 1
        enumDecl.members[0].name shouldBe "ONLY"
    }
})
