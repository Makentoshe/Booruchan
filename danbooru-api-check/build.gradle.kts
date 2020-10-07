plugins {
    id("org.jetbrains.kotlin.jvm")
    // enable JaCoco plugin
    jacoco
}

group = "com.makentoshe.booruchan.danbooru.apicheck"
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
        implementation(project(":danbooru"))
    }

    implementation(kotlin("stdlib"))

    // Ktor (http client)
    val ktorVersion = "1.3.1"
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")

    implementation("junit:junit:4.12")
}

// idkw, but "onlyIf" does not disables/skips this task, so this is a workaround
tasks.test.configure {
    // for ci/cd: this should be managed by build system.
    // we should disable tests by default (ide), because the REAL api will invoked each build
    // and may cause accident DDoS
    enabled = project.hasProperty("netable")
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
    // Just for logging and debugging
    val project = project(":danbooru")
    println("Project ${project.projectDir}")
    println("Source sets: ${project.sourceSets.asMap}")
    println("Compile classpath: ${project.sourceSets.main.get().compileClasspath.firstOrNull()}")
    println("Runtime classpath: ${project.sourceSets.main.get().runtimeClasspath.firstOrNull()}")

    // apply source set from danbooru module
    sourceSets(project(":danbooru").sourceSets.main.get())

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
