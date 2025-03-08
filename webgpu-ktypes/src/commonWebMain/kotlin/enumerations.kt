@file:Suppress("unused")
// This file has been generated DO NO EDIT
package io.ygdrasil.webgpu

actual enum class GPUAddressMode(val value: String) {
	ClampToEdge("clamp-to-edge"),
	Repeat("repeat"),
	MirrorRepeat("mirror-repeat");
}

actual enum class GPUBlendFactor(val value: String) {
	Zero("zero"),
	One("one"),
	Src("src"),
	OneMinusSrc("one-minus-src"),
	SrcAlpha("src-alpha"),
	OneMinusSrcAlpha("one-minus-src-alpha"),
	Dst("dst"),
	OneMinusDst("one-minus-dst"),
	DstAlpha("dst-alpha"),
	OneMinusDstAlpha("one-minus-dst-alpha"),
	SrcAlphaSaturated("src-alpha-saturated"),
	Constant("constant"),
	OneMinusConstant("one-minus-constant"),
	Src1("src1"),
	OneMinusSrc1("one-minus-src1"),
	Src1Alpha("src1-alpha"),
	OneMinusSrc1Alpha("one-minus-src1-alpha");
}

actual enum class GPUBlendOperation(val value: String) {
	Add("add"),
	Subtract("subtract"),
	ReverseSubtract("reverse-subtract"),
	Min("min"),
	Max("max");
}

actual enum class GPUBufferBindingType(val value: String) {
	BindingNotUsed("unsupported"),
	Uniform("uniform"),
	Storage("storage"),
	ReadOnlyStorage("read-only-storage");
}

actual enum class GPUBufferMapState(val value: String) {
	Unmapped("unmapped"),
	Pending("pending"),
	Mapped("mapped");
}

actual enum class GPUCompareFunction(val value: String) {
	Never("never"),
	Less("less"),
	Equal("equal"),
	LessEqual("less-equal"),
	Greater("greater"),
	NotEqual("not-equal"),
	GreaterEqual("greater-equal"),
	Always("always");
}

actual enum class GPUCompilationMessageType(val value: String) {
	Error("error"),
	Warning("warning"),
	Info("info");
}

actual enum class GPUCullMode(val value: String) {
	None("none"),
	Front("front"),
	Back("back");
}

actual enum class GPUDeviceLostReason(val value: String) {
	Unknown("unknown"),
	Destroyed("destroyed"),
	InstanceDropped("unsupported"),
	FailedCreation("unsupported");
}

actual enum class GPUErrorFilter(val value: String) {
	Validation("validation"),
	OutOfMemory("out-of-memory"),
	Internal("internal");
}

actual enum class GPUFeatureName(val value: String) {
	DepthClipControl("depth-clip-control"),
	Depth32FloatStencil8("depth32float-stencil8"),
	TimestampQuery("timestamp-query"),
	TextureCompressionBC("texture-compression-bc"),
	TextureCompressionBCSliced3D("texture-compression-bc-sliced-3d"),
	TextureCompressionETC2("texture-compression-etc2"),
	TextureCompressionASTC("texture-compression-astc"),
	TextureCompressionASTCSliced3D("texture-compression-astc-sliced-3d"),
	IndirectFirstInstance("indirect-first-instance"),
	ShaderF16("shader-f16"),
	RG11B10UfloatRenderable("rg11b10ufloat-renderable"),
	BGRA8UnormStorage("bgra8unorm-storage"),
	Float32Filterable("float32-filterable"),
	Float32Blendable("float32-blendable"),
	ClipDistances("clip-distances"),
	DualSourceBlending("dual-source-blending");
}

actual enum class GPUFilterMode(val value: String) {
	Nearest("nearest"),
	Linear("linear");
}

actual enum class GPUFrontFace(val value: String) {
	CCW("ccw"),
	CW("cw");
}

actual enum class GPUIndexFormat(val value: String) {
	Uint16("uint16"),
	Uint32("uint32");
}

actual enum class GPULoadOp(val value: String) {
	Load("load"),
	Clear("clear");
}

actual enum class GPUMipmapFilterMode(val value: String) {
	Nearest("nearest"),
	Linear("linear");
}

actual enum class GPUPowerPreference(val value: String) {
	LowPower("low-power"),
	HighPerformance("high-performance");
}

actual enum class GPUPrimitiveTopology(val value: String) {
	PointList("point-list"),
	LineList("line-list"),
	LineStrip("line-strip"),
	TriangleList("triangle-list"),
	TriangleStrip("triangle-strip");
}

actual enum class GPUQueryType(val value: String) {
	Occlusion("occlusion"),
	Timestamp("timestamp");
}

actual enum class GPUSamplerBindingType(val value: String) {
	BindingNotUsed("unsupported"),
	Filtering("filtering"),
	NonFiltering("non-filtering"),
	Comparison("comparison");
}

actual enum class GPUStencilOperation(val value: String) {
	Keep("keep"),
	Zero("zero"),
	Replace("replace"),
	Invert("invert"),
	IncrementClamp("increment-clamp"),
	DecrementClamp("decrement-clamp"),
	IncrementWrap("increment-wrap"),
	DecrementWrap("decrement-wrap");
}

actual enum class GPUStorageTextureAccess(val value: String) {
	BindingNotUsed("unsupported"),
	WriteOnly("write-only"),
	ReadOnly("read-only"),
	ReadWrite("read-write");
}

actual enum class GPUStoreOp(val value: String) {
	Store("store"),
	Discard("discard");
}

actual enum class GPUTextureAspect(val value: String) {
	All("all"),
	StencilOnly("stencil-only"),
	DepthOnly("depth-only");
}

actual enum class GPUTextureDimension(val value: String) {
	OneD("unsupported"),
	TwoD("unsupported"),
	ThreeD("unsupported");
}

actual enum class GPUTextureFormat(val value: String) {
	R8Unorm("r8unorm"),
	R8Snorm("r8snorm"),
	R8Uint("r8uint"),
	R8Sint("r8sint"),
	R16Uint("r16uint"),
	R16Sint("r16sint"),
	R16Float("r16float"),
	RG8Unorm("rg8unorm"),
	RG8Snorm("rg8snorm"),
	RG8Uint("rg8uint"),
	RG8Sint("rg8sint"),
	R32Float("r32float"),
	R32Uint("r32uint"),
	R32Sint("r32sint"),
	RG16Uint("rg16uint"),
	RG16Sint("rg16sint"),
	RG16Float("rg16float"),
	RGBA8Unorm("rgba8unorm"),
	RGBA8UnormSrgb("rgba8unorm-srgb"),
	RGBA8Snorm("rgba8snorm"),
	RGBA8Uint("rgba8uint"),
	RGBA8Sint("rgba8sint"),
	BGRA8Unorm("bgra8unorm"),
	BGRA8UnormSrgb("bgra8unorm-srgb"),
	RGB10A2Uint("rgb10a2uint"),
	RGB10A2Unorm("rgb10a2unorm"),
	RG11B10Ufloat("rg11b10ufloat"),
	RGB9E5Ufloat("rgb9e5ufloat"),
	RG32Float("rg32float"),
	RG32Uint("rg32uint"),
	RG32Sint("rg32sint"),
	RGBA16Uint("rgba16uint"),
	RGBA16Sint("rgba16sint"),
	RGBA16Float("rgba16float"),
	RGBA32Float("rgba32float"),
	RGBA32Uint("rgba32uint"),
	RGBA32Sint("rgba32sint"),
	Stencil8("stencil8"),
	Depth16Unorm("depth16unorm"),
	Depth24Plus("depth24plus"),
	Depth24PlusStencil8("depth24plus-stencil8"),
	Depth32Float("depth32float"),
	Depth32FloatStencil8("depth32float-stencil8"),
	BC1RGBAUnorm("bc1-rgba-unorm"),
	BC1RGBAUnormSrgb("bc1-rgba-unorm-srgb"),
	BC2RGBAUnorm("bc2-rgba-unorm"),
	BC2RGBAUnormSrgb("bc2-rgba-unorm-srgb"),
	BC3RGBAUnorm("bc3-rgba-unorm"),
	BC3RGBAUnormSrgb("bc3-rgba-unorm-srgb"),
	BC4RUnorm("bc4-r-unorm"),
	BC4RSnorm("bc4-r-snorm"),
	BC5RGUnorm("bc5-rg-unorm"),
	BC5RGSnorm("bc5-rg-snorm"),
	BC6HRGBUfloat("bc6h-rgb-ufloat"),
	BC6HRGBFloat("bc6h-rgb-float"),
	BC7RGBAUnorm("bc7-rgba-unorm"),
	BC7RGBAUnormSrgb("bc7-rgba-unorm-srgb"),
	ETC2RGB8Unorm("etc2-rgb8unorm"),
	ETC2RGB8UnormSrgb("etc2-rgb8unorm-srgb"),
	ETC2RGB8A1Unorm("etc2-rgb8a1unorm"),
	ETC2RGB8A1UnormSrgb("etc2-rgb8a1unorm-srgb"),
	ETC2RGBA8Unorm("etc2-rgba8unorm"),
	ETC2RGBA8UnormSrgb("etc2-rgba8unorm-srgb"),
	EACR11Unorm("eac-r11unorm"),
	EACR11Snorm("eac-r11snorm"),
	EACRG11Unorm("eac-rg11unorm"),
	EACRG11Snorm("eac-rg11snorm"),
	ASTC4x4Unorm("astc-4x4-unorm"),
	ASTC4x4UnormSrgb("astc-4x4-unorm-srgb"),
	ASTC5x4Unorm("astc-5x4-unorm"),
	ASTC5x4UnormSrgb("astc-5x4-unorm-srgb"),
	ASTC5x5Unorm("astc-5x5-unorm"),
	ASTC5x5UnormSrgb("astc-5x5-unorm-srgb"),
	ASTC6x5Unorm("astc-6x5-unorm"),
	ASTC6x5UnormSrgb("astc-6x5-unorm-srgb"),
	ASTC6x6Unorm("astc-6x6-unorm"),
	ASTC6x6UnormSrgb("astc-6x6-unorm-srgb"),
	ASTC8x5Unorm("astc-8x5-unorm"),
	ASTC8x5UnormSrgb("astc-8x5-unorm-srgb"),
	ASTC8x6Unorm("astc-8x6-unorm"),
	ASTC8x6UnormSrgb("astc-8x6-unorm-srgb"),
	ASTC8x8Unorm("astc-8x8-unorm"),
	ASTC8x8UnormSrgb("astc-8x8-unorm-srgb"),
	ASTC10x5Unorm("astc-10x5-unorm"),
	ASTC10x5UnormSrgb("astc-10x5-unorm-srgb"),
	ASTC10x6Unorm("astc-10x6-unorm"),
	ASTC10x6UnormSrgb("astc-10x6-unorm-srgb"),
	ASTC10x8Unorm("astc-10x8-unorm"),
	ASTC10x8UnormSrgb("astc-10x8-unorm-srgb"),
	ASTC10x10Unorm("astc-10x10-unorm"),
	ASTC10x10UnormSrgb("astc-10x10-unorm-srgb"),
	ASTC12x10Unorm("astc-12x10-unorm"),
	ASTC12x10UnormSrgb("astc-12x10-unorm-srgb"),
	ASTC12x12Unorm("astc-12x12-unorm"),
	ASTC12x12UnormSrgb("astc-12x12-unorm-srgb");
}

actual enum class GPUTextureSampleType(val value: String) {
	BindingNotUsed("unsupported"),
	Float("float"),
	UnfilterableFloat("unfilterable-float"),
	Depth("depth"),
	Sint("sint"),
	Uint("uint");
}

actual enum class GPUTextureViewDimension(val value: String) {
	OneD("unsupported"),
	TwoD("unsupported"),
	TwoDArray("unsupported"),
	Cube("cube"),
	CubeArray("cube-array"),
	ThreeD("unsupported");
}

actual enum class GPUVertexFormat(val value: String) {
	Uint8("uint8"),
	Uint8x2("uint8x2"),
	Uint8x4("uint8x4"),
	Sint8("sint8"),
	Sint8x2("sint8x2"),
	Sint8x4("sint8x4"),
	Unorm8("unorm8"),
	Unorm8x2("unorm8x2"),
	Unorm8x4("unorm8x4"),
	Snorm8("snorm8"),
	Snorm8x2("snorm8x2"),
	Snorm8x4("snorm8x4"),
	Uint16("uint16"),
	Uint16x2("uint16x2"),
	Uint16x4("uint16x4"),
	Sint16("sint16"),
	Sint16x2("sint16x2"),
	Sint16x4("sint16x4"),
	Unorm16("unorm16"),
	Unorm16x2("unorm16x2"),
	Unorm16x4("unorm16x4"),
	Snorm16("snorm16"),
	Snorm16x2("snorm16x2"),
	Snorm16x4("snorm16x4"),
	Float16("float16"),
	Float16x2("float16x2"),
	Float16x4("float16x4"),
	Float32("float32"),
	Float32x2("float32x2"),
	Float32x3("float32x3"),
	Float32x4("float32x4"),
	Uint32("uint32"),
	Uint32x2("uint32x2"),
	Uint32x3("uint32x3"),
	Uint32x4("uint32x4"),
	Sint32("sint32"),
	Sint32x2("sint32x2"),
	Sint32x3("sint32x3"),
	Sint32x4("sint32x4"),
	Unorm1010102("unorm10-10-10-2"),
	Unorm8x4BGRA("unorm8x4-bgra");
}

actual enum class GPUVertexStepMode(val value: String) {
	VertexBufferNotUsed("unsupported"),
	Vertex("vertex"),
	Instance("instance");
}
