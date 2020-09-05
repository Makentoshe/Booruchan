package build

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.schedule

abstract class PipelineBuildDaily(
    name: String,
    minute: Int = 0,
    hour: Int = 0, init:
    BuildType.() -> Unit
) : PipelineBuild(name, {

    // Triggers are used to add builds to the queue either when an event occurs (like a VCS check-in)
    // or periodically with some configurable interval
    triggers {
        schedule {
            daily {
                this.hour = hour
                this.minute = minute
            }
        }
    }

    init()
})