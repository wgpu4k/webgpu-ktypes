import generator.CheckCacheTask
import generator.CheckMissingDocumentationTask
import generator.GenerateBindingTask
import generator.LLMDocGeneratorTask
import generator.files.RemoteFileManager
import generator.tasks.ModelGenerator
import generator.tasks.ModelWriter

tasks.register<GenerateBindingTask>("generate-binding")
tasks.register<CheckCacheTask>("check-cache")
tasks.register<LLMDocGeneratorTask>("generate-doc-from-llm")
tasks.register<CheckMissingDocumentationTask>("check-missing-doc")