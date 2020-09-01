plugins {
    id("org.jetbrains.kotlin.jvm")
    kotlin("plugin.serialization") version "1.4.0"
}

group = "com.makentoshe.booruchan.gelbooru"
version = "1.0"

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
        implementation(project(":core"))
    }

    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    // Jsoup (xml parsing)
    // https://jsoup.org/download
    implementation("org.jsoup:jsoup:1.13.1")

    // Kotlinx Serialization (serialization plugin)
    // https://github.com/Kotlin/kotlinx.serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.0-RC")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-properties:1.0.0-RC")

    val ktorVersion = "1.4.0"
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")

    // Junit 4 (testing framework)
    implementation("junit:junit:4.12")
}
