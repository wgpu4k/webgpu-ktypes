package generator.mapper

import generator.domain.KDoc
import generator.domain.MapperContext

internal fun MapperContext.injectDocumentation(documentation: Map<String, String>) {
    interfaces.forEach { kInterface ->
        documentation.get(kInterface.name)?.let {
            kInterface.kDoc = KDoc(it)
        }
    }
}