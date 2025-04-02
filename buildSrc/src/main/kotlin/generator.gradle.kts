import generator.tasks.ModelGenerator
import generator.tasks.ModelWriter

tasks.register("generate-binding") {
    group = "generator"
    doLast {
        val context = ModelGenerator.context
        ModelWriter.write(context)
    }
}
