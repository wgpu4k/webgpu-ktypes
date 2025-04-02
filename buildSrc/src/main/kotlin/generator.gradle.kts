import generator.CheckCacheTask
import generator.GenerateBindingTask
import generator.files.RemoteFileManager
import generator.tasks.ModelGenerator
import generator.tasks.ModelWriter

tasks.register<GenerateBindingTask>("generate-binding")
tasks.register<CheckCacheTask>("check-cache")