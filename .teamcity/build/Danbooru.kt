package build

import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.gradle

object Danbooru : PipelineBuild("Danbooru", {

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