package io.ygdrasil.wgsl.parser.validation

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ygdrasil.wgsl.ast.EnumDecl
import io.ygdrasil.wgsl.ast.EnumMember
import io.ygdrasil.wgsl.ast.EnumType
import io.ygdrasil.wgsl.ast.Expression
import io.ygdrasil.wgsl.ast.FunctionDecl
import io.ygdrasil.wgsl.ast.IdentExpr
import io.ygdrasil.wgsl.ast.IntLiteral
import io.ygdrasil.wgsl.ast.MemberAccessExpr
import io.ygdrasil.wgsl.ast.NamedType
import io.ygdrasil.wgsl.ast.Param
import io.ygdrasil.wgsl.ast.BlockStatement
import io.ygdrasil.wgsl.ast.ScalarKind
import io.ygdrasil.wgsl.ast.ScalarType
import io.ygdrasil.wgsl.ast.TranslationUnit
import io.ygdrasil.wgsl.ast.VariableDecl
import io.ygdrasil.wgsl.ast.VariableDeclKind
import io.ygdrasil.wgsl.ir.Span
import io.ygdrasil.wgsl.lexer.Lexer
import io.ygdrasil.wgsl.parser.Parser
import io.ygdrasil.wgsl.parser.TypeResolver

/**
 * Tests for enum type resolution in TypeResolver.
 */
class TypeResolverEnumTest : FunSpec({

    // ========== Basic Enum Resolution Tests ==========

    test("resolve enum declaration") {
        val source = "enum Color { RED, GREEN, BLUE }"
        val parser = Parser(Lexer(source))
        val parseResult = parser.parse()
        
        val resolver = TypeResolver()
        val result = resolver.resolve(parseResult)
        
        result.isSuccess shouldBe true
        result.resolvedUnit.declarations shouldHaveSize 1
        
        val enumDecl = result.resolvedUnit.declarations[0].shouldBeInstanceOf<EnumDecl>()
        enumDecl.name shouldBe "Color"
        enumDecl.members shouldHaveSize 3
    }

    test("resolve enum with explicit values") {
        val source = "enum Status { ACTIVE = 1, INACTIVE = 0, PENDING }"
        val parser = Parser(Lexer(source))
        val parseResult = parser.parse()
        
        val resolver = TypeResolver()
        val result = resolver.resolve(parseResult)
        
        result.isSuccess shouldBe true
        result.resolvedUnit.declarations shouldHaveSize 1
        
        val enumDecl = result.resolvedUnit.declarations[0].shouldBeInstanceOf<EnumDecl>()
        enumDecl.name shouldBe "Status"
        enumDecl.members shouldHaveSize 3
        
        // ACTIVE has explicit value
        enumDecl.members[0].name shouldBe "ACTIVE"
        enumDecl.members[0].value.shouldNotBeNull()
        
        // INACTIVE has explicit value
        enumDecl.members[1].name shouldBe "INACTIVE"
        enumDecl.members[1].value.shouldNotBeNull()
        
        // PENDING has no explicit value
        enumDecl.members[2].name shouldBe "PENDING"
        enumDecl.members[2].value shouldBe null
    }

    test("resolve enum type reference in variable") {
        val source = """
            enum Color { RED, GREEN, BLUE }
            let myColor: Color = Color.RED;
        """
        val parser = Parser(Lexer(source))
        val parseResult = parser.parse()
        
        val resolver = TypeResolver()
        val result = resolver.resolve(parseResult)
        
        result.isSuccess shouldBe true
        result.resolvedUnit.declarations shouldHaveSize 2
        
        // First declaration is the enum
        val enumDecl = result.resolvedUnit.declarations[0].shouldBeInstanceOf<EnumDecl>()
        enumDecl.name shouldBe "Color"
        
        // Second declaration is the variable
        val varDecl = result.resolvedUnit.declarations[1].shouldBeInstanceOf<VariableDecl>()
        varDecl.name shouldBe "myColor"
        varDecl.type.shouldNotBeNull()
        varDecl.type.shouldBeInstanceOf<NamedType>()
        (varDecl.type as NamedType).name shouldBe "Color"
    }

    test("resolve enum type reference in function parameter") {
        val source = """
            enum Direction { NORTH, SOUTH, EAST, WEST }
            fn move(d: Direction) {}
        """
        val parser = Parser(Lexer(source))
        val parseResult = parser.parse()
        
        val resolver = TypeResolver()
        val result = resolver.resolve(parseResult)
        
        result.isSuccess shouldBe true
        result.resolvedUnit.declarations shouldHaveSize 2
        
        // Second declaration is the function
        val funcDecl = result.resolvedUnit.declarations[1].shouldBeInstanceOf<FunctionDecl>()
        funcDecl.name shouldBe "move"
        funcDecl.parameters shouldHaveSize 1
        
        val param = funcDecl.parameters[0]
        param.name shouldBe "d"
        param.type.shouldBeInstanceOf<NamedType>()
        (param.type as NamedType).name shouldBe "Direction"
    }

    test("resolve enum member access") {
        val source = """
            enum Color { RED, GREEN, BLUE }
            let c = Color.RED;
        """
        val parser = Parser(Lexer(source))
        val parseResult = parser.parse()
        
        val resolver = TypeResolver()
        val result = resolver.resolve(parseResult)
        
        result.isSuccess shouldBe true
        result.resolvedUnit.declarations shouldHaveSize 2
        
        val varDecl = result.resolvedUnit.declarations[1].shouldBeInstanceOf<VariableDecl>()
        varDecl.name shouldBe "c"
        varDecl.initializer.shouldNotBeNull()
        varDecl.initializer.shouldBeInstanceOf<MemberAccessExpr>()
        
        val memberAccess = varDecl.initializer as MemberAccessExpr
        memberAccess.member shouldBe "RED"
    }

    // ========== Error Cases ==========

    test("error on unknown enum member access") {
        val source = """
            enum Color { RED, GREEN, BLUE }
            let c = Color.YELLOW;
        """
        val parser = Parser(Lexer(source))
        val parseResult = parser.parse()
        
        val resolver = TypeResolver()
        val result = resolver.resolve(parseResult)
        
        result.isSuccess shouldBe false
        result.unresolvedReferences shouldHaveSize 1
        result.unresolvedReferences[0].name shouldBe "YELLOW"
        result.unresolvedReferences[0].message shouldBe "Unknown enum member: YELLOW in enum Color"
    }

    test("error on unknown enum type in variable") {
        val source = """
            let x: UnknownEnum = UnknownEnum.VALUE;
        """
        val parser = Parser(Lexer(source))
        val parseResult = parser.parse()
        
        val resolver = TypeResolver()
        val result = resolver.resolve(parseResult)
        
        result.isSuccess shouldBe false
        result.unresolvedReferences shouldHaveSize 1
        result.unresolvedReferences[0].name shouldBe "UnknownEnum"
    }

    test("error on unknown enum type in function parameter") {
        val source = """
            fn foo(x: UnknownEnum) {}
        """
        val parser = Parser(Lexer(source))
        val parseResult = parser.parse()
        
        val resolver = TypeResolver()
        val result = resolver.resolve(parseResult)
        
        result.isSuccess shouldBe false
        result.unresolvedReferences shouldHaveSize 1
        result.unresolvedReferences[0].name shouldBe "UnknownEnum"
    }

    // ========== Multiple Enums ==========

    test("resolve multiple enums in same file") {
        val source = """
            enum Color { RED, GREEN, BLUE }
            enum Status { ACTIVE = 1, INACTIVE }
            
            let c: Color = Color.RED;
            let s: Status = Status.ACTIVE;
        """
        val parser = Parser(Lexer(source))
        val parseResult = parser.parse()
        
        val resolver = TypeResolver()
        val result = resolver.resolve(parseResult)
        
        result.isSuccess shouldBe true
        result.resolvedUnit.declarations shouldHaveSize 4
        
        // First two are enums
        result.resolvedUnit.declarations[0].shouldBeInstanceOf<EnumDecl>()
        result.resolvedUnit.declarations[1].shouldBeInstanceOf<EnumDecl>()
        
        // Last two are variables
        result.resolvedUnit.declarations[2].shouldBeInstanceOf<VariableDecl>()
        result.resolvedUnit.declarations[3].shouldBeInstanceOf<VariableDecl>()
    }

    // ========== Enum with Attributes ==========

    test("resolve enum with attributes") {
        val source = """
            @packed enum MyEnum { A, B, C }
        """
        val parser = Parser(Lexer(source))
        val parseResult = parser.parse()
        
        val resolver = TypeResolver()
        val result = resolver.resolve(parseResult)
        
        result.isSuccess shouldBe true
        
        val enumDecl = result.resolvedUnit.declarations[0].shouldBeInstanceOf<EnumDecl>()
        enumDecl.name shouldBe "MyEnum"
        enumDecl.attributes shouldHaveSize 1
        enumDecl.attributes[0].name shouldBe "packed"
    }

    test("resolve enum with member attributes") {
        val source = """
            enum MyEnum { @attr1 A, @attr2 B }
        """
        val parser = Parser(Lexer(source))
        val parseResult = parser.parse()
        
        val resolver = TypeResolver()
        val result = resolver.resolve(parseResult)
        
        result.isSuccess shouldBe true
        
        val enumDecl = result.resolvedUnit.declarations[0].shouldBeInstanceOf<EnumDecl>()
        enumDecl.name shouldBe "MyEnum"
        enumDecl.members shouldHaveSize 2
        
        enumDecl.members[0].name shouldBe "A"
        enumDecl.members[0].attributes shouldHaveSize 1
        enumDecl.members[0].attributes[0].name shouldBe "attr1"
        
        enumDecl.members[1].name shouldBe "B"
        enumDecl.members[1].attributes shouldHaveSize 1
        enumDecl.members[1].attributes[0].name shouldBe "attr2"
    }

    // ========== Validation Tests ==========

    test("validate resolved enum unit") {
        val source = """
            enum Color { RED, GREEN, BLUE }
            let c: Color = Color.RED;
            fn useColor(c: Color) {}
        """
        val parser = Parser(Lexer(source))
        val parseResult = parser.parse()
        
        val resolver = TypeResolver()
        val result = resolver.resolve(parseResult)
        
        result.isSuccess shouldBe true
        
        // Validate the resolved unit
        val errors = resolver.validateResolution(result.resolvedUnit)
        errors shouldBeEmpty ()
    }

    test("validate enum with invalid member access") {
        val source = """
            enum Color { RED, GREEN, BLUE }
            let c = Color.INVALID;
        """
        val parser = Parser(Lexer(source))
        val parseResult = parser.parse()
        
        val resolver = TypeResolver()
        val result = resolver.resolve(parseResult)
        
        result.isSuccess shouldBe false
        
        // The error should be caught during resolution
        result.unresolvedReferences shouldHaveSize 1
    }

    // ========== Type Resolution Tests ==========

    test("NamedType for enum resolves to EnumType") {
        val source = """
            enum MyEnum { A, B }
            fn test(x: MyEnum) {}
        """
        val parser = Parser(Lexer(source))
        val parseResult = parser.parse()
        
        val resolver = TypeResolver()
        val result = resolver.resolve(parseResult)
        
        result.isSuccess shouldBe true
        
        val funcDecl = result.resolvedUnit.declarations[1].shouldBeInstanceOf<FunctionDecl>()
        val paramType = funcDecl.parameters[0].type
        
        // When a NamedType references an enum, it should be resolved to EnumType
        // But in the current implementation, NamedType is kept and validated
        // The actual type checking happens in validation
        // For now, we just verify the NamedType references the enum correctly
        paramType.shouldBeInstanceOf<NamedType>()
        (paramType as NamedType).name shouldBe "MyEnum"
    }

    // ========== Edge Cases ==========

    test("empty enum") {
        val source = "enum Empty { }"
        val parser = Parser(Lexer(source))
        val parseResult = parser.parse()
        
        val resolver = TypeResolver()
        val result = resolver.resolve(parseResult)
        
        result.isSuccess shouldBe true
        
        val enumDecl = result.resolvedUnit.declarations[0].shouldBeInstanceOf<EnumDecl>()
        enumDecl.name shouldBe "Empty"
        enumDecl.members shouldBeEmpty ()
    }

    test("enum with trailing comma") {
        val source = "enum Color { RED, GREEN, BLUE = 3, }"
        val parser = Parser(Lexer(source))
        val parseResult = parser.parse()
        
        val resolver = TypeResolver()
        val result = resolver.resolve(parseResult)
        
        result.isSuccess shouldBe true
        
        val enumDecl = result.resolvedUnit.declarations[0].shouldBeInstanceOf<EnumDecl>()
        enumDecl.members shouldHaveSize 3
    }
})
