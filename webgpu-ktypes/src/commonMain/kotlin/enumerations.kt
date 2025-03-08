@file:Suppress("unused")
// This file has been generated DO NO EDIT
package io.ygdrasil.webgpu

expect enum class GPUAddressMode {
	ClampToEdge,
	Repeat,
	MirrorRepeat;
}

expect enum class GPUBlendFactor {
	Zero,
	One,
	Src,
	OneMinusSrc,
	SrcAlpha,
	OneMinusSrcAlpha,
	Dst,
	OneMinusDst,
	DstAlpha,
	OneMinusDstAlpha,
	SrcAlphaSaturated,
	Constant,
	OneMinusConstant,
	Src1,
	OneMinusSrc1,
	Src1Alpha,
	OneMinusSrc1Alpha;
}

expect enum class GPUBlendOperation {
	Add,
	Subtract,
	ReverseSubtract,
	Min,
	Max;
}

expect enum class GPUBufferBindingType {
	BindingNotUsed,
	Uniform,
	Storage,
	ReadOnlyStorage;
}

expect enum class GPUBufferMapState {
	Unmapped,
	Pending,
	Mapped;
}

expect enum class GPUCompareFunction {
	Never,
	Less,
	Equal,
	LessEqual,
	Greater,
	NotEqual,
	GreaterEqual,
	Always;
}

expect enum class GPUCompilationMessageType {
	Error,
	Warning,
	Info;
}

expect enum class GPUCullMode {
	None,
	Front,
	Back;
}

expect enum class GPUDeviceLostReason {
	Unknown,
	Destroyed,
	InstanceDropped,
	FailedCreation;
}

expect enum class GPUErrorFilter {
	Validation,
	OutOfMemory,
	Internal;
}

expect enum class GPUFeatureName {
	DepthClipControl,
	Depth32FloatStencil8,
	TimestampQuery,
	TextureCompressionBC,
	TextureCompressionBCSliced3D,
	TextureCompressionETC2,
	TextureCompressionASTC,
	TextureCompressionASTCSliced3D,
	IndirectFirstInstance,
	ShaderF16,
	RG11B10UfloatRenderable,
	BGRA8UnormStorage,
	Float32Filterable,
	Float32Blendable,
	ClipDistances,
	DualSourceBlending;
}

expect enum class GPUFilterMode {
	Nearest,
	Linear;
}

expect enum class GPUFrontFace {
	CCW,
	CW;
}

expect enum class GPUIndexFormat {
	Uint16,
	Uint32;
}

expect enum class GPULoadOp {
	Load,
	Clear;
}

expect enum class GPUMipmapFilterMode {
	Nearest,
	Linear;
}

expect enum class GPUPowerPreference {
	LowPower,
	HighPerformance;
}

expect enum class GPUPrimitiveTopology {
	PointList,
	LineList,
	LineStrip,
	TriangleList,
	TriangleStrip;
}

expect enum class GPUQueryType {
	Occlusion,
	Timestamp;
}

expect enum class GPUSamplerBindingType {
	BindingNotUsed,
	Filtering,
	NonFiltering,
	Comparison;
}

expect enum class GPUStencilOperation {
	Keep,
	Zero,
	Replace,
	Invert,
	IncrementClamp,
	DecrementClamp,
	IncrementWrap,
	DecrementWrap;
}

expect enum class GPUStorageTextureAccess {
	BindingNotUsed,
	WriteOnly,
	ReadOnly,
	ReadWrite;
}

expect enum class GPUStoreOp {
	Store,
	Discard;
}

expect enum class GPUTextureAspect {
	All,
	StencilOnly,
	DepthOnly;
}

expect enum class GPUTextureDimension {
	OneD,
	TwoD,
	ThreeD;
}

expect enum class GPUTextureFormat {
	R8Unorm,
	R8Snorm,
	R8Uint,
	R8Sint,
	R16Uint,
	R16Sint,
	R16Float,
	RG8Unorm,
	RG8Snorm,
	RG8Uint,
	RG8Sint,
	R32Float,
	R32Uint,
	R32Sint,
	RG16Uint,
	RG16Sint,
	RG16Float,
	RGBA8Unorm,
	RGBA8UnormSrgb,
	RGBA8Snorm,
	RGBA8Uint,
	RGBA8Sint,
	BGRA8Unorm,
	BGRA8UnormSrgb,
	RGB10A2Uint,
	RGB10A2Unorm,
	RG11B10Ufloat,
	RGB9E5Ufloat,
	RG32Float,
	RG32Uint,
	RG32Sint,
	RGBA16Uint,
	RGBA16Sint,
	RGBA16Float,
	RGBA32Float,
	RGBA32Uint,
	RGBA32Sint,
	Stencil8,
	Depth16Unorm,
	Depth24Plus,
	Depth24PlusStencil8,
	Depth32Float,
	Depth32FloatStencil8,
	BC1RGBAUnorm,
	BC1RGBAUnormSrgb,
	BC2RGBAUnorm,
	BC2RGBAUnormSrgb,
	BC3RGBAUnorm,
	BC3RGBAUnormSrgb,
	BC4RUnorm,
	BC4RSnorm,
	BC5RGUnorm,
	BC5RGSnorm,
	BC6HRGBUfloat,
	BC6HRGBFloat,
	BC7RGBAUnorm,
	BC7RGBAUnormSrgb,
	ETC2RGB8Unorm,
	ETC2RGB8UnormSrgb,
	ETC2RGB8A1Unorm,
	ETC2RGB8A1UnormSrgb,
	ETC2RGBA8Unorm,
	ETC2RGBA8UnormSrgb,
	EACR11Unorm,
	EACR11Snorm,
	EACRG11Unorm,
	EACRG11Snorm,
	ASTC4x4Unorm,
	ASTC4x4UnormSrgb,
	ASTC5x4Unorm,
	ASTC5x4UnormSrgb,
	ASTC5x5Unorm,
	ASTC5x5UnormSrgb,
	ASTC6x5Unorm,
	ASTC6x5UnormSrgb,
	ASTC6x6Unorm,
	ASTC6x6UnormSrgb,
	ASTC8x5Unorm,
	ASTC8x5UnormSrgb,
	ASTC8x6Unorm,
	ASTC8x6UnormSrgb,
	ASTC8x8Unorm,
	ASTC8x8UnormSrgb,
	ASTC10x5Unorm,
	ASTC10x5UnormSrgb,
	ASTC10x6Unorm,
	ASTC10x6UnormSrgb,
	ASTC10x8Unorm,
	ASTC10x8UnormSrgb,
	ASTC10x10Unorm,
	ASTC10x10UnormSrgb,
	ASTC12x10Unorm,
	ASTC12x10UnormSrgb,
	ASTC12x12Unorm,
	ASTC12x12UnormSrgb;
}

expect enum class GPUTextureSampleType {
	BindingNotUsed,
	Float,
	UnfilterableFloat,
	Depth,
	Sint,
	Uint;
}

expect enum class GPUTextureViewDimension {
	OneD,
	TwoD,
	TwoDArray,
	Cube,
	CubeArray,
	ThreeD;
}

expect enum class GPUVertexFormat {
	Uint8,
	Uint8x2,
	Uint8x4,
	Sint8,
	Sint8x2,
	Sint8x4,
	Unorm8,
	Unorm8x2,
	Unorm8x4,
	Snorm8,
	Snorm8x2,
	Snorm8x4,
	Uint16,
	Uint16x2,
	Uint16x4,
	Sint16,
	Sint16x2,
	Sint16x4,
	Unorm16,
	Unorm16x2,
	Unorm16x4,
	Snorm16,
	Snorm16x2,
	Snorm16x4,
	Float16,
	Float16x2,
	Float16x4,
	Float32,
	Float32x2,
	Float32x3,
	Float32x4,
	Uint32,
	Uint32x2,
	Uint32x3,
	Uint32x4,
	Sint32,
	Sint32x2,
	Sint32x3,
	Sint32x4,
	Unorm1010102,
	Unorm8x4BGRA;
}

expect enum class GPUVertexStepMode {
	VertexBufferNotUsed,
	Vertex,
	Instance;
}
