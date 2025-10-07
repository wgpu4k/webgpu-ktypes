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

    versionCatalogs {
        create("kotlinWrappers") {
            val wrappersVersion = "2025.9.8"
            from("org.jetbrains.kotlin-wrappers:kotlin-wrappers-catalog:$wrappersVersion")
        }
    }
}

include("webgpu-ktypes")
include("webgpu-ktypes-descriptors")
include("webgpu-ktypes-web")
include("webgpu-ktypes-specifications")
