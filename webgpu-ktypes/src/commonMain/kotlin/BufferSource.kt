interface GPUBufferSource {

    companion object {
        /**
         * Creates a [GPUBufferSource] using a [GPUBufferSourceProvider] and a variable number of bytes.
         *
         * @param builder The [GPUBufferSourceProvider] to create the buffer source.
         * @param bytes The bytes to be used for creating the [GPUBufferSource].
         * @return A new [GPUBufferSource] instance.
         */
        fun of(builder: GPUBufferSourceProvider, vararg bytes: Byte): GPUBufferSource = builder.of(*bytes)

        /**
         * Creates a [GPUBufferSource] using a [GPUBufferSourceProvider] and a byte array.
         *
         * @param builder The [GPUBufferSourceProvider] to create the buffer source.
         * @param data The byte array to be used for creating the [GPUBufferSource].
         * @return A new [GPUBufferSource] instance.
         */
        fun of(builder: GPUBufferSourceProvider, data: ByteArray): GPUBufferSource = builder.of(*data)
    }

}

interface GPUBufferSourceProvider {

    /**
     * Creates a [GPUBufferSource] using a variable number of bytes.
     *
     * @param bytes The bytes to be used for creating the [GPUBufferSource].
     * @return A new [GPUBufferSource] instance.
     */
    fun of(vararg bytes: Byte): GPUBufferSource

    /**
     * Creates a [GPUBufferSource] using a byte array.
     *
     * @param data The byte array to be used for creating the [GPUBufferSource].
     * @return A new [GPUBufferSource] instance.
     */
    fun of(data: ByteArray): GPUBufferSource
}


