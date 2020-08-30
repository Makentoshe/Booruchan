package build

import jetbrains.buildServer.configs.kotlin.v2019_2.PublishMode
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.gradle

object Gelbooru : PipelineBuild("Gelbooru", {

    publishArtifacts = PublishMode.SUCCESSFUL
    artifactRules = "./booruchan-gelbooru/build/libs/* => gelbooru"

    dependencies {
        snapshot(Core) {}
        artifacts(Core) {
            artifactRules = "core/* => ./booruchan-gelbooru/libs"
        }
    }

    steps {
        gradle {
            name = "$name module build"
            tasks = ":booruchan-gelbooru:build"
            gradleParams = "-Pmodular"
            buildFile = "build.gradle.kts"
        }
    }
})