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
            val wrappersVersion = "2025.12.6"
            from("org.jetbrains.kotlin-wrappers:kotlin-wrappers-catalog:$wrappersVersion")
        }
    }
}

include("webgpu-ktypes")
include("webgpu-ktypes-descriptors")
include("webgpu-ktypes-web")
include("webgpu-ktypes-specifications")

// WGSL Shader Transpiler modules
listOf("core", "parser", "generator", "cli", "tests").forEach { project ->
    include(":wgsl:$project")
    project(":wgsl:$project").projectDir = file("wgsl/$project")
}
