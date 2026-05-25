package io.ygdrasil.wgsl.tests

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe

class GoldenCompletenessTest : FunSpec({
    val rootDir = GoldenCorpus.findProjectRoot()
    val inputNames = GoldenCorpus.inputNames(rootDir)
    val documentedMissingOutputs = GoldenCorpus.documentedMissingOutputs(rootDir)

    test("all missing golden outputs are explicitly documented") {
        inputNames.size shouldBe 160
        documentedMissingOutputs.size shouldBe 0

        GoldenCorpus.backends.forEach { backend ->
            GoldenCorpus.missingOutputs(backend, rootDir) shouldBe documentedMissingOutputs
        }
    }

    test("golden output directories do not contain orphan outputs") {
        GoldenCorpus.backends.forEach { backend ->
            GoldenCorpus.orphanOutputs(backend, rootDir).shouldBeEmpty()
        }
    }

    test("documented missing golden outputs still exist as inputs") {
        (documentedMissingOutputs - inputNames).shouldBeEmpty()
    }

    test("expected golden failures are validated and versioned") {
        GoldenExpectedFailures.load(rootDir).size shouldBe 239
    }
})
