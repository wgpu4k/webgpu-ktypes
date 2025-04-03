package generator.files

import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileInputStream
import java.net.URI
import java.net.URL
import java.nio.file.Path
import java.nio.file.Paths
import java.security.MessageDigest
import java.time.LocalDateTime
import kotlin.io.path.absolutePathString

class RemoteFileManager(private val basePath: Path) {

    object Urls {
        val webgpuHtml = URI("https://www.w3.org/TR/webgpu/").toURL()
        val webgpuIdl = URI("https://gpuweb.github.io/gpuweb/webgpu.idl").toURL()
    }

    object Files {
        val webgpuHtml = "webgpu.html"
        val webgpuIdl = "webgpu.idl"
        val documentation = "documentation.json"
    }

    val specificationsSourcePath by lazy {
        basePath.resolve("webgpu-ktypes-specifications").resolve("src").resolve("jvmMain").resolve("resources")
    }
    private val cachePath by lazy {
        specificationsSourcePath.resolve("cache.json").absolutePathString()
            .also { println("Using cache file at $it") }
    }

    var fileCache: FileCache = loadFileCache()
    val files = listOf(
        Files.webgpuHtml to Urls.webgpuHtml,
        Files.webgpuIdl to Urls.webgpuIdl,
    )

    fun checkCache() {
        val currentDate = LocalDateTime.now()
        fun isOldCache(cacheFile: FileCache.CachedFile): Boolean = cacheFile.updateDate.plusDays(1) < currentDate

        files.forEach { (fileName, url) ->
            val cacheFile = fileCache.findFile(fileName)
            when (cacheFile) {
                null -> updateCache(fileName, url)
                else -> if (isOldCache(cacheFile)) checkCache(fileName, url, cacheFile) else println("Cached file $fileName is up to date")
            }
        }
    }

    private fun checkCache(fileName: String, uRL: URL, cacheFile: FileCache.CachedFile) {
        val tempFile = File.createTempFile("temp", ".tmp")
        uRL.downloadToPath(tempFile.toPath())
        if (cacheFile.hash != tempFile.calculateHash()) {
            updateCache(fileName, uRL)
        }
        tempFile.delete()
    }

    private fun updateCache(fileName: String, uRL: URL) {
        val targetPath = specificationsSourcePath.resolve(fileName)
        uRL.downloadToPath(targetPath)
        val hash = File(targetPath.absolutePathString()).calculateHash()
        fileCache = fileCache.addFile(fileName, hash)
        saveFileCache(fileCache, cachePath)
    }

    private fun loadFileCache() = loadFileCache(cachePath)
        .getOrElse { FileCache(emptyList()).also { saveFileCache(it, cachePath)} }

    private fun saveFileCache() {
        saveFileCache(fileCache, cachePath)
    }

    private fun saveFileCache(fileCache: FileCache, filePath: String) {
        val jsonString = Json.Default.encodeToString(fileCache)
        java.nio.file.Files.write(Paths.get(filePath), jsonString.toByteArray())
    }

    private fun loadFileCache(filePath: String): Result<FileCache> = runCatching {
        val jsonString = java.nio.file.Files.readString(Paths.get(filePath))
        Json.Default.decodeFromString<FileCache>(jsonString)
    }

    private fun File.calculateHash(algorithm: String = "SHA-256"): String {
        val digest = MessageDigest.getInstance(algorithm)
        FileInputStream(this).use { fis ->
            val buffer = ByteArray(8192)
            var bytesRead: Int
            while (fis.read(buffer).also { bytesRead = it } != -1) {
                digest.update(buffer, 0, bytesRead)
            }
        }

        return digest.digest().joinToString("") { "%02x".format(it) }
    }


    private fun URL.downloadToPath(paths: Path) {
        if (java.nio.file.Files.exists(paths)) return
        runCatching {
            openStream().use { inputStream ->
                java.nio.file.Files.newOutputStream(paths).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        }.onFailure { error(it)}
    }

    fun findFile(fileName: String): FileCache.CachedFile? {
        return fileCache.findFile(fileName)
    }

    fun findFilePath(fileName: String): Path? {
        return fileCache.findFile(fileName)?.let { specificationsSourcePath.resolve(it.name) }
    }

}
