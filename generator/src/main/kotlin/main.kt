import tasks.ModelGenerator
import tasks.ModelWriter


fun main() {
    val context = ModelGenerator.context
    ModelWriter.write(context)
}
