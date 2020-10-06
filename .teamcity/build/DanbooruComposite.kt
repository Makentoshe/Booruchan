package build

object DanbooruComposite : PipelineBuild("Danbooru Composite", {

    this.type = Type.COMPOSITE

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