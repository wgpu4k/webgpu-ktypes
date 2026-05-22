package io.ygdrasil.wgsl.tests

import io.ygdrasil.wgsl.back.BackendRegistry
import io.ygdrasil.wgsl.wgsl.WgslWriterFactory

class RoundTripTest : GoldenTestBase("wgsl") {
    init {
        // Ensure WGSL backend is registered
        BackendRegistry.DEFAULT.register("wgsl", WgslWriterFactory())
    }
}
