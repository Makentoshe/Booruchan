package build

import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.gradle

/**
 * Checks Danbooru's real api to be sure, that is all works properly
 * */
object DanbooruApiCheck : PipelineBuild("Danbooru api check", {

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
            gradleParams = "-Pmodular"
            buildFile = "build.gradle.kts"
        }
    }
})