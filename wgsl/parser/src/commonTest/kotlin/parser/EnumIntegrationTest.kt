package io.ygdrasil.wgsl.parser

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ygdrasil.wgsl.ast.*
import io.ygdrasil.wgsl.lexer.Lexer

/**
 * Tests for integration between user-defined enums and predeclared enumerants.
 * 
 * This test suite verifies that both enum systems work correctly and
 * can be used together in the same code.
 */
class EnumIntegrationTest : FunSpec({
    
    context("User-defined enums") {
        test("parse user-defined enum declaration") {
            val source = """
                enum MyEnum {
                    A,
                    B,
                    C
                }
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            unit.declarations shouldHaveSize 1
            
            val enumDecl = unit.declarations[0] as EnumDecl
            enumDecl.name shouldBe "MyEnum"
            enumDecl.members shouldHaveSize 3
        }
        
        test("access user-defined enum member creates EnumMemberExpr") {
            val source = """
                enum MyEnum {
                    A,
                    B
                }
                let x = MyEnum.A;
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            unit.declarations shouldHaveSize 2
            
            // The variable declaration with enum member access
            val varDecl = unit.declarations[1] as VariableDecl
            val enumMemberExpr = varDecl.initializer.shouldBeInstanceOf<EnumMemberExpr>()
            enumMemberExpr.enumDecl.name shouldBe "MyEnum"
            enumMemberExpr.memberName shouldBe "A"
            enumMemberExpr.getEnumTypeName() shouldBe "MyEnum"
        }
    }
    
    context("Predeclared enumerants") {
        test("access predeclared enumerant creates PredeclaredEnumerantExpr") {
            val source = "let mode = AddressMode.clamp_to_edge;"
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            
            val varDecl = unit.declarations[0] as VariableDecl
            val enumExpr = varDecl.initializer.shouldBeInstanceOf<PredeclaredEnumerantExpr>()
            enumExpr.category shouldBe "AddressMode"
            enumExpr.enumerant.value shouldBe "clamp_to_edge"
        }
    }
    
    context("Mixed usage") {
        test("use both user-defined and predeclared enums in same module") {
            val source = """
                enum MyEnum {
                    A,
                    B
                }
                
                let userValue = MyEnum.A;
                let predefinedValue = AddressMode.clamp_to_edge;
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            unit.declarations shouldHaveSize 3
            
            // Enum declaration
            unit.declarations[0].shouldBeInstanceOf<EnumDecl>()
            
            // User-defined enum access (EnumMemberExpr after resolution)
            val userVar = unit.declarations[1] as VariableDecl
            userVar.initializer.shouldBeInstanceOf<EnumMemberExpr>()
            
            // Predeclared enumerant access (PredeclaredEnumerantExpr)
            val predefinedVar = unit.declarations[2] as VariableDecl
            predefinedVar.initializer.shouldBeInstanceOf<PredeclaredEnumerantExpr>()
        }
        
        test("consistent dot notation syntax for accessing values") {
            // Both use the same dot notation syntax
            val source1 = """
                enum MyEnum { Value }
                let a = MyEnum.Value;
            """.trimIndent()
            val source2 = "let b = AddressMode.clamp_to_edge;"
            
            val parser1 = Parser(Lexer(source1))
            val unit1 = parser1.parse()
            
            val parser2 = Parser(Lexer(source2))
            val unit2 = parser2.parse()
            
            // Both should parse successfully
            parser1.errors.isEmpty() shouldBe true
            parser2.errors.isEmpty() shouldBe true
            
            // First creates EnumMemberExpr (resolved from MemberAccessExpr), second creates PredeclaredEnumerantExpr
            (unit1.declarations[1] as VariableDecl).initializer.shouldBeInstanceOf<EnumMemberExpr>()
            (unit2.declarations[0] as VariableDecl).initializer.shouldBeInstanceOf<PredeclaredEnumerantExpr>()
        }
    }
    
    context("Enum in struct members") {
        test("user-defined enum type in struct") {
            val source = """
                enum MyEnum { A, B }
                struct S {
                    mode: MyEnum,
                }
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
            
            val structDecl = unit.declarations[1] as StructDecl
            structDecl.members shouldHaveSize 1
            structDecl.members[0].type.shouldBeInstanceOf<NamedType>()
            (structDecl.members[0].type as NamedType).name shouldBe "MyEnum"
        }
    }
    
    context("Enum expressions") {
        test("compare enum values") {
            val source = """
                enum MyEnum { A, B }
                let result = MyEnum.A == MyEnum.B;
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
        }
        
        test("pass enum to function") {
            val source = """
                enum MyEnum { A, B }
                fn process(e: MyEnum) { }
                fn main() {
                    process(MyEnum.A);
                }
            """.trimIndent()
            
            val parser = Parser(Lexer(source))
            val unit = parser.parse()
            
            parser.errors.isEmpty() shouldBe true
        }
    }
})
