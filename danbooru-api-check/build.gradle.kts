plugins {
    id("org.jetbrains.kotlin.jvm")
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
    if (project.hasProperty("modular")) {
        // for ci/cd build
        implementation(fileTree(mapOf("dir" to "libs", "include" to arrayOf("*.jar"))))
    } else {
        // for gradle/ide build
        implementation(project(":core"))
        implementation(project(":danbooru"))
    }

    implementation(kotlin("stdlib"))

    // Ktor (http client)
    val ktorVersion = "1.4.0"
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")

    implementation("junit:junit:4.12")
}


// idkw, but "onlyIf" does not disables/skips this task, so this is a workaround
tasks.test.configure {
    // for ci/cd: this should be managed by build system.
    // we should disable tests by default (ide), because the REAL api will invoked each build
    // and may cause accident DDoS
    enabled = project.hasProperty("modular")
}
