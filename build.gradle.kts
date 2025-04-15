
plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.android.library) apply false
    generator
}

allprojects {
    group = "io.ygdrasil"
    version = System.getenv("VERSION")?.takeIf { it.isNotBlank() } ?: "0.0.5-SNAPSHOT"
}