package build

import jetbrains.buildServer.configs.kotlin.v2019_2.PublishMode
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.gradle

/**
 * Builds and unit tests api tools for Danbooru
 * */
object Danbooru : PipelineBuild("Danbooru", {

    publishArtifacts = PublishMode.SUCCESSFUL
    artifactRules = "./danbooru/build/libs/* => danbooru"

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
