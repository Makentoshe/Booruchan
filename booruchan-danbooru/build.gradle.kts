plugins {
    id("org.jetbrains.kotlin.jvm")
}

group = "com.makentoshe.booruchan.danbooru"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // check should we request jar dependencies
    // building from jar allows us to avoid rebuilding 'core' module each time
    // and reuse the same artifacts if they were not modified
    if (project.hasProperty("modular")) {
        // for ci/cd build
        implementation(fileTree(mapOf("dir" to "libs", "include" to arrayOf("*.jar"))))
    } else {
        // for gradle/ide build
        implementation(project(":booruchan-core"))
    }

    implementation("org.jetbrains.kotlin:kotlin-stdlib")
}
