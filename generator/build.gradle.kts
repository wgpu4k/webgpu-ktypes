plugins {
    kotlin("jvm")
}

dependencies {
    implementation("de.fabmax:webidl-util:0.10.0")
}

kotlin {
    jvmToolchain(21)
}