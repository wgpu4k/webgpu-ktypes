@file:Suppress("unused")
// This file has been generated DO NO EDIT
package io.ygdrasil.webgpu

external interface WGPUObjectBase : JsObject {
	var label: String
}

external interface WGPUSupportedLimits : JsObject {
	var maxTextureDimension1D: JsNumber  /* unsigned long */
	var maxTextureDimension2D: JsNumber  /* unsigned long */
	var maxTextureDimension3D: JsNumber  /* unsigned long */
	var maxTextureArrayLayers: JsNumber  /* unsigned long */
	var maxBindGroups: JsNumber  /* unsigned long */
	var maxBindGroupsPlusVertexBuffers: JsNumber  /* unsigned long */
	var maxBindingsPerBindGroup: JsNumber  /* unsigned long */
	var maxDynamicUniformBuffersPerPipelineLayout: JsNumber  /* unsigned long */
	var maxDynamicStorageBuffersPerPipelineLayout: JsNumber  /* unsigned long */
	var maxSampledTexturesPerShaderStage: JsNumber  /* unsigned long */
	var maxSamplersPerShaderStage: JsNumber  /* unsigned long */
	var maxStorageBuffersPerShaderStage: JsNumber  /* unsigned long */
	var maxStorageTexturesPerShaderStage: JsNumber  /* unsigned long */
	var maxUniformBuffersPerShaderStage: JsNumber  /* unsigned long */
	var maxUniformBufferBindingSize: JsNumber  /* unsigned long long */
	var maxStorageBufferBindingSize: JsNumber  /* unsigned long long */
	var minUniformBufferOffsetAlignment: JsNumber  /* unsigned long */
	var minStorageBufferOffsetAlignment: JsNumber  /* unsigned long */
	var maxVertexBuffers: JsNumber  /* unsigned long */
	var maxBufferSize: JsNumber  /* unsigned long long */
	var maxVertexAttributes: JsNumber  /* unsigned long */
	var maxVertexBufferArrayStride: JsNumber  /* unsigned long */
	var maxInterStageShaderVariables: JsNumber  /* unsigned long */
	var maxColorAttachments: JsNumber  /* unsigned long */
	var maxColorAttachmentBytesPerSample: JsNumber  /* unsigned long */
	var maxComputeWorkgroupStorageSize: JsNumber  /* unsigned long */
	var maxComputeInvocationsPerWorkgroup: JsNumber  /* unsigned long */
	var maxComputeWorkgroupSizeX: JsNumber  /* unsigned long */
	var maxComputeWorkgroupSizeY: JsNumber  /* unsigned long */
	var maxComputeWorkgroupSizeZ: JsNumber  /* unsigned long */
	var maxComputeWorkgroupsPerDimension: JsNumber  /* unsigned long */
}

external interface WGPUSupportedFeatures : JsObject
external interface WGPUAdapterInfo : JsObject {
	var vendor: String
	var architecture: String
	var device: String
	var description: String
	var subgroupMinSize: JsNumber  /* unsigned long */
	var subgroupMaxSize: JsNumber  /* unsigned long */
}

external interface WGPU : JsObject {
	var wgslLanguageFeatures: JsObject /* WGSLLanguageFeatures */
	fun requestAdapter(): JsObject /* Promise */
	fun requestAdapter(options: WGPURequestAdapterOptions): JsObject /* Promise */
	fun getPreferredCanvasFormat(): String
}

external interface WGPUAdapter : JsObject {
	var features: WGPUSupportedFeatures
	var limits: WGPUSupportedLimits
	var info: WGPUAdapterInfo
	var isFallbackAdapter: JsObject /* boolean */
	fun requestDevice(): JsObject /* Promise */
	fun requestDevice(descriptor: WGPUDeviceDescriptor): JsObject /* Promise */
}

external interface WGPUDevice : JsObject, EventTarget, WGPUObjectBase {
	var features: WGPUSupportedFeatures
	var limits: WGPUSupportedLimits
	var adapterInfo: WGPUAdapterInfo
	var queue: WGPUQueue
	var lost: JsObject /* Promise */
	var onuncapturederror: JsObject /* EventHandler */
	fun destroy()
	fun createBuffer(descriptor: WGPUBufferDescriptor): WGPUBuffer
	fun createTexture(descriptor: WGPUTextureDescriptor): WGPUTexture
	fun createSampler(): WGPUSampler
	fun createSampler(descriptor: WGPUSamplerDescriptor): WGPUSampler
	fun importExternalTexture(descriptor: WGPUExternalTextureDescriptor): WGPUExternalTexture
	fun createBindGroupLayout(descriptor: WGPUBindGroupLayoutDescriptor): WGPUBindGroupLayout
	fun createPipelineLayout(descriptor: WGPUPipelineLayoutDescriptor): WGPUPipelineLayout
	fun createBindGroup(descriptor: WGPUBindGroupDescriptor): WGPUBindGroup
	fun createShaderModule(descriptor: WGPUShaderModuleDescriptor): WGPUShaderModule
	fun createComputePipeline(descriptor: WGPUComputePipelineDescriptor): WGPUComputePipeline
	fun createRenderPipeline(descriptor: WGPURenderPipelineDescriptor): WGPURenderPipeline
	fun createComputePipelineAsync(descriptor: WGPUComputePipelineDescriptor): JsObject /* Promise */
	fun createRenderPipelineAsync(descriptor: WGPURenderPipelineDescriptor): JsObject /* Promise */
	fun createCommandEncoder(): WGPUCommandEncoder
	fun createCommandEncoder(descriptor: WGPUCommandEncoderDescriptor): WGPUCommandEncoder
	fun createRenderBundleEncoder(descriptor: WGPURenderBundleEncoderDescriptor): WGPURenderBundleEncoder
	fun createQuerySet(descriptor: WGPUQuerySetDescriptor): WGPUQuerySet
	fun pushErrorScope(filter: String)
	fun popErrorScope(): JsObject /* Promise */
}

external interface WGPUBuffer : JsObject, WGPUObjectBase {
	var size: JsObject
	var usage: JsObject
	var mapState: String
	fun mapAsync(mode: JsObject): JsObject /* Promise */
	fun mapAsync(mode: JsObject, offset: JsObject): JsObject /* Promise */
	fun mapAsync(mode: JsObject, offset: JsObject, size: JsObject): JsObject /* Promise */
	fun getMappedRange(): JsObject /* ArrayBuffer */
	fun getMappedRange(offset: JsObject): JsObject /* ArrayBuffer */
	fun getMappedRange(offset: JsObject, size: JsObject): JsObject /* ArrayBuffer */
	fun unmap()
	fun destroy()
}

external interface WGPUTexture : JsObject, WGPUObjectBase {
	var width: JsObject
	var height: JsObject
	var depthOrArrayLayers: JsObject
	var mipLevelCount: JsObject
	var sampleCount: JsObject
	var dimension: String
	var format: String
	var usage: JsObject
	fun createView(): WGPUTextureView
	fun createView(descriptor: WGPUTextureViewDescriptor): WGPUTextureView
	fun destroy()
}

external interface WGPUTextureView : JsObject, WGPUObjectBase
external interface WGPUExternalTexture : JsObject, WGPUObjectBase
external interface WGPUSampler : JsObject, WGPUObjectBase
external interface WGPUBindGroupLayout : JsObject, WGPUObjectBase
external interface WGPUBindGroup : JsObject, WGPUObjectBase
external interface WGPUPipelineLayout : JsObject, WGPUObjectBase
external interface WGPUShaderModule : JsObject, WGPUObjectBase {
	fun getCompilationInfo(): JsObject /* Promise */
}

external interface WGPUCompilationMessage : JsObject {
	var message: String
	var type: String
	var lineNum: JsNumber  /* unsigned long long */
	var linePos: JsNumber  /* unsigned long long */
	var offset: JsNumber  /* unsigned long long */
	var length: JsNumber  /* unsigned long long */
}

external interface WGPUCompilationInfo : JsObject {
	var messages: JsArray<JsObject> /* FrozenArray<GPUCompilationMessage> */
}

external interface WGPUPipelineError : JsObject, DOMException {
	var reason: JsObject
}

external interface WGPUPipelineBase : JsObject {
	fun getBindGroupLayout(index: JsNumber  /* unsigned long */): WGPUBindGroupLayout
}

external interface WGPUComputePipeline : JsObject, WGPUObjectBase, WGPUPipelineBase
external interface WGPURenderPipeline : JsObject, WGPUObjectBase, WGPUPipelineBase
external interface WGPUCommandBuffer : JsObject, WGPUObjectBase
external interface WGPUCommandsMixin : JsObject
external interface WGPUCommandEncoder : JsObject, WGPUObjectBase, WGPUCommandsMixin, WGPUDebugCommandsMixin {
	fun beginRenderPass(descriptor: WGPURenderPassDescriptor): WGPURenderPassEncoder
	fun beginComputePass(): WGPUComputePassEncoder
	fun beginComputePass(descriptor: WGPUComputePassDescriptor): WGPUComputePassEncoder
	fun copyBufferToBuffer(source: WGPUBuffer, sourceOffset: JsObject, destination: WGPUBuffer, destinationOffset: JsObject, size: JsObject)
	fun copyBufferToTexture(source: WGPUTexelCopyBufferInfo, destination: WGPUTexelCopyTextureInfo, copySize: JsObject)
	fun copyTextureToBuffer(source: WGPUTexelCopyTextureInfo, destination: WGPUTexelCopyBufferInfo, copySize: JsObject)
	fun copyTextureToTexture(source: WGPUTexelCopyTextureInfo, destination: WGPUTexelCopyTextureInfo, copySize: JsObject)
	fun clearBuffer(buffer: WGPUBuffer)
	fun clearBuffer(buffer: WGPUBuffer, offset: JsObject)
	fun clearBuffer(buffer: WGPUBuffer, offset: JsObject, size: JsObject)
	fun resolveQuerySet(querySet: WGPUQuerySet, firstQuery: JsObject, queryCount: JsObject, destination: WGPUBuffer, destinationOffset: JsObject)
	fun finish(): WGPUCommandBuffer
	fun finish(descriptor: WGPUCommandBufferDescriptor): WGPUCommandBuffer
}

external interface WGPUBindingCommandsMixin : JsObject {
	fun setBindGroup(index: JsObject, bindGroup: JsObject)
	fun setBindGroup(index: JsObject, bindGroup: JsObject, dynamicOffsets: JsArray<JsObject> /* sequence<GPUBufferDynamicOffset> */)
	fun setBindGroup(index: JsObject, bindGroup: JsObject, dynamicOffsetsData: JsObject /* Uint32Array */, dynamicOffsetsDataStart: JsObject, dynamicOffsetsDataLength: JsObject)
}

external interface WGPUDebugCommandsMixin : JsObject {
	fun pushDebugGroup(groupLabel: String)
	fun popDebugGroup()
	fun insertDebugMarker(markerLabel: String)
}

external interface WGPUComputePassEncoder : JsObject, WGPUObjectBase, WGPUCommandsMixin, WGPUDebugCommandsMixin, WGPUBindingCommandsMixin {
	fun setPipeline(pipeline: WGPUComputePipeline)
	fun dispatchWorkgroups(workgroupCountX: JsObject)
	fun dispatchWorkgroups(workgroupCountX: JsObject, workgroupCountY: JsObject)
	fun dispatchWorkgroups(workgroupCountX: JsObject, workgroupCountY: JsObject, workgroupCountZ: JsObject)
	fun dispatchWorkgroupsIndirect(indirectBuffer: WGPUBuffer, indirectOffset: JsObject)
	fun end()
}

external interface WGPURenderPassEncoder : JsObject, WGPUObjectBase, WGPUCommandsMixin, WGPUDebugCommandsMixin, WGPUBindingCommandsMixin, WGPURenderCommandsMixin {
	fun setViewport(x: JsNumber  /* float */, y: JsNumber  /* float */, width: JsNumber  /* float */, height: JsNumber  /* float */, minDepth: JsNumber  /* float */, maxDepth: JsNumber  /* float */)
	fun setScissorRect(x: JsObject, y: JsObject, width: JsObject, height: JsObject)
	fun setBlendConstant(color: JsObject)
	fun setStencilReference(reference: JsObject)
	fun beginOcclusionQuery(queryIndex: JsObject)
	fun endOcclusionQuery()
	fun executeBundles(bundles: JsArray<JsObject> /* sequence<GPURenderBundle> */)
	fun end()
}

external interface WGPURenderCommandsMixin : JsObject {
	fun setPipeline(pipeline: WGPURenderPipeline)
	fun setIndexBuffer(buffer: WGPUBuffer, indexFormat: String)
	fun setIndexBuffer(buffer: WGPUBuffer, indexFormat: String, offset: JsObject)
	fun setIndexBuffer(buffer: WGPUBuffer, indexFormat: String, offset: JsObject, size: JsObject)
	fun setVertexBuffer(slot: JsObject, buffer: JsObject)
	fun setVertexBuffer(slot: JsObject, buffer: JsObject, offset: JsObject)
	fun setVertexBuffer(slot: JsObject, buffer: JsObject, offset: JsObject, size: JsObject)
	fun draw(vertexCount: JsObject)
	fun draw(vertexCount: JsObject, instanceCount: JsObject)
	fun draw(vertexCount: JsObject, instanceCount: JsObject, firstVertex: JsObject)
	fun draw(vertexCount: JsObject, instanceCount: JsObject, firstVertex: JsObject, firstInstance: JsObject)
	fun drawIndexed(indexCount: JsObject)
	fun drawIndexed(indexCount: JsObject, instanceCount: JsObject)
	fun drawIndexed(indexCount: JsObject, instanceCount: JsObject, firstIndex: JsObject)
	fun drawIndexed(indexCount: JsObject, instanceCount: JsObject, firstIndex: JsObject, baseVertex: JsObject)
	fun drawIndexed(indexCount: JsObject, instanceCount: JsObject, firstIndex: JsObject, baseVertex: JsObject, firstInstance: JsObject)
	fun drawIndirect(indirectBuffer: WGPUBuffer, indirectOffset: JsObject)
	fun drawIndexedIndirect(indirectBuffer: WGPUBuffer, indirectOffset: JsObject)
}

external interface WGPURenderBundle : JsObject, WGPUObjectBase
external interface WGPURenderBundleEncoder : JsObject, WGPUObjectBase, WGPUCommandsMixin, WGPUDebugCommandsMixin, WGPUBindingCommandsMixin, WGPURenderCommandsMixin {
	fun finish(): WGPURenderBundle
	fun finish(descriptor: WGPURenderBundleDescriptor): WGPURenderBundle
}

external interface WGPUQueue : JsObject, WGPUObjectBase {
	fun submit(commandBuffers: JsArray<JsObject> /* sequence<GPUCommandBuffer> */)
	fun onSubmittedWorkDone(): JsObject /* Promise */
	fun writeBuffer(buffer: WGPUBuffer, bufferOffset: JsObject, data: JsObject /* AllowSharedBufferSource */)
	fun writeBuffer(buffer: WGPUBuffer, bufferOffset: JsObject, data: JsObject /* AllowSharedBufferSource */, dataOffset: JsObject)
	fun writeBuffer(buffer: WGPUBuffer, bufferOffset: JsObject, data: JsObject /* AllowSharedBufferSource */, dataOffset: JsObject, size: JsObject)
	fun writeTexture(destination: WGPUTexelCopyTextureInfo, data: JsObject /* AllowSharedBufferSource */, dataLayout: WGPUTexelCopyBufferLayout, size: JsObject)
	fun copyExternalImageToTexture(source: WGPUCopyExternalImageSourceInfo, destination: WGPUCopyExternalImageDestInfo, copySize: JsObject)
}

external interface WGPUQuerySet : JsObject, WGPUObjectBase {
	var type: String
	var count: JsObject
	fun destroy()
}

external interface WGPUCanvasContext : JsObject {
	var canvas: JsObject /* (HTMLCanvasElement or OffscreenCanvas) */
	fun configure(configuration: WGPUCanvasConfiguration)
	fun unconfigure()
	fun getConfiguration(): JsObject
	fun getCurrentTexture(): WGPUTexture
}

external interface WGPUDeviceLostInfo : JsObject {
	var reason: String
	var message: String
}

external interface WGPUError : JsObject {
	var message: String
}

external interface WGPUValidationError : JsObject, WGPUError
external interface WGPUOutOfMemoryError : JsObject, WGPUError
external interface WGPUInternalError : JsObject, WGPUError
external interface WGPUUncapturedErrorEvent : JsObject, Event {
	var error: WGPUError
}

external interface WGPUObjectDescriptorBase : JsObject {
	var label: String
}

external interface WGPURequestAdapterOptions : JsObject {
	var featureLevel: String
	var powerPreference: String
	var forceFallbackAdapter: JsObject /* boolean */
	var xrCompatible: JsObject /* boolean */
}

external interface WGPUDeviceDescriptor : JsObject, WGPUObjectDescriptorBase {
	var requiredFeatures: JsArray<JsObject> /* sequence<GPUFeatureName> */
	var requiredLimits: JsMap<JsObject, JsObject>  /* record<DOMString, (GPUSize64orundefined)>  */
	var defaultQueue: WGPUQueueDescriptor
}

external interface WGPUBufferDescriptor : JsObject, WGPUObjectDescriptorBase {
	var size: JsObject
	var usage: JsObject
	var mappedAtCreation: JsObject /* boolean */
}

external interface WGPUTextureDescriptor : JsObject, WGPUObjectDescriptorBase {
	var size: JsObject
	var mipLevelCount: JsObject
	var sampleCount: JsObject
	var dimension: String
	var format: String
	var usage: JsObject
	var viewFormats: JsArray<JsObject> /* sequence<GPUTextureFormat> */
}

external interface WGPUTextureViewDescriptor : JsObject, WGPUObjectDescriptorBase {
	var format: String
	var dimension: String
	var usage: JsObject
	var aspect: String
	var baseMipLevel: JsObject
	var mipLevelCount: JsObject
	var baseArrayLayer: JsObject
	var arrayLayerCount: JsObject
}

external interface WGPUExternalTextureDescriptor : JsObject, WGPUObjectDescriptorBase {
	var colorSpace: JsObject /* PredefinedColorSpace */
}

external interface WGPUSamplerDescriptor : JsObject, WGPUObjectDescriptorBase {
	var addressModeU: String
	var addressModeV: String
	var addressModeW: String
	var magFilter: String
	var minFilter: String
	var mipmapFilter: String
	var lodMinClamp: JsNumber  /* float */
	var lodMaxClamp: JsNumber  /* float */
	var compare: String
	var maxAnisotropy: JsNumber  /* unsigned short */
}

external interface WGPUBindGroupLayoutDescriptor : JsObject, WGPUObjectDescriptorBase {
	var entries: JsArray<JsObject> /* sequence<GPUBindGroupLayoutEntry> */
}

external interface WGPUBindGroupLayoutEntry : JsObject {
	var binding: JsObject
	var visibility: JsObject
	var buffer: WGPUBufferBindingLayout
	var sampler: WGPUSamplerBindingLayout
	var texture: WGPUTextureBindingLayout
	var storageTexture: WGPUStorageTextureBindingLayout
}

external interface WGPUBufferBindingLayout : JsObject {
	var type: String
	var hasDynamicOffset: JsObject /* boolean */
	var minBindingSize: JsObject
}

external interface WGPUSamplerBindingLayout : JsObject {
	var type: String
}

external interface WGPUTextureBindingLayout : JsObject {
	var sampleType: String
	var viewDimension: String
	var multisampled: JsObject /* boolean */
}

external interface WGPUStorageTextureBindingLayout : JsObject {
	var access: String
	var format: String
	var viewDimension: String
}

external interface WGPUExternalTextureBindingLayout : JsObject
external interface WGPUBindGroupDescriptor : JsObject, WGPUObjectDescriptorBase {
	var layout: WGPUBindGroupLayout
	var entries: JsArray<JsObject> /* sequence<GPUBindGroupEntry> */
}

external interface WGPUBindGroupEntry : JsObject {
	var binding: JsObject
	var resource: JsObject
}

external interface WGPUBufferBinding : JsObject {
	var buffer: WGPUBuffer
	var offset: JsObject
	var size: JsObject
}

external interface WGPUPipelineLayoutDescriptor : JsObject, WGPUObjectDescriptorBase {
	var bindGroupLayouts: JsArray<JsObject> /* sequence<GPUBindGroupLayout?> */
}

external interface WGPUShaderModuleDescriptor : JsObject, WGPUObjectDescriptorBase {
	var code: String
	var compilationHints: JsArray<JsObject> /* sequence<GPUShaderModuleCompilationHint> */
}

external interface WGPUShaderModuleCompilationHint : JsObject {
	var entryPoint: String
	var layout: JsObject
}

external interface WGPUPipelineErrorInit : JsObject
external interface WGPUPipelineDescriptorBase : JsObject, WGPUObjectDescriptorBase {
	var layout: JsObject
}

external interface WGPUProgrammableStage : JsObject {
	var module: WGPUShaderModule
	var entryPoint: String
	var constants: JsMap<JsObject, JsObject>  /* record<USVString, GPUPipelineConstantValue>  */
}

external interface WGPUComputePipelineDescriptor : JsObject, WGPUPipelineDescriptorBase {
	var compute: WGPUProgrammableStage
}

external interface WGPURenderPipelineDescriptor : JsObject, WGPUPipelineDescriptorBase {
	var vertex: WGPUVertexState
	var primitive: WGPUPrimitiveState
	var depthStencil: WGPUDepthStencilState
	var multisample: WGPUMultisampleState
	var fragment: WGPUFragmentState
}

external interface WGPUPrimitiveState : JsObject {
	var topology: String
	var stripIndexFormat: String
	var frontFace: String
	var cullMode: String
	var unclippedDepth: JsObject /* boolean */
}

external interface WGPUMultisampleState : JsObject {
	var count: JsObject
	var mask: JsObject
	var alphaToCoverageEnabled: JsObject /* boolean */
}

external interface WGPUFragmentState : JsObject, WGPUProgrammableStage {
	var targets: JsArray<JsObject> /* sequence<GPUColorTargetState?> */
}

external interface WGPUColorTargetState : JsObject {
	var format: String
	var blend: WGPUBlendState
	var writeMask: JsObject
}

external interface WGPUBlendState : JsObject {
	var color: WGPUBlendComponent
	var alpha: WGPUBlendComponent
}

external interface WGPUBlendComponent : JsObject {
	var operation: String
	var srcFactor: String
	var dstFactor: String
}

external interface WGPUDepthStencilState : JsObject {
	var format: String
	var depthWriteEnabled: JsObject /* boolean */
	var depthCompare: String
	var stencilFront: WGPUStencilFaceState
	var stencilBack: WGPUStencilFaceState
	var stencilReadMask: JsObject
	var stencilWriteMask: JsObject
	var depthBias: JsObject
	var depthBiasSlopeScale: JsNumber  /* float */
	var depthBiasClamp: JsNumber  /* float */
}

external interface WGPUStencilFaceState : JsObject {
	var compare: String
	var failOp: String
	var depthFailOp: String
	var passOp: String
}

external interface WGPUVertexState : JsObject, WGPUProgrammableStage {
	var buffers: JsArray<JsObject> /* sequence<GPUVertexBufferLayout?> */
}

external interface WGPUVertexBufferLayout : JsObject {
	var arrayStride: JsObject
	var stepMode: String
	var attributes: JsArray<JsObject> /* sequence<GPUVertexAttribute> */
}

external interface WGPUVertexAttribute : JsObject {
	var format: String
	var offset: JsObject
	var shaderLocation: JsObject
}

external interface WGPUTexelCopyBufferLayout : JsObject {
	var offset: JsObject
	var bytesPerRow: JsObject
	var rowsPerImage: JsObject
}

external interface WGPUTexelCopyBufferInfo : JsObject, WGPUTexelCopyBufferLayout {
	var buffer: WGPUBuffer
}

external interface WGPUTexelCopyTextureInfo : JsObject {
	var texture: WGPUTexture
	var mipLevel: JsObject
	var origin: JsObject
	var aspect: String
}

external interface WGPUCopyExternalImageDestInfo : JsObject, WGPUTexelCopyTextureInfo {
	var colorSpace: JsObject /* PredefinedColorSpace */
	var premultipliedAlpha: JsObject /* boolean */
}

external interface WGPUCopyExternalImageSourceInfo : JsObject {
	var origin: JsObject
	var flipY: JsObject /* boolean */
}

external interface WGPUCommandBufferDescriptor : JsObject, WGPUObjectDescriptorBase
external interface WGPUCommandEncoderDescriptor : JsObject, WGPUObjectDescriptorBase
external interface WGPUComputePassTimestampWrites : JsObject {
	var querySet: WGPUQuerySet
	var beginningOfPassWriteIndex: JsObject
	var endOfPassWriteIndex: JsObject
}

external interface WGPUComputePassDescriptor : JsObject, WGPUObjectDescriptorBase {
	var timestampWrites: WGPUComputePassTimestampWrites
}

external interface WGPURenderPassTimestampWrites : JsObject {
	var querySet: WGPUQuerySet
	var beginningOfPassWriteIndex: JsObject
	var endOfPassWriteIndex: JsObject
}

external interface WGPURenderPassDescriptor : JsObject, WGPUObjectDescriptorBase {
	var colorAttachments: JsArray<JsObject> /* sequence<GPURenderPassColorAttachment?> */
	var depthStencilAttachment: WGPURenderPassDepthStencilAttachment
	var occlusionQuerySet: WGPUQuerySet
	var timestampWrites: WGPURenderPassTimestampWrites
	var maxDrawCount: JsObject
}

external interface WGPURenderPassColorAttachment : JsObject {
	var view: WGPUTextureView
	var depthSlice: JsObject
	var resolveTarget: WGPUTextureView
	var clearValue: JsObject
	var loadOp: String
	var storeOp: String
}

external interface WGPURenderPassDepthStencilAttachment : JsObject {
	var view: WGPUTextureView
	var depthClearValue: JsNumber  /* float */
	var depthLoadOp: String
	var depthStoreOp: String
	var depthReadOnly: JsObject /* boolean */
	var stencilClearValue: JsObject
	var stencilLoadOp: String
	var stencilStoreOp: String
	var stencilReadOnly: JsObject /* boolean */
}

external interface WGPURenderPassLayout : JsObject, WGPUObjectDescriptorBase {
	var colorFormats: JsArray<JsObject> /* sequence<GPUTextureFormat?> */
	var depthStencilFormat: String
	var sampleCount: JsObject
}

external interface WGPURenderBundleDescriptor : JsObject, WGPUObjectDescriptorBase
external interface WGPURenderBundleEncoderDescriptor : JsObject, WGPURenderPassLayout {
	var depthReadOnly: JsObject /* boolean */
	var stencilReadOnly: JsObject /* boolean */
}

external interface WGPUQueueDescriptor : JsObject, WGPUObjectDescriptorBase
external interface WGPUQuerySetDescriptor : JsObject, WGPUObjectDescriptorBase {
	var type: String
	var count: JsObject
}

external interface WGPUCanvasToneMapping : JsObject
external interface WGPUCanvasConfiguration : JsObject {
	var device: WGPUDevice
	var format: String
	var usage: JsObject
	var viewFormats: JsArray<JsObject> /* sequence<GPUTextureFormat> */
	var colorSpace: JsObject /* PredefinedColorSpace */
}

external interface WGPUUncapturedErrorEventInit : JsObject, EventInit {
	var error: WGPUError
}

external interface WGPUColorDict : JsObject {
	var r: JsNumber  /* double */
	var g: JsNumber  /* double */
	var b: JsNumber  /* double */
	var a: JsNumber  /* double */
}

external interface WGPUOrigin2DDict : JsObject {
	var x: JsObject
	var y: JsObject
}

external interface WGPUOrigin3DDict : JsObject {
	var x: JsObject
	var y: JsObject
	var z: JsObject
}

external interface WGPUExtent3DDict : JsObject {
	var width: JsObject
	var height: JsObject
	var depthOrArrayLayers: JsObject
}
