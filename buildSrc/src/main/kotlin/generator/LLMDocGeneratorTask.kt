package generator

import generator.files.RemoteFileManager
import generator.lm.DocumentGeneratorManager
import generator.tasks.ModelGenerator
import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class LLMDocGeneratorTask : DefaultTask() {

    init {
        group = "generator"
    }

    @TaskAction
    fun launch() = runBlocking {
        val remoteFileManager = RemoteFileManager(project.projectDir.toPath())
        val context = ModelGenerator(remoteFileManager).context
        val htmlDocumentation = remoteFileManager.findFilePath(RemoteFileManager.Files.webgpuHtml)
            ?: error("Cannot find the html documentation")
         DocumentGeneratorManager(context, remoteFileManager, htmlDocumentation, logger)
            .also { it.inferHtmlDocumentation() }
    }
}
