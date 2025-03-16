import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    publish
    kotlin("multiplatform")
}

kotlin {
    js {
        // TODO remove when poc ended
        binaries.executable()
        browser()
        nodejs()
    }

    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        // TODO remove when poc ended
        binaries.executable()
        browser()
        nodejs()
    }

    compilerOptions {
        allWarningsAsErrors = true
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    sourceSets.commonMain.dependencies {
        implementation(project(":webgpu-ktypes"))
    }
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.coroutines)
            }
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
