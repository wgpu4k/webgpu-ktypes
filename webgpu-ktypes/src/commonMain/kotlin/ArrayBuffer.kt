@file:OptIn(ExperimentalUnsignedTypes::class)

package io.ygdrasil.webgpu


/**
 * Represents a buffer of raw binary data, which can be used in various WebGPU operations.
 * Provides a mechanism to work directly with raw memory for performance-critical computations
 * or data manipulation when working with GPU resources.
 *
 * This class is an abstraction for handling binary data in a format compatible with WebGPU,
 * allowing interoperability with GPU resources like buffers and textures.
 */
expect sealed interface ArrayBuffer {
    companion object {
        /**
         * Creates an ArrayBuffer from a ByteArray.
         * @param array the byte array to convert
         * @return an ArrayBuffer containing the data from the byte array
         */
        fun from(array: ByteArray): ArrayBuffer

        /**
         * Creates an ArrayBuffer from a ShortArray.
         * @param array the short array to convert
         * @return an ArrayBuffer containing the data from the short array
         */
        fun from(array: ShortArray): ArrayBuffer

        /**
         * Creates an ArrayBuffer from an IntArray.
         * @param array the int array to convert
         * @return an ArrayBuffer containing the data from the int array
         */
        fun from(array: IntArray): ArrayBuffer

        /**
         * Creates an ArrayBuffer from a LongArray.
         * @param array the long array to convert
         * @return an ArrayBuffer containing the data from the long array
         */
        fun from(array: LongArray): ArrayBuffer

        /**
         * Creates an ArrayBuffer from a FloatArray.
         * @param array the float array to convert
         * @return an ArrayBuffer containing the data from the float array
         */
        fun from(array: FloatArray): ArrayBuffer

        /**
         * Creates an ArrayBuffer from a DoubleArray.
         * @param array the double array to convert
         * @return an ArrayBuffer containing the data from the double array
         */
        fun from(array: DoubleArray): ArrayBuffer

        /**
         * Creates an ArrayBuffer from a UByteArray.
         * @param array the unsigned byte array to convert
         * @return an ArrayBuffer containing the data from the unsigned byte array
         */
        fun from(array: UByteArray): ArrayBuffer

        /**
         * Creates an ArrayBuffer from a UShortArray.
         * @param array the unsigned short array to convert
         * @return an ArrayBuffer containing the data from the unsigned short array
         */
        fun from(array: UShortArray): ArrayBuffer

        /**
         * Creates an ArrayBuffer from a UIntArray.
         * @param array the unsigned int array to convert
         * @return an ArrayBuffer containing the data from the unsigned int array
         */
        fun from(array: UIntArray): ArrayBuffer

        /**
         * Creates an ArrayBuffer from a ULongArray.
         * @param array the unsigned long array to convert
         * @return an ArrayBuffer containing the data from the unsigned long array
         */
        fun from(array: ULongArray): ArrayBuffer
    }
}