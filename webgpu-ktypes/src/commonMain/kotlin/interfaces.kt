@file:Suppress("unused")
// This file has been generated DO NO EDIT
package io.ygdrasil.webgpu

sealed interface GPUBindingResource
interface GPUSampler : GPUBindingResource, GPUObjectBase
interface GPUTextureView : GPUBindingResource, GPUObjectBase
interface GPUBufferBinding : GPUBindingResource {
	val buffer: GPUBuffer
	val offset: GPUSize64
	val size: GPUSize64?
}

interface GPUExternalTexture : GPUBindingResource
interface GPUColor {
	val r: Double
	val g: Double
	val b: Double
	val a: Double
}

interface GPUOrigin2D {
	val x: GPUIntegerCoordinate
	val y: GPUIntegerCoordinate
}

interface GPUOrigin3D {
	val x: GPUIntegerCoordinate
	val y: GPUIntegerCoordinate
	val z: GPUIntegerCoordinate
}

interface GPUExtent3D {
	val width: GPUIntegerCoordinate
	val height: GPUIntegerCoordinate
	val depthOrArrayLayers: GPUIntegerCoordinate
}

interface GPUObjectBase {
	var label: String
}

interface GPUSupportedLimits {
	val maxTextureDimension1D: UInt
	val maxTextureDimension2D: UInt
	val maxTextureDimension3D: UInt
	val maxTextureArrayLayers: UInt
	val maxBindGroups: UInt
	val maxBindGroupsPlusVertexBuffers: UInt
	val maxBindingsPerBindGroup: UInt
	val maxDynamicUniformBuffersPerPipelineLayout: UInt
	val maxDynamicStorageBuffersPerPipelineLayout: UInt
	val maxSampledTexturesPerShaderStage: UInt
	val maxSamplersPerShaderStage: UInt
	val maxStorageBuffersPerShaderStage: UInt
	val maxStorageTexturesPerShaderStage: UInt
	val maxUniformBuffersPerShaderStage: UInt
	val maxUniformBufferBindingSize: ULong
	val maxStorageBufferBindingSize: ULong
	val minUniformBufferOffsetAlignment: UInt
	val minStorageBufferOffsetAlignment: UInt
	val maxVertexBuffers: UInt
	val maxBufferSize: ULong
	val maxVertexAttributes: UInt
	val maxVertexBufferArrayStride: UInt
	val maxInterStageShaderVariables: UInt
	val maxColorAttachments: UInt
	val maxColorAttachmentBytesPerSample: UInt
	val maxComputeWorkgroupStorageSize: UInt
	val maxComputeInvocationsPerWorkgroup: UInt
	val maxComputeWorkgroupSizeX: UInt
	val maxComputeWorkgroupSizeY: UInt
	val maxComputeWorkgroupSizeZ: UInt
	val maxComputeWorkgroupsPerDimension: UInt
}

interface GPUSupportedFeatures
interface WGSLLanguageFeatures
interface GPUAdapterInfo {
	val vendor: String
	val architecture: String
	val device: String
	val description: String
	val subgroupMinSize: UInt
	val subgroupMaxSize: UInt
}

interface GPU {
	val wgslLanguageFeatures: WGSLLanguageFeatures
	suspend fun requestAdapter(options: GPURequestAdapterOptions? = null): GPUAdapter?
	fun getPreferredCanvasFormat(): GPUTextureFormat
}

interface GPUAdapter {
	val features: GPUSupportedFeatures
	val limits: GPUSupportedLimits
	val info: GPUAdapterInfo
	val isFallbackAdapter: Boolean
	suspend fun requestDevice(descriptor: GPUDeviceDescriptor? = null): GPUDevice
}

interface GPUDevice : GPUObjectBase {
	val features: GPUSupportedFeatures
	val limits: GPUSupportedLimits
	val adapterInfo: GPUAdapterInfo
	val queue: GPUQueue
	fun destroy()
	fun createBuffer(descriptor: GPUBufferDescriptor): GPUBuffer
	fun createTexture(descriptor: GPUTextureDescriptor): GPUTexture
	fun createSampler(descriptor: GPUSamplerDescriptor? = null): GPUSampler
	fun createBindGroupLayout(descriptor: GPUBindGroupLayoutDescriptor): GPUBindGroupLayout
	fun createPipelineLayout(descriptor: GPUPipelineLayoutDescriptor): GPUPipelineLayout
	fun createBindGroup(descriptor: GPUBindGroupDescriptor): GPUBindGroup
	fun createShaderModule(descriptor: GPUShaderModuleDescriptor): GPUShaderModule
	fun createComputePipeline(descriptor: GPUComputePipelineDescriptor): GPUComputePipeline
	fun createRenderPipeline(descriptor: GPURenderPipelineDescriptor): GPURenderPipeline
	suspend fun createComputePipelineAsync(descriptor: GPUComputePipelineDescriptor): GPUComputePipeline
	suspend fun createRenderPipelineAsync(descriptor: GPURenderPipelineDescriptor): GPURenderPipeline
	fun createCommandEncoder(descriptor: GPUCommandEncoderDescriptor? = null): GPUCommandEncoder
	fun createRenderBundleEncoder(descriptor: GPURenderBundleEncoderDescriptor): GPURenderBundleEncoder
	fun createQuerySet(descriptor: GPUQuerySetDescriptor): GPUQuerySet
	fun pushErrorScope(filter: GPUErrorFilter)
	suspend fun popErrorScope(): GPUError?
}

interface GPUBuffer : GPUObjectBase {
	val size: GPUSize64Out
	val usage: GPUFlagsConstant
	val mapState: GPUBufferMapState
	suspend fun mapAsync(mode: GPUMapModeFlags, offset: GPUSize64 = 0u, size: GPUSize64)
	fun getMappedRange(offset: GPUSize64 = 0u, size: GPUSize64): ArrayBuffer
	fun unmap()
	fun destroy()
}

interface GPUTexture : GPUObjectBase {
	val width: GPUIntegerCoordinateOut
	val height: GPUIntegerCoordinateOut
	val depthOrArrayLayers: GPUIntegerCoordinateOut
	val mipLevelCount: GPUIntegerCoordinateOut
	val sampleCount: GPUSize32Out
	val dimension: GPUTextureDimension
	val format: GPUTextureFormat
	val usage: GPUFlagsConstant
	fun createView(descriptor: GPUTextureViewDescriptor? = null): GPUTextureView
	fun destroy()
}

interface GPUBindGroupLayout : GPUObjectBase
interface GPUBindGroup : GPUObjectBase
interface GPUPipelineLayout : GPUObjectBase
interface GPUShaderModule : GPUObjectBase {
	suspend fun getCompilationInfo(): GPUCompilationInfo
}

interface GPUCompilationMessage {
	val message: String
	val type: GPUCompilationMessageType
	val lineNum: ULong
	val linePos: ULong
	val offset: ULong
	val length: ULong
}

interface GPUCompilationInfo {
	val messages: List<GPUCompilationMessage>
}

interface GPUPipelineBase {
	fun getBindGroupLayout(index: UInt): GPUBindGroupLayout
}

interface GPUComputePipeline : GPUObjectBase, GPUPipelineBase
interface GPURenderPipeline : GPUObjectBase, GPUPipelineBase
interface GPUCommandBuffer : GPUObjectBase
interface GPUCommandsMixin
interface GPUCommandEncoder : GPUObjectBase, GPUCommandsMixin, GPUDebugCommandsMixin {
	fun beginRenderPass(descriptor: GPURenderPassDescriptor): GPURenderPassEncoder
	fun beginComputePass(descriptor: GPUComputePassDescriptor? = null): GPUComputePassEncoder
	fun copyBufferToBuffer(source: GPUBuffer, sourceOffset: GPUSize64, destination: GPUBuffer, destinationOffset: GPUSize64, size: GPUSize64)
	fun copyBufferToTexture(source: GPUTexelCopyBufferInfo, destination: GPUTexelCopyTextureInfo, copySize: GPUExtent3D)
	fun copyTextureToBuffer(source: GPUTexelCopyTextureInfo, destination: GPUTexelCopyBufferInfo, copySize: GPUExtent3D)
	fun copyTextureToTexture(source: GPUTexelCopyTextureInfo, destination: GPUTexelCopyTextureInfo, copySize: GPUExtent3D)
	fun clearBuffer(buffer: GPUBuffer, offset: GPUSize64 = 0u, size: GPUSize64)
	fun resolveQuerySet(querySet: GPUQuerySet, firstQuery: GPUSize32, queryCount: GPUSize32, destination: GPUBuffer, destinationOffset: GPUSize64)
	fun finish(descriptor: GPUCommandBufferDescriptor? = null): GPUCommandBuffer
}

interface GPUBindingCommandsMixin {
	fun setBindGroup(index: GPUIndex32, bindGroup: GPUBindGroup?, dynamicOffsetsData: List<UInt>, dynamicOffsetsDataStart: GPUSize64, dynamicOffsetsDataLength: GPUSize32)
}

interface GPUDebugCommandsMixin {
	fun pushDebugGroup(groupLabel: String)
	fun popDebugGroup()
	fun insertDebugMarker(markerLabel: String)
}

interface GPUComputePassEncoder : GPUObjectBase, GPUCommandsMixin, GPUDebugCommandsMixin, GPUBindingCommandsMixin {
	fun setPipeline(pipeline: GPUComputePipeline)
	fun dispatchWorkgroups(workgroupCountX: GPUSize32, workgroupCountY: GPUSize32 = 1u, workgroupCountZ: GPUSize32 = 1u)
	fun dispatchWorkgroupsIndirect(indirectBuffer: GPUBuffer, indirectOffset: GPUSize64)
	fun end()
}

interface GPURenderPassEncoder : GPUObjectBase, GPUCommandsMixin, GPUDebugCommandsMixin, GPUBindingCommandsMixin, GPURenderCommandsMixin {
	fun setViewport(x: Float, y: Float, width: Float, height: Float, minDepth: Float, maxDepth: Float)
	fun setScissorRect(x: GPUIntegerCoordinate, y: GPUIntegerCoordinate, width: GPUIntegerCoordinate, height: GPUIntegerCoordinate)
	fun setBlendConstant(color: GPUColor)
	fun setStencilReference(reference: GPUStencilValue)
	fun beginOcclusionQuery(queryIndex: GPUSize32)
	fun endOcclusionQuery()
	fun executeBundles(bundles: List<GPURenderBundle>)
	fun end()
}

interface GPURenderCommandsMixin {
	fun setPipeline(pipeline: GPURenderPipeline)
	fun setIndexBuffer(buffer: GPUBuffer, indexFormat: GPUIndexFormat, offset: GPUSize64 = 0u, size: GPUSize64)
	fun setVertexBuffer(slot: GPUIndex32, buffer: GPUBuffer?, offset: GPUSize64 = 0u, size: GPUSize64)
	fun draw(vertexCount: GPUSize32, instanceCount: GPUSize32 = 1u, firstVertex: GPUSize32 = 0u, firstInstance: GPUSize32 = 0u)
	fun drawIndexed(indexCount: GPUSize32, instanceCount: GPUSize32 = 1u, firstIndex: GPUSize32 = 0u, baseVertex: GPUSignedOffset32 = 0, firstInstance: GPUSize32 = 0u)
	fun drawIndirect(indirectBuffer: GPUBuffer, indirectOffset: GPUSize64)
	fun drawIndexedIndirect(indirectBuffer: GPUBuffer, indirectOffset: GPUSize64)
}

interface GPURenderBundle : GPUObjectBase
interface GPURenderBundleEncoder : GPUObjectBase, GPUCommandsMixin, GPUDebugCommandsMixin, GPUBindingCommandsMixin, GPURenderCommandsMixin {
	fun finish(descriptor: GPURenderBundleDescriptor? = null): GPURenderBundle
}

interface GPUQueue : GPUObjectBase {
	fun submit(commandBuffers: List<GPUCommandBuffer>)
	suspend fun onSubmittedWorkDone()
	fun writeBuffer(buffer: GPUBuffer, bufferOffset: GPUSize64, data: GPUBufferSource, dataOffset: GPUSize64 = 0u, size: GPUSize64)
	fun writeTexture(destination: GPUTexelCopyTextureInfo, data: GPUBufferSource, dataLayout: GPUTexelCopyBufferLayout, size: GPUExtent3D)
}

interface GPUQuerySet : GPUObjectBase {
	val type: GPUQueryType
	val count: GPUSize32Out
	fun destroy()
}

interface GPUDeviceLostInfo {
	val reason: GPUDeviceLostReason
	val message: String
}

interface GPUError {
	val message: String
}

interface GPUValidationError : GPUError
interface GPUOutOfMemoryError : GPUError
interface GPUInternalError : GPUError
interface GPUObjectDescriptorBase {
	val label: String
}

interface GPURequestAdapterOptions {
	val featureLevel: String
	val powerPreference: GPUPowerPreference?
	val forceFallbackAdapter: Boolean
	val xrCompatible: Boolean
}

interface GPUDeviceDescriptor : GPUObjectDescriptorBase {
	val requiredFeatures: List<GPUFeatureName>
	val requiredLimits: GPUSupportedLimits?
	val defaultQueue: GPUQueueDescriptor
}

interface GPUBufferDescriptor : GPUObjectDescriptorBase {
	val size: GPUSize64
	val usage: GPUBufferUsageFlags
	val mappedAtCreation: Boolean
}

interface GPUTextureDescriptor : GPUObjectDescriptorBase {
	val size: GPUExtent3D
	val mipLevelCount: GPUIntegerCoordinate
	val sampleCount: GPUSize32
	val dimension: GPUTextureDimension
	val format: GPUTextureFormat
	val usage: GPUTextureUsageFlags
	val viewFormats: List<GPUTextureFormat>
}

interface GPUTextureViewDescriptor : GPUObjectDescriptorBase {
	val format: GPUTextureFormat?
	val dimension: GPUTextureViewDimension?
	val usage: GPUTextureUsageFlags
	val aspect: GPUTextureAspect
	val baseMipLevel: GPUIntegerCoordinate
	val mipLevelCount: GPUIntegerCoordinate?
	val baseArrayLayer: GPUIntegerCoordinate
	val arrayLayerCount: GPUIntegerCoordinate?
}

interface GPUSamplerDescriptor : GPUObjectDescriptorBase {
	val addressModeU: GPUAddressMode
	val addressModeV: GPUAddressMode
	val addressModeW: GPUAddressMode
	val magFilter: GPUFilterMode
	val minFilter: GPUFilterMode
	val mipmapFilter: GPUMipmapFilterMode
	val lodMinClamp: Float
	val lodMaxClamp: Float
	val compare: GPUCompareFunction?
	val maxAnisotropy: UShort
}

interface GPUBindGroupLayoutDescriptor : GPUObjectDescriptorBase {
	val entries: List<GPUBindGroupLayoutEntry>
}

interface GPUBindGroupLayoutEntry {
	val binding: GPUIndex32
	val visibility: GPUShaderStageFlags
	val buffer: GPUBufferBindingLayout?
	val sampler: GPUSamplerBindingLayout?
	val texture: GPUTextureBindingLayout?
	val storageTexture: GPUStorageTextureBindingLayout?
}

interface GPUBufferBindingLayout {
	val type: GPUBufferBindingType
	val hasDynamicOffset: Boolean
	val minBindingSize: GPUSize64
}

interface GPUSamplerBindingLayout {
	val type: GPUSamplerBindingType
}

interface GPUTextureBindingLayout {
	val sampleType: GPUTextureSampleType
	val viewDimension: GPUTextureViewDimension
	val multisampled: Boolean
}

interface GPUStorageTextureBindingLayout {
	val access: GPUStorageTextureAccess
	val format: GPUTextureFormat
	val viewDimension: GPUTextureViewDimension
}

interface GPUBindGroupDescriptor : GPUObjectDescriptorBase {
	val layout: GPUBindGroupLayout
	val entries: List<GPUBindGroupEntry>
}

interface GPUBindGroupEntry {
	val binding: GPUIndex32
	val resource: GPUBindingResource
}

interface GPUPipelineLayoutDescriptor : GPUObjectDescriptorBase {
	val bindGroupLayouts: List<GPUBindGroupLayout?>
}

interface GPUShaderModuleDescriptor : GPUObjectDescriptorBase {
	val code: String
	val compilationHints: List<GPUShaderModuleCompilationHint>
}

interface GPUShaderModuleCompilationHint {
	val entryPoint: String
	val layout: GPUPipelineLayout??
}

interface GPUPipelineDescriptorBase : GPUObjectDescriptorBase {
	val layout: GPUPipelineLayout?
}

interface GPUProgrammableStage {
	val module: GPUShaderModule
	val entryPoint: String?
	val constants: Map<String, GPUPipelineConstantValue>
}

interface GPUComputePipelineDescriptor : GPUPipelineDescriptorBase {
	val compute: GPUProgrammableStage
}

interface GPURenderPipelineDescriptor : GPUPipelineDescriptorBase {
	val vertex: GPUVertexState
	val primitive: GPUPrimitiveState
	val depthStencil: GPUDepthStencilState?
	val multisample: GPUMultisampleState
	val fragment: GPUFragmentState?
}

interface GPUPrimitiveState {
	val topology: GPUPrimitiveTopology
	val stripIndexFormat: GPUIndexFormat?
	val frontFace: GPUFrontFace
	val cullMode: GPUCullMode
	val unclippedDepth: Boolean
}

interface GPUMultisampleState {
	val count: GPUSize32
	val mask: GPUSampleMask
	val alphaToCoverageEnabled: Boolean
}

interface GPUFragmentState : GPUProgrammableStage {
	val targets: List<GPUColorTargetState?>
}

interface GPUColorTargetState {
	val format: GPUTextureFormat
	val blend: GPUBlendState?
	val writeMask: GPUColorWriteFlags
}

interface GPUBlendState {
	val color: GPUBlendComponent
	val alpha: GPUBlendComponent
}

interface GPUBlendComponent {
	val operation: GPUBlendOperation
	val srcFactor: GPUBlendFactor
	val dstFactor: GPUBlendFactor
}

interface GPUDepthStencilState {
	val format: GPUTextureFormat
	val depthWriteEnabled: Boolean?
	val depthCompare: GPUCompareFunction?
	val stencilFront: GPUStencilFaceState
	val stencilBack: GPUStencilFaceState
	val stencilReadMask: GPUStencilValue
	val stencilWriteMask: GPUStencilValue
	val depthBias: GPUDepthBias
	val depthBiasSlopeScale: Float
	val depthBiasClamp: Float
}

interface GPUStencilFaceState {
	val compare: GPUCompareFunction
	val failOp: GPUStencilOperation
	val depthFailOp: GPUStencilOperation
	val passOp: GPUStencilOperation
}

interface GPUVertexState : GPUProgrammableStage {
	val buffers: List<GPUVertexBufferLayout?>
}

interface GPUVertexBufferLayout {
	val arrayStride: GPUSize64
	val stepMode: GPUVertexStepMode
	val attributes: List<GPUVertexAttribute>
}

interface GPUVertexAttribute {
	val format: GPUVertexFormat
	val offset: GPUSize64
	val shaderLocation: GPUIndex32
}

interface GPUTexelCopyBufferLayout {
	val offset: GPUSize64
	val bytesPerRow: GPUSize32?
	val rowsPerImage: GPUSize32?
}

interface GPUTexelCopyBufferInfo : GPUTexelCopyBufferLayout {
	val buffer: GPUBuffer
}

interface GPUTexelCopyTextureInfo {
	val texture: GPUTexture
	val mipLevel: GPUIntegerCoordinate
	val origin: GPUOrigin3D
	val aspect: GPUTextureAspect
}

interface GPUCommandBufferDescriptor : GPUObjectDescriptorBase
interface GPUCommandEncoderDescriptor : GPUObjectDescriptorBase
interface GPUComputePassTimestampWrites {
	val querySet: GPUQuerySet
	val beginningOfPassWriteIndex: GPUSize32?
	val endOfPassWriteIndex: GPUSize32?
}

interface GPUComputePassDescriptor : GPUObjectDescriptorBase {
	val timestampWrites: GPUComputePassTimestampWrites?
}

interface GPURenderPassTimestampWrites {
	val querySet: GPUQuerySet
	val beginningOfPassWriteIndex: GPUSize32?
	val endOfPassWriteIndex: GPUSize32?
}

interface GPURenderPassDescriptor : GPUObjectDescriptorBase {
	val colorAttachments: List<GPURenderPassColorAttachment?>
	val depthStencilAttachment: GPURenderPassDepthStencilAttachment?
	val occlusionQuerySet: GPUQuerySet?
	val timestampWrites: GPURenderPassTimestampWrites?
	val maxDrawCount: GPUSize64
}

interface GPURenderPassColorAttachment {
	val view: GPUTextureView
	val depthSlice: GPUIntegerCoordinate?
	val resolveTarget: GPUTextureView?
	val clearValue: GPUColor?
	val loadOp: GPULoadOp
	val storeOp: GPUStoreOp
}

interface GPURenderPassDepthStencilAttachment {
	val view: GPUTextureView
	val depthClearValue: Float?
	val depthLoadOp: GPULoadOp?
	val depthStoreOp: GPUStoreOp?
	val depthReadOnly: Boolean
	val stencilClearValue: GPUStencilValue
	val stencilLoadOp: GPULoadOp?
	val stencilStoreOp: GPUStoreOp?
	val stencilReadOnly: Boolean
}

interface GPURenderPassLayout : GPUObjectDescriptorBase {
	val colorFormats: List<GPUTextureFormat?>
	val depthStencilFormat: GPUTextureFormat?
	val sampleCount: GPUSize32
}

interface GPURenderBundleDescriptor : GPUObjectDescriptorBase
interface GPURenderBundleEncoderDescriptor : GPURenderPassLayout {
	val depthReadOnly: Boolean
	val stencilReadOnly: Boolean
}

interface GPUQueueDescriptor : GPUObjectDescriptorBase
interface GPUQuerySetDescriptor : GPUObjectDescriptorBase {
	val type: GPUQueryType
	val count: GPUSize32
}
