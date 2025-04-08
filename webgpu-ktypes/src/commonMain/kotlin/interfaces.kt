@file:Suppress("unused")
// This file has been generated DO NO EDIT
package io.ygdrasil.webgpu


/**
 * Represents a binding resource in the WebGPU API. This sealed interface can be one of several types:
 * - [GPUSampler]
 * - [GPUTextureView]
 * - [GPUBufferBinding]
 * - [GPUExternalTexture]
 * 
 * This interface is used to specify the type of resource that can be bound in a bind group. For more details, refer to the
 * [WebGPU specification on GPUBindingResource](https://www.w3.org/TR/webgpu/#typedefdef-gpubindingresource).
 * 
 */
sealed interface GPUBindingResource

/**
 * /**
 *  * The `GPUSampler` interface encodes transformations and filtering information that can be used in a shader to interpret texture resource data.
 *  * 
 *  * This interface is created via the [GPUDevice.createSampler()] method.
 *  * 
 *  * For more details, refer to the [WebGPU specification](https://www.w3.org/TR/webgpu/#gpusampler).
 *  */
 * interface GPUSampler : GPUBindingResource, GPUObjectBase, AutoCloseable
 * 
 */
interface GPUSampler : GPUBindingResource, GPUObjectBase, AutoCloseable

/**
 * A `GPUTextureView` represents a view onto some subset of the texture subresources defined by a particular [GPUTexture]. This interface allows for efficient access and manipulation of specific portions of a texture, enabling optimized rendering and data processing.
 * 
 * The `GPUTextureView` is part of the WebGPU API and is designed to be used in conjunction with other GPU resources such as [GPUBindGroup] and [GPURenderPipeline]. It provides a way to bind specific texture views to shaders, enabling advanced rendering techniques.
 * 
 * This interface inherits from `GPUBindingResource` and `GPUObjectBase`, which means it can be used as a binding resource in various GPU operations. Additionally, it implements the `AutoCloseable` interface, allowing for proper resource management and cleanup.
 * 
 * **See also:**
 * - [WebGPU Specification: GPUTextureView](https://www.w3.org/TR/webgpu/#gputextureview)
 * 
 */
interface GPUTextureView : GPUBindingResource, GPUObjectBase, AutoCloseable

/**
 * The `GPUBufferBinding` interface describes a buffer and an optional range to bind as a resource. This is used in the context of WebGPU to specify how buffers should be bound for shader access.
 * 
 * For more details, refer to the [WebGPU specification](https://www.w3.org/TR/webgpu/#dictdef-gpubufferbinding).
 * 
 */
interface GPUBufferBinding : GPUBindingResource {
	val buffer: GPUBuffer
	val offset: GPUSize64
	val size: GPUSize64?
}


/**
 * Represents a color in the RGBA format, which can be either a sequence of four `Double` values or a [GPUColorDict]. This interface provides access to the red, green, blue, and alpha channel values.
 * 
 * For more details, refer to the [WebGPU specification](https://www.w3.org/TR/webgpu/#gpucolor).
 * 
 */
interface GPUColor {
	val r: Double
	val g: Double
	val b: Double
	val a: Double
}


/**
 * Represents a 2D origin point in GPU coordinates. This interface can be used to specify the starting point for various GPU operations, such as texture sampling or buffer updates.
 * 
 * The `GPUOrigin2D` type is defined as either a sequence of two values or a dictionary with `x` and `y` properties. This allows for flexible initialization and usage in different contexts.
 * 
 * For more details, refer to the [WebGPU specification](https://www.w3.org/TR/webgpu/#gpuorigin2d).
 * 
 */
interface GPUOrigin2D {
	val x: GPUIntegerCoordinate
	val y: GPUIntegerCoordinate
}


/**
 * Represents a 3D origin point in GPU coordinates. This interface can be used to specify the starting point for various GPU operations, such as texture sampling or buffer updates.
 * 
 * The `GPUOrigin3D` type can be either a sequence of three [GPUIntegerCoordinate] values or an instance of [GPUOrigin3DDict]. When accessed, the properties `x`, `y`, and `z` will refer to the corresponding values in the sequence or dictionary.
 * 
 * For more details, see the [WebGPU specification](https://www.w3.org/TR/webgpu/#gpuorigin3d).
 * 
 */
interface GPUOrigin3D {
	val x: GPUIntegerCoordinate
	val y: GPUIntegerCoordinate
	val z: GPUIntegerCoordinate
}


/**
 * Represents a 3-dimensional extent, which defines the size of a texture or other GPU resources. This interface can be used to specify dimensions in three axes: width, height, and depth or array layers.
 * 
 * For more details, refer to the [WebGPU specification](https://www.w3.org/TR/webgpu/#gpuextent3d).
 * 
 * @see [GPUExtent3DDict](https://www.w3.org/TR/webgpu/#dictdef-gpuextent3ddict)
 * 
 */
interface GPUExtent3D {
	val width: GPUIntegerCoordinate
	val height: GPUIntegerCoordinate
	val depthOrArrayLayers: GPUIntegerCoordinate
}


/**
 * The `GPUObjectBase` interface is a mixin that provides a common base for all WebGPU objects. It includes properties such as a label, which can be used to identify the object in debugging and error messages.
 * 
 * This interface is fundamental to the WebGPU API as it ensures that all WebGPU objects share a consistent set of properties and behaviors. The `label` property allows developers to assign meaningful names to their WebGPU objects, making it easier to debug and manage them.
 * 
 * For more details, refer to the [WebGPU specification](https://www.w3.org/TR/webgpu/#gpuobjectbase).
 * 
 */
interface GPUObjectBase {
	var label: String
}


/**
 * The `GPUSupportedLimits` interface provides access to the supported limits of a GPU adapter or device.
 * These limits define the maximum capabilities and constraints for various resources and operations,
 * such as texture dimensions, bind groups, buffers, and shader stages. This information is crucial
 * for optimizing performance and ensuring compatibility with the underlying hardware.
 * 
 * For more details, refer to the [W3C WebGPU specification](https://www.w3.org/TR/webgpu/#gpusupportedlimits).
 * 
 */
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


/**
 * The `GPUAdapterInfo` interface exposes various identifying information about an adapter. None of the members in `GPUAdapterInfo` are guaranteed to be populated with any particular value; if no value is provided, the attribute will return the empty string " ". It is at the user agent’s discretion which values to reveal, and it is likely that on some devices none of the values will be populated. As such, applications **must** be able to handle any possible `GPUAdapterInfo` values, including the absence of those values.
 * 
 * The `GPUAdapterInfo` for an adapter is exposed via [GPUAdapter.info](https://www.w3.org/TR/webgpu/#dom-gpuadapter-info) and [GPUDevice.adapterInfo](https://www.w3.org/TR/webgpu/#dom-gpudevice-adapterinfo). This info is immutable: for a given adapter, each `GPUAdapterInfo` attribute will return the same value every time it’s accessed.
 * 
 * **Note:** Though the `GPUAdapterInfo` attributes are immutable *once accessed*, an implementation may delay the decision on what to expose for each attribute until the first time it is accessed.
 * 
 */
interface GPUAdapterInfo {
	val vendor: String
	val architecture: String
	val device: String
	val description: String
	val subgroupMinSize: UInt
	val subgroupMaxSize: UInt
	val isFallbackAdapter: Boolean
}


/**
 * The `GPUAdapter` interface encapsulates a GPU adapter and describes its capabilities, including supported features and limits. This interface is essential for interacting with the underlying GPU hardware and obtaining a `GPUDevice` to perform rendering operations.
 * 
 * To obtain a `GPUAdapter`, use the `requestAdapter()` method provided by the `GPU` object. The `GPUAdapter` provides read-only access to its features, limits, and information about the adapter.
 * 
 * For more details, refer to the [WebGPU specification](https://www.w3.org/TR/webgpu/#gpuadapter).
 * 
 */
interface GPUAdapter : AutoCloseable {
	val features: GPUSupportedFeatures
	val limits: GPUSupportedLimits
	val info: GPUAdapterInfo
	suspend fun requestDevice(descriptor: GPUDeviceDescriptor? = null): Result<GPUDevice>
}


/**
 * The `GPUDevice` interface encapsulates a GPU device and exposes the functionality of that device. It is the top-level interface through which WebGPU interfaces are created.
 * 
 * To obtain a `GPUDevice`, use the `requestDevice()` method on a `GPUAdapter`.
 * 
 * **See also:**
 * - [WebGPU Specification: GPUDevice](https://www.w3.org/TR/webgpu/#gpudevice)
 * 
 */
interface GPUDevice : GPUObjectBase, AutoCloseable {
	val features: GPUSupportedFeatures
	val limits: GPUSupportedLimits
	val adapterInfo: GPUAdapterInfo
	val queue: GPUQueue
	fun createBuffer(descriptor: GPUBufferDescriptor): GPUBuffer
	fun createTexture(descriptor: GPUTextureDescriptor): GPUTexture
	fun createSampler(descriptor: GPUSamplerDescriptor? = null): GPUSampler
	fun createBindGroupLayout(descriptor: GPUBindGroupLayoutDescriptor): GPUBindGroupLayout
	fun createPipelineLayout(descriptor: GPUPipelineLayoutDescriptor): GPUPipelineLayout
	fun createBindGroup(descriptor: GPUBindGroupDescriptor): GPUBindGroup
	fun createShaderModule(descriptor: GPUShaderModuleDescriptor): GPUShaderModule
	fun createComputePipeline(descriptor: GPUComputePipelineDescriptor): GPUComputePipeline
	fun createRenderPipeline(descriptor: GPURenderPipelineDescriptor): GPURenderPipeline
	suspend fun createComputePipelineAsync(descriptor: GPUComputePipelineDescriptor): Result<GPUComputePipeline>
	suspend fun createRenderPipelineAsync(descriptor: GPURenderPipelineDescriptor): Result<GPURenderPipeline>
	fun createCommandEncoder(descriptor: GPUCommandEncoderDescriptor? = null): GPUCommandEncoder
	fun createRenderBundleEncoder(descriptor: GPURenderBundleEncoderDescriptor): GPURenderBundleEncoder
	fun createQuerySet(descriptor: GPUQuerySetDescriptor): GPUQuerySet
	fun pushErrorScope(filter: GPUErrorFilter)
	suspend fun popErrorScope(): Result<GPUError?>
}


/**
 * The `GPUBuffer` interface represents a block of memory that can be used in GPU operations. Data is stored in linear layout, meaning each byte of the allocation can be addressed by its offset from the start of the buffer, subject to alignment restrictions depending on the operation. Some buffers can be mapped, making the block of memory accessible via an `ArrayBuffer` called its mapping.
 * 
 * Buffers are created via [GPUDevice.createBuffer()](https://www.w3.org/TR/webgpu/#dom-gpudevice-createbuffer). Buffers may be [mappedAtCreation](https://www.w3.org/TR/webgpu/#dom-gpubufferdescriptor-mappedatcreation).
 * 
 * Refer to the [WebGPU specification](https://www.w3.org/TR/webgpu/#gpubuffer) for more details.
 * 
 */
interface GPUBuffer : GPUObjectBase, AutoCloseable {
	val size: GPUSize64Out
	val usage: GPUBufferUsageFlags
	val mapState: GPUBufferMapState
	suspend fun mapAsync(mode: GPUMapModeFlags, offset: GPUSize64 = 0u, size: GPUSize64? = null): Result<Unit>
	fun getMappedRange(offset: GPUSize64 = 0u, size: GPUSize64? = null): ArrayBuffer
	fun unmap()
}


/**
 * Represents a texture in the WebGPU API. A texture is composed of 1D, 2D, or 3D arrays of data that can contain multiple values per element to represent things like colors.
 * Textures can be read and written in various ways depending on their usage flags. They are often stored in GPU memory with a layout optimized for multidimensional access.
 * 
 * For more details, refer to the [WebGPU specification](https://www.w3.org/TR/webgpu/#texture-interface).
 * 
 */
interface GPUTexture : GPUObjectBase, AutoCloseable {
	val width: GPUIntegerCoordinateOut
	val height: GPUIntegerCoordinateOut
	val depthOrArrayLayers: GPUIntegerCoordinateOut
	val mipLevelCount: GPUIntegerCoordinateOut
	val sampleCount: GPUSize32Out
	val dimension: GPUTextureDimension
	val format: GPUTextureFormat
	val usage: GPUTextureUsageFlags
	fun createView(descriptor: GPUTextureViewDescriptor? = null): GPUTextureView
}


/**
 * The `GPUBindGroupLayout` interface defines the structure that specifies how resources are bound in a [GPUBindGroup] and made accessible to shader stages. This layout is crucial for organizing and managing bindings efficiently within the WebGPU pipeline.
 * 
 * **Inheritance:**
 * - Implements `AutoCloseable`
 * - Inherits from [GPUObjectBase]
 * 
 * **Notes:**
 * - The `GPUBindGroupLayout` is immutable once created.
 * - It must be properly closed to free up resources when no longer needed.
 * 
 * **See Also:**
 * - [WebGPU Specification: GPUBindGroupLayout](https://www.w3.org/TR/webgpu/#gpubindgrouplayout)
 * 
 */
interface GPUBindGroupLayout : GPUObjectBase, AutoCloseable

/**
 * A `GPUBindGroup` defines a set of resources to be bound together in a group and specifies how these resources are used in shader stages. This interface is essential for managing the binding of buffers, textures, samplers, and other resources that shaders need during rendering.
 * 
 * The `GPUBindGroup` interface extends [GPUObjectBase], which provides common functionality for GPU objects such as reference counting and lifecycle management. It also implements `AutoCloseable`, allowing for proper resource cleanup when the bind group is no longer needed.
 * 
 * For more details, refer to the [WebGPU specification section on GPUBindGroup](https://www.w3.org/TR/webgpu/#gpubindgroup).
 * 
 * **See Also:**
 * - [GPUObjectBase]
 * 
 */
interface GPUBindGroup : GPUObjectBase, AutoCloseable

/**
 * A [GPUPipelineLayout](https://www.w3.org/TR/webgpu/#gpupipelinelayout) defines the mapping between resources of all [GPUBindGroup](https://www.w3.org/TR/webgpu/#gpubindgroup) objects set up during command encoding in [setBindGroup()](https://www.w3.org/TR/webgpu/#dom-gpubindingcommandsmixin-setbindgroup), and the shaders of the pipeline set by [GPURenderCommandsMixin.setPipeline] or [GPUComputePassEncoder.setPipeline].
 * 
 * This interface extends [GPUObjectBase] and implements [AutoCloseable], allowing for proper resource management.
 * 
 * **See Also:**
 * - [GPUObjectBase]
 * - [AutoCloseable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-auto-closeable/)
 * 
 */
interface GPUPipelineLayout : GPUObjectBase, AutoCloseable

/**
 * The `GPUShaderModule` interface represents a reference to an internal shader module object in the WebGPU API. This interface is used to manage and interact with shader modules, which contain the compiled code for shaders.
 * 
 * A shader module is created from shader source code or precompiled binary data and can be used to create pipeline objects that define how rendering operations are performed.
 * 
 * For more details, refer to the [WebGPU specification on GPUShaderModule](https://www.w3.org/TR/webgpu/#shader-module).
 * 
 * **Inheritance:**
 * - `GPUObjectBase`
 * - `AutoCloseable`
 * 
 */
interface GPUShaderModule : GPUObjectBase, AutoCloseable {
	suspend fun getCompilationInfo(): Result<GPUCompilationInfo>
}


/**
 * The `GPUCompilationMessage` interface represents an informational, warning, or error message generated by the [GPUShaderModule] compiler. These messages are designed to be human-readable and assist developers in diagnosing issues with their shader code. Each message can correspond to a specific point in the shader code, a substring of the shader code, or may not correspond to any specific point at all.
 * 
 * See also: [W3C Specification](https://www.w3.org/TR/webgpu/#dom-gpucompilationmessage)
 * 
 */
interface GPUCompilationMessage {
	val message: String
	val type: GPUCompilationMessageType
	val lineNum: ULong
	val linePos: ULong
	val offset: ULong
	val length: ULong
}


/**
 * Represents the compilation information for a GPU shader module. This interface provides access to messages generated during the compilation process, which can be useful for debugging and optimization.
 * 
 * For more details, refer to the [WebGPU specification](https://www.w3.org/TR/webgpu/).
 * 
 */
interface GPUCompilationInfo {
	val messages: List<GPUCompilationMessage>
}


/**
 * The `GPUPipelineBase` interface represents the base class for GPU pipelines in WebGPU. It provides a method to retrieve bind group layouts, which are essential for configuring resources used by shaders.
 * 
 * This interface is part of the WebGPU API and is designed to be implemented by specific pipeline types such as compute pipelines or render pipelines.
 * 
 */
interface GPUPipelineBase {
	fun getBindGroupLayout(index: UInt): GPUBindGroupLayout
}


/**
 * A [GPUComputePipeline](https://www.w3.org/TR/webgpu/#gpucomputepipeline) is a specialized type of pipeline that controls the compute shader stage. It is used within a [GPUComputePassEncoder](https://www.w3.org/TR/webgpu/#gpucomputepassencoder) to execute compute shaders, which are essential for general-purpose computations on the GPU.
 * 
 * This interface extends [GPUObjectBase], [GPUPipelineBase], and implements [AutoCloseable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.io/-auto-closeable/). It provides methods to manage the lifecycle of compute pipelines, ensuring that resources are properly released when they are no longer needed.
 * 
 * **Important Notes:**
 * - Ensure that the `pipelineDescriptor` is correctly configured with the necessary shader module and other parameters.
 * - Properly manage the lifecycle of the pipeline by closing it when it is no longer needed to avoid memory leaks.
 * 
 * **See Also:**
 * - [GPUComputePassEncoder](https://www.w3.org/TR/webgpu/#gpucomputepassencoder)
 * - [GPUPipelineBase]
 * 
 */
interface GPUComputePipeline : GPUObjectBase, GPUPipelineBase, AutoCloseable

/**
 * A [GPURenderPipeline](https://www.w3.org/TR/webgpu/#gpurenderpipeline) is a type of pipeline that controls the vertex and fragment shader stages. It can be used in both [GPURenderPassEncoder] and [GPURenderBundleEncoder].
 * 
 * This interface extends [GPUObjectBase](https://www.w3.org/TR/webgpu/#gpuobjectbase), [GPUPipelineBase](https://www.w3.org/TR/webgpu/#gpupipelinebase), and implements [AutoCloseable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-auto-closeable/) to manage resource lifecycle.
 * 
 * ### Render Pipeline Inputs
 * - **Bindings**: According to the given [GPUPipelineLayout].
 * - **Vertex and Index Buffers**: Described by [GPUVertexState](https://www.w3.org/TR/webgpu/#dictdef-gpuvertexstate).
 * - **Color Attachments**: Described by [GPUColorTargetState](https://www.w3.org/TR/webgpu/#dictdef-gpucolortargetstate).
 * - **Depth-Stencil Attachment** (optional): Described by [GPUDepthStencilState](https://www.w3.org/TR/webgpu/#dictdef-gpudepthstencilstate).
 * 
 */
interface GPURenderPipeline : GPUObjectBase, GPUPipelineBase, AutoCloseable

/**
 * The `GPUCommandBuffer` interface represents a command buffer in the WebGPU API. It is used to encapsulate a list of GPU commands that can be executed on the [Queue timeline](https://www.w3.org/TR/webgpu/#queue-timeline).
 * 
 * This interface inherits from [`GPUObjectBase`](https://www.w3.org/TR/webgpu/#gpuobjectbase) and implements `AutoCloseable`, allowing for proper resource management.
 * 
 * ### Device Timeline Properties
 * 
 * - **[[command_list]]**: A read-only list of [GPU commands](https://www.w3.org/TR/webgpu/#gpu-command) to be executed when this command buffer is submitted. This property is essential for managing the sequence of operations that will be performed on the GPU.
 * 
 * - **[[renderState]]**: The current state used by any render pass commands being executed. Initially, this property is `null`, but it can be set to a valid [RenderState](https://www.w3.org/TR/webgpu/#renderstate) during the execution of render passes.
 * 
 * In this example, a `GPUCommandBuffer` is created using the `device.createCommandBuffer()` method. Commands are added to the command buffer within a render pass, and finally, the command buffer is submitted to the queue for execution.
 * 
 * ### Notes
 * 
 * - The `GPUCommandBuffer` interface is designed to be used in conjunction with other WebGPU interfaces such as [`GPUDevice`](https://www.w3.org/TR/webgpu/#gpudevice) and [`GPUQueue`](https://www.w3.org/TR/webgpu/#gpuqueue).
 * - Proper management of command buffers is crucial for efficient GPU resource utilization. Always ensure that command buffers are properly closed after use to avoid memory leaks.
 * 
 */
interface GPUCommandBuffer : GPUObjectBase, AutoCloseable

/**
 * The `GPUCommandsMixin` interface defines state common to all interfaces which encode commands. This mixin does not include any methods but serves as a base for other command-encoding interfaces in the WebGPU API.
 * 
 * **Context and Purpose:**
 * This interface is part of the WebGPU specification and is used to provide a consistent way to manage command encoding across different GPU-related interfaces. It ensures that all command-encoding interfaces share a common set of properties or behaviors, even if it does not define any methods itself.
 * 
 * **References:**
 * - [WebGPU Specification: GPUCommandsMixin](https://www.w3.org/TR/webgpu/#gpucommandsmixin)
 * 
 */
interface GPUCommandsMixin

/**
 * The `GPUCommandEncoder` interface represents a command encoder that allows the creation of command buffers for rendering and compute operations. It is part of the WebGPU API, which provides low-level access to GPU capabilities.
 * 
 * This interface includes methods for beginning render and compute passes, copying data between buffers and textures, clearing buffers, resolving query sets, and finishing command encoding.
 * 
 * For more details, refer to the [WebGPU specification](https://www.w3.org/TR/webgpu/#command-encoder).
 * 
 * **Included Interfaces:**
 * - `GPUObjectBase`: Provides base functionality for GPU objects.
 * - `GPUCommandsMixin`: Mixin interface for common GPU commands.
 * - `GPUDebugCommandsMixin`: Mixin interface for debug-related GPU commands.
 * 
 * **See Also:**
 * - [GPURenderPassEncoder](https://www.w3.org/TR/webgpu/#gpurenderpassencoder)
 * - [GPUComputePassEncoder](https://www.w3.org/TR/webgpu/#gpucomputepassencoder)
 * 
 */
interface GPUCommandEncoder : GPUObjectBase, GPUCommandsMixin, GPUDebugCommandsMixin, AutoCloseable {
	fun beginRenderPass(descriptor: GPURenderPassDescriptor): GPURenderPassEncoder
	fun beginComputePass(descriptor: GPUComputePassDescriptor? = null): GPUComputePassEncoder
	fun copyBufferToBuffer(source: GPUBuffer, sourceOffset: GPUSize64, destination: GPUBuffer, destinationOffset: GPUSize64, size: GPUSize64? = null)
	fun copyBufferToTexture(source: GPUTexelCopyBufferInfo, destination: GPUTexelCopyTextureInfo, copySize: GPUExtent3D)
	fun copyTextureToBuffer(source: GPUTexelCopyTextureInfo, destination: GPUTexelCopyBufferInfo, copySize: GPUExtent3D)
	fun copyTextureToTexture(source: GPUTexelCopyTextureInfo, destination: GPUTexelCopyTextureInfo, copySize: GPUExtent3D)
	fun clearBuffer(buffer: GPUBuffer, offset: GPUSize64 = 0u, size: GPUSize64? = null)
	fun resolveQuerySet(querySet: GPUQuerySet, firstQuery: GPUSize32, queryCount: GPUSize32, destination: GPUBuffer, destinationOffset: GPUSize64)
	fun finish(descriptor: GPUCommandBufferDescriptor? = null): GPUCommandBuffer
}


/**
 * The `GPUBindingCommandsMixin` interface extends the functionality of GPU command objects by providing methods to set bind groups. This mixin assumes the presence of `GPUObjectBase` and `GPUCommandsMixin` members on the same object.
 * 
 * It includes device timeline properties for managing bind groups and dynamic offsets, which are essential for configuring the rendering pipeline in WebGPU.
 * 
 * For more details, refer to the [WebGPU specification](https://www.w3.org/TR/webgpu/#gpubindingcommandsmixin).
 * 
 */
interface GPUBindingCommandsMixin {
	fun setBindGroup(index: GPUIndex32, bindGroup: GPUBindGroup?, dynamicOffsetsData: List<UInt> = emptyList())
}


/**
 * The `GPUDebugCommandsMixin` interface provides methods to apply debug labels to groups of commands or insert a single label into the command sequence. This is useful for debugging and profiling purposes, allowing developers to create a hierarchy of labeled commands that can be visualized in browser developer tools.
 * 
 * Debug groups can be nested to create a hierarchy of labeled commands. These groups must be well-balanced, meaning every `pushDebugGroup` call must have a corresponding `popDebugGroup` call. Like [object labels](https://www.w3.org/TR/webgpu/#dom-gpuobjectbase-label), these labels have no required behavior but may be shown in error messages and browser developer tools, and may be passed to native API backends.
 * 
 * This interface assumes the presence of `GPUObjectBase` and `GPUCommandsMixin` members on the same object. It must only be included by interfaces which also include those mixins.
 * 
 */
interface GPUDebugCommandsMixin {
	fun pushDebugGroup(groupLabel: String)
	fun popDebugGroup()
	fun insertDebugMarker(markerLabel: String)
}


/**
 * The `GPUComputePassEncoder` interface represents a compute pass encoder, which is used to encode commands for a compute pass. This interface allows you to set the pipeline, dispatch workgroups, and end the compute pass.
 * 
 * A compute pass encoder is created by a [GPUCommandEncoder] and is used to record commands that will be executed on the GPU. The primary purpose of this interface is to manage the execution of compute shaders, which are used for general-purpose computations on the GPU.
 * 
 * **Inherited from:**
 * - [GPUObjectBase]
 * - [GPUCommandsMixin]
 * - [GPUDebugCommandsMixin]
 * - [GPUBindingCommandsMixin]
 * 
 * **Device Timeline Properties:**
 * - `[[command_encoder]]`: The GPUCommandEncoder that created this compute pass encoder.
 * - `[[endTimestampWrite]]`: A GPU command, if any, writing a timestamp when the pass ends. Defaults to null.
 * - `[[pipeline]]`: The current [GPUComputePipeline]. Initially null.
 * 
 */
interface GPUComputePassEncoder : GPUObjectBase, GPUCommandsMixin, GPUDebugCommandsMixin, GPUBindingCommandsMixin {
	fun setPipeline(pipeline: GPUComputePipeline)
	fun dispatchWorkgroups(workgroupCountX: GPUSize32, workgroupCountY: GPUSize32 = 1u, workgroupCountZ: GPUSize32 = 1u)
	fun dispatchWorkgroupsIndirect(indirectBuffer: GPUBuffer, indirectOffset: GPUSize64)
	fun end()
}


/**
 * The `GPURenderPassEncoder` interface represents a render pass encoder, which is used to encode commands into a render pass. This interface allows for the configuration of various rendering states and the execution of draw calls within a render pass.
 * 
 * A render pass encoder is created by a [GPUCommandEncoder] and is used to record commands that will be executed on the GPU. The `GPURenderPassEncoder` interface includes methods for setting viewport, scissor rectangle, blend constant, stencil reference, beginning and ending occlusion queries, executing render bundles, and ending the render pass.
 * 
 * For more details, refer to the [WebGPU specification](https://www.w3.org/TR/webgpu/#gpurenderpassencoder).
 * 
 */
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


/**
 * The `GPURenderCommandsMixin` interface defines rendering commands that are common to both [GPURenderPassEncoder](https://www.w3.org/TR/webgpu/#gpurenderpassencoder) and [GPURenderBundleEncoder](https://www.w3.org/TR/webgpu/#gpurenderbundleencoder). This mixin is used to encapsulate the rendering commands that can be executed within a render pass or bundle.
 * 
 * The `GPURenderCommandsMixin` assumes the presence of members from [GPUObjectBase](https://www.w3.org/TR/webgpu/#gpuobjectbase), [GPUCommandsMixin](https://www.w3.org/TR/webgpu/#gpucommandsmixin), and [GPUBindingCommandsMixin](https://www.w3.org/TR/webgpu/#gpubindingcommandsmixin) on the same object. It must only be included by interfaces that also include those mixins.
 * 
 */
interface GPURenderCommandsMixin {
	fun setPipeline(pipeline: GPURenderPipeline)
	fun setIndexBuffer(buffer: GPUBuffer, indexFormat: GPUIndexFormat, offset: GPUSize64 = 0u, size: GPUSize64? = null)
	fun setVertexBuffer(slot: GPUIndex32, buffer: GPUBuffer?, offset: GPUSize64 = 0u, size: GPUSize64? = null)
	fun draw(vertexCount: GPUSize32, instanceCount: GPUSize32 = 1u, firstVertex: GPUSize32 = 0u, firstInstance: GPUSize32 = 0u)
	fun drawIndexed(indexCount: GPUSize32, instanceCount: GPUSize32 = 1u, firstIndex: GPUSize32 = 0u, baseVertex: GPUSignedOffset32 = 0, firstInstance: GPUSize32 = 0u)
	fun drawIndirect(indirectBuffer: GPUBuffer, indirectOffset: GPUSize64)
	fun drawIndexedIndirect(indirectBuffer: GPUBuffer, indirectOffset: GPUSize64)
}


/**
 * /**
 *  * Represents a render bundle in the WebGPU API. A `GPURenderBundle` encapsulates a list of GPU commands that can be executed by a [GPURenderPassEncoder](https://www.w3.org/TR/webgpu/#dom-gpurenderpassencoder).
 *  * 
 *  * This interface is part of the WebGPU API, which provides a low-level, cross-platform graphics API for the web. For more details, refer to the [official W3C specification](https://www.w3.org/TR/webgpu/#gpurenderbundle).
 *  * 
 *  * @see [GPUObjectBase](https://www.w3.org/TR/webgpu/#dom-gpuobjectbase)
 *  */
 * interface GPURenderBundle : GPUObjectBase
 * 
 */
interface GPURenderBundle : GPUObjectBase

/**
 * The `GPURenderBundleEncoder` interface represents an encoder for creating render bundles in WebGPU. A render bundle is a collection of rendering commands that can be executed multiple times with different parameters, improving performance by reducing the overhead of command encoding.
 * 
 * This interface inherits from several mixins and interfaces:
 * - [GPUObjectBase]: Provides basic object properties such as `label`.
 * - [GPUCommandsMixin]: Mixin for common GPU commands.
 * - [GPUDebugCommandsMixin]: Mixin for debug-related commands.
 * - [GPUBindingCommandsMixin]: Mixin for binding-related commands.
 * - [GPURenderCommandsMixin]: Mixin for render-related commands.
 * - [AutoCloseable]: Ensures that resources are closed properly.
 * 
 * For more details, refer to the [WebGPU specification](https://www.w3.org/TR/webgpu/).
 * 
 */
interface GPURenderBundleEncoder : GPUObjectBase, GPUCommandsMixin, GPUDebugCommandsMixin, GPUBindingCommandsMixin, GPURenderCommandsMixin, AutoCloseable {
	fun finish(descriptor: GPURenderBundleDescriptor? = null): GPURenderBundle
}


/**
 * The `GPUQueue` interface represents a queue that allows for the submission of command buffers and other operations related to GPU execution. It is part of the WebGPU API, providing a way to manage and execute commands on the GPU.
 * 
 * This interface inherits from [GPUObjectBase](https://www.w3.org/TR/webgpu/#gpuobjectbase), which provides basic object management functionality.
 * 
 * For more details, refer to the [WebGPU specification](https://www.w3.org/TR/webgpu/#gpuqueue).
 * 
 */
interface GPUQueue : GPUObjectBase {
	fun submit(commandBuffers: List<GPUCommandBuffer>)
	suspend fun onSubmittedWorkDone(): Result<Unit>
	fun writeBuffer(buffer: GPUBuffer, bufferOffset: GPUSize64, data: ArrayBuffer, dataOffset: GPUSize64 = 0u, size: GPUSize64? = null)
	fun writeTexture(destination: GPUTexelCopyTextureInfo, data: ArrayBuffer, dataLayout: GPUTexelCopyBufferLayout, size: GPUExtent3D)
}


/**
 * Represents a set of queries in the WebGPU API. A `GPUQuerySet` is used to manage and retrieve information about GPU operations such as occlusion queries, pipeline statistics queries, etc.
 * 
 * This interface inherits from [GPUObjectBase](https://www.w3.org/TR/webgpu/#gpuobjectbase) and implements [AutoCloseable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-auto-closeable/) to ensure proper resource management. The `destroy` method must be called to release the resources associated with this query set.
 * 
 * For more details, refer to the [WebGPU specification on GPUQuerySet](https://www.w3.org/TR/webgpu/#gpuqueryset).
 * 
 */
interface GPUQuerySet : GPUObjectBase, AutoCloseable {
	val type: GPUQueryType
	val count: GPUSize32Out
}


/**
 * Represents information about why a [GPUDevice](https://www.w3.org/TR/webgpu/#gpudevice) was lost. This interface provides details that can help developers understand the cause of device loss and take appropriate actions.
 * 
 * **See also:**
 * - [WebGPU Specification: GPUDeviceLostInfo](https://www.w3.org/TR/webgpu/#gpudevicelostinfo)
 * 
 */
interface GPUDeviceLostInfo {
	val reason: GPUDeviceLostReason
	val message: String
}


/**
 * The `GPUError` interface represents the base class for all errors that can be surfaced from WebGPU operations. This includes errors returned by [popErrorScope()] and those triggered by the `uncapturederror` event.
 * 
 * **Context:** Errors are generated under specific conditions defined in the respective algorithms of WebGPU operations. No errors are generated from a lost device. For more details, refer to the [Errors & Debugging section] of the WebGPU specification.
 * 
 * **Note:** Future versions of this specification may introduce new subtypes of `GPUError`. Applications should handle this possibility by using the error's `message` property when possible and specializing using `instanceof`. Use `error.constructor.name` for serialization purposes, such as generating debug reports.
 * 
 */
interface GPUError {
	val message: String
}


/**
 * A subtype of [GPUError] that indicates an operation did not satisfy all validation requirements. Validation errors are always indicative of an application error and are expected to fail the same way across all devices, assuming the same [[features]] and [[limits]] are in use.
 * 
 * For more details, refer to the [W3C WebGPU specification](https://www.w3.org/TR/webgpu/#gpuvalidationerror).
 * 
 * In this example, if an operation on the `GPUDevice` fails due to a validation error, it will be caught by the `catch` block, and the error message will be printed.
 * 
 */
interface GPUValidationError : GPUError

/**
 * Represents a subtype of GPUError that indicates an out-of-memory condition. This error occurs when there is insufficient free memory to complete the requested operation.
 * 
 * The operation may succeed if attempted again with a lower memory requirement (e.g., using smaller texture dimensions), or if memory used by other resources is released first.
 * 
 * **See Also:**
 * - [GPUError](https://www.w3.org/TR/webgpu/#gpuerror)
 * 
 */
interface GPUOutOfMemoryError : GPUError

/**
 * A subtype of GPUError that indicates an operation failed for a system or implementation-specific reason, even when all validation requirements have been satisfied. This error may occur if the operation exceeds the capabilities of the implementation in ways not easily captured by the supported limits.
 * 
 * For example, the same operation might succeed on other devices or under different circumstances.
 * 
 * **See also:**
 * - GPUError
 * - [Supported Limits](https://www.w3.org/TR/webgpu/#supported-limits)
 * 
 * **Related Operations:**
 * - [Generate an Internal Error](https://www.w3.org/TR/webgpu/#generate-an-internal-error)
 * 
 */
interface GPUInternalError : GPUError

/**
 * Represents the base descriptor for GPU objects. This interface is used to provide a common structure for labeling GPU objects, which can be helpful for debugging and identification purposes.
 * 
 * For more details, refer to the [W3C WebGPU specification](https://www.w3.org/TR/webgpu/#dictdef-gpuobjectdescriptorbase).
 * 
 */
interface GPUObjectDescriptorBase {
	val label: String
}


/**
 * The `GPURequestAdapterOptions` interface provides hints to the user agent indicating what configuration is suitable for the application. This interface allows developers to specify preferences and constraints for the GPU adapter selection process.
 * 
 * For more details, refer to the [W3C WebGPU specification](https://www.w3.org/TR/webgpu/#dictdef-gpurequestadapteroptions).
 * 
 */
interface GPURequestAdapterOptions {
	val featureLevel: String
	val powerPreference: GPUPowerPreference?
	val forceFallbackAdapter: Boolean
	val xrCompatible: Boolean
}


/**
 * The `GPUDeviceDescriptor` interface describes a device request. It specifies the features, limits, and default queue descriptor required by the GPU device.
 * 
 * This interface is used to configure the creation of a [GPUDevice] object, which represents a GPU adapter and provides methods for creating GPU resources such as buffers, textures, and pipelines.
 * 
 * For more details, refer to the [WebGPU specification on `GPUDeviceDescriptor`](https://www.w3.org/TR/webgpu/#gpudevicedescriptor).
 * 
 */
interface GPUDeviceDescriptor : GPUObjectDescriptorBase {
	val requiredFeatures: List<GPUFeatureName>
	val requiredLimits: GPUSupportedLimits?
	val defaultQueue: GPUQueueDescriptor
}


/**
 * Represents a descriptor for creating GPU buffers. This interface extends [GPUObjectDescriptorBase](https://www.w3.org/TR/webgpu/#gpuobjectdescriptorbase) and is used to specify the properties of a buffer, such as its size, usage flags, and whether it should be mapped at creation.
 * 
 * For more details, refer to the [WebGPU specification on GPUBufferDescriptor](https://www.w3.org/TR/webgpu/#gpubufferdescriptor).
 * 
 */
interface GPUBufferDescriptor : GPUObjectDescriptorBase {
	val size: GPUSize64
	val usage: GPUBufferUsageFlags
	val mappedAtCreation: Boolean
}


/**
 * Represents a descriptor for creating GPU textures. This interface extends [GPUObjectDescriptorBase](https://www.w3.org/TR/webgpu/#gpuobjectdescriptorbase) and defines the properties required to specify the characteristics of a texture.
 * 
 * For more details, refer to the [WebGPU specification on GPUTextureDescriptor](https://www.w3.org/TR/webgpu/#gputexturedescriptor).
 * 
 */
interface GPUTextureDescriptor : GPUObjectDescriptorBase {
	val size: GPUExtent3D
	val mipLevelCount: GPUIntegerCoordinate
	val sampleCount: GPUSize32
	val dimension: GPUTextureDimension
	val format: GPUTextureFormat
	val usage: GPUTextureUsageFlags
	val viewFormats: List<GPUTextureFormat>
}


/**
 * The `GPUTextureViewDescriptor` interface defines a set of properties that describe how to create a view on a texture. This descriptor is used when creating a [GPUTextureView](https://www.w3.org/TR/webgpu/#gputextureview) object, which represents a specific way to access the data in a texture.
 * 
 * A texture view allows for different formats, dimensions, and usages of the underlying texture data. This is particularly useful for scenarios where you need to access the same texture data in multiple ways without duplicating the actual texture data.
 * 
 */
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


/**
 * The `GPUSamplerDescriptor` interface defines the properties of a sampler used in WebGPU. This descriptor specifies how textures are sampled during rendering, including addressing modes, filtering modes, and level-of-detail (LOD) clamping. It is part of the GPUObjectDescriptorBase hierarchy and is used to create `GPUSampler` objects.
 * 
 * For more details, refer to the [WebGPU specification on GPUSamplerDescriptor](https://www.w3.org/TR/webgpu/#dictdef-gpusamplerdescriptor).
 * 
 */
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


/**
 * Represents a descriptor for creating a GPUBindGroupLayout. This interface extends GPUObjectDescriptorBase and is used to define the layout of bind groups in WebGPU.
 * 
 * A `GPUBindGroupLayoutDescriptor` specifies a list of entries that describe shader resource bindings. Each entry defines how resources are bound to shaders, including buffers, samplers, textures, and external textures.
 * 
 */
interface GPUBindGroupLayoutDescriptor : GPUObjectDescriptorBase {
	val entries: List<GPUBindGroupLayoutEntry>
}


/**
 * Represents a binding layout entry for a GPU bind group. This interface defines the structure of individual bindings within a [GPUBindGroupLayout](https://www.w3.org/TR/webgpu/#gpubindgrouplayout).
 * 
 * A `GPUBindGroupLayoutEntry` specifies how resources are bound to shader stages, including buffers, samplers, textures, and storage textures. Only one type of binding (buffer, sampler, texture, storageTexture) can be defined for any given entry.
 * 
 * **See also:**
 * - [WebGPU Specification: GPUBindGroupLayoutEntry](https://www.w3.org/TR/webgpu/#dictdef-gpubindgrouplayoutentry)
 * 
 */
interface GPUBindGroupLayoutEntry {
	val binding: GPUIndex32
	val visibility: GPUShaderStageFlags
	val buffer: GPUBufferBindingLayout?
	val sampler: GPUSamplerBindingLayout?
	val texture: GPUTextureBindingLayout?
	val storageTexture: GPUStorageTextureBindingLayout?
}


/**
 * Represents a layout for buffer bindings in WebGPU. This interface defines the properties required to specify how buffers should be bound to binding points in shaders.
 * 
 * For more details, refer to the [WebGPU specification](https://www.w3.org/TR/webgpu/#gpubufferbindinglayout-dictionary).
 * 
 */
interface GPUBufferBindingLayout {
	val type: GPUBufferBindingType
	val hasDynamicOffset: Boolean
	val minBindingSize: GPUSize64
}


/**
 * Represents a binding layout for samplers in WebGPU. This interface defines the type of sampler that can be bound to a specific binding.
 * 
 * For more details, refer to the [WebGPU specification](https://www.w3.org/TR/webgpu/#dictdef-gpusamplerbindinglayout).
 * 
 */
interface GPUSamplerBindingLayout {
	val type: GPUSamplerBindingType
}


/**
 * Represents the layout for a GPU texture binding. This interface defines the required properties for specifying how textures should be bound in a GPU pipeline.
 * 
 * For more details, refer to the [WebGPU specification](https://www.w3.org/TR/webgpu/#dictdef-gputexturebindinglayout).
 * 
 */
interface GPUTextureBindingLayout {
	val sampleType: GPUTextureSampleType
	val viewDimension: GPUTextureViewDimension
	val multisampled: Boolean
}


/**
 * Represents the layout configuration for a storage texture binding in WebGPU. This interface defines how textures are accessed and used within shaders, specifying the access mode, format, and view dimension.
 * 
 * For more details, refer to the [WebGPU specification on GPUStorageTextureBindingLayout](https://www.w3.org/TR/webgpu/#dictdef-gpustoragetexturebindinglayout).
 * 
 */
interface GPUStorageTextureBindingLayout {
	val access: GPUStorageTextureAccess
	val format: GPUTextureFormat
	val viewDimension: GPUTextureViewDimension
}


/**
 * The `GPUBindGroupDescriptor` interface represents a descriptor for creating bind groups in WebGPU. It extends the `GPUObjectDescriptorBase` and is used to specify the layout and entries of a bind group.
 * 
 * For more details, refer to the [WebGPU specification](https://www.w3.org/TR/webgpu/#dictdef-gpubindgroupdescriptor).
 * 
 */
interface GPUBindGroupDescriptor : GPUObjectDescriptorBase {
	val layout: GPUBindGroupLayout
	val entries: List<GPUBindGroupEntry>
}


/**
 * Represents a single resource to be bound in a [GPUBindGroup]. This interface is used to describe the binding of resources such as samplers, texture views, external textures, or buffer bindings within a bind group.
 * 
 * For more details, refer to the [WebGPU specification on GPUBindGroupEntry](https://www.w3.org/TR/webgpu/#dictdef-gpubindgroupentry).
 * 
 */
interface GPUBindGroupEntry {
	val binding: GPUIndex32
	val resource: GPUBindingResource
}


/**
 * The `GPUPipelineLayoutDescriptor` interface defines all the [GPUBindGroupLayout](https://www.w3.org/TR/webgpu/#dictdef-gpubindgrouplayout)s used by a pipeline. This descriptor is essential for configuring the layout of bind groups in a GPU pipeline, ensuring that shader modules can access resources correctly.
 * 
 * **Inheritance**: This interface inherits from [GPUObjectDescriptorBase](https://www.w3.org/TR/webgpu/#dictdef-gpuobjectdescriptorbase).
 * 
 * In this example, `bindGroupLayout1` and `bindGroupLayout2` are instances of [GPUBindGroupLayout]. The `bindGroupLayouts` list defines the layout of bind groups that the pipeline will use.
 * 
 */
interface GPUPipelineLayoutDescriptor : GPUObjectDescriptorBase {
	val bindGroupLayouts: List<GPUBindGroupLayout>
}


/**
 * Represents a descriptor for creating a [GPUShaderModule] in WebGPU. This interface extends `GPUObjectDescriptorBase` and is used to specify the WGSL source code and compilation hints required to create a shader module. The shader module is a compiled version of the shader code that can be used in rendering or compute pipelines.
 * 
 * **See also:**
 * - [W3C WebGPU Specification: GPUShaderModuleDescriptor](https://www.w3.org/TR/webgpu/#dictdef-gpushadermoduledescriptor)
 * 
 */
interface GPUShaderModuleDescriptor : GPUObjectDescriptorBase {
	val code: String
	val compilationHints: List<GPUShaderModuleCompilationHint>
}


/**
 * Represents a hint for compiling a GPUShaderModule. This interface provides information about the entry point and layout that may be used with the shader module in future pipeline creation calls.
 * 
 * For more details, refer to the WebGPU specification on Shader Module Compilation Information: https://www.w3.org/TR/webgpu/#shader-module-compilation-information.
 * 
 */
interface GPUShaderModuleCompilationHint {
	val entryPoint: String
	val layout: GPUPipelineLayout??
}


/**
 * Represents the base descriptor for a GPU pipeline. This interface extends [GPUObjectDescriptorBase] and is used to define the layout of a GPU pipeline.
 * 
 * The `layout` property specifies either a [GPUPipelineLayout] or an automatic layout mode (`"auto"`). When `"auto"` is specified, the pipeline layout is generated automatically.
 * 
 */
interface GPUPipelineDescriptorBase : GPUObjectDescriptorBase {
	val layout: GPUPipelineLayout?
}


/**
 * Represents a programmable stage in a GPU pipeline. This interface describes the entry point in a user-provided [GPUShaderModule] that controls one of the programmable stages of a pipeline.
 * Entry point names follow the rules defined in [WGSL identifier comparison](https://gpuweb.github.io/gpuweb/wgsl/#identifier-comparison).
 * 
 */
interface GPUProgrammableStage {
	val module: GPUShaderModule
	val entryPoint: String?
	val constants: Map<String, GPUPipelineConstantValue>
}


/**
 * Represents a descriptor for creating a compute pipeline in WebGPU. This interface extends [GPUPipelineDescriptorBase] and is used to define the configuration for a compute pipeline, which executes compute shaders.
 * 
 * A compute pipeline is responsible for performing general-purpose computations on the GPU. It does not render graphics but can be used for tasks such as data processing, simulations, and other parallel computations.
 * 
 * For more details, refer to the [WebGPU specification](https://www.w3.org/TR/webgpu/#dictdef-gpucomputepipelinedescriptor).
 * 
 */
interface GPUComputePipelineDescriptor : GPUPipelineDescriptorBase {
	val compute: GPUProgrammableStage
}


/**
 * The `GPURenderPipelineDescriptor` interface extends `GPUPipelineDescriptorBase` and defines the configuration for a render pipeline in WebGPU. It specifies the vertex, primitive, depth-stencil, multisample, and fragment states required to create a render pipeline. For more details, refer to the [W3C specification](https://www.w3.org/TR/webgpu/#dictdef-gpurenderpipelinedescriptor).
 * 
 */
interface GPURenderPipelineDescriptor : GPUPipelineDescriptorBase {
	val vertex: GPUVertexState
	val primitive: GPUPrimitiveState
	val depthStencil: GPUDepthStencilState?
	val multisample: GPUMultisampleState
	val fragment: GPUFragmentState?
}


/**
 * Represents the state of a primitive in WebGPU, defining how primitives are rendered. This interface is used to configure various aspects of primitive rendering such as topology, strip index format, front face orientation, cull mode, and depth clipping behavior.
 * 
 * For more details, refer to the [WebGPU specification](https://www.w3.org/TR/webgpu/#dictdef-gpuprimitivestate).
 * 
 */
interface GPUPrimitiveState {
	val topology: GPUPrimitiveTopology
	val stripIndexFormat: GPUIndexFormat?
	val frontFace: GPUFrontFace
	val cullMode: GPUCullMode
	val unclippedDepth: Boolean
}


/**
 * Represents the multisampling state used by a [GPURenderPipeline](https://www.w3.org/TR/webgpu/#gpurenderpipeline) to interact with render pass attachments that support multisampling.
 * 
 * This interface defines how many samples per pixel are used, which samples are written to, and whether alpha-to-coverage is enabled. The multisample state is crucial for rendering high-quality images by reducing aliasing artifacts.
 * 
 * **See also:**
 * - [GPUMultisampleState dictionary in the WebGPU specification](https://www.w3.org/TR/webgpu/#dictdef-gpumultisamplestate)
 * 
 */
interface GPUMultisampleState {
	val count: GPUSize32
	val mask: GPUSampleMask
	val alphaToCoverageEnabled: Boolean
}


/**
 * Represents a fragment state in WebGPU, which is a type of programmable stage that defines how fragments are processed during rendering. This interface extends [GPUProgrammableStage] and includes specific configurations for color targets.
 * 
 * The `GPUFragmentState` interface is used to configure the fragment shader stage of a GPU pipeline, specifying how the colors are written to the render target. This is crucial for defining the visual output of a rendering operation.
 * 
 * **See also:**
 * - [WebGPU Specification: GPUProgrammableStage](https://www.w3.org/TR/webgpu/#gpuprogrammablestage)
 * 
 */
interface GPUFragmentState : GPUProgrammableStage {
	val targets: List<GPUColorTargetState>
}


/**
 * Represents the state of a color target in a GPU render pipeline. This interface defines the format, blending behavior, and write mask for a color attachment in a render pass.
 * 
 * For more details, refer to the [W3C WebGPU specification](https://www.w3.org/TR/webgpu/#dictdef-gpucolortargetstate).
 * 
 */
interface GPUColorTargetState {
	val format: GPUTextureFormat
	val blend: GPUBlendState?
	val writeMask: GPUColorWriteFlags
}


/**
 * Represents the blend state used in rendering operations, defining how colors and alpha values are blended.
 * 
 * This interface is part of the WebGPU API and corresponds to the `GPUBlendState` dictionary defined in the [WebGPU specification](https://www.w3.org/TR/webgpu/#dictdef-gpublendstate).
 * 
 * The `GPUBlendState` interface includes two properties: `color` and `alpha`, both of type `GPUBlendComponent`. These properties specify the blending behavior for color channels and alpha channels, respectively.
 * 
 */
interface GPUBlendState {
	val color: GPUBlendComponent
	val alpha: GPUBlendComponent
}


/**
 * Represents a blend component used in blending operations for color or alpha components of a fragment. This interface defines how the source and destination colors are combined during rendering.
 * 
 * For more details, refer to the [WebGPU specification](https://www.w3.org/TR/webgpu/#dictdef-gpublendcomponent).
 * 
 */
interface GPUBlendComponent {
	val operation: GPUBlendOperation
	val srcFactor: GPUBlendFactor
	val dstFactor: GPUBlendFactor
}


/**
 * Represents the depth and stencil state configuration for a GPU render pipeline. This interface defines various properties that control how depth and stencil tests are performed during rendering.
 * 
 * For more details, refer to the [WebGPU specification](https://www.w3.org/TR/webgpu/#depth-stencil-state).
 * 
 */
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


/**
 * Represents a set of stencil face state parameters that define how stencil tests and operations are performed. This interface is used to configure the behavior of the stencil buffer for front or back-facing triangles in a render pipeline.
 * 
 * For more details, refer to the [W3C WebGPU specification](https://www.w3.org/TR/webgpu/#dictdef-gpustencilfacestate).
 * 
 */
interface GPUStencilFaceState {
	val compare: GPUCompareFunction
	val failOp: GPUStencilOperation
	val depthFailOp: GPUStencilOperation
	val passOp: GPUStencilOperation
}


/**
 * Represents a vertex state in the WebGPU API, defining how vertex data is laid out and processed. This interface extends [GPUProgrammableStage], allowing it to be used as part of a render pipeline.
 * 
 * A `GPUVertexState` object specifies the layout of vertex attribute data in vertex buffers. Each buffer's layout is defined by a list of `GPUVertexBufferLayout` objects, which describe the structure and stride of the vertex data.
 * 
 * For more details, refer to the [W3C WebGPU specification](https://www.w3.org/TR/webgpu/#vertex-state).
 * 
 */
interface GPUVertexState : GPUProgrammableStage {
	val buffers: List<GPUVertexBufferLayout>
}


/**
 * Represents the layout of a vertex buffer in WebGPU. This interface defines how vertices are structured and accessed, including the stride between elements, the step mode (whether data is per-vertex or per-instance), and the attributes that describe the vertex data.
 * 
 * For more details, refer to the [WebGPU specification](https://www.w3.org/TR/webgpu/#dictdef-gpuvertexbufferlayout).
 * 
 */
interface GPUVertexBufferLayout {
	val arrayStride: GPUSize64
	val stepMode: GPUVertexStepMode
	val attributes: List<GPUVertexAttribute>
}


/**
 * Represents a vertex attribute in the WebGPU API. This interface defines the format, offset, and shader location of a vertex attribute.
 * 
 * A `GPUVertexAttribute` is used to describe how data from a vertex buffer should be interpreted by the GPU. It specifies the format of the data (e.g., float32, uint32), the byte offset within the vertex buffer where the data starts, and the shader location that corresponds to this attribute.
 * 
 * For more details, refer to the [W3C WebGPU specification](https://www.w3.org/TR/webgpu/#dictdef-gpuvertexattribute).
 * 
 */
interface GPUVertexAttribute {
	val format: GPUVertexFormat
	val offset: GPUSize64
	val shaderLocation: GPUIndex32
}


/**
 * The `GPUTexelCopyBufferLayout` interface describes the layout of texels in a buffer of bytes during a texel copy operation. This interface is used to define how data is organized in a [GPUBuffer](https://www.w3.org/TR/webgpu/#gpubuffer) or an [AllowSharedBufferSource](https://webidl.spec.whatwg.org/#AllowSharedBufferSource) when performing texel copy operations.
 * 
 * For more details, refer to the [WebGPU specification](https://www.w3.org/TR/webgpu/#gputexelcopybufferlayout).
 * 
 */
interface GPUTexelCopyBufferLayout {
	val offset: GPUSize64
	val bytesPerRow: GPUSize32?
	val rowsPerImage: GPUSize32?
}


/**
 * The `GPUTexelCopyBufferInfo` interface describes the information about a buffer source or destination of a texel copy operation. This includes details such as the buffer itself and its layout.
 * 
 * Together with the `copySize`, it defines the footprint of a region of texels in a [GPUBuffer](https://www.w3.org/TR/webgpu/#gpubuffer). This interface is essential for operations that involve copying texel data between buffers and textures.
 * 
 * For more details, refer to the [WebGPU specification on GPUTexelCopyBufferInfo](https://www.w3.org/TR/webgpu/#gputexelcopybufferinfo).
 * 
 */
interface GPUTexelCopyBufferInfo : GPUTexelCopyBufferLayout {
	val buffer: GPUBuffer
}


/**
 * Represents the information about a texture source or destination for a texel copy operation. This interface describes the sub-region of a texture that spans one or more contiguous texture subresources at the same mip-map level.
 * 
 * For more details, refer to the [WebGPU specification](https://www.w3.org/TR/webgpu/#gputexelcopytextureinfo).
 * 
 */
interface GPUTexelCopyTextureInfo {
	val texture: GPUTexture
	val mipLevel: GPUIntegerCoordinate
	val origin: GPUOrigin3D
	val aspect: GPUTextureAspect
}


/**
 * The `GPUCommandBufferDescriptor` interface represents a descriptor for creating command buffers in WebGPU. This interface inherits from [GPUObjectDescriptorBase], providing a base set of properties and methods that are common to all GPU object descriptors.
 * 
 * A command buffer is a sequence of commands that can be submitted to the GPU for execution. The `GPUCommandBufferDescriptor` specifies the configuration options for creating these command buffers, such as label and usage flags.
 * 
 * This interface is used when calling [GPUDevice.createCommandBuffer] to create a new command buffer with the specified configuration.
 * 
 * **See also:**
 * - [WebGPU Specification: GPUCommandBufferDescriptor](https://www.w3.org/TR/webgpu/#dictdef-gpucommandbufferdescriptor)
 * 
 */
interface GPUCommandBufferDescriptor : GPUObjectDescriptorBase

/**
 * The `GPUCommandEncoderDescriptor` interface represents a descriptor used to create a [GPUCommandEncoder](https://www.w3.org/TR/webgpu/#gpucommandencoder) object. This descriptor inherits from the base descriptor interface [GPUObjectDescriptorBase](https://www.w3.org/TR/webgpu/#dictdef-gpuobjectdescriptorbase).
 * 
 * The `GPUCommandEncoderDescriptor` is used to specify configuration options for the command encoder, such as label and device.
 * 
 * **See Also:**
 * - [GPUObjectDescriptorBase](https://www.w3.org/TR/webgpu/#dictdef-gpuobjectdescriptorbase)
 * 
 */
interface GPUCommandEncoderDescriptor : GPUObjectDescriptorBase

/**
 * Represents a dictionary that specifies the query set and indices where timestamps will be written during a compute pass. This interface is used to measure the duration of compute passes by recording timestamps at the beginning and end of the pass.
 * 
 * For more details, refer to the [WebGPU specification on GPUComputePassTimestampWrites](https://www.w3.org/TR/webgpu/#dictdef-gpucomputepasstimestampwrites).
 * 
 */
interface GPUComputePassTimestampWrites {
	val querySet: GPUQuerySet
	val beginningOfPassWriteIndex: GPUSize32?
	val endOfPassWriteIndex: GPUSize32?
}


/**
 * Represents a descriptor for configuring a compute pass in WebGPU. This interface extends [GPUObjectDescriptorBase] and is used to specify the details of a compute pass, including timestamp writes.
 * 
 * For more information, see the [WebGPU specification](https://www.w3.org/TR/webgpu/#dictdef-gpucomputepassdescriptor).
 * 
 */
interface GPUComputePassDescriptor : GPUObjectDescriptorBase {
	val timestampWrites: GPUComputePassTimestampWrites?
}


/**
 * Represents a dictionary that specifies the query set and indices where timestamps will be written during a render pass. This interface is used to capture timing information at the beginning and end of a render pass.
 * 
 * For more details, refer to the [WebGPU specification on GPURenderPassTimestampWrites](https://www.w3.org/TR/webgpu/#dom-gpurenderpasstimestampwrites).
 * 
 */
interface GPURenderPassTimestampWrites {
	val querySet: GPUQuerySet
	val beginningOfPassWriteIndex: GPUSize32?
	val endOfPassWriteIndex: GPUSize32?
}


/**
 * The `GPURenderPassDescriptor` interface defines the configuration for a render pass in WebGPU. It specifies the color attachments, depth/stencil attachment, occlusion query set, timestamp writes, and maximum draw count for the render pass.
 * 
 * This descriptor is used to configure the rendering process by specifying how different types of data will be handled during the render pass. The `colorAttachments` property defines which color buffers will receive the output from the render pass. The `depthStencilAttachment` specifies the depth/stencil buffer that will be used for depth testing and stencil operations. The `occlusionQuerySet` allows for occlusion queries to be performed, and the `timestampWrites` can be used to write timestamps during the render pass.
 * 
 * For more details, refer to the [WebGPU specification](https://www.w3.org/TR/webgpu/#dictdef-gpurenderpassdescriptor).
 * 
 */
interface GPURenderPassDescriptor : GPUObjectDescriptorBase {
	val colorAttachments: List<GPURenderPassColorAttachment>
	val depthStencilAttachment: GPURenderPassDepthStencilAttachment?
	val occlusionQuerySet: GPUQuerySet?
	val timestampWrites: GPURenderPassTimestampWrites?
	val maxDrawCount: GPUSize64
}


/**
 * Represents a color attachment for a render pass in the WebGPU API. This interface defines the properties required to configure how colors are rendered and stored during a rendering operation.
 * 
 * For more details, refer to the [WebGPU specification](https://www.w3.org/TR/webgpu/#dictdef-gpurenderpasscolorattachment).
 * 
 */
interface GPURenderPassColorAttachment {
	val view: GPUTextureView
	val depthSlice: GPUIntegerCoordinate?
	val resolveTarget: GPUTextureView?
	val clearValue: GPUColor?
	val loadOp: GPULoadOp
	val storeOp: GPUStoreOp
}


/**
 * The `GPURenderPassDepthStencilAttachment` interface represents a depth/stencil attachment for a render pass. It specifies the texture view and various operations to be performed on the depth and stencil components of that view during the render pass.
 * 
 * For more details, refer to the [W3C WebGPU specification](https://www.w3.org/TR/webgpu/#gpurenderpassdepthstencilattachment).
 * 
 */
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


/**
 * Represents the layout of a render pass, specifying the formats and sample counts for color and depth/stencil attachments.
 * 
 * This interface is used to define the configuration of a render pass, which includes the formats of the color attachments and the optional depth/stencil attachment. It also specifies the number of samples per pixel in the attachments.
 * 
 * For more details, refer to the [W3C WebGPU specification](https://www.w3.org/TR/webgpu/#gpurenderpasslayout).
 * 
 */
interface GPURenderPassLayout : GPUObjectDescriptorBase {
	val colorFormats: List<GPUTextureFormat>
	val depthStencilFormat: GPUTextureFormat?
	val sampleCount: GPUSize32
}


/**
 * Represents a descriptor for creating a [GPURenderBundle]. This interface inherits from [GPUObjectDescriptorBase], which provides common properties and methods for GPU objects.
 * 
 * The `GPURenderBundleDescriptor` is used to specify the configuration options when creating a render bundle. A render bundle encapsulates a sequence of rendering commands that can be executed multiple times with different parameters, improving performance by reducing the overhead of command encoding.
 * 
 */
interface GPURenderBundleDescriptor : GPUObjectDescriptorBase

/**
 * Represents a descriptor for creating a GPURenderBundleEncoder. This interface extends the GPURenderPassLayout and is used to specify whether the depth or stencil components of a render pass are read-only.
 * 
 * @see [WebGPU Specification - GPURenderBundleEncoderDescriptor](https://www.w3.org/TR/webgpu/#dictdef-gpurenderbundleencoderdescriptor)
 * 
 */
interface GPURenderBundleEncoderDescriptor : GPURenderPassLayout {
	val depthReadOnly: Boolean
	val stencilReadOnly: Boolean
}


/**
 * The `GPUQueueDescriptor` interface describes a queue request in the WebGPU API. This dictionary inherits from [GPUObjectDescriptorBase](https://www.w3.org/TR/webgpu/#dictdef-gpuobjectdescriptorbase), which means it includes all properties and methods defined by that base class.
 * 
 * The `GPUQueueDescriptor` is used to configure and create GPU queues, which are responsible for submitting commands to the GPU. This interface does not define any additional properties beyond those inherited from [GPUObjectDescriptorBase].
 * 
 * **See Also:**
 * - [GPUObjectDescriptorBase](https://www.w3.org/TR/webgpu/#dictdef-gpuobjectdescriptorbase) for inherited properties and methods.
 * 
 */
interface GPUQueueDescriptor : GPUObjectDescriptorBase

/**
 * Represents a descriptor for creating a [GPUQuerySet](https://www.w3.org/TR/webgpu/#gpuqueryset) object. This interface extends [GPUObjectDescriptorBase], providing the necessary configuration parameters to define the type and count of queries managed by the query set.
 * 
 * **See also:**
 * - [WebGPU Specification: GPUQuerySetDescriptor](https://www.w3.org/TR/webgpu/#dictdef-gpuquerysetdescriptor)
 * 
 */
interface GPUQuerySetDescriptor : GPUObjectDescriptorBase {
	val type: GPUQueryType
	val count: GPUSize32
}
