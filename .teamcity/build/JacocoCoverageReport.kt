package build

import jetbrains.buildServer.configs.kotlin.v2019_2.PublishMode
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.gradle

/**
 * Build aggregates jacoco coverage reports from each module
 * that uses jacoco plugin and publish as build report for whole project
 *
 * The main idea that the each build runs own jacoco tests for own module
 * and coverage tasks. After all this reports aggregates in one
 * for better coverage understanding.
 */
object JacocoCoverageReport : PipelineBuildDaily("Test coverage report", 0, 0, {

    description = """
        Reruns tests for whole project and aggregates jacoco reports. 
    """.trimIndent()

    publishArtifacts = PublishMode.NORMALLY_FINISHED
    artifactRules = """
        ./build/reports/jacoco/testJacocoCoverageReport/html-zip/* => jacocoHtmlReport
    """.trimIndent()

    steps {
        gradle {
            name = "Generate coverage report for whole project"
            tasks = "clean build testJacocoCoverageReport --info -Pnetable"
            buildFile = "build.gradle.kts"
        }
    }
})