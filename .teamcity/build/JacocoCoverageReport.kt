package build

import jetbrains.buildServer.configs.kotlin.v2019_2.PublishMode
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.gradle

/**
 * Build aggregates jacoco coverage reports from each module
 * that uses jacoco plugin and publish as build report for whole project
 *
 * The main idea that the each module runs own jacoco tests
 * and coverage tasks. After all this reports aggregates in one
 * for better coverage understanding.
 */
object JacocoCoverageReport: PipelineBuildVcs("Test coverage report", {

    publishArtifacts = PublishMode.NORMALLY_FINISHED
    artifactRules = """
        ./build/reports/jacoco/testJacocoCoverageReport/html-zip/* => jacocoHtmlReport
    """.trimIndent()

    // Build depends on all other builds and collects their reports to merge
    dependencies {
        snapshot(Core) {}

        snapshot(Danbooru) {}
        snapshot(DanbooruNetworkCheck) {}

        snapshot(Gelbooru) {}
    }

    steps {
        gradle {
            name = "Generate coverage report for whole project"
            tasks = "testJacocoCoverageReport --info"
            buildFile = "build.gradle.kts"
        }
    }
})