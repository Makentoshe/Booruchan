package build

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import vcs.BooruchanVcsRoot

abstract class PipelineBuild(name: String, init: BuildType.() -> Unit) : BuildType({
    this.name = name

    // A VCS Root is a set of settings defining how TeamCity communicates with a version control system to
    // monitor changes and get sources of a build
    vcs { root(BooruchanVcsRoot) }

    init()
})

