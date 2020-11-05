plugins {
    id("org.jetbrains.kotlin.jvm")
}

group = "com.makentoshe.booruchan.application"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    api(project(":core"))
    api(project(":gelbooru"))
    api(project(":danbooru"))

    implementation(kotlin("stdlib"))

    // Ktor (http client)
    // https://github.com/ktorio/ktor
    val ktorVersion = properties["version.ktor"]
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
}

// Allows to use kotlin.Result type as a return
val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions.freeCompilerArgs = listOf("-Xallow-result-return-type")

// Allows to use kotlin.Result type as a return
val compileTestKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileTestKotlin.kotlinOptions.freeCompilerArgs = listOf("-Xallow-result-return-type")
