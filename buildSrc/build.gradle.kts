plugins {
	`kotlin-dsl`
	alias(libs.plugins.kotlin.serialization)
}


repositories {
	gradlePluginPortal()
	google()
	mavenCentral()
}

dependencies {
	implementation(libs.okhttp)
	implementation(libs.zip4j)
	implementation(libs.dokka)
	implementation(libs.commons.io)

	implementation(libs.webidl.util)
	implementation(libs.kaml)
	implementation(libs.wgpu.specs)

	implementation(libs.ktor.client.core)
	implementation(libs.ktor.client.cio)
	implementation(libs.ktor.serialization.kotlinx.json)
	implementation(libs.ktor.client.content.negotiation)

	implementation(libs.kotlinx.serialization.json)
	implementation(libs.jsoup)
    implementation(libs.kotlinpoet)

	implementation(libs.coroutines)

}
