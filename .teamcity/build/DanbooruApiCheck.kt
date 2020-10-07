package build

import jetbrains.buildServer.configs.kotlin.v2019_2.PublishMode
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.gradle

/**
 * Checks Danbooru's real api to be sure, that is all works properly
 * */
object DanbooruApiCheck : PipelineBuildDaily("Danbooru api check", 0, 0, {

    publishArtifacts = PublishMode.NORMALLY_FINISHED
    artifactRules = """
        ./danbooru-api-check/build/reports/jacoco/test/html-zip/* => jacocoHtmlReport
    """.trimIndent()

    dependencies {
        snapshot(Core) {}
        artifacts(Core) {
            artifactRules = "core/* => ./danbooru-api-check/libs"
        }
        snapshot(Danbooru) {}
        artifacts(Danbooru) {
            artifactRules = "danbooru/* => ./danbooru-api-check/libs"
        }
    }

    steps {
        gradle {
            name = "$name module api check"
            tasks = ":danbooru-api-check:build --info"
            gradleParams = "-Pjarable -Pnetable"
            buildFile = "build.gradle.kts"
        }
    }
})