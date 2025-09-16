@file:OptIn(ExperimentalWasmJsInterop::class)

package io.ygdrasil.webgpu

import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.js

actual fun <T : JsObject> createJsObject(): T = js("({ })")