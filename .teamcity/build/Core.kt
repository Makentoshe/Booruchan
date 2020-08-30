package build

import jetbrains.buildServer.configs.kotlin.v2019_2.PublishMode
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.gradle

object Core : PipelineBuild("Core", {

    publishArtifacts = PublishMode.SUCCESSFUL
    artifactRules = "./booruchan-core/build/libs/* => core"

    steps {
        gradle {
            name = "$name module build"
            tasks = "booruchan-core:build"
            buildFile = "build.gradle.kts"
        }
    }
})