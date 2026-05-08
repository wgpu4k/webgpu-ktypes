@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import com.android.build.api.dsl.androidLibrary
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    publish
    kotlin("multiplatform")
    alias(libs.plugins.android.library)
}

kotlin {
    js {
        browser()
        nodejs()
    }

    jvm {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_25
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()
    watchosArm64()
    watchosSimulatorArm64()
    watchosX64()
    macosArm64()
    macosX64()
    linuxArm64()
    linuxX64()
    mingwX64()
    androidNativeArm64()
    androidNativeX64()

    androidLibrary {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }

        namespace = "io.ygdrasil.webgpu.ktypes.descriptors"
        compileSdk = 36
        minSdk = 28
    }


    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        nodejs()
    }

    compilerOptions {
        allWarningsAsErrors = true
    }

    sourceSets.commonMain.dependencies {
        implementation(project(":webgpu-ktypes"))
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}
