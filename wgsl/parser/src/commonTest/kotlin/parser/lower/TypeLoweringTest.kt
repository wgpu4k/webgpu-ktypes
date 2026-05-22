package io.ygdrasil.wgsl.parser.lower

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.beInstanceOf
import io.ygdrasil.wgsl.ir.ScalarKind
import io.ygdrasil.wgsl.ir.TypeInner
import io.ygdrasil.wgsl.ir.VectorSize
import io.ygdrasil.wgsl.parser.lowerWgsl
import io.ygdrasil.wgsl.parser.findScalarType
import io.ygdrasil.wgsl.parser.findType
import io.ygdrasil.wgsl.parser.findVectorType

class TypeLoweringTest : FunSpec({
    test("T001: should lower simple function with i32 return type") {
        val module = lowerWgsl("fn main() -> i32 { return 0; }")
        
        module.types.toList() shouldHaveSize 1
        val type = module.types.toList()[0]
        type.inner shouldBe TypeInner.Scalar(ScalarKind.Sint, 4)
    }

    test("T002: should lower function with f32 return type") {
        val module = lowerWgsl("fn main() -> f32 { return 0.0; }")
        
        module.types.toList() shouldHaveSize 1
        val type = module.types.toList()[0]
        type.inner shouldBe TypeInner.Scalar(ScalarKind.F32, 4)
    }

    test("T003: should lower function with bool return type") {
        val module = lowerWgsl("fn main() -> bool { return true; }")
        
        // Find the bool type by kind and width
        val boolType = module.findScalarType(ScalarKind.Bool, 1)
        boolType shouldNotBe null
    }

    test("T004: should lower vec2<f32> type") {
        val module = lowerWgsl("fn main() -> vec2<f32> { return vec2(0.0); }")
        
        module.types.toList() shouldHaveSize 2 // f32 + vec2<f32>
        
        // Find the vector type
        val vec2Type = module.types.toList().find { type: io.ygdrasil.wgsl.ir.Type ->
            type.inner is TypeInner.Vector && 
            (type.inner as TypeInner.Vector).size == VectorSize.Bi
        }
        vec2Type shouldNotBe null
    }

    test("T005: should lower vec3<f32> type") {
        val module = lowerWgsl("fn main() -> vec3<f32> { return vec3(0.0); }")
        
        val vec3Type = module.types.toList().find { type: io.ygdrasil.wgsl.ir.Type ->
            type.inner is TypeInner.Vector && 
            (type.inner as TypeInner.Vector).size == VectorSize.Tri
        }
        vec3Type shouldNotBe null
    }

    test("T006: should lower vec4<f32> type") {
        val module = lowerWgsl("fn main() -> vec4<f32> { return vec4(0.0); }")
        
        val vec4Type = module.types.toList().find { type: io.ygdrasil.wgsl.ir.Type ->
            type.inner is TypeInner.Vector && 
            (type.inner as TypeInner.Vector).size == VectorSize.Quad
        }
        vec4Type shouldNotBe null
    }

    test("T007: should lower simple struct") {
        // Note: There's a known bug where struct parsing creates an extra empty member
        // between members with trailing commas. This test verifies the struct is lowered
        // and has the expected members (ignoring empty ones).
        val module = lowerWgsl("""
            struct S {
                x: i32,
                y: f32
            }
            fn main() -> S { return S(0, 0.0); }
        """)
        
        val structType = module.findType { inner -> inner is TypeInner.Struct }
        structType shouldNotBe null
        
        val irStruct = structType!!.inner as TypeInner.Struct
        // Verified: struct has exactly 2 members after the parsing fix
        // Verify that x and y members exist
        irStruct.members.size shouldBe 2
        
        // Find members by name
        val xMember = irStruct.members.find { it.name == "x" }
        val yMember = irStruct.members.find { it.name == "y" }
        
        xMember shouldNotBe null
        yMember shouldNotBe null
    }

    test("vec3_should_not_duplicate_scalar_types") {
        val module = lowerWgsl("fn main() -> vec3<f32> { return vec3(1.0); }")
        
        val scalarF32Count = module.types.toList().count {
            it.inner is TypeInner.Scalar && 
            (it.inner as TypeInner.Scalar).kind == ScalarKind.F32
        }
        
        // Bug: crée un nouveau F32 pour chaque vec3
        scalarF32Count shouldBe 1
    }

    test("nested_struct_members_should_resolve_to_actual_types") {
        val module = lowerWgsl("""
            struct Inner { a: i32 }
            struct Outer { inner: Inner }
        """)
        
        // Trouver Outer (celui qui a un membre 'inner')
        val outerType = module.types.toList().find { type ->
            type.inner is TypeInner.Struct && 
            (type.inner as TypeInner.Struct).members.any { it.name == "inner" }
        }
        outerType shouldNotBe null
        
        val innerMember = (outerType!!.inner as TypeInner.Struct).members.find { it.name == "inner" }
        innerMember shouldNotBe null
        
        // Le type du membre inner doit pointer vers Inner (pas un struct vide)
        val innerType = module.types[innerMember!!.type]
        (innerType.inner as? TypeInner.Struct)?.members?.isEmpty() shouldBe false
    }
})
