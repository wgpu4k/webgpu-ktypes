@file:Suppress("unused")
// This file has been generated DO NO EDIT
package io.ygdrasil.webgpu

actual enum class GPUAddressMode(val value: UInt) {
	ClampToEdge(1u),
	Repeat(2u),
	MirrorRepeat(3u);


	companion object {
		/**
		 * Retrieves the corresponding [UInt] for the given value.
		 *
		 * @param value The dependent platform value representing the WebGPU value.
		 * @return The matching [UInt] or `null` if no match is found.
		 */
		fun of(value: UInt): GPUAddressMode? {
			return entries.find { it.value == value }
		}
    }

}

actual enum class GPUBlendFactor(val value: UInt) {
	Zero(1u),
	One(2u),
	Src(3u),
	OneMinusSrc(4u),
	SrcAlpha(5u),
	OneMinusSrcAlpha(6u),
	Dst(7u),
	OneMinusDst(8u),
	DstAlpha(9u),
	OneMinusDstAlpha(10u),
	SrcAlphaSaturated(11u),
	Constant(12u),
	OneMinusConstant(13u),
	Src1(14u),
	OneMinusSrc1(15u),
	Src1Alpha(16u),
	OneMinusSrc1Alpha(17u);


	companion object {
		/**
		 * Retrieves the corresponding [UInt] for the given value.
		 *
		 * @param value The dependent platform value representing the WebGPU value.
		 * @return The matching [UInt] or `null` if no match is found.
		 */
		fun of(value: UInt): GPUBlendFactor? {
			return entries.find { it.value == value }
		}
    }

}

actual enum class GPUBlendOperation(val value: UInt) {
	Add(1u),
	Subtract(2u),
	ReverseSubtract(3u),
	Min(4u),
	Max(5u);


	companion object {
		/**
		 * Retrieves the corresponding [UInt] for the given value.
		 *
		 * @param value The dependent platform value representing the WebGPU value.
		 * @return The matching [UInt] or `null` if no match is found.
		 */
		fun of(value: UInt): GPUBlendOperation? {
			return entries.find { it.value == value }
		}
    }

}

actual enum class GPUBufferBindingType(val value: UInt) {
	BindingNotUsed(1u),
	Uniform(3u),
	Storage(4u),
	ReadOnlyStorage(5u);


	companion object {
		/**
		 * Retrieves the corresponding [UInt] for the given value.
		 *
		 * @param value The dependent platform value representing the WebGPU value.
		 * @return The matching [UInt] or `null` if no match is found.
		 */
		fun of(value: UInt): GPUBufferBindingType? {
			return entries.find { it.value == value }
		}
    }

}

actual enum class GPUBufferMapState(val value: UInt) {
	Unmapped(1u),
	Pending(2u),
	Mapped(3u);


	companion object {
		/**
		 * Retrieves the corresponding [UInt] for the given value.
		 *
		 * @param value The dependent platform value representing the WebGPU value.
		 * @return The matching [UInt] or `null` if no match is found.
		 */
		fun of(value: UInt): GPUBufferMapState? {
			return entries.find { it.value == value }
		}
    }

}

actual enum class GPUCompareFunction(val value: UInt) {
	Never(1u),
	Less(2u),
	Equal(3u),
	LessEqual(4u),
	Greater(5u),
	NotEqual(6u),
	GreaterEqual(7u),
	Always(8u);


	companion object {
		/**
		 * Retrieves the corresponding [UInt] for the given value.
		 *
		 * @param value The dependent platform value representing the WebGPU value.
		 * @return The matching [UInt] or `null` if no match is found.
		 */
		fun of(value: UInt): GPUCompareFunction? {
			return entries.find { it.value == value }
		}
    }

}

actual enum class GPUCompilationMessageType(val value: UInt) {
	Error(1u),
	Warning(2u),
	Info(3u);


	companion object {
		/**
		 * Retrieves the corresponding [UInt] for the given value.
		 *
		 * @param value The dependent platform value representing the WebGPU value.
		 * @return The matching [UInt] or `null` if no match is found.
		 */
		fun of(value: UInt): GPUCompilationMessageType? {
			return entries.find { it.value == value }
		}
    }

}

actual enum class GPUCullMode(val value: UInt) {
	None(1u),
	Front(2u),
	Back(3u);


	companion object {
		/**
		 * Retrieves the corresponding [UInt] for the given value.
		 *
		 * @param value The dependent platform value representing the WebGPU value.
		 * @return The matching [UInt] or `null` if no match is found.
		 */
		fun of(value: UInt): GPUCullMode? {
			return entries.find { it.value == value }
		}
    }

}

actual enum class GPUDeviceLostReason(val value: UInt) {
	Unknown(1u),
	Destroyed(2u),
	InstanceDropped(3u),
	FailedCreation(4u);


	companion object {
		/**
		 * Retrieves the corresponding [UInt] for the given value.
		 *
		 * @param value The dependent platform value representing the WebGPU value.
		 * @return The matching [UInt] or `null` if no match is found.
		 */
		fun of(value: UInt): GPUDeviceLostReason? {
			return entries.find { it.value == value }
		}
    }

}

actual enum class GPUErrorFilter(val value: UInt) {
	Validation(1u),
	OutOfMemory(2u),
	Internal(3u);


	companion object {
		/**
		 * Retrieves the corresponding [UInt] for the given value.
		 *
		 * @param value The dependent platform value representing the WebGPU value.
		 * @return The matching [UInt] or `null` if no match is found.
		 */
		fun of(value: UInt): GPUErrorFilter? {
			return entries.find { it.value == value }
		}
    }

}

actual enum class GPUFeatureName(val value: UInt) {
	DepthClipControl(1u),
	Depth32FloatStencil8(2u),
	TimestampQuery(3u),
	TextureCompressionBC(4u),
	TextureCompressionBCSliced3D(5u),
	TextureCompressionETC2(6u),
	TextureCompressionASTC(7u),
	TextureCompressionASTCSliced3D(8u),
	IndirectFirstInstance(9u),
	ShaderF16(10u),
	RG11B10UfloatRenderable(11u),
	BGRA8UnormStorage(12u),
	Float32Filterable(13u),
	Float32Blendable(14u),
	ClipDistances(15u),
	DualSourceBlending(16u);


	companion object {
		/**
		 * Retrieves the corresponding [UInt] for the given value.
		 *
		 * @param value The dependent platform value representing the WebGPU value.
		 * @return The matching [UInt] or `null` if no match is found.
		 */
		fun of(value: UInt): GPUFeatureName? {
			return entries.find { it.value == value }
		}
    }

}

actual enum class GPUFilterMode(val value: UInt) {
	Nearest(1u),
	Linear(2u);


	companion object {
		/**
		 * Retrieves the corresponding [UInt] for the given value.
		 *
		 * @param value The dependent platform value representing the WebGPU value.
		 * @return The matching [UInt] or `null` if no match is found.
		 */
		fun of(value: UInt): GPUFilterMode? {
			return entries.find { it.value == value }
		}
    }

}

actual enum class GPUFrontFace(val value: UInt) {
	CCW(1u),
	CW(2u);


	companion object {
		/**
		 * Retrieves the corresponding [UInt] for the given value.
		 *
		 * @param value The dependent platform value representing the WebGPU value.
		 * @return The matching [UInt] or `null` if no match is found.
		 */
		fun of(value: UInt): GPUFrontFace? {
			return entries.find { it.value == value }
		}
    }

}

actual enum class GPUIndexFormat(val value: UInt) {
	Uint16(1u),
	Uint32(2u);


	companion object {
		/**
		 * Retrieves the corresponding [UInt] for the given value.
		 *
		 * @param value The dependent platform value representing the WebGPU value.
		 * @return The matching [UInt] or `null` if no match is found.
		 */
		fun of(value: UInt): GPUIndexFormat? {
			return entries.find { it.value == value }
		}
    }

}

actual enum class GPULoadOp(val value: UInt) {
	Load(1u),
	Clear(2u);


	companion object {
		/**
		 * Retrieves the corresponding [UInt] for the given value.
		 *
		 * @param value The dependent platform value representing the WebGPU value.
		 * @return The matching [UInt] or `null` if no match is found.
		 */
		fun of(value: UInt): GPULoadOp? {
			return entries.find { it.value == value }
		}
    }

}

actual enum class GPUMipmapFilterMode(val value: UInt) {
	Nearest(1u),
	Linear(2u);


	companion object {
		/**
		 * Retrieves the corresponding [UInt] for the given value.
		 *
		 * @param value The dependent platform value representing the WebGPU value.
		 * @return The matching [UInt] or `null` if no match is found.
		 */
		fun of(value: UInt): GPUMipmapFilterMode? {
			return entries.find { it.value == value }
		}
    }

}

actual enum class GPUPowerPreference(val value: UInt) {
	LowPower(1u),
	HighPerformance(2u);


	companion object {
		/**
		 * Retrieves the corresponding [UInt] for the given value.
		 *
		 * @param value The dependent platform value representing the WebGPU value.
		 * @return The matching [UInt] or `null` if no match is found.
		 */
		fun of(value: UInt): GPUPowerPreference? {
			return entries.find { it.value == value }
		}
    }

}

actual enum class GPUPrimitiveTopology(val value: UInt) {
	PointList(1u),
	LineList(2u),
	LineStrip(3u),
	TriangleList(4u),
	TriangleStrip(5u);


	companion object {
		/**
		 * Retrieves the corresponding [UInt] for the given value.
		 *
		 * @param value The dependent platform value representing the WebGPU value.
		 * @return The matching [UInt] or `null` if no match is found.
		 */
		fun of(value: UInt): GPUPrimitiveTopology? {
			return entries.find { it.value == value }
		}
    }

}

actual enum class GPUQueryType(val value: UInt) {
	Occlusion(1u),
	Timestamp(2u);


	companion object {
		/**
		 * Retrieves the corresponding [UInt] for the given value.
		 *
		 * @param value The dependent platform value representing the WebGPU value.
		 * @return The matching [UInt] or `null` if no match is found.
		 */
		fun of(value: UInt): GPUQueryType? {
			return entries.find { it.value == value }
		}
    }

}

actual enum class GPUSamplerBindingType(val value: UInt) {
	BindingNotUsed(1u),
	Filtering(3u),
	NonFiltering(4u),
	Comparison(5u);


	companion object {
		/**
		 * Retrieves the corresponding [UInt] for the given value.
		 *
		 * @param value The dependent platform value representing the WebGPU value.
		 * @return The matching [UInt] or `null` if no match is found.
		 */
		fun of(value: UInt): GPUSamplerBindingType? {
			return entries.find { it.value == value }
		}
    }

}

actual enum class GPUStencilOperation(val value: UInt) {
	Keep(1u),
	Zero(2u),
	Replace(3u),
	Invert(4u),
	IncrementClamp(5u),
	DecrementClamp(6u),
	IncrementWrap(7u),
	DecrementWrap(8u);


	companion object {
		/**
		 * Retrieves the corresponding [UInt] for the given value.
		 *
		 * @param value The dependent platform value representing the WebGPU value.
		 * @return The matching [UInt] or `null` if no match is found.
		 */
		fun of(value: UInt): GPUStencilOperation? {
			return entries.find { it.value == value }
		}
    }

}

actual enum class GPUStorageTextureAccess(val value: UInt) {
	BindingNotUsed(1u),
	WriteOnly(3u),
	ReadOnly(4u),
	ReadWrite(5u);


	companion object {
		/**
		 * Retrieves the corresponding [UInt] for the given value.
		 *
		 * @param value The dependent platform value representing the WebGPU value.
		 * @return The matching [UInt] or `null` if no match is found.
		 */
		fun of(value: UInt): GPUStorageTextureAccess? {
			return entries.find { it.value == value }
		}
    }

}

actual enum class GPUStoreOp(val value: UInt) {
	Store(1u),
	Discard(2u);


	companion object {
		/**
		 * Retrieves the corresponding [UInt] for the given value.
		 *
		 * @param value The dependent platform value representing the WebGPU value.
		 * @return The matching [UInt] or `null` if no match is found.
		 */
		fun of(value: UInt): GPUStoreOp? {
			return entries.find { it.value == value }
		}
    }

}

actual enum class GPUTextureAspect(val value: UInt) {
	All(1u),
	StencilOnly(2u),
	DepthOnly(3u);


	companion object {
		/**
		 * Retrieves the corresponding [UInt] for the given value.
		 *
		 * @param value The dependent platform value representing the WebGPU value.
		 * @return The matching [UInt] or `null` if no match is found.
		 */
		fun of(value: UInt): GPUTextureAspect? {
			return entries.find { it.value == value }
		}
    }

}

actual enum class GPUTextureDimension(val value: UInt) {
	OneD(1u),
	TwoD(2u),
	ThreeD(3u);


	companion object {
		/**
		 * Retrieves the corresponding [UInt] for the given value.
		 *
		 * @param value The dependent platform value representing the WebGPU value.
		 * @return The matching [UInt] or `null` if no match is found.
		 */
		fun of(value: UInt): GPUTextureDimension? {
			return entries.find { it.value == value }
		}
    }

}

actual enum class GPUTextureFormat(val value: UInt) {
	R8Unorm(1u),
	R8Snorm(2u),
	R8Uint(3u),
	R8Sint(4u),
	R16Uint(5u),
	R16Sint(6u),
	R16Float(7u),
	RG8Unorm(8u),
	RG8Snorm(9u),
	RG8Uint(10u),
	RG8Sint(11u),
	R32Float(12u),
	R32Uint(13u),
	R32Sint(14u),
	RG16Uint(15u),
	RG16Sint(16u),
	RG16Float(17u),
	RGBA8Unorm(18u),
	RGBA8UnormSrgb(19u),
	RGBA8Snorm(20u),
	RGBA8Uint(21u),
	RGBA8Sint(22u),
	BGRA8Unorm(23u),
	BGRA8UnormSrgb(24u),
	RGB10A2Uint(25u),
	RGB10A2Unorm(26u),
	RG11B10Ufloat(27u),
	RGB9E5Ufloat(28u),
	RG32Float(29u),
	RG32Uint(30u),
	RG32Sint(31u),
	RGBA16Uint(32u),
	RGBA16Sint(33u),
	RGBA16Float(34u),
	RGBA32Float(35u),
	RGBA32Uint(36u),
	RGBA32Sint(37u),
	Stencil8(38u),
	Depth16Unorm(39u),
	Depth24Plus(40u),
	Depth24PlusStencil8(41u),
	Depth32Float(42u),
	Depth32FloatStencil8(43u),
	BC1RGBAUnorm(44u),
	BC1RGBAUnormSrgb(45u),
	BC2RGBAUnorm(46u),
	BC2RGBAUnormSrgb(47u),
	BC3RGBAUnorm(48u),
	BC3RGBAUnormSrgb(49u),
	BC4RUnorm(50u),
	BC4RSnorm(51u),
	BC5RGUnorm(52u),
	BC5RGSnorm(53u),
	BC6HRGBUfloat(54u),
	BC6HRGBFloat(55u),
	BC7RGBAUnorm(56u),
	BC7RGBAUnormSrgb(57u),
	ETC2RGB8Unorm(58u),
	ETC2RGB8UnormSrgb(59u),
	ETC2RGB8A1Unorm(60u),
	ETC2RGB8A1UnormSrgb(61u),
	ETC2RGBA8Unorm(62u),
	ETC2RGBA8UnormSrgb(63u),
	EACR11Unorm(64u),
	EACR11Snorm(65u),
	EACRG11Unorm(66u),
	EACRG11Snorm(67u),
	ASTC4x4Unorm(68u),
	ASTC4x4UnormSrgb(69u),
	ASTC5x4Unorm(70u),
	ASTC5x4UnormSrgb(71u),
	ASTC5x5Unorm(72u),
	ASTC5x5UnormSrgb(73u),
	ASTC6x5Unorm(74u),
	ASTC6x5UnormSrgb(75u),
	ASTC6x6Unorm(76u),
	ASTC6x6UnormSrgb(77u),
	ASTC8x5Unorm(78u),
	ASTC8x5UnormSrgb(79u),
	ASTC8x6Unorm(80u),
	ASTC8x6UnormSrgb(81u),
	ASTC8x8Unorm(82u),
	ASTC8x8UnormSrgb(83u),
	ASTC10x5Unorm(84u),
	ASTC10x5UnormSrgb(85u),
	ASTC10x6Unorm(86u),
	ASTC10x6UnormSrgb(87u),
	ASTC10x8Unorm(88u),
	ASTC10x8UnormSrgb(89u),
	ASTC10x10Unorm(90u),
	ASTC10x10UnormSrgb(91u),
	ASTC12x10Unorm(92u),
	ASTC12x10UnormSrgb(93u),
	ASTC12x12Unorm(94u),
	ASTC12x12UnormSrgb(95u);


	companion object {
		/**
		 * Retrieves the corresponding [UInt] for the given value.
		 *
		 * @param value The dependent platform value representing the WebGPU value.
		 * @return The matching [UInt] or `null` if no match is found.
		 */
		fun of(value: UInt): GPUTextureFormat? {
			return entries.find { it.value == value }
		}
    }

}

actual enum class GPUTextureSampleType(val value: UInt) {
	BindingNotUsed(1u),
	Float(3u),
	UnfilterableFloat(4u),
	Depth(5u),
	Sint(6u),
	Uint(7u);


	companion object {
		/**
		 * Retrieves the corresponding [UInt] for the given value.
		 *
		 * @param value The dependent platform value representing the WebGPU value.
		 * @return The matching [UInt] or `null` if no match is found.
		 */
		fun of(value: UInt): GPUTextureSampleType? {
			return entries.find { it.value == value }
		}
    }

}

actual enum class GPUTextureViewDimension(val value: UInt) {
	OneD(1u),
	TwoD(2u),
	TwoDArray(3u),
	Cube(4u),
	CubeArray(5u),
	ThreeD(6u);


	companion object {
		/**
		 * Retrieves the corresponding [UInt] for the given value.
		 *
		 * @param value The dependent platform value representing the WebGPU value.
		 * @return The matching [UInt] or `null` if no match is found.
		 */
		fun of(value: UInt): GPUTextureViewDimension? {
			return entries.find { it.value == value }
		}
    }

}

actual enum class GPUVertexFormat(val value: UInt) {
	Uint8(1u),
	Uint8x2(2u),
	Uint8x4(3u),
	Sint8(4u),
	Sint8x2(5u),
	Sint8x4(6u),
	Unorm8(7u),
	Unorm8x2(8u),
	Unorm8x4(9u),
	Snorm8(10u),
	Snorm8x2(11u),
	Snorm8x4(12u),
	Uint16(13u),
	Uint16x2(14u),
	Uint16x4(15u),
	Sint16(16u),
	Sint16x2(17u),
	Sint16x4(18u),
	Unorm16(19u),
	Unorm16x2(20u),
	Unorm16x4(21u),
	Snorm16(22u),
	Snorm16x2(23u),
	Snorm16x4(24u),
	Float16(25u),
	Float16x2(26u),
	Float16x4(27u),
	Float32(28u),
	Float32x2(29u),
	Float32x3(30u),
	Float32x4(31u),
	Uint32(32u),
	Uint32x2(33u),
	Uint32x3(34u),
	Uint32x4(35u),
	Sint32(36u),
	Sint32x2(37u),
	Sint32x3(38u),
	Sint32x4(39u),
	Unorm1010102(40u),
	Unorm8x4BGRA(41u);


	companion object {
		/**
		 * Retrieves the corresponding [UInt] for the given value.
		 *
		 * @param value The dependent platform value representing the WebGPU value.
		 * @return The matching [UInt] or `null` if no match is found.
		 */
		fun of(value: UInt): GPUVertexFormat? {
			return entries.find { it.value == value }
		}
    }

}

actual enum class GPUVertexStepMode(val value: UInt) {
	VertexBufferNotUsed(1u),
	Vertex(3u),
	Instance(4u);


	companion object {
		/**
		 * Retrieves the corresponding [UInt] for the given value.
		 *
		 * @param value The dependent platform value representing the WebGPU value.
		 * @return The matching [UInt] or `null` if no match is found.
		 */
		fun of(value: UInt): GPUVertexStepMode? {
			return entries.find { it.value == value }
		}
    }

}
