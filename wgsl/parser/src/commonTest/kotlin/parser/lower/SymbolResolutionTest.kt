package io.ygdrasil.wgsl.parser.lower

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldNotBe
import io.ygdrasil.wgsl.parser.TestUtils.lowerWgsl

/**
 * Tests pour la résolution des symboles (variables, fonctions, etc.)
 */
class SymbolResolutionTest : FunSpec({
    test("undefined_variables_should_throw_LoweringError") {
        val exception = kotlin.runCatching {
            lowerWgsl("fn main() -> i32 { return undefined_var; }")
        }.exceptionOrNull()
        
        exception shouldNotBe null
    }
})
