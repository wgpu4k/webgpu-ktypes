package io.ygdrasil.webgpu

import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.w3c.dom.DocumentReadyState
import org.w3c.dom.LOADING

fun main() {
    if (document.readyState != DocumentReadyState.Companion.LOADING) {
        run()
        } else {
        window.addEventListener("DOMContentLoaded") {
            run()
        }
    }
}

fun run() {
    MainScope().launch {
        val canvas = document.getElementById("webgpu")?.unsafeCast<HTMLCanvasElement>()
            ?: error("webgpu canvs not found")
        run(canvas)
    }
}