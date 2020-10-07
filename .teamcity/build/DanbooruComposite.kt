package build

import jetbrains.buildServer.configs.kotlin.v2019_2.PublishMode

object DanbooruComposite : PipelineBuild("Danbooru Composite", {
    this.type = Type.COMPOSITE

    publishArtifacts = PublishMode.NORMALLY_FINISHED
    artifactRules = """
        ./danbooru-api-check/build/reports/jacoco/test/html-zip/* => jacocoHtmlReport
    """.trimIndent()

    vcs {
        showDependenciesChanges = true
    }

    dependencies {
        snapshot(Danbooru) {
        }
        snapshot(DanbooruApiCheck) {
        }
    }
})