plugins {
    id("org.jetbrains.kotlin.jvm")
}

group = "com.makentoshe.booruchan.gelbooru"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // check should we request jar dependencies
    if (project.hasProperty("modular")) {
        implementation(fileTree(mapOf("dir" to "libs", "include" to arrayOf("*.jar"))))
    } else {
        implementation(project(":booruchan-core"))
    }

    implementation("org.jetbrains.kotlin:kotlin-stdlib")
}
