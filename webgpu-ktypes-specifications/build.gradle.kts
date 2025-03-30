plugins {
    publish
    kotlin("multiplatform")
}

kotlin {
    jvm()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
