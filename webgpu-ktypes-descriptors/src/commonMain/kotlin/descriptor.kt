@file:Suppress("unused")
// This file has been generated DO NO EDIT
package io.ygdrasil.webgpu

data class Color(
	override val r: Double,
	override val g: Double,
	override val b: Double,
	override val a: Double
): GPUColor

data class Origin2D(
	override val x: GPUIntegerCoordinate = 0u,
	override val y: GPUIntegerCoordinate = 0u
): GPUOrigin2D

data class Origin3D(
	override val x: GPUIntegerCoordinate = 0u,
	override val y: GPUIntegerCoordinate = 0u,
	override val z: GPUIntegerCoordinate = 0u
): GPUOrigin3D

data class Extent3D(
	override val width: GPUIntegerCoordinate,
	override val height: GPUIntegerCoordinate = 1u,
	override val depthOrArrayLayers: GPUIntegerCoordinate = 1u
): GPUExtent3D

data class ObjectDescriptorBase(
	override val label: String = ""
): GPUObjectDescriptorBase

data class RequestAdapterOptions(
	override val featureLevel: String = "core",
	override val powerPreference: GPUPowerPreference? = null,
	override val forceFallbackAdapter: Boolean = false,
	override val xrCompatible: Boolean = false
): GPURequestAdapterOptions

data class DeviceDescriptor(
	override val requiredFeatures: List<GPUFeatureName> = emptyList(),
	override val requiredLimits: GPUSupportedLimits? = null,
	override val defaultQueue: GPUQueueDescriptor = QueueDescriptor(),
	override val label: String = ""
): GPUDeviceDescriptor

data class BufferDescriptor(
	override val size: GPUSize64,
	override val usage: GPUBufferUsageFlags,
	override val mappedAtCreation: Boolean = false,
	override val label: String = ""
): GPUBufferDescriptor

data class TextureDescriptor(
	override val size: GPUExtent3D,
	override val format: GPUTextureFormat,
	override val usage: GPUTextureUsageFlags,
	override val mipLevelCount: GPUIntegerCoordinate = 1u,
	override val sampleCount: GPUSize32 = 1u,
	override val dimension: GPUTextureDimension = GPUTextureDimension.TwoD,
	override val viewFormats: List<GPUTextureFormat> = emptyList(),
	override val label: String = ""
): GPUTextureDescriptor

data class TextureViewDescriptor(
	override val format: GPUTextureFormat? = null,
	override val dimension: GPUTextureViewDimension? = null,
	override val usage: GPUTextureUsageFlags = 0u,
	override val aspect: GPUTextureAspect = GPUTextureAspect.All,
	override val baseMipLevel: GPUIntegerCoordinate = 0u,
	override val mipLevelCount: GPUIntegerCoordinate? = null,
	override val baseArrayLayer: GPUIntegerCoordinate = 0u,
	override val arrayLayerCount: GPUIntegerCoordinate? = null,
	override val label: String = ""
): GPUTextureViewDescriptor

data class SamplerDescriptor(
	override val addressModeU: GPUAddressMode = GPUAddressMode.ClampToEdge,
	override val addressModeV: GPUAddressMode = GPUAddressMode.ClampToEdge,
	override val addressModeW: GPUAddressMode = GPUAddressMode.ClampToEdge,
	override val magFilter: GPUFilterMode = GPUFilterMode.Nearest,
	override val minFilter: GPUFilterMode = GPUFilterMode.Nearest,
	override val mipmapFilter: GPUMipmapFilterMode = GPUMipmapFilterMode.Nearest,
	override val lodMinClamp: Float = 0f,
	override val lodMaxClamp: Float = 32f,
	override val compare: GPUCompareFunction? = null,
	override val maxAnisotropy: UShort = 1u,
	override val label: String = ""
): GPUSamplerDescriptor

data class BindGroupLayoutDescriptor(
	override val entries: List<GPUBindGroupLayoutEntry>,
	override val label: String = ""
): GPUBindGroupLayoutDescriptor

data class BindGroupLayoutEntry(
	override val binding: GPUIndex32,
	override val visibility: GPUShaderStageFlags,
	override val buffer: GPUBufferBindingLayout? = null,
	override val sampler: GPUSamplerBindingLayout? = null,
	override val texture: GPUTextureBindingLayout? = null,
	override val storageTexture: GPUStorageTextureBindingLayout? = null
): GPUBindGroupLayoutEntry

data class BufferBindingLayout(
	override val type: GPUBufferBindingType = GPUBufferBindingType.Uniform,
	override val hasDynamicOffset: Boolean = false,
	override val minBindingSize: GPUSize64 = 0u
): GPUBufferBindingLayout

data class SamplerBindingLayout(
	override val type: GPUSamplerBindingType = GPUSamplerBindingType.Filtering
): GPUSamplerBindingLayout

data class TextureBindingLayout(
	override val sampleType: GPUTextureSampleType = GPUTextureSampleType.Float,
	override val viewDimension: GPUTextureViewDimension = GPUTextureViewDimension.TwoD,
	override val multisampled: Boolean = false
): GPUTextureBindingLayout

data class StorageTextureBindingLayout(
	override val format: GPUTextureFormat,
	override val access: GPUStorageTextureAccess = GPUStorageTextureAccess.WriteOnly,
	override val viewDimension: GPUTextureViewDimension = GPUTextureViewDimension.TwoD
): GPUStorageTextureBindingLayout

data class BindGroupDescriptor(
	override val layout: GPUBindGroupLayout,
	override val entries: List<GPUBindGroupEntry>,
	override val label: String = ""
): GPUBindGroupDescriptor

data class BindGroupEntry(
	override val binding: GPUIndex32,
	override val resource: GPUBindingResource
): GPUBindGroupEntry

data class BufferBinding(
	override val buffer: GPUBuffer,
	override val offset: GPUSize64 = 0u,
	override val size: GPUSize64? = null
): GPUBufferBinding

data class PipelineLayoutDescriptor(
	override val bindGroupLayouts: List<GPUBindGroupLayout?>,
	override val label: String = ""
): GPUPipelineLayoutDescriptor

data class ShaderModuleDescriptor(
	override val code: String,
	override val compilationHints: List<GPUShaderModuleCompilationHint> = emptyList(),
	override val label: String = ""
): GPUShaderModuleDescriptor

data class ShaderModuleCompilationHint(
	override val entryPoint: String,
	override val layout: GPUPipelineLayout? = null
): GPUShaderModuleCompilationHint

data class PipelineDescriptorBase(
	override val layout: GPUPipelineLayout? = null,
	override val label: String = ""
): GPUPipelineDescriptorBase

data class ProgrammableStage(
	override val module: GPUShaderModule,
	override val entryPoint: String? = null,
	override val constants: Map<String, GPUPipelineConstantValue> = emptyMap()
): GPUProgrammableStage

data class ComputePipelineDescriptor(
	override val compute: GPUProgrammableStage,
	override val layout: GPUPipelineLayout? = null,
	override val label: String = ""
): GPUComputePipelineDescriptor

data class RenderPipelineDescriptor(
	override val vertex: GPUVertexState,
	override val primitive: GPUPrimitiveState = PrimitiveState(),
	override val depthStencil: GPUDepthStencilState? = null,
	override val multisample: GPUMultisampleState = MultisampleState(),
	override val fragment: GPUFragmentState? = null,
	override val layout: GPUPipelineLayout? = null,
	override val label: String = ""
): GPURenderPipelineDescriptor

data class PrimitiveState(
	override val topology: GPUPrimitiveTopology = GPUPrimitiveTopology.TriangleList,
	override val stripIndexFormat: GPUIndexFormat? = null,
	override val frontFace: GPUFrontFace = GPUFrontFace.CCW,
	override val cullMode: GPUCullMode = GPUCullMode.None,
	override val unclippedDepth: Boolean = false
): GPUPrimitiveState

data class MultisampleState(
	override val count: GPUSize32 = 1u,
	override val mask: GPUSampleMask = 0xFFFFFFFFu,
	override val alphaToCoverageEnabled: Boolean = false
): GPUMultisampleState

data class FragmentState(
	override val targets: List<GPUColorTargetState?>,
	override val module: GPUShaderModule,
	override val entryPoint: String? = null,
	override val constants: Map<String, GPUPipelineConstantValue> = emptyMap()
): GPUFragmentState

data class ColorTargetState(
	override val format: GPUTextureFormat,
	override val blend: GPUBlendState? = null,
	override val writeMask: GPUColorWriteFlags = 0xFu
): GPUColorTargetState

data class BlendState(
	override val color: GPUBlendComponent,
	override val alpha: GPUBlendComponent
): GPUBlendState

data class BlendComponent(
	override val operation: GPUBlendOperation = GPUBlendOperation.Add,
	override val srcFactor: GPUBlendFactor = GPUBlendFactor.One,
	override val dstFactor: GPUBlendFactor = GPUBlendFactor.Zero
): GPUBlendComponent

data class DepthStencilState(
	override val format: GPUTextureFormat,
	override val depthWriteEnabled: Boolean? = null,
	override val depthCompare: GPUCompareFunction? = null,
	override val stencilFront: GPUStencilFaceState = StencilFaceState(),
	override val stencilBack: GPUStencilFaceState = StencilFaceState(),
	override val stencilReadMask: GPUStencilValue = 0xFFFFFFFFu,
	override val stencilWriteMask: GPUStencilValue = 0xFFFFFFFFu,
	override val depthBias: GPUDepthBias = 0,
	override val depthBiasSlopeScale: Float = 0f,
	override val depthBiasClamp: Float = 0f
): GPUDepthStencilState

data class StencilFaceState(
	override val compare: GPUCompareFunction = GPUCompareFunction.Always,
	override val failOp: GPUStencilOperation = GPUStencilOperation.Keep,
	override val depthFailOp: GPUStencilOperation = GPUStencilOperation.Keep,
	override val passOp: GPUStencilOperation = GPUStencilOperation.Keep
): GPUStencilFaceState

data class VertexState(
	override val module: GPUShaderModule,
	override val buffers: List<GPUVertexBufferLayout?> = emptyList(),
	override val entryPoint: String? = null,
	override val constants: Map<String, GPUPipelineConstantValue> = emptyMap()
): GPUVertexState

data class VertexBufferLayout(
	override val arrayStride: GPUSize64,
	override val attributes: List<GPUVertexAttribute>,
	override val stepMode: GPUVertexStepMode = GPUVertexStepMode.Vertex
): GPUVertexBufferLayout

data class VertexAttribute(
	override val format: GPUVertexFormat,
	override val offset: GPUSize64,
	override val shaderLocation: GPUIndex32
): GPUVertexAttribute

data class TexelCopyBufferLayout(
	override val offset: GPUSize64 = 0u,
	override val bytesPerRow: GPUSize32? = null,
	override val rowsPerImage: GPUSize32? = null
): GPUTexelCopyBufferLayout

data class TexelCopyBufferInfo(
	override val buffer: GPUBuffer,
	override val offset: GPUSize64 = 0u,
	override val bytesPerRow: GPUSize32? = null,
	override val rowsPerImage: GPUSize32? = null
): GPUTexelCopyBufferInfo

data class TexelCopyTextureInfo(
	override val texture: GPUTexture,
	override val mipLevel: GPUIntegerCoordinate = 0u,
	override val origin: GPUOrigin3D = Origin3D(),
	override val aspect: GPUTextureAspect = GPUTextureAspect.All
): GPUTexelCopyTextureInfo

data class CommandBufferDescriptor(
	override val label: String = ""
): GPUCommandBufferDescriptor

data class CommandEncoderDescriptor(
	override val label: String = ""
): GPUCommandEncoderDescriptor

data class ComputePassTimestampWrites(
	override val querySet: GPUQuerySet,
	override val beginningOfPassWriteIndex: GPUSize32? = null,
	override val endOfPassWriteIndex: GPUSize32? = null
): GPUComputePassTimestampWrites

data class ComputePassDescriptor(
	override val timestampWrites: GPUComputePassTimestampWrites? = null,
	override val label: String = ""
): GPUComputePassDescriptor

data class RenderPassTimestampWrites(
	override val querySet: GPUQuerySet,
	override val beginningOfPassWriteIndex: GPUSize32? = null,
	override val endOfPassWriteIndex: GPUSize32? = null
): GPURenderPassTimestampWrites

data class RenderPassDescriptor(
	override val colorAttachments: List<GPURenderPassColorAttachment?>,
	override val depthStencilAttachment: GPURenderPassDepthStencilAttachment? = null,
	override val occlusionQuerySet: GPUQuerySet? = null,
	override val timestampWrites: GPURenderPassTimestampWrites? = null,
	override val maxDrawCount: GPUSize64 = 50000000u,
	override val label: String = ""
): GPURenderPassDescriptor

data class RenderPassColorAttachment(
	override val view: GPUTextureView,
	override val loadOp: GPULoadOp,
	override val storeOp: GPUStoreOp,
	override val depthSlice: GPUIntegerCoordinate? = null,
	override val resolveTarget: GPUTextureView? = null,
	override val clearValue: GPUColor? = null
): GPURenderPassColorAttachment

data class RenderPassDepthStencilAttachment(
	override val view: GPUTextureView,
	override val depthClearValue: Float? = null,
	override val depthLoadOp: GPULoadOp? = null,
	override val depthStoreOp: GPUStoreOp? = null,
	override val depthReadOnly: Boolean = false,
	override val stencilClearValue: GPUStencilValue = 0u,
	override val stencilLoadOp: GPULoadOp? = null,
	override val stencilStoreOp: GPUStoreOp? = null,
	override val stencilReadOnly: Boolean = false
): GPURenderPassDepthStencilAttachment

data class RenderPassLayout(
	override val colorFormats: List<GPUTextureFormat?>,
	override val depthStencilFormat: GPUTextureFormat? = null,
	override val sampleCount: GPUSize32 = 1u,
	override val label: String = ""
): GPURenderPassLayout

data class RenderBundleDescriptor(
	override val label: String = ""
): GPURenderBundleDescriptor

data class RenderBundleEncoderDescriptor(
	override val colorFormats: List<GPUTextureFormat?>,
	override val depthReadOnly: Boolean = false,
	override val stencilReadOnly: Boolean = false,
	override val depthStencilFormat: GPUTextureFormat? = null,
	override val sampleCount: GPUSize32 = 1u,
	override val label: String = ""
): GPURenderBundleEncoderDescriptor

data class QueueDescriptor(
	override val label: String = ""
): GPUQueueDescriptor

data class QuerySetDescriptor(
	override val type: GPUQueryType,
	override val count: GPUSize32,
	override val label: String = ""
): GPUQuerySetDescriptor
