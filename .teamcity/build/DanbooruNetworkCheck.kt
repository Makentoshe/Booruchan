package build

import jetbrains.buildServer.configs.kotlin.v2019_2.PublishMode
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.gradle

/**
 * Checks Danbooru's real api to be sure, that is all works properly
 * */
object DanbooruNetworkCheck : PipelineBuildDaily("Danbooru network check", 0, 0, {
    description = "Checks Danbooru's real api to be sure, that it is works properly"
    val modulePath = "./danbooru/network-check"
    val gradlePath = ":danbooru:network-check"

    publishArtifacts = PublishMode.NORMALLY_FINISHED
    artifactRules = """
        $modulePath/build/reports/jacoco/test/html-zip/* => jacocoHtmlReport
    """.trimIndent()

    dependencies {
        snapshot(Core) {}
        artifacts(Core) {
            artifactRules = "core/* => $modulePath/libs"
        }
        snapshot(Danbooru) {}
        artifacts(Danbooru) {
            artifactRules = "danbooru/* => $modulePath/libs"
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