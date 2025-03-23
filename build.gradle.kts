plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.android.library) apply false
}

allprojects {
    group = "io.ygdrasil"
    version = System.getenv("VERSION")?.takeIf { it.isNotBlank() } ?: "0.0.1-SNAPSHOT"
}
