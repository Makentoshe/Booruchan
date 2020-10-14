package build

import jetbrains.buildServer.configs.kotlin.v2019_2.PublishMode
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.gradle

object Gelbooru : PipelineBuildVcs("Gelbooru", {

    publishArtifacts = PublishMode.NORMALLY_FINISHED
    artifactRules = """
        ./gelbooru/build/libs/gelbooru-shadow-* => gelbooru
        ./gelbooru/build/reports/jacoco/test/html-zip/* => jacocoHtmlReport
    """.trimIndent()

    dependencies {
        snapshot(Core) {}
        artifacts(Core) {
            artifactRules = "core/* => ./gelbooru/libs"
        }
    }

    steps {
        gradle {
            name = "$name module build"
            tasks = ":gelbooru:build"
            gradleParams = "-Pjarable"
            buildFile = "build.gradle.kts"
        }
    }
})

