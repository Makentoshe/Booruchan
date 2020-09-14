import java.io.File
import java.io.BufferedOutputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

plugins {
    id("org.jetbrains.kotlin.jvm")
    kotlin("plugin.serialization") version "1.3.72"
    id("com.github.johnrengelman.shadow") version "6.0.0"
    jacoco // enable JaCoco plugin
}

group = "com.makentoshe.booruchan.danbooru"
version = "1.0"

repositories {
    mavenCentral()
    jcenter()
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

    implementation("junit:junit:4.12")
}

// executes "shadowJar" task straight after "build"
tasks.build {
    finalizedBy(tasks.shadowJar)
}

// "shadowJar" task configurations
tasks.shadowJar {
    archiveBaseName.set("${project.name}-shadow")
    archiveClassifier.set("")
    archiveVersion.set(project.version.toString())
}

jacoco {
    toolVersion = "0.8.5"
}

// executes "jacocoTestReport" task straight after "test"
tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

// "jacocoTestReport" task configurations
tasks.jacocoTestReport {
    reports {
        xml.isEnabled = false
        csv.isEnabled = false
        html.isEnabled = true
    }
    finalizedBy(tasks.getByName("jacocoHtmlZip"))
}

tasks.register<Zip>("jacocoHtmlZip") {
    archiveFileName.set("jacocoHtml.zip")
    destinationDirectory.set(file("$buildDir/reports/jacoco/test/html-zip"))
    from("$buildDir/reports/jacoco/test/html")
}
