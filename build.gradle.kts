plugins {
    generator
}

allprojects {
    group = "io.ygdrasil"
    version = (findProperty("releaseVersion") as? String)
        ?.takeIf { it.isNotBlank() }
        ?: "0.0.10-SNAPSHOT"
}
