package generator

import generator.files.RemoteFileManager
import generator.tasks.ModelGenerator
import generator.tasks.ModelWriter
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class GenerateBindingTask : DefaultTask() {

    init {
        group = "generator"
    }

    @TaskAction
    fun launch() {
        val remoteFileManager = RemoteFileManager(project.projectDir.toPath())
        val context = ModelGenerator(remoteFileManager).context
        ModelWriter.write(context)
    }
}