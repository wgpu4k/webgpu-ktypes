package io.ygdrasil.webgpu

external object navigator {
    val gpu: GPU?
}

external object window {
    var devicePixelRatio: JsNumber
}

external interface WGPUShaderModuleDescriptor: JsObject {
    var code: String
}

external interface WGPUCommandEncoder: JsObject {
    fun beginRenderPass(descriptor: JsObject): WGPURenderPassEncoder
    fun finish(): JsObject
}

external interface WGPURenderPassEncoder: JsObject {
    fun setPipeline(pipeline: JsObject)
    fun draw(vertexCount: Int, instanceCount: Int = 1, firstVertex: Int = 0, firstInstance: Int = 0)
    fun end()
}

external interface WGPUQueue: JsObject {
    fun submit(commandBuffers: JsObject)
}

external interface WGPUTexture: JsObject {
    fun createView(): JsObject
}

external interface WGPUDevice: JsObject {
    fun createShaderModule(descriptor: JsObject): JsObject
    fun createRenderPipeline(descriptor: JsObject): JsObject
    fun createCommandEncoder(): WGPUCommandEncoder
    val queue: WGPUQueue
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
    fun getCurrentTexture(): WGPUTexture
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

// Additional interfaces for JavaScript array manipulation
external interface JsArray: JsObject {
    fun push(item: JsObject)
    val length: Int
}

// More specific WebGPU interfaces
external interface WGPURenderPipelineDescriptor: JsObject {
    var layout: String
    var vertex: JsObject
    var fragment: JsObject
    var primitive: JsObject
}

external interface WGPUVertexState: JsObject {
    var module: JsObject
    var entryPoint: String
}

external interface WGPUFragmentState: JsObject {
    var module: JsObject
    var entryPoint: String
    var targets: JsObject
}

external interface WGPUPrimitiveState: JsObject {
    var topology: String
}

external interface WGPURenderPassDescriptor: JsObject {
    var colorAttachments: JsObject
}

external interface WGPUColorAttachment: JsObject {
    var view: JsObject
    var clearValue: JsObject
    var loadOp: String
    var storeOp: String
}
