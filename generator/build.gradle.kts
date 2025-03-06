plugins {
    `kotlin-dsl`
    kotlin("plugin.serialization") version "2.1.0"
}

repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
    mavenLocal()
    //wgpu4k snapshot & preview repository
    maven("https://gitlab.com/api/v4/projects/25805863/packages/maven")
}


dependencies {
    implementation(libs.webidl.util)
    implementation(libs.kaml)
    implementation(libs.wgpu.specs)
}

kotlin {
    jvmToolchain(21)
}