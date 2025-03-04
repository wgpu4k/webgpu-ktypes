import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
    js {
        browser()
        nodejs()
    }

    jvm {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_21
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()
    watchosArm32()
    watchosArm64()
    watchosSimulatorArm64()
    watchosX64()
    macosArm64()
    macosX64()
    linuxArm64()
    linuxX64()
    mingwX64()
    androidNativeArm32()
    androidNativeArm64()
    androidNativeX86()
    androidNativeX64()

    androidTarget {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_21
        }

        publishLibraryVariants("release", "debug")
    }


    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        nodejs()
    }
}

android {
    namespace = "io.ygdrasil.webgpu.ktypes"
    compileSdk = 35

    defaultConfig {
        minSdk = 28
    }

}
