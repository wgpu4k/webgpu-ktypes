@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("multiplatform")
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotest)
}

kotlin {
    jvm {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_25
        }
    }

    androidLibrary {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
        namespace = "io.ygdrasil.wgsl.tests"
        compileSdk = 36
        minSdk = 28
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":wgsl:core"))
                implementation(project(":wgsl:parser"))
                implementation(project(":wgsl:generator"))
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.bundles.kotest)
            }
        }

        jvmTest {
            dependencies {
                implementation(libs.kotest.runner.junit5)
                implementation(libs.kotlin.logging)
                implementation(libs.logback.classic)
            }
        }
    }
}

tasks.named<Test>("jvmTest") {
    useJUnitPlatform()
    val goldenUpdate = System.getenv("GOLDEN_UPDATE")?.toBoolean() ?: false
    val showFullExceptions = System.getenv("GOLDEN_DEBUG")?.toBoolean() ?: false
    testLogging {
        showExceptions = showFullExceptions
        showStandardStreams = false
        events = setOf(
            org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
        )
        exceptionFormat = if (goldenUpdate || showFullExceptions) {
            org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        } else {
            org.gradle.api.tasks.testing.logging.TestExceptionFormat.SHORT
        }
    }
    
    // Configure logback for tests
    systemProperty("logback.configurationFile", layout.projectDirectory.dir("src/jvmTest/resources").file("logback-test.xml").asFile.path)
}

// Task to run the golden debug tool
val goldenDebug by tasks.creating(JavaExec::class) {
    group = "debug"
    description = "Run golden test debugger on a single file"
    
    classpath = sourceSets["jvmTest"].runtimeClasspath
    mainClass = "io.ygdrasil.wgsl.tests.GoldenDebugKt"
    
    // Pass arguments to the main function
    args = project.properties.getOrDefault("args", "").toString().split("\\s+".toRegex())
    
    // Ensure the JVM arguments include the classpath
    jvmArgs = listOf("-Dfile.encoding=UTF-8", "-Dlogback.configurationFile=${layout.projectDirectory.dir("src/jvmTest/resources")}/logback-test.xml")
}
