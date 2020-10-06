package build

import jetbrains.buildServer.configs.kotlin.v2019_2.PublishMode
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

/**
 * Checks Danbooru's real api to be sure, that is all works properly
 * */
object DanbooruApiCheck : PipelineBuildDaily("Danbooru api check", 0, 0, {

    publishArtifacts = PublishMode.SUCCESSFUL
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
        script {
            scriptContent = """
                tree .
            """.trimIndent()
        }
        gradle {
            name = "$name module api check"
            tasks = ":danbooru-api-check:build --info"
            gradleParams = "-Pjarable -Pnetable"
            buildFile = "build.gradle.kts"
        }
    }
})