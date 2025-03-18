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
	var size: JsObject /* GPUSize64Out */
	var usage: JsObject /* GPUFlagsConstant */
	var mapState: String
	fun mapAsync(mode: JsObject /* GPUMapModeFlags */): JsObject /* Promise */
	fun mapAsync(mode: JsObject /* GPUMapModeFlags */, offset: JsObject /* GPUSize64 */): JsObject /* Promise */
	fun mapAsync(mode: JsObject /* GPUMapModeFlags */, offset: JsObject /* GPUSize64 */, size: JsObject /* GPUSize64 */): JsObject /* Promise */
	fun getMappedRange(): JsObject /* ArrayBuffer */
	fun getMappedRange(offset: JsObject /* GPUSize64 */): JsObject /* ArrayBuffer */
	fun getMappedRange(offset: JsObject /* GPUSize64 */, size: JsObject /* GPUSize64 */): JsObject /* ArrayBuffer */
	fun unmap()
	fun destroy()
}

external interface WGPUTexture : JsObject, WGPUObjectBase {
	var width: JsObject /* GPUIntegerCoordinateOut */
	var height: JsObject /* GPUIntegerCoordinateOut */
	var depthOrArrayLayers: JsObject /* GPUIntegerCoordinateOut */
	var mipLevelCount: JsObject /* GPUIntegerCoordinateOut */
	var sampleCount: JsObject /* GPUSize32Out */
	var dimension: String
	var format: String
	var usage: JsObject /* GPUFlagsConstant */
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
	var reason: JsObject /* GPUPipelineErrorReason */
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
	fun copyBufferToBuffer(source: WGPUBuffer, sourceOffset: JsObject /* GPUSize64 */, destination: WGPUBuffer, destinationOffset: JsObject /* GPUSize64 */, size: JsObject /* GPUSize64 */)
	fun copyBufferToTexture(source: WGPUTexelCopyBufferInfo, destination: WGPUTexelCopyTextureInfo, copySize: JsObject /* GPUExtent3D */)
	fun copyTextureToBuffer(source: WGPUTexelCopyTextureInfo, destination: WGPUTexelCopyBufferInfo, copySize: JsObject /* GPUExtent3D */)
	fun copyTextureToTexture(source: WGPUTexelCopyTextureInfo, destination: WGPUTexelCopyTextureInfo, copySize: JsObject /* GPUExtent3D */)
	fun clearBuffer(buffer: WGPUBuffer)
	fun clearBuffer(buffer: WGPUBuffer, offset: JsObject /* GPUSize64 */)
	fun clearBuffer(buffer: WGPUBuffer, offset: JsObject /* GPUSize64 */, size: JsObject /* GPUSize64 */)
	fun resolveQuerySet(querySet: WGPUQuerySet, firstQuery: JsObject /* GPUSize32 */, queryCount: JsObject /* GPUSize32 */, destination: WGPUBuffer, destinationOffset: JsObject /* GPUSize64 */)
	fun finish(): WGPUCommandBuffer
	fun finish(descriptor: WGPUCommandBufferDescriptor): WGPUCommandBuffer
}

external interface WGPUBindingCommandsMixin : JsObject {
	fun setBindGroup(index: JsObject /* GPUIndex32 */, bindGroup: JsObject /* GPUBindGroup? */)
	fun setBindGroup(index: JsObject /* GPUIndex32 */, bindGroup: JsObject /* GPUBindGroup? */, dynamicOffsets: JsArray<JsObject> /* sequence<GPUBufferDynamicOffset> */)
	fun setBindGroup(index: JsObject /* GPUIndex32 */, bindGroup: JsObject /* GPUBindGroup? */, dynamicOffsetsData: JsObject /* Uint32Array */, dynamicOffsetsDataStart: JsObject /* GPUSize64 */, dynamicOffsetsDataLength: JsObject /* GPUSize32 */)
}

external interface WGPUDebugCommandsMixin : JsObject {
	fun pushDebugGroup(groupLabel: String)
	fun popDebugGroup()
	fun insertDebugMarker(markerLabel: String)
}

external interface WGPUComputePassEncoder : JsObject, WGPUObjectBase, WGPUCommandsMixin, WGPUDebugCommandsMixin, WGPUBindingCommandsMixin {
	fun setPipeline(pipeline: WGPUComputePipeline)
	fun dispatchWorkgroups(workgroupCountX: JsObject /* GPUSize32 */)
	fun dispatchWorkgroups(workgroupCountX: JsObject /* GPUSize32 */, workgroupCountY: JsObject /* GPUSize32 */)
	fun dispatchWorkgroups(workgroupCountX: JsObject /* GPUSize32 */, workgroupCountY: JsObject /* GPUSize32 */, workgroupCountZ: JsObject /* GPUSize32 */)
	fun dispatchWorkgroupsIndirect(indirectBuffer: WGPUBuffer, indirectOffset: JsObject /* GPUSize64 */)
	fun end()
}

external interface WGPURenderPassEncoder : JsObject, WGPUObjectBase, WGPUCommandsMixin, WGPUDebugCommandsMixin, WGPUBindingCommandsMixin, WGPURenderCommandsMixin {
	fun setViewport(x: JsNumber  /* float */, y: JsNumber  /* float */, width: JsNumber  /* float */, height: JsNumber  /* float */, minDepth: JsNumber  /* float */, maxDepth: JsNumber  /* float */)
	fun setScissorRect(x: JsObject /* GPUIntegerCoordinate */, y: JsObject /* GPUIntegerCoordinate */, width: JsObject /* GPUIntegerCoordinate */, height: JsObject /* GPUIntegerCoordinate */)
	fun setBlendConstant(color: JsObject /* GPUColor */)
	fun setStencilReference(reference: JsObject /* GPUStencilValue */)
	fun beginOcclusionQuery(queryIndex: JsObject /* GPUSize32 */)
	fun endOcclusionQuery()
	fun executeBundles(bundles: JsArray<JsObject> /* sequence<GPURenderBundle> */)
	fun end()
}

external interface WGPURenderCommandsMixin : JsObject {
	fun setPipeline(pipeline: WGPURenderPipeline)
	fun setIndexBuffer(buffer: WGPUBuffer, indexFormat: String)
	fun setIndexBuffer(buffer: WGPUBuffer, indexFormat: String, offset: JsObject /* GPUSize64 */)
	fun setIndexBuffer(buffer: WGPUBuffer, indexFormat: String, offset: JsObject /* GPUSize64 */, size: JsObject /* GPUSize64 */)
	fun setVertexBuffer(slot: JsObject /* GPUIndex32 */, buffer: JsObject /* GPUBuffer? */)
	fun setVertexBuffer(slot: JsObject /* GPUIndex32 */, buffer: JsObject /* GPUBuffer? */, offset: JsObject /* GPUSize64 */)
	fun setVertexBuffer(slot: JsObject /* GPUIndex32 */, buffer: JsObject /* GPUBuffer? */, offset: JsObject /* GPUSize64 */, size: JsObject /* GPUSize64 */)
	fun draw(vertexCount: JsObject /* GPUSize32 */)
	fun draw(vertexCount: JsObject /* GPUSize32 */, instanceCount: JsObject /* GPUSize32 */)
	fun draw(vertexCount: JsObject /* GPUSize32 */, instanceCount: JsObject /* GPUSize32 */, firstVertex: JsObject /* GPUSize32 */)
	fun draw(vertexCount: JsObject /* GPUSize32 */, instanceCount: JsObject /* GPUSize32 */, firstVertex: JsObject /* GPUSize32 */, firstInstance: JsObject /* GPUSize32 */)
	fun drawIndexed(indexCount: JsObject /* GPUSize32 */)
	fun drawIndexed(indexCount: JsObject /* GPUSize32 */, instanceCount: JsObject /* GPUSize32 */)
	fun drawIndexed(indexCount: JsObject /* GPUSize32 */, instanceCount: JsObject /* GPUSize32 */, firstIndex: JsObject /* GPUSize32 */)
	fun drawIndexed(indexCount: JsObject /* GPUSize32 */, instanceCount: JsObject /* GPUSize32 */, firstIndex: JsObject /* GPUSize32 */, baseVertex: JsObject /* GPUSignedOffset32 */)
	fun drawIndexed(indexCount: JsObject /* GPUSize32 */, instanceCount: JsObject /* GPUSize32 */, firstIndex: JsObject /* GPUSize32 */, baseVertex: JsObject /* GPUSignedOffset32 */, firstInstance: JsObject /* GPUSize32 */)
	fun drawIndirect(indirectBuffer: WGPUBuffer, indirectOffset: JsObject /* GPUSize64 */)
	fun drawIndexedIndirect(indirectBuffer: WGPUBuffer, indirectOffset: JsObject /* GPUSize64 */)
}

external interface WGPURenderBundle : JsObject, WGPUObjectBase
external interface WGPURenderBundleEncoder : JsObject, WGPUObjectBase, WGPUCommandsMixin, WGPUDebugCommandsMixin, WGPUBindingCommandsMixin, WGPURenderCommandsMixin {
	fun finish(): WGPURenderBundle
	fun finish(descriptor: WGPURenderBundleDescriptor): WGPURenderBundle
}

external interface WGPUQueue : JsObject, WGPUObjectBase {
	fun submit(commandBuffers: JsArray<JsObject> /* sequence<GPUCommandBuffer> */)
	fun onSubmittedWorkDone(): JsObject /* Promise */
	fun writeBuffer(buffer: WGPUBuffer, bufferOffset: JsObject /* GPUSize64 */, data: JsObject /* AllowSharedBufferSource */)
	fun writeBuffer(buffer: WGPUBuffer, bufferOffset: JsObject /* GPUSize64 */, data: JsObject /* AllowSharedBufferSource */, dataOffset: JsObject /* GPUSize64 */)
	fun writeBuffer(buffer: WGPUBuffer, bufferOffset: JsObject /* GPUSize64 */, data: JsObject /* AllowSharedBufferSource */, dataOffset: JsObject /* GPUSize64 */, size: JsObject /* GPUSize64 */)
	fun writeTexture(destination: WGPUTexelCopyTextureInfo, data: JsObject /* AllowSharedBufferSource */, dataLayout: WGPUTexelCopyBufferLayout, size: JsObject /* GPUExtent3D */)
	fun copyExternalImageToTexture(source: WGPUCopyExternalImageSourceInfo, destination: WGPUCopyExternalImageDestInfo, copySize: JsObject /* GPUExtent3D */)
}

external interface WGPUQuerySet : JsObject, WGPUObjectBase {
	var type: String
	var count: JsObject /* GPUSize32Out */
	fun destroy()
}

external interface WGPUCanvasContext : JsObject {
	var canvas: JsObject /* (HTMLCanvasElement or OffscreenCanvas) */
	fun configure(configuration: WGPUCanvasConfiguration)
	fun unconfigure()
	fun getConfiguration(): JsObject /* GPUCanvasConfiguration? */
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
	var size: JsObject /* GPUSize64 */
	var usage: JsObject /* GPUBufferUsageFlags */
	var mappedAtCreation: JsObject /* boolean */
}

external interface WGPUTextureDescriptor : JsObject, WGPUObjectDescriptorBase {
	var size: JsObject /* GPUExtent3D */
	var mipLevelCount: JsObject /* GPUIntegerCoordinate */
	var sampleCount: JsObject /* GPUSize32 */
	var dimension: String
	var format: String
	var usage: JsObject /* GPUTextureUsageFlags */
	var viewFormats: JsArray<JsObject> /* sequence<GPUTextureFormat> */
}

external interface WGPUTextureViewDescriptor : JsObject, WGPUObjectDescriptorBase {
	var format: String
	var dimension: String
	var usage: JsObject /* GPUTextureUsageFlags */
	var aspect: String
	var baseMipLevel: JsObject /* GPUIntegerCoordinate */
	var mipLevelCount: JsObject /* GPUIntegerCoordinate */
	var baseArrayLayer: JsObject /* GPUIntegerCoordinate */
	var arrayLayerCount: JsObject /* GPUIntegerCoordinate */
}

external interface WGPUExternalTextureDescriptor : JsObject, WGPUObjectDescriptorBase {
	var source: JsObject /* (HTMLVideoElement or VideoFrame) */
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
	var binding: JsObject /* GPUIndex32 */
	var visibility: JsObject /* GPUShaderStageFlags */
	var buffer: WGPUBufferBindingLayout
	var sampler: WGPUSamplerBindingLayout
	var texture: WGPUTextureBindingLayout
	var storageTexture: WGPUStorageTextureBindingLayout
	var externalTexture: WGPUExternalTextureBindingLayout
}

external interface WGPUBufferBindingLayout : JsObject {
	var type: String
	var hasDynamicOffset: JsObject /* boolean */
	var minBindingSize: JsObject /* GPUSize64 */
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
	var binding: JsObject /* GPUIndex32 */
	var resource: JsObject /* GPUBindingResource */
}

external interface WGPUBufferBinding : JsObject {
	var buffer: WGPUBuffer
	var offset: JsObject /* GPUSize64 */
	var size: JsObject /* GPUSize64 */
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
	var layout: JsObject /* (GPUPipelineLayout or GPUAutoLayoutMode) */
}

external interface WGPUPipelineErrorInit : JsObject {
	var reason: JsObject /* GPUPipelineErrorReason */
}

external interface WGPUPipelineDescriptorBase : JsObject, WGPUObjectDescriptorBase {
	var layout: JsObject /* (GPUPipelineLayout or GPUAutoLayoutMode) */
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
	var count: JsObject /* GPUSize32 */
	var mask: JsObject /* GPUSampleMask */
	var alphaToCoverageEnabled: JsObject /* boolean */
}

external interface WGPUFragmentState : JsObject, WGPUProgrammableStage {
	var targets: JsArray<JsObject> /* sequence<GPUColorTargetState?> */
}

external interface WGPUColorTargetState : JsObject {
	var format: String
	var blend: WGPUBlendState
	var writeMask: JsObject /* GPUColorWriteFlags */
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
	var stencilReadMask: JsObject /* GPUStencilValue */
	var stencilWriteMask: JsObject /* GPUStencilValue */
	var depthBias: JsObject /* GPUDepthBias */
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
	var arrayStride: JsObject /* GPUSize64 */
	var stepMode: String
	var attributes: JsArray<JsObject> /* sequence<GPUVertexAttribute> */
}

external interface WGPUVertexAttribute : JsObject {
	var format: String
	var offset: JsObject /* GPUSize64 */
	var shaderLocation: JsObject /* GPUIndex32 */
}

external interface WGPUTexelCopyBufferLayout : JsObject {
	var offset: JsObject /* GPUSize64 */
	var bytesPerRow: JsObject /* GPUSize32 */
	var rowsPerImage: JsObject /* GPUSize32 */
}

external interface WGPUTexelCopyBufferInfo : JsObject, WGPUTexelCopyBufferLayout {
	var buffer: WGPUBuffer
}

external interface WGPUTexelCopyTextureInfo : JsObject {
	var texture: WGPUTexture
	var mipLevel: JsObject /* GPUIntegerCoordinate */
	var origin: JsObject /* GPUOrigin3D */
	var aspect: String
}

external interface WGPUCopyExternalImageDestInfo : JsObject, WGPUTexelCopyTextureInfo {
	var colorSpace: JsObject /* PredefinedColorSpace */
	var premultipliedAlpha: JsObject /* boolean */
}

external interface WGPUCopyExternalImageSourceInfo : JsObject {
	var source: JsObject /* GPUCopyExternalImageSource */
	var origin: JsObject /* GPUOrigin2D */
	var flipY: JsObject /* boolean */
}

external interface WGPUCommandBufferDescriptor : JsObject, WGPUObjectDescriptorBase
external interface WGPUCommandEncoderDescriptor : JsObject, WGPUObjectDescriptorBase
external interface WGPUComputePassTimestampWrites : JsObject {
	var querySet: WGPUQuerySet
	var beginningOfPassWriteIndex: JsObject /* GPUSize32 */
	var endOfPassWriteIndex: JsObject /* GPUSize32 */
}

external interface WGPUComputePassDescriptor : JsObject, WGPUObjectDescriptorBase {
	var timestampWrites: WGPUComputePassTimestampWrites
}

external interface WGPURenderPassTimestampWrites : JsObject {
	var querySet: WGPUQuerySet
	var beginningOfPassWriteIndex: JsObject /* GPUSize32 */
	var endOfPassWriteIndex: JsObject /* GPUSize32 */
}

external interface WGPURenderPassDescriptor : JsObject, WGPUObjectDescriptorBase {
	var colorAttachments: JsArray<JsObject> /* sequence<GPURenderPassColorAttachment?> */
	var depthStencilAttachment: WGPURenderPassDepthStencilAttachment
	var occlusionQuerySet: WGPUQuerySet
	var timestampWrites: WGPURenderPassTimestampWrites
	var maxDrawCount: JsObject /* GPUSize64 */
}

external interface WGPURenderPassColorAttachment : JsObject {
	var view: WGPUTextureView
	var depthSlice: JsObject /* GPUIntegerCoordinate */
	var resolveTarget: WGPUTextureView
	var clearValue: JsObject /* GPUColor */
	var loadOp: String
	var storeOp: String
}

external interface WGPURenderPassDepthStencilAttachment : JsObject {
	var view: WGPUTextureView
	var depthClearValue: JsNumber  /* float */
	var depthLoadOp: String
	var depthStoreOp: String
	var depthReadOnly: JsObject /* boolean */
	var stencilClearValue: JsObject /* GPUStencilValue */
	var stencilLoadOp: String
	var stencilStoreOp: String
	var stencilReadOnly: JsObject /* boolean */
}

external interface WGPURenderPassLayout : JsObject, WGPUObjectDescriptorBase {
	var colorFormats: JsArray<JsObject> /* sequence<GPUTextureFormat?> */
	var depthStencilFormat: String
	var sampleCount: JsObject /* GPUSize32 */
}

external interface WGPURenderBundleDescriptor : JsObject, WGPUObjectDescriptorBase
external interface WGPURenderBundleEncoderDescriptor : JsObject, WGPURenderPassLayout {
	var depthReadOnly: JsObject /* boolean */
	var stencilReadOnly: JsObject /* boolean */
}

external interface WGPUQueueDescriptor : JsObject, WGPUObjectDescriptorBase
external interface WGPUQuerySetDescriptor : JsObject, WGPUObjectDescriptorBase {
	var type: String
	var count: JsObject /* GPUSize32 */
}

external interface WGPUCanvasToneMapping : JsObject {
	var mode: JsObject /* GPUCanvasToneMappingMode */
}

external interface WGPUCanvasConfiguration : JsObject {
	var device: WGPUDevice
	var format: String
	var usage: JsObject /* GPUTextureUsageFlags */
	var viewFormats: JsArray<JsObject> /* sequence<GPUTextureFormat> */
	var colorSpace: JsObject /* PredefinedColorSpace */
	var toneMapping: WGPUCanvasToneMapping
	var alphaMode: JsObject /* GPUCanvasAlphaMode */
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
	var x: JsObject /* GPUIntegerCoordinate */
	var y: JsObject /* GPUIntegerCoordinate */
}

external interface WGPUOrigin3DDict : JsObject {
	var x: JsObject /* GPUIntegerCoordinate */
	var y: JsObject /* GPUIntegerCoordinate */
	var z: JsObject /* GPUIntegerCoordinate */
}

external interface WGPUExtent3DDict : JsObject {
	var width: JsObject /* GPUIntegerCoordinate */
	var height: JsObject /* GPUIntegerCoordinate */
	var depthOrArrayLayers: JsObject /* GPUIntegerCoordinate */
}
