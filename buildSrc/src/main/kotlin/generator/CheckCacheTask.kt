package generator

import generator.files.RemoteFileManager
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class CheckCacheTask : DefaultTask() {

    init {
        group = "generator"
    }

    @TaskAction
    fun launch() {
        RemoteFileManager(project.projectDir.toPath())
            .checkCache()
    }
}