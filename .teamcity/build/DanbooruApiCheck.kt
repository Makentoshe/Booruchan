package build

/**
 * Checks Danbooru's real api to be sure, that is all works properly
 * */
object DanbooruApiCheck: PipelineBuild("Danbooru api check", {

    dependencies {
        snapshot(build.Danbooru) {}
        artifacts(build.Danbooru) {
            artifactRules = "core/* => ./danbooru/libs"
            artifactRules = "danbooru/* => ./danbooru-test-api/libs"
        }
    }

    steps {
        gradle {
            name = "$name module api check"
            tasks = ":danbooru-test-api:build"
            gradleParams = "-Pmodular"
            buildFile = "build.gradle.kts"
        }
    }
})