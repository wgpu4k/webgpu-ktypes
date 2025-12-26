@file:OptIn(ExperimentalUnsignedTypes::class)

package io.ygdrasil.webgpu

/**
 * A Kotlin/JS value class that serves as a wrapper for the JavaScript `ArrayBuffer`.
 *
 * This class allows for interoperability between Kotlin and JavaScript by embedding
 * the `js.buffer.ArrayBuffer` instance, enabling the handling of binary data in
 * scenarios such as Web API interactions, file operations, or low-level binary data
 * processing.
 *
 * @property buffer The underlying `js.buffer.ArrayBuffer` instance being wrapped.
 */
value class WebArrayBuffer internal constructor(val buffer: js.buffer.ArrayBuffer): ArrayBuffer {
    override val size: ULong
        get() = buffer.byteLength.toULong()

    // Read methods - convert entire buffer to typed arrays
    override fun toByteArray(): ByteArray = buffer.readByteArray()
    override fun toShortArray(): ShortArray = buffer.readShortArray()
    override fun toIntArray(): IntArray = buffer.readIntArray()
    override fun toFloatArray(): FloatArray = buffer.readFloatArray()
    override fun toDoubleArray(): DoubleArray = buffer.readDoubleArray()
    override fun toUByteArray(): UByteArray = buffer.readUByteArray()
    override fun toUShortArray(): UShortArray = buffer.readUShortArray()
    override fun toUIntArray(): UIntArray = buffer.readUIntArray()

    // Indexed read methods
    override fun getByte(offset: Int): Byte = buffer.readByte(offset)
    override fun getShort(offset: Int): Short = buffer.readShort(offset)
    override fun getInt(offset: Int): Int = buffer.readInt(offset)
    override fun getFloat(offset: Int): Float = buffer.readFloat(offset)
    override fun getDouble(offset: Int): Double = buffer.readDouble(offset)
    override fun getUByte(offset: Int): UByte = buffer.readUByte(offset)
    override fun getUShort(offset: Int): UShort = buffer.readUShort(offset)
    override fun getUInt(offset: Int): UInt = buffer.readUInt(offset)

    // Indexed write methods
    override fun setByte(offset: Int, value: Byte) = buffer.writeByte(offset, value)
    override fun setShort(offset: Int, value: Short) = buffer.writeShort(offset, value)
    override fun setInt(offset: Int, value: Int) = buffer.writeInt(offset, value)
    override fun setFloat(offset: Int, value: Float) = buffer.writeFloat(offset, value)
    override fun setDouble(offset: Int, value: Double) = buffer.writeDouble(offset, value)
    override fun setUByte(offset: Int, value: UByte) = buffer.writeUByte(offset, value)
    override fun setUShort(offset: Int, value: UShort) = buffer.writeUShort(offset, value)
    override fun setUInt(offset: Int, value: UInt) = buffer.writeUInt(offset, value)
}