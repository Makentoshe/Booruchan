package build

import jetbrains.buildServer.configs.kotlin.v2019_2.PublishMode
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.gradle

object Core : PipelineBuildVcs("Core", {

    publishArtifacts = PublishMode.SUCCESSFUL
    artifactRules = "./core/build/libs/core-shadow-* => core"

    steps {
        gradle {
            name = "$name module build"
            tasks = ":core:clean :core:build --info"
            buildFile = "build.gradle.kts"
        }
    }
})