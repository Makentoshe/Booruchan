plugins {
    id("com.github.johnrengelman.shadow") version "6.0.0"
    id("org.jetbrains.kotlin.jvm")
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

// disable jar for default build because it does not provides any dependencies
tasks.jar {
    enabled = false
}

// executes "shadowJar" task straight after "build"
tasks.build {
    finalizedBy(tasks.shadowJar)
}

// "shadowJar" task configurations
tasks.shadowJar {
    archiveBaseName.set("core-shadow")
}