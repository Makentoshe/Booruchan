plugins {
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
        attributes["Implementation-Title"] = "Booruchan Core"
    }
    val classpath = configurations.runtimeClasspath.get().filter {
        !it.absolutePath.contains("org.jetbrains.kotlin")
    }.map {
        if (it.isDirectory) it else zipTree(it)
    }
    from(classpath)
    with(tasks.jar.get() as CopySpec)
}
