import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.72"
    jacoco // enable JaCoco plugin
}

group = "com.makentoshe.booruchan"
version = "1.0"

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = "1.8"

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions.jvmTarget = "1.8"

// Task allows to run and aggregate coverage reports for whole project
// NOTE: the `JacocoReport` tasks do *not* depend on the `test` task by default. Meaning you have to ensure
// that `test` (or other tasks generating code coverage) run before generating the report.
tasks.register<JacocoReport>("testJacocoCoverageReport") {
    // If a subproject applies the 'jacoco' plugin, add the result it to the report
    subprojects {
        val subproject = this
        subproject.plugins.withType<JacocoPlugin>().configureEach {
            subproject.tasks.matching { it.extensions.findByType<JacocoTaskExtension>() != null }.configureEach {
                sourceSets(subproject.sourceSets.main.get())
                executionData(this)
            }

            // To automatically run `test` every time `./gradlew testJacocoCoverageReport` is called,
            // you may want to set up a task dependency between them as shown below.
            // Note that this requires the `test` tasks to be resolved eagerly (see `forEach`) which
            // may have a negative effect on the configuration time of your build.
/*
            subproject.tasks.matching { it.extensions.findByType<JacocoTaskExtension>() != null }.forEach {
                rootProject.tasks["testJacocoCoverageReport"].dependsOn(it)
            }
*/
        }
    }

    reports {
        html.isEnabled = true
    }

    finalizedBy(tasks.getByName("jacocoHtmlZip"))
}

// Task allows to archive jacoco html reports for future using in the ci/cd tools
tasks.register<Zip>("jacocoHtmlZip") {
    archiveFileName.set("jacocoHtml.zip")
    destinationDirectory.set(file("$buildDir/reports/jacoco/testJacocoCoverageReport/html-zip"))
    from("$buildDir/reports/jacoco/testJacocoCoverageReport/html")
}

// A "one way" task allows to upgrade android module to a standalone project
// that can be opened and edited separately.
// NOTE: From this point the whole project build will be always failed
// because the android module will be unconfigured properly
tasks.register<Copy>("android-standalone") {
    try {
        val buildGradleKts = "$projectDir${File.separator}build.gradle.kts"
        val gradlewBat = "$projectDir${File.separator}gradlew.bat"
        val gradlew = "$projectDir${File.separator}gradlew"
        val gradleProperties = "$projectDir${File.separator}gradle.properties"
        val localProperties = "$projectDir${File.separator}local.properties"

        from(buildGradleKts, gradlewBat, gradlew, gradleProperties, localProperties)
        into(project(":application:android").projectDir)
    } catch (upe: UnknownProjectException) {
        // cause after upgrading module
    }
}

// Android section
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.2")
        classpath(kotlin("gradle-plugin", version = "1.3.72"))

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}
