plugins {
    publish
    kotlin("multiplatform")
}

kotlin {
    js {
        // TODO remove when poc ended
        binaries.executable()
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
        nodejs()

    }

    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        // TODO remove when poc ended
        binaries.executable()
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
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
        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        commonMain {
            dependencies {
                implementation(libs.coroutines)
            }
        }

        jsTest {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }

        wasmJsTest {
            dependencies {
                implementation(kotlin("test-wasm-js"))
            }
        }

    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
