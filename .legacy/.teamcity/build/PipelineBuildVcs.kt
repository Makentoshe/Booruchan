package build

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

abstract class PipelineBuildVcs(name: String, init: BuildType.() -> Unit) : PipelineBuild(name, {

    // Triggers are used to add builds to the queue either when an event occurs (like a VCS check-in)
    // or periodically with some configurable interval
    triggers { vcs { } }

    init()
})