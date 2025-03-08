plugins {
	`kotlin-dsl`
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
}

