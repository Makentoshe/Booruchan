package build

import jetbrains.buildServer.configs.kotlin.v2019_2.PublishMode
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.gradle

/**
 * Builds and unit tests api tools for Danbooru
 * */
object Danbooru : PipelineBuildVcs("Danbooru", {

    publishArtifacts = PublishMode.SUCCESSFUL
    artifactRules = """
        ./danbooru/build/libs/danbooru-shadow-* => danbooru
        ./danbooru/build/reports/jacoco/test/html/* => danbooru-jacoco-report 
    """.trimIndent()

    dependencies {
        snapshot(Core) {}
        artifacts(Core) {
            artifactRules = "core/* => ./danbooru/libs"
        }
    }

    steps {
        gradle {
            name = "$name module build"
            tasks = ":danbooru:build"
            gradleParams = "-Pmodular"
            buildFile = "build.gradle.kts"
        }
    }
})
