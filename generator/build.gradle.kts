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


    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    implementation("org.jsoup:jsoup:1.17.2")


}

kotlin {
    jvmToolchain(21)
}