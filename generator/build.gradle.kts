plugins {
    `kotlin-dsl`
    kotlin("plugin.serialization") version "2.1.20"
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

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.content.negotiation)


    implementation(libs.kotlinx.serialization.json)
    implementation(libs.jsoup)


}

kotlin {
    jvmToolchain(21)
}