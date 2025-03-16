package io.ygdrasil.webgpu

import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

fun main() {
    window.addEventListener("DOMContentLoaded", {
        val canvas = document.getElementById("webgpu") ?: error("fail to get canvas")
        MainScope().launch {
            run(canvas.unsafeCast<HTMLCanvasElement>())
        }
    })
}