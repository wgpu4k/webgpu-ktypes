@file:Suppress("unused")
// This file has been generated DO NO EDIT
package io.ygdrasil.webgpu

enum class GPUBufferUsage(override val value: ULong): FlagEnumeration {
	None(0uL),
	MapRead(1uL),
	MapWrite(2uL),
	CopySrc(4uL),
	CopyDst(8uL),
	Index(16uL),
	Vertex(32uL),
	Uniform(64uL),
	Storage(128uL),
	Indirect(256uL),
	QueryResolve(512uL);
}

enum class GPUColorWrite(override val value: ULong): FlagEnumeration {
	None(0uL),
	Red(1uL),
	Green(2uL),
	Blue(4uL),
	Alpha(8uL),
	All(15uL);
}

enum class GPUMapMode(override val value: ULong): FlagEnumeration {
	None(0uL),
	Read(1uL),
	Write(2uL);
}

enum class GPUShaderStage(override val value: ULong): FlagEnumeration {
	None(0uL),
	Vertex(1uL),
	Fragment(2uL),
	Compute(4uL);
}

enum class GPUTextureUsage(override val value: ULong): FlagEnumeration {
	None(0uL),
	CopySrc(1uL),
	CopyDst(2uL),
	TextureBinding(4uL),
	StorageBinding(8uL),
	RenderAttachment(16uL);
}
