package io.ygdrasil.webgpu

/**
 * Represents a source of GPU buffers that can be used within a graphics or compute pipeline.
 * This interface acts as a common abstraction for managing GPU-related data sources.
 */
interface GPUBufferSource {

    companion object {

        /**
         * Creates a [GPUBufferSource] using a [GPUBufferSourceProvider] and a byte array.
         *
         * @param builder The [GPUBufferSourceProvider] to create the buffer source.
         * @param data The byte array to be used for creating the [GPUBufferSource].
         * @return A new [GPUBufferSource] instance.
         */
        fun of(builder: GPUBufferSourceProvider, data: ByteArray): GPUBufferSource = builder.of(data)

        /**
         * Creates a [GPUBufferSource] using a [GPUBufferSourceProvider] and an integer array.
         *
         * @param builder The [GPUBufferSourceProvider] to create the buffer source.
         * @param data The integer array to be used for creating the [GPUBufferSource].
         * @return A new [GPUBufferSource] instance.
         */
        fun of(builder: GPUBufferSourceProvider, data: IntArray): GPUBufferSource = builder.of(data)
    }

}

interface GPUBufferSourceProvider {

    /**
     * Creates a [GPUBufferSource] using a byte array.
     *
     * @param data The byte array to be used for creating the [GPUBufferSource].
     * @return A new [GPUBufferSource] instance.
     */
    fun of(data: ByteArray): GPUBufferSource

    /**
     * Creates a [GPUBufferSource] using an integer array.
     *
     * @param data The integer array to be used for creating the [GPUBufferSource].
     * @return A new [GPUBufferSource] instance.
     */
    fun of(data: IntArray): GPUBufferSource
}


