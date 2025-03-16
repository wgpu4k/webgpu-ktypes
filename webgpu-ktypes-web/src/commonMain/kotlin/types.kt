package io.ygdrasil.webgpu

external object navigator {
    val gpu: GPU?
}

external object window {
    var devicePixelRatio: JsNumber
}


external interface WGPUDevice: JsObject {

}

external interface WGPUAdapter: JsObject {
    fun requestDevice(): JsObject
}

external interface WGPUAdapterDescriptor: JsObject {
    var featureLevel: String
}

external interface GPU: JsObject {
    fun getPreferredCanvasFormat(): String
    abstract fun requestAdapter(any: JsObject): JsObject
}

external interface HTMLCanvasElement: JsObject {
    fun getContext(name: String): JsObject

    var clientHeight: JsNumber
    var clientWidth: JsNumber
    var width: JsNumber
    var height: JsNumber
}

external interface WGPUCanvasContext : JsObject {
    var canvas: HTMLCanvasElement /* HTMLCanvasElement | OffscreenCanvas */
    fun configure(configuration: WGPUCanvasConfiguration)
    fun unconfigure()
    fun getCurrentTexture(): JsObject
}

external interface WGPUCanvasConfiguration: JsObject {
    // GPUDevice
    var device: JsObject
    var format: JsString
    // GPUTextureUsageFlags
    var usage: JsNumber?
    // Array<String>
    var viewFormats: JsObject
    var colorSpace: JsString?
    var alphaMode: JsString?
}