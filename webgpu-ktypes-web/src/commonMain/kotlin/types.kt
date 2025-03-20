@file:Suppress("unused")
// This file has been generated DO NO EDIT
package io.ygdrasil.webgpu

typealias WGPUSupportedFeatures = JsSet<JsObject> /* DOMString */
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
	fun requestAdapter(options: WGPURequestAdapterOptions  /* GPURequestAdapterOptions */): JsObject /* Promise */
	fun getPreferredCanvasFormat(): String  /* GPUTextureFormat */
}

external interface WGPUAdapter : JsObject {
	var features: WGPUSupportedFeatures  /* GPUSupportedFeatures */
	var limits: WGPUSupportedLimits  /* GPUSupportedLimits */
	var info: WGPUAdapterInfo  /* GPUAdapterInfo */
	var isFallbackAdapter: Boolean
	fun requestDevice(): JsObject /* Promise */
	fun requestDevice(descriptor: WGPUDeviceDescriptor  /* GPUDeviceDescriptor */): JsObject /* Promise */
}

external interface WGPUDevice : JsObject, EventTarget, WGPUObjectBase {
	var features: WGPUSupportedFeatures  /* GPUSupportedFeatures */
	var limits: WGPUSupportedLimits  /* GPUSupportedLimits */
	var adapterInfo: WGPUAdapterInfo  /* GPUAdapterInfo */
	var queue: WGPUQueue  /* GPUQueue */
	var lost: JsObject /* Promise */
	var onuncapturederror: JsObject /* EventHandler */
	fun destroy()
	fun createBuffer(descriptor: WGPUBufferDescriptor  /* GPUBufferDescriptor */): WGPUBuffer  /* GPUBuffer */
	fun createTexture(descriptor: WGPUTextureDescriptor  /* GPUTextureDescriptor */): WGPUTexture  /* GPUTexture */
	fun createSampler(): WGPUSampler  /* GPUSampler */
	fun createSampler(descriptor: WGPUSamplerDescriptor  /* GPUSamplerDescriptor */): WGPUSampler  /* GPUSampler */
	fun importExternalTexture(descriptor: WGPUExternalTextureDescriptor  /* GPUExternalTextureDescriptor */): WGPUExternalTexture  /* GPUExternalTexture */
	fun createBindGroupLayout(descriptor: WGPUBindGroupLayoutDescriptor  /* GPUBindGroupLayoutDescriptor */): WGPUBindGroupLayout  /* GPUBindGroupLayout */
	fun createPipelineLayout(descriptor: WGPUPipelineLayoutDescriptor  /* GPUPipelineLayoutDescriptor */): WGPUPipelineLayout  /* GPUPipelineLayout */
	fun createBindGroup(descriptor: WGPUBindGroupDescriptor  /* GPUBindGroupDescriptor */): WGPUBindGroup  /* GPUBindGroup */
	fun createShaderModule(descriptor: WGPUShaderModuleDescriptor  /* GPUShaderModuleDescriptor */): WGPUShaderModule  /* GPUShaderModule */
	fun createComputePipeline(descriptor: WGPUComputePipelineDescriptor  /* GPUComputePipelineDescriptor */): WGPUComputePipeline  /* GPUComputePipeline */
	fun createRenderPipeline(descriptor: WGPURenderPipelineDescriptor  /* GPURenderPipelineDescriptor */): WGPURenderPipeline  /* GPURenderPipeline */
	fun createComputePipelineAsync(descriptor: WGPUComputePipelineDescriptor  /* GPUComputePipelineDescriptor */): JsObject /* Promise */
	fun createRenderPipelineAsync(descriptor: WGPURenderPipelineDescriptor  /* GPURenderPipelineDescriptor */): JsObject /* Promise */
	fun createCommandEncoder(): WGPUCommandEncoder  /* GPUCommandEncoder */
	fun createCommandEncoder(descriptor: WGPUCommandEncoderDescriptor  /* GPUCommandEncoderDescriptor */): WGPUCommandEncoder  /* GPUCommandEncoder */
	fun createRenderBundleEncoder(descriptor: WGPURenderBundleEncoderDescriptor  /* GPURenderBundleEncoderDescriptor */): WGPURenderBundleEncoder  /* GPURenderBundleEncoder */
	fun createQuerySet(descriptor: WGPUQuerySetDescriptor  /* GPUQuerySetDescriptor */): WGPUQuerySet  /* GPUQuerySet */
	fun pushErrorScope(filter: String  /* GPUErrorFilter */)
	fun popErrorScope(): JsObject /* Promise */
}

external interface WGPUBuffer : JsObject, WGPUObjectBase {
	var size: JsNumber  /* GPUSize64Out */
	var usage: JsNumber  /* GPUFlagsConstant */
	var mapState: String  /* GPUBufferMapState */
	fun mapAsync(mode: JsNumber  /* GPUMapModeFlags */): JsObject /* Promise */
	fun mapAsync(mode: JsNumber  /* GPUMapModeFlags */, offset: JsNumber  /* GPUSize64 */): JsObject /* Promise */
	fun mapAsync(mode: JsNumber  /* GPUMapModeFlags */, offset: JsNumber  /* GPUSize64 */, size: JsNumber  /* GPUSize64 */): JsObject /* Promise */
	fun getMappedRange(): JsObject /* ArrayBuffer */
	fun getMappedRange(offset: JsNumber  /* GPUSize64 */): JsObject /* ArrayBuffer */
	fun getMappedRange(offset: JsNumber  /* GPUSize64 */, size: JsNumber  /* GPUSize64 */): JsObject /* ArrayBuffer */
	fun unmap()
	fun destroy()
}

external interface WGPUTexture : JsObject, WGPUObjectBase {
	var width: JsNumber  /* GPUIntegerCoordinateOut */
	var height: JsNumber  /* GPUIntegerCoordinateOut */
	var depthOrArrayLayers: JsNumber  /* GPUIntegerCoordinateOut */
	var mipLevelCount: JsNumber  /* GPUIntegerCoordinateOut */
	var sampleCount: JsNumber  /* GPUSize32Out */
	var dimension: String  /* GPUTextureDimension */
	var format: String  /* GPUTextureFormat */
	var usage: JsNumber  /* GPUFlagsConstant */
	fun createView(): WGPUTextureView  /* GPUTextureView */
	fun createView(descriptor: WGPUTextureViewDescriptor  /* GPUTextureViewDescriptor */): WGPUTextureView  /* GPUTextureView */
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
	var type: String  /* GPUCompilationMessageType */
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
	fun getBindGroupLayout(index: JsNumber  /* unsigned long */): WGPUBindGroupLayout  /* GPUBindGroupLayout */
}

external interface WGPUComputePipeline : JsObject, WGPUObjectBase, WGPUPipelineBase
external interface WGPURenderPipeline : JsObject, WGPUObjectBase, WGPUPipelineBase
external interface WGPUCommandBuffer : JsObject, WGPUObjectBase
external interface WGPUCommandsMixin : JsObject
external interface WGPUCommandEncoder : JsObject, WGPUObjectBase, WGPUCommandsMixin, WGPUDebugCommandsMixin {
	fun beginRenderPass(descriptor: WGPURenderPassDescriptor  /* GPURenderPassDescriptor */): WGPURenderPassEncoder  /* GPURenderPassEncoder */
	fun beginComputePass(): WGPUComputePassEncoder  /* GPUComputePassEncoder */
	fun beginComputePass(descriptor: WGPUComputePassDescriptor  /* GPUComputePassDescriptor */): WGPUComputePassEncoder  /* GPUComputePassEncoder */
	fun copyBufferToBuffer(source: WGPUBuffer  /* GPUBuffer */, sourceOffset: JsNumber  /* GPUSize64 */, destination: WGPUBuffer  /* GPUBuffer */, destinationOffset: JsNumber  /* GPUSize64 */, size: JsNumber  /* GPUSize64 */)
	fun copyBufferToTexture(source: WGPUTexelCopyBufferInfo  /* GPUTexelCopyBufferInfo */, destination: WGPUTexelCopyTextureInfo  /* GPUTexelCopyTextureInfo */, copySize: JsObject /* GPUExtent3D */)
	fun copyTextureToBuffer(source: WGPUTexelCopyTextureInfo  /* GPUTexelCopyTextureInfo */, destination: WGPUTexelCopyBufferInfo  /* GPUTexelCopyBufferInfo */, copySize: JsObject /* GPUExtent3D */)
	fun copyTextureToTexture(source: WGPUTexelCopyTextureInfo  /* GPUTexelCopyTextureInfo */, destination: WGPUTexelCopyTextureInfo  /* GPUTexelCopyTextureInfo */, copySize: JsObject /* GPUExtent3D */)
	fun clearBuffer(buffer: WGPUBuffer  /* GPUBuffer */)
	fun clearBuffer(buffer: WGPUBuffer  /* GPUBuffer */, offset: JsNumber  /* GPUSize64 */)
	fun clearBuffer(buffer: WGPUBuffer  /* GPUBuffer */, offset: JsNumber  /* GPUSize64 */, size: JsNumber  /* GPUSize64 */)
	fun resolveQuerySet(querySet: WGPUQuerySet  /* GPUQuerySet */, firstQuery: JsNumber  /* GPUSize32 */, queryCount: JsNumber  /* GPUSize32 */, destination: WGPUBuffer  /* GPUBuffer */, destinationOffset: JsNumber  /* GPUSize64 */)
	fun finish(): WGPUCommandBuffer  /* GPUCommandBuffer */
	fun finish(descriptor: WGPUCommandBufferDescriptor  /* GPUCommandBufferDescriptor */): WGPUCommandBuffer  /* GPUCommandBuffer */
}

external interface WGPUBindingCommandsMixin : JsObject {
	fun setBindGroup(index: JsNumber  /* GPUIndex32 */, bindGroup: JsObject /* GPUBindGroup? */)
	fun setBindGroup(index: JsNumber  /* GPUIndex32 */, bindGroup: JsObject /* GPUBindGroup? */, dynamicOffsets: JsArray<JsObject> /* sequence<GPUBufferDynamicOffset> */)
	fun setBindGroup(index: JsNumber  /* GPUIndex32 */, bindGroup: JsObject /* GPUBindGroup? */, dynamicOffsetsData: JsObject /* Uint32Array */, dynamicOffsetsDataStart: JsNumber  /* GPUSize64 */, dynamicOffsetsDataLength: JsNumber  /* GPUSize32 */)
}

external interface WGPUDebugCommandsMixin : JsObject {
	fun pushDebugGroup(groupLabel: String)
	fun popDebugGroup()
	fun insertDebugMarker(markerLabel: String)
}

external interface WGPUComputePassEncoder : JsObject, WGPUObjectBase, WGPUCommandsMixin, WGPUDebugCommandsMixin, WGPUBindingCommandsMixin {
	fun setPipeline(pipeline: WGPUComputePipeline  /* GPUComputePipeline */)
	fun dispatchWorkgroups(workgroupCountX: JsNumber  /* GPUSize32 */)
	fun dispatchWorkgroups(workgroupCountX: JsNumber  /* GPUSize32 */, workgroupCountY: JsNumber  /* GPUSize32 */)
	fun dispatchWorkgroups(workgroupCountX: JsNumber  /* GPUSize32 */, workgroupCountY: JsNumber  /* GPUSize32 */, workgroupCountZ: JsNumber  /* GPUSize32 */)
	fun dispatchWorkgroupsIndirect(indirectBuffer: WGPUBuffer  /* GPUBuffer */, indirectOffset: JsNumber  /* GPUSize64 */)
	fun end()
}

external interface WGPURenderPassEncoder : JsObject, WGPUObjectBase, WGPUCommandsMixin, WGPUDebugCommandsMixin, WGPUBindingCommandsMixin, WGPURenderCommandsMixin {
	fun setViewport(x: JsNumber  /* float */, y: JsNumber  /* float */, width: JsNumber  /* float */, height: JsNumber  /* float */, minDepth: JsNumber  /* float */, maxDepth: JsNumber  /* float */)
	fun setScissorRect(x: JsNumber  /* GPUIntegerCoordinate */, y: JsNumber  /* GPUIntegerCoordinate */, width: JsNumber  /* GPUIntegerCoordinate */, height: JsNumber  /* GPUIntegerCoordinate */)
	fun setBlendConstant(color: JsObject /* GPUColor */)
	fun setStencilReference(reference: JsNumber  /* GPUStencilValue */)
	fun beginOcclusionQuery(queryIndex: JsNumber  /* GPUSize32 */)
	fun endOcclusionQuery()
	fun executeBundles(bundles: JsArray<JsObject> /* sequence<GPURenderBundle> */)
	fun end()
}

external interface WGPURenderCommandsMixin : JsObject {
	fun setPipeline(pipeline: WGPURenderPipeline  /* GPURenderPipeline */)
	fun setIndexBuffer(buffer: WGPUBuffer  /* GPUBuffer */, indexFormat: String  /* GPUIndexFormat */)
	fun setIndexBuffer(buffer: WGPUBuffer  /* GPUBuffer */, indexFormat: String  /* GPUIndexFormat */, offset: JsNumber  /* GPUSize64 */)
	fun setIndexBuffer(buffer: WGPUBuffer  /* GPUBuffer */, indexFormat: String  /* GPUIndexFormat */, offset: JsNumber  /* GPUSize64 */, size: JsNumber  /* GPUSize64 */)
	fun setVertexBuffer(slot: JsNumber  /* GPUIndex32 */, buffer: JsObject /* GPUBuffer? */)
	fun setVertexBuffer(slot: JsNumber  /* GPUIndex32 */, buffer: JsObject /* GPUBuffer? */, offset: JsNumber  /* GPUSize64 */)
	fun setVertexBuffer(slot: JsNumber  /* GPUIndex32 */, buffer: JsObject /* GPUBuffer? */, offset: JsNumber  /* GPUSize64 */, size: JsNumber  /* GPUSize64 */)
	fun draw(vertexCount: JsNumber  /* GPUSize32 */)
	fun draw(vertexCount: JsNumber  /* GPUSize32 */, instanceCount: JsNumber  /* GPUSize32 */)
	fun draw(vertexCount: JsNumber  /* GPUSize32 */, instanceCount: JsNumber  /* GPUSize32 */, firstVertex: JsNumber  /* GPUSize32 */)
	fun draw(vertexCount: JsNumber  /* GPUSize32 */, instanceCount: JsNumber  /* GPUSize32 */, firstVertex: JsNumber  /* GPUSize32 */, firstInstance: JsNumber  /* GPUSize32 */)
	fun drawIndexed(indexCount: JsNumber  /* GPUSize32 */)
	fun drawIndexed(indexCount: JsNumber  /* GPUSize32 */, instanceCount: JsNumber  /* GPUSize32 */)
	fun drawIndexed(indexCount: JsNumber  /* GPUSize32 */, instanceCount: JsNumber  /* GPUSize32 */, firstIndex: JsNumber  /* GPUSize32 */)
	fun drawIndexed(indexCount: JsNumber  /* GPUSize32 */, instanceCount: JsNumber  /* GPUSize32 */, firstIndex: JsNumber  /* GPUSize32 */, baseVertex: JsNumber  /* GPUSignedOffset32 */)
	fun drawIndexed(indexCount: JsNumber  /* GPUSize32 */, instanceCount: JsNumber  /* GPUSize32 */, firstIndex: JsNumber  /* GPUSize32 */, baseVertex: JsNumber  /* GPUSignedOffset32 */, firstInstance: JsNumber  /* GPUSize32 */)
	fun drawIndirect(indirectBuffer: WGPUBuffer  /* GPUBuffer */, indirectOffset: JsNumber  /* GPUSize64 */)
	fun drawIndexedIndirect(indirectBuffer: WGPUBuffer  /* GPUBuffer */, indirectOffset: JsNumber  /* GPUSize64 */)
}

external interface WGPURenderBundle : JsObject, WGPUObjectBase
external interface WGPURenderBundleEncoder : JsObject, WGPUObjectBase, WGPUCommandsMixin, WGPUDebugCommandsMixin, WGPUBindingCommandsMixin, WGPURenderCommandsMixin {
	fun finish(): WGPURenderBundle  /* GPURenderBundle */
	fun finish(descriptor: WGPURenderBundleDescriptor  /* GPURenderBundleDescriptor */): WGPURenderBundle  /* GPURenderBundle */
}

external interface WGPUQueue : JsObject, WGPUObjectBase {
	fun submit(commandBuffers: JsArray<JsObject> /* sequence<GPUCommandBuffer> */)
	fun onSubmittedWorkDone(): JsObject /* Promise */
	fun writeBuffer(buffer: WGPUBuffer  /* GPUBuffer */, bufferOffset: JsNumber  /* GPUSize64 */, data: JsObject /* AllowSharedBufferSource */)
	fun writeBuffer(buffer: WGPUBuffer  /* GPUBuffer */, bufferOffset: JsNumber  /* GPUSize64 */, data: JsObject /* AllowSharedBufferSource */, dataOffset: JsNumber  /* GPUSize64 */)
	fun writeBuffer(buffer: WGPUBuffer  /* GPUBuffer */, bufferOffset: JsNumber  /* GPUSize64 */, data: JsObject /* AllowSharedBufferSource */, dataOffset: JsNumber  /* GPUSize64 */, size: JsNumber  /* GPUSize64 */)
	fun writeTexture(destination: WGPUTexelCopyTextureInfo  /* GPUTexelCopyTextureInfo */, data: JsObject /* AllowSharedBufferSource */, dataLayout: WGPUTexelCopyBufferLayout  /* GPUTexelCopyBufferLayout */, size: JsObject /* GPUExtent3D */)
	fun copyExternalImageToTexture(source: WGPUCopyExternalImageSourceInfo  /* GPUCopyExternalImageSourceInfo */, destination: WGPUCopyExternalImageDestInfo  /* GPUCopyExternalImageDestInfo */, copySize: JsObject /* GPUExtent3D */)
}

external interface WGPUQuerySet : JsObject, WGPUObjectBase {
	var type: String  /* GPUQueryType */
	var count: JsNumber  /* GPUSize32Out */
	fun destroy()
}

external interface WGPUCanvasContext : JsObject {
	var canvas: JsObject /* (HTMLCanvasElement or OffscreenCanvas) */
	fun configure(configuration: WGPUCanvasConfiguration  /* GPUCanvasConfiguration */)
	fun unconfigure()
	fun getConfiguration(): JsObject /* GPUCanvasConfiguration? */
	fun getCurrentTexture(): WGPUTexture  /* GPUTexture */
}

external interface WGPUDeviceLostInfo : JsObject {
	var reason: String  /* GPUDeviceLostReason */
	var message: String
}

external interface WGPUError : JsObject {
	var message: String
}

external interface WGPUValidationError : JsObject, WGPUError
external interface WGPUOutOfMemoryError : JsObject, WGPUError
external interface WGPUInternalError : JsObject, WGPUError
external interface WGPUUncapturedErrorEvent : JsObject, Event {
	var error: WGPUError  /* GPUError */
}

external interface WGPUObjectDescriptorBase : JsObject {
	var label: String
}

external interface WGPURequestAdapterOptions : JsObject {
	var featureLevel: String
	var powerPreference: String  /* GPUPowerPreference */
	var forceFallbackAdapter: Boolean
	var xrCompatible: Boolean
}

external interface WGPUDeviceDescriptor : JsObject, WGPUObjectDescriptorBase {
	var requiredFeatures: JsArray<JsObject> /* sequence<GPUFeatureName> */
	var requiredLimits: JsMap<JsObject, JsObject>  /* record<DOMString, (GPUSize64orundefined)>  */
	var defaultQueue: WGPUQueueDescriptor  /* GPUQueueDescriptor */
}

external interface WGPUBufferDescriptor : JsObject, WGPUObjectDescriptorBase {
	var size: JsNumber  /* GPUSize64 */
	var usage: JsNumber  /* GPUBufferUsageFlags */
	var mappedAtCreation: Boolean
}

external interface WGPUTextureDescriptor : JsObject, WGPUObjectDescriptorBase {
	var size: JsObject /* GPUExtent3D */
	var mipLevelCount: JsNumber  /* GPUIntegerCoordinate */
	var sampleCount: JsNumber  /* GPUSize32 */
	var dimension: String  /* GPUTextureDimension */
	var format: String  /* GPUTextureFormat */
	var usage: JsNumber  /* GPUTextureUsageFlags */
	var viewFormats: JsArray<JsObject> /* sequence<GPUTextureFormat> */
}

external interface WGPUTextureViewDescriptor : JsObject, WGPUObjectDescriptorBase {
	var format: String  /* GPUTextureFormat */
	var dimension: String  /* GPUTextureViewDimension */
	var usage: JsNumber  /* GPUTextureUsageFlags */
	var aspect: String  /* GPUTextureAspect */
	var baseMipLevel: JsNumber  /* GPUIntegerCoordinate */
	var mipLevelCount: JsNumber  /* GPUIntegerCoordinate */
	var baseArrayLayer: JsNumber  /* GPUIntegerCoordinate */
	var arrayLayerCount: JsNumber  /* GPUIntegerCoordinate */
}

external interface WGPUExternalTextureDescriptor : JsObject, WGPUObjectDescriptorBase {
	var source: JsObject /* (HTMLVideoElement or VideoFrame) */
	var colorSpace: JsObject /* PredefinedColorSpace */
}

external interface WGPUSamplerDescriptor : JsObject, WGPUObjectDescriptorBase {
	var addressModeU: String  /* GPUAddressMode */
	var addressModeV: String  /* GPUAddressMode */
	var addressModeW: String  /* GPUAddressMode */
	var magFilter: String  /* GPUFilterMode */
	var minFilter: String  /* GPUFilterMode */
	var mipmapFilter: String  /* GPUMipmapFilterMode */
	var lodMinClamp: JsNumber  /* float */
	var lodMaxClamp: JsNumber  /* float */
	var compare: String  /* GPUCompareFunction */
	var maxAnisotropy: JsNumber  /* unsigned short */
}

external interface WGPUBindGroupLayoutDescriptor : JsObject, WGPUObjectDescriptorBase {
	var entries: JsArray<JsObject> /* sequence<GPUBindGroupLayoutEntry> */
}

external interface WGPUBindGroupLayoutEntry : JsObject {
	var binding: JsNumber  /* GPUIndex32 */
	var visibility: JsNumber  /* GPUShaderStageFlags */
	var buffer: WGPUBufferBindingLayout  /* GPUBufferBindingLayout */
	var sampler: WGPUSamplerBindingLayout  /* GPUSamplerBindingLayout */
	var texture: WGPUTextureBindingLayout  /* GPUTextureBindingLayout */
	var storageTexture: WGPUStorageTextureBindingLayout  /* GPUStorageTextureBindingLayout */
	var externalTexture: WGPUExternalTextureBindingLayout  /* GPUExternalTextureBindingLayout */
}

external interface WGPUBufferBindingLayout : JsObject {
	var type: String  /* GPUBufferBindingType */
	var hasDynamicOffset: Boolean
	var minBindingSize: JsNumber  /* GPUSize64 */
}

external interface WGPUSamplerBindingLayout : JsObject {
	var type: String  /* GPUSamplerBindingType */
}

external interface WGPUTextureBindingLayout : JsObject {
	var sampleType: String  /* GPUTextureSampleType */
	var viewDimension: String  /* GPUTextureViewDimension */
	var multisampled: Boolean
}

external interface WGPUStorageTextureBindingLayout : JsObject {
	var access: String  /* GPUStorageTextureAccess */
	var format: String  /* GPUTextureFormat */
	var viewDimension: String  /* GPUTextureViewDimension */
}

external interface WGPUExternalTextureBindingLayout : JsObject
external interface WGPUBindGroupDescriptor : JsObject, WGPUObjectDescriptorBase {
	var layout: WGPUBindGroupLayout  /* GPUBindGroupLayout */
	var entries: JsArray<JsObject> /* sequence<GPUBindGroupEntry> */
}

external interface WGPUBindGroupEntry : JsObject {
	var binding: JsNumber  /* GPUIndex32 */
	var resource: JsObject /* GPUBindingResource */
}

external interface WGPUBufferBinding : JsObject {
	var buffer: WGPUBuffer  /* GPUBuffer */
	var offset: JsNumber  /* GPUSize64 */
	var size: JsNumber  /* GPUSize64 */
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
	var module: WGPUShaderModule  /* GPUShaderModule */
	var entryPoint: String
	var constants: JsMap<JsObject, JsObject>  /* record<USVString, GPUPipelineConstantValue>  */
}

external interface WGPUComputePipelineDescriptor : JsObject, WGPUPipelineDescriptorBase {
	var compute: WGPUProgrammableStage  /* GPUProgrammableStage */
}

external interface WGPURenderPipelineDescriptor : JsObject, WGPUPipelineDescriptorBase {
	var vertex: WGPUVertexState  /* GPUVertexState */
	var primitive: WGPUPrimitiveState  /* GPUPrimitiveState */
	var depthStencil: WGPUDepthStencilState  /* GPUDepthStencilState */
	var multisample: WGPUMultisampleState  /* GPUMultisampleState */
	var fragment: WGPUFragmentState  /* GPUFragmentState */
}

external interface WGPUPrimitiveState : JsObject {
	var topology: String  /* GPUPrimitiveTopology */
	var stripIndexFormat: String  /* GPUIndexFormat */
	var frontFace: String  /* GPUFrontFace */
	var cullMode: String  /* GPUCullMode */
	var unclippedDepth: Boolean
}

external interface WGPUMultisampleState : JsObject {
	var count: JsNumber  /* GPUSize32 */
	var mask: JsNumber  /* GPUSampleMask */
	var alphaToCoverageEnabled: Boolean
}

external interface WGPUFragmentState : JsObject, WGPUProgrammableStage {
	var targets: JsArray<JsObject> /* sequence<GPUColorTargetState?> */
}

external interface WGPUColorTargetState : JsObject {
	var format: String  /* GPUTextureFormat */
	var blend: WGPUBlendState  /* GPUBlendState */
	var writeMask: JsNumber  /* GPUColorWriteFlags */
}

external interface WGPUBlendState : JsObject {
	var color: WGPUBlendComponent  /* GPUBlendComponent */
	var alpha: WGPUBlendComponent  /* GPUBlendComponent */
}

external interface WGPUBlendComponent : JsObject {
	var operation: String  /* GPUBlendOperation */
	var srcFactor: String  /* GPUBlendFactor */
	var dstFactor: String  /* GPUBlendFactor */
}

external interface WGPUDepthStencilState : JsObject {
	var format: String  /* GPUTextureFormat */
	var depthWriteEnabled: Boolean
	var depthCompare: String  /* GPUCompareFunction */
	var stencilFront: WGPUStencilFaceState  /* GPUStencilFaceState */
	var stencilBack: WGPUStencilFaceState  /* GPUStencilFaceState */
	var stencilReadMask: JsNumber  /* GPUStencilValue */
	var stencilWriteMask: JsNumber  /* GPUStencilValue */
	var depthBias: JsNumber  /* GPUDepthBias */
	var depthBiasSlopeScale: JsNumber  /* float */
	var depthBiasClamp: JsNumber  /* float */
}

external interface WGPUStencilFaceState : JsObject {
	var compare: String  /* GPUCompareFunction */
	var failOp: String  /* GPUStencilOperation */
	var depthFailOp: String  /* GPUStencilOperation */
	var passOp: String  /* GPUStencilOperation */
}

external interface WGPUVertexState : JsObject, WGPUProgrammableStage {
	var buffers: JsArray<JsObject> /* sequence<GPUVertexBufferLayout?> */
}

external interface WGPUVertexBufferLayout : JsObject {
	var arrayStride: JsNumber  /* GPUSize64 */
	var stepMode: String  /* GPUVertexStepMode */
	var attributes: JsArray<JsObject> /* sequence<GPUVertexAttribute> */
}

external interface WGPUVertexAttribute : JsObject {
	var format: String  /* GPUVertexFormat */
	var offset: JsNumber  /* GPUSize64 */
	var shaderLocation: JsNumber  /* GPUIndex32 */
}

external interface WGPUTexelCopyBufferLayout : JsObject {
	var offset: JsNumber  /* GPUSize64 */
	var bytesPerRow: JsNumber  /* GPUSize32 */
	var rowsPerImage: JsNumber  /* GPUSize32 */
}

external interface WGPUTexelCopyBufferInfo : JsObject, WGPUTexelCopyBufferLayout {
	var buffer: WGPUBuffer  /* GPUBuffer */
}

external interface WGPUTexelCopyTextureInfo : JsObject {
	var texture: WGPUTexture  /* GPUTexture */
	var mipLevel: JsNumber  /* GPUIntegerCoordinate */
	var origin: JsObject /* GPUOrigin3D */
	var aspect: String  /* GPUTextureAspect */
}

external interface WGPUCopyExternalImageDestInfo : JsObject, WGPUTexelCopyTextureInfo {
	var colorSpace: JsObject /* PredefinedColorSpace */
	var premultipliedAlpha: Boolean
}

external interface WGPUCopyExternalImageSourceInfo : JsObject {
	var source: JsObject /* GPUCopyExternalImageSource */
	var origin: JsObject /* GPUOrigin2D */
	var flipY: Boolean
}

external interface WGPUCommandBufferDescriptor : JsObject, WGPUObjectDescriptorBase
external interface WGPUCommandEncoderDescriptor : JsObject, WGPUObjectDescriptorBase
external interface WGPUComputePassTimestampWrites : JsObject {
	var querySet: WGPUQuerySet  /* GPUQuerySet */
	var beginningOfPassWriteIndex: JsNumber  /* GPUSize32 */
	var endOfPassWriteIndex: JsNumber  /* GPUSize32 */
}

external interface WGPUComputePassDescriptor : JsObject, WGPUObjectDescriptorBase {
	var timestampWrites: WGPUComputePassTimestampWrites  /* GPUComputePassTimestampWrites */
}

external interface WGPURenderPassTimestampWrites : JsObject {
	var querySet: WGPUQuerySet  /* GPUQuerySet */
	var beginningOfPassWriteIndex: JsNumber  /* GPUSize32 */
	var endOfPassWriteIndex: JsNumber  /* GPUSize32 */
}

external interface WGPURenderPassDescriptor : JsObject, WGPUObjectDescriptorBase {
	var colorAttachments: JsArray<JsObject> /* sequence<GPURenderPassColorAttachment?> */
	var depthStencilAttachment: WGPURenderPassDepthStencilAttachment  /* GPURenderPassDepthStencilAttachment */
	var occlusionQuerySet: WGPUQuerySet  /* GPUQuerySet */
	var timestampWrites: WGPURenderPassTimestampWrites  /* GPURenderPassTimestampWrites */
	var maxDrawCount: JsNumber  /* GPUSize64 */
}

external interface WGPURenderPassColorAttachment : JsObject {
	var view: WGPUTextureView  /* GPUTextureView */
	var depthSlice: JsNumber  /* GPUIntegerCoordinate */
	var resolveTarget: WGPUTextureView  /* GPUTextureView */
	var clearValue: JsObject /* GPUColor */
	var loadOp: String  /* GPULoadOp */
	var storeOp: String  /* GPUStoreOp */
}

external interface WGPURenderPassDepthStencilAttachment : JsObject {
	var view: WGPUTextureView  /* GPUTextureView */
	var depthClearValue: JsNumber  /* float */
	var depthLoadOp: String  /* GPULoadOp */
	var depthStoreOp: String  /* GPUStoreOp */
	var depthReadOnly: Boolean
	var stencilClearValue: JsNumber  /* GPUStencilValue */
	var stencilLoadOp: String  /* GPULoadOp */
	var stencilStoreOp: String  /* GPUStoreOp */
	var stencilReadOnly: Boolean
}

external interface WGPURenderPassLayout : JsObject, WGPUObjectDescriptorBase {
	var colorFormats: JsArray<JsObject> /* sequence<GPUTextureFormat?> */
	var depthStencilFormat: String  /* GPUTextureFormat */
	var sampleCount: JsNumber  /* GPUSize32 */
}

external interface WGPURenderBundleDescriptor : JsObject, WGPUObjectDescriptorBase
external interface WGPURenderBundleEncoderDescriptor : JsObject, WGPURenderPassLayout {
	var depthReadOnly: Boolean
	var stencilReadOnly: Boolean
}

external interface WGPUQueueDescriptor : JsObject, WGPUObjectDescriptorBase
external interface WGPUQuerySetDescriptor : JsObject, WGPUObjectDescriptorBase {
	var type: String  /* GPUQueryType */
	var count: JsNumber  /* GPUSize32 */
}

external interface WGPUCanvasToneMapping : JsObject {
	var mode: JsObject /* GPUCanvasToneMappingMode */
}

external interface WGPUCanvasConfiguration : JsObject {
	var device: WGPUDevice  /* GPUDevice */
	var format: String  /* GPUTextureFormat */
	var usage: JsNumber  /* GPUTextureUsageFlags */
	var viewFormats: JsArray<JsObject> /* sequence<GPUTextureFormat> */
	var colorSpace: JsObject /* PredefinedColorSpace */
	var toneMapping: WGPUCanvasToneMapping  /* GPUCanvasToneMapping */
	var alphaMode: JsObject /* GPUCanvasAlphaMode */
}

external interface WGPUUncapturedErrorEventInit : JsObject, EventInit {
	var error: WGPUError  /* GPUError */
}

external interface WGPUColorDict : JsObject {
	var r: JsNumber  /* double */
	var g: JsNumber  /* double */
	var b: JsNumber  /* double */
	var a: JsNumber  /* double */
}

external interface WGPUOrigin2DDict : JsObject {
	var x: JsNumber  /* GPUIntegerCoordinate */
	var y: JsNumber  /* GPUIntegerCoordinate */
}

external interface WGPUOrigin3DDict : JsObject {
	var x: JsNumber  /* GPUIntegerCoordinate */
	var y: JsNumber  /* GPUIntegerCoordinate */
	var z: JsNumber  /* GPUIntegerCoordinate */
}

external interface WGPUExtent3DDict : JsObject {
	var width: JsNumber  /* GPUIntegerCoordinate */
	var height: JsNumber  /* GPUIntegerCoordinate */
	var depthOrArrayLayers: JsNumber  /* GPUIntegerCoordinate */
}
