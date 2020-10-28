plugins {
    id("org.jetbrains.kotlin.jvm")
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

    implementation("junit:junit:4.12")
}

// Allows to use kotlin.Result type as a return
val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions.freeCompilerArgs = listOf("-Xallow-result-return-type")

// Allows to use kotlin.Result type as a return
val compileTestKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileTestKotlin.kotlinOptions.freeCompilerArgs = listOf("-Xallow-result-return-type")

// executes "shadowJar" task straight after "build"
tasks.build {
    finalizedBy(tasks.getByName("shadowJar"))
}

task<Jar>("shadowJar") {
    manifest {
        attributes["Implementation-Title"] = "Booruchan Danbooru"
    }
    val classpath = configurations.runtimeClasspath.get().filter {
        !it.absolutePath.contains("org.jetbrains.kotlin")
    }.map {
        if (it.isDirectory) it else zipTree(it)
    }
    from(classpath)
    with(tasks.jar.get() as CopySpec)
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
