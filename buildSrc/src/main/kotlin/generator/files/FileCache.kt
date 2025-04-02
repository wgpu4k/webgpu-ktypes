package generator.files

import kotlinx.serialization.Serializable
import java.io.File
import java.time.LocalDateTime

@kotlinx.serialization.Serializable
data class FileCache(
    val cachedFiles: List<CachedFile>
) {

    @kotlinx.serialization.Serializable
    data class CachedFile(
        val name: String,
        val hash: String,
        @kotlinx.serialization.Serializable(with = LocalDateTimeSerializer::class)
        val updateDate: LocalDateTime
    )

    fun findFile(name: String) = cachedFiles.find { File(it.name).name == name }
    fun addFile(name: String, hash: String) = FileCache(cachedFiles + CachedFile(name, hash, LocalDateTime.now()))
}