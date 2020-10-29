plugins {
    id("org.jetbrains.kotlin.jvm")
    `maven-publish` // for publishing artifacts
}

group = "com.makentoshe.booruchan"
version = "0.0.0"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
}

// Allows to use kotlin.Result type as a return
val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions.freeCompilerArgs = listOf("-Xallow-result-return-type")

// Allows to use kotlin.Result type as a return
val compileTestKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileTestKotlin.kotlinOptions.freeCompilerArgs = listOf("-Xallow-result-return-type")

// configure publish
publishing {
    publications {
        // default jar publication info with transitive dependencies
        create<MavenPublication>(project.name) { from(components["java"]) }
    }
    repositories {
        // local repository
        maven { url = uri("file://${rootProject.buildDir}/repository") }
    }
}
