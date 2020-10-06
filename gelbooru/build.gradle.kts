plugins {
    id("org.jetbrains.kotlin.jvm")
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
    if (project.hasProperty("jarable")) {
        // for ci/cd build
        implementation(fileTree(mapOf("dir" to "libs", "include" to arrayOf("*.jar"))))
    } else {
        // for gradle/ide build
        implementation(project(":core"))
    }

    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    // Jsoup (xml parsing)
    // https://jsoup.org/download
    val jsoupVersion = "1.13.1"
    implementation("org.jsoup:jsoup:$jsoupVersion")

    // Jackson (xml parsing)
    // https://github.com/FasterXML/jackson
    val jacksonVersion = "2.11.2"
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")

    // Ktor (http client)
    val ktorVersion = "1.3.1"
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")

    // Junit 4 (testing framework)
    implementation("junit:junit:4.12")
}
