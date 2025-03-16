package io.ygdrasil.webgpu

suspend fun run(canvas: HTMLCanvasElement) {
    val desc = createJsObject<AdapterDescriptor>().also {
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
    canvas.width = (canvas.clientWidth.asDouble() * devicePixelRatio) as JsNumber
    canvas.height = (canvas.clientHeight.asDouble() * devicePixelRatio) as JsNumber
    val presentationFormat = navigator.gpu.getPreferredCanvasFormat()

    println("next $presentationFormat")
}

/*



const devicePixelRatio = window.devicePixelRatio;
canvas.width = canvas.clientWidth * devicePixelRatio;
canvas.height = canvas.clientHeight * devicePixelRatio;
const presentationFormat = navigator.gpu.getPreferredCanvasFormat();

context.configure({
  device,
  format: presentationFormat,
});

const pipeline = device.createRenderPipeline({
  layout: 'auto',
  vertex: {
    module: device.createShaderModule({
      code: triangleVertWGSL,
    }),
  },
  fragment: {
    module: device.createShaderModule({
      code: redFragWGSL,
    }),
    targets: [
      {
        format: presentationFormat,
      },
    ],
  },
  primitive: {
    topology: 'triangle-list',
  },
});

function frame() {
  const commandEncoder = device.createCommandEncoder();
  const textureView = context.getCurrentTexture().createView();

  const renderPassDescriptor: GPURenderPassDescriptor = {
    colorAttachments: [
      {
        view: textureView,
        clearValue: [0, 0, 0, 0], // Clear to transparent
        loadOp: 'clear',
        storeOp: 'store',
      },
    ],
  };

  const passEncoder = commandEncoder.beginRenderPass(renderPassDescriptor);
  passEncoder.setPipeline(pipeline);
  passEncoder.draw(3);
  passEncoder.end();

  device.queue.submit([commandEncoder.finish()]);
  requestAnimationFrame(frame);
}

requestAnimationFrame(frame);

 */