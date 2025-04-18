rootProject.name = "webgpu-ktypes-root"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        google()
        mavenCentral()

    }
}

include("webgpu-ktypes")
include("webgpu-ktypes-descriptors")
include("webgpu-ktypes-web")
include("webgpu-ktypes-specifications")
