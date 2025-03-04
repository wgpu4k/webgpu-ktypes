plugins {
    id("org.jetbrains.kotlin.jvm") version "2.1.10"
    id("com.android.library") version "8.2.2" apply false
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("de.fabmax:webidl-util:0.10.0")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}