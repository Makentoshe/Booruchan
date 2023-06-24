package build

import jetbrains.buildServer.configs.kotlin.v2019_2.PublishMode
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.gradle

/**
 * Checks Gelbooru's real api to be sure, that is all works properly
 * */
object GelbooruNetworkCheck : PipelineBuildDaily("Gelbooru network check", 0, 0, {
    description = "Checks Gelbooru's real api to be sure, that it is works properly"
    val modulePath = "./gelbooru/network-check"
    val gradlePath = ":gelbooru:network-check"

    publishArtifacts = PublishMode.NORMALLY_FINISHED
    artifactRules = """
        $modulePath/build/reports/jacoco/test/html-zip/* => jacocoHtmlReport
    """.trimIndent()

    dependencies {
        snapshot(Core) {}
        artifacts(Core) {
            artifactRules = "core/* => $modulePath/libs"
        }
        snapshot(Gelbooru) {}
        artifacts(Gelbooru) {
            artifactRules = "gelbooru/* => $modulePath/libs"
        }
    }

    steps {
        gradle {
            name = "$name module api check"
            tasks = "$gradlePath:build --info"
            gradleParams = "-Pjarable -Pnetable"
            buildFile = "build.gradle.kts"
        }
    }
})