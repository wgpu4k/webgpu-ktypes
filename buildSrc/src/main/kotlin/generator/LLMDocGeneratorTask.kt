package generator

import generator.files.RemoteFileManager
import generator.lm.DocumentGeneratorManager
import generator.tasks.ModelGenerator
import kotlinx.coroutines.runBlocking
import org.gradle.api.tasks.TaskAction
import org.jsoup.Jsoup
import publish.AbstractTask

open class LLMDocGeneratorTask : AbstractTask() {

    init {
        group = "generator"
    }

    @TaskAction
    fun launch() = runBlocking {
        val remoteFileManager = RemoteFileManager(project.projectDir.toPath())
        val context = ModelGenerator(remoteFileManager).context
        val htmlDocumentation = remoteFileManager.findFilePath(RemoteFileManager.Files.webgpuHtml)
            ?: error("Cannot find the html documentation")
        val documentGeneratorManager = DocumentGeneratorManager(context, htmlDocumentation)
            .also { it.inferDocumentation() }
    }
}
