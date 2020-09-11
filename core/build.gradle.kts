plugins {
    id("org.jetbrains.kotlin.jvm")
    id("com.github.johnrengelman.shadow") version "6.0.0"
}

group = "com.makentoshe.booruchan.core"
version = "1.0"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
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
