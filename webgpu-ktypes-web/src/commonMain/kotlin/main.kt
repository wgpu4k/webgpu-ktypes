package io.ygdrasil.webgpu

// Define shader code as constants
const val triangleVertWGSL = """
    @vertex
    fn main(@builtin(vertex_index) vertexIndex : u32) -> @builtin(position) vec4<f32> {
        var pos = array<vec2<f32>, 3>(
            vec2<f32>(0.0, 0.5),
            vec2<f32>(-0.5, -0.5),
            vec2<f32>(0.5, -0.5)
        );
        return vec4<f32>(pos[vertexIndex], 0.0, 1.0);
    }
"""

const val redFragWGSL = """
    @fragment
    fn main() -> @location(0) vec4<f32> {
        return vec4<f32>(1.0, 0.0, 0.0, 1.0);
    }
"""

// Define requestAnimationFrame as an external function
external fun requestAnimationFrame(callback: () -> Unit)

suspend fun run(canvas: HTMLCanvasElement) {
    val desc = createJsObject<WGPUAdapterDescriptor>().also {
        it.featureLevel = "compatibility"
    }
    val gpu = navigator.gpu ?: error("fail to get GPU")
    val adapter = gpu.requestAdapter(desc.castAs())
        .wait<WGPUAdapter>()

    val device = adapter.requestDevice()
        .wait<WGPUDevice>()

    val context = canvas.getContext("webgpu")
        .castAs<WGPUCanvasContext>()

    val devicePixelRatio = window.devicePixelRatio.asDouble()
    canvas.width = (canvas.clientWidth.asDouble() * devicePixelRatio).asJsNumber()
    canvas.height = (canvas.clientHeight.asDouble() * devicePixelRatio).asJsNumber()
    val presentationFormat = navigator.gpu.getPreferredCanvasFormat()

    // Configure the canvas context
    val canvasConfig = createJsObject<WGPUCanvasConfiguration>().also {
        it.device = device
        it.format = presentationFormat
    }
    context.configure(canvasConfig)

    // Create shader modules for vertex and fragment shaders
    val vertexShaderModuleDesc = createJsObject<WGPUShaderModuleDescriptor>()
    vertexShaderModuleDesc.code = triangleVertWGSL
    val vertexShaderModule = device.createShaderModule(vertexShaderModuleDesc)

    val fragmentShaderModuleDesc = createJsObject<WGPUShaderModuleDescriptor>()
    fragmentShaderModuleDesc.code = redFragWGSL
    val fragmentShaderModule = device.createShaderModule(fragmentShaderModuleDesc)

    // Create render pipeline
    val pipelineDesc = createJsObject<WGPURenderPipelineDescriptor>()
    pipelineDesc.layout = "auto"

    // Set vertex state
    val vertexState = createJsObject<WGPUVertexState>()
    vertexState.module = vertexShaderModule
    pipelineDesc.vertex = vertexState

    // Set fragment state
    val fragmentState = createJsObject<WGPUFragmentState>()
    fragmentState.module = fragmentShaderModule

    // Create targets array with one element
    val targetArray = createJsObject<JsArray>()
    val target = createJsObject<WGPUColorTargetState>()
    target.format = presentationFormat
    targetArray.push(target)

    fragmentState.targets = targetArray
    pipelineDesc.fragment = fragmentState

    // Set primitive state
    val primitiveState = createJsObject<WGPUPrimitiveState>()
    primitiveState.topology = "triangle-list"
    pipelineDesc.primitive = primitiveState

    val pipeline = device.createRenderPipeline(pipelineDesc)

    // Define the frame function
    fun frame() {
        val commandEncoder = device.createCommandEncoder()
        val textureView = context.getCurrentTexture().createView()

        // Create render pass descriptor
        val renderPassDesc = createJsObject<WGPURenderPassDescriptor>()

        // Create color attachments array with one element
        val colorAttachmentsArray = createJsObject<JsArray>()
        val colorAttachment = createJsObject<WGPUColorAttachment>()
        colorAttachment.view = textureView

        // Create clear value array [0, 0, 0, 0]
        val clearValueArray = createJsObject<JsArray>()
        clearValueArray.push(0)
        clearValueArray.push(0)
        clearValueArray.push(0)
        clearValueArray.push(0)

        colorAttachment.clearValue = clearValueArray
        colorAttachment.loadOp = "clear"
        colorAttachment.storeOp = "store"

        colorAttachmentsArray.push(colorAttachment)
        renderPassDesc.colorAttachments = colorAttachmentsArray

        // Begin render pass
        val passEncoder = commandEncoder.beginRenderPass(renderPassDesc)
        passEncoder.setPipeline(pipeline)
        passEncoder.draw(3)
        passEncoder.end()

        // Submit command buffer
        val commandBuffer = commandEncoder.finish()

        val commandBuffersArray = createJsObject<JsArray>()
        commandBuffersArray.push(commandBuffer)

        device.queue.submit(commandBuffersArray)

        // Schedule next frame
        requestAnimationFrame(::frame)
    }

    // Start the animation loop
    requestAnimationFrame(::frame)
}
