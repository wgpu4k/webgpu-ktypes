@file:OptIn(ExperimentalUnsignedTypes::class)

package io.ygdrasil.webgpu

import java.nio.ByteBuffer

/**
 * An Android-specific implementation of the `ArrayBuffer` interface.
 *
 * `AndroidArrayBuffer` provides a lightweight wrapper around a direct `ByteBuffer`,
 * allowing Android platforms to manage, access, and manipulate raw binary data in a way
 * that conforms to the `ArrayBuffer` abstraction.
 *
 * This class leverages the `@JvmInline` annotation, making it a value class. This ensures
 * minimal runtime overhead and allows the `ByteBuffer` instance to be used with improved
 * performance due to inlining and reduced object allocations.
 *
 * @param buffer The underlying direct `ByteBuffer` instance that serves as the basis for this array buffer.
 * @throws IllegalStateException if the provided ByteBuffer is not a direct buffer
 */
@JvmInline
value class AndroidArrayBuffer internal constructor(val buffer: ByteBuffer): ArrayBuffer {
    init {
        if (buffer.isDirect.not()) error("ByteBuffer must be direct")
    }

    override val size: ULong
        get() = buffer.capacity().toULong()

    // Read methods - convert entire buffer to typed arrays

    override fun toByteArray(): ByteArray {
        val array = ByteArray(buffer.capacity())
        buffer.duplicate().get(array)
        return array
    }

    override fun toShortArray(): ShortArray {
        val array = ShortArray(buffer.capacity() / Short.SIZE_BYTES)
        buffer.duplicate().asShortBuffer().get(array)
        return array
    }

    override fun toIntArray(): IntArray {
        val array = IntArray(buffer.capacity() / Int.SIZE_BYTES)
        buffer.duplicate().asIntBuffer().get(array)
        return array
    }

    override fun toFloatArray(): FloatArray {
        val array = FloatArray(buffer.capacity() / Float.SIZE_BYTES)
        buffer.duplicate().asFloatBuffer().get(array)
        return array
    }

    override fun toDoubleArray(): DoubleArray {
        val array = DoubleArray(buffer.capacity() / Double.SIZE_BYTES)
        buffer.duplicate().asDoubleBuffer().get(array)
        return array
    }

    override fun toUByteArray(): UByteArray = toByteArray().asUByteArray()

    override fun toUShortArray(): UShortArray = toShortArray().asUShortArray()

    override fun toUIntArray(): UIntArray = toIntArray().asUIntArray()

    // Indexed read methods

    override fun getByte(offset: Int): Byte = buffer.get(offset)

    override fun getShort(offset: Int): Short = buffer.getShort(offset)

    override fun getInt(offset: Int): Int = buffer.getInt(offset)

    override fun getFloat(offset: Int): Float = buffer.getFloat(offset)

    override fun getDouble(offset: Int): Double = buffer.getDouble(offset)

    override fun getUByte(offset: Int): UByte = getByte(offset).toUByte()

    override fun getUShort(offset: Int): UShort = getShort(offset).toUShort()

    override fun getUInt(offset: Int): UInt = getInt(offset).toUInt()

    // Indexed write methods

    override fun setByte(offset: Int, value: Byte) {
        buffer.put(offset, value)
    }

    override fun setShort(offset: Int, value: Short) {
        buffer.putShort(offset, value)
    }

    override fun setInt(offset: Int, value: Int) {
        buffer.putInt(offset, value)
    }

    override fun setFloat(offset: Int, value: Float) {
        buffer.putFloat(offset, value)
    }

    override fun setDouble(offset: Int, value: Double) {
        buffer.putDouble(offset, value)
    }

    override fun setUByte(offset: Int, value: UByte) {
        setByte(offset, value.toByte())
    }

    override fun setUShort(offset: Int, value: UShort) {
        setShort(offset, value.toShort())
    }

    override fun setUInt(offset: Int, value: UInt) {
        setInt(offset, value.toInt())
    }

}