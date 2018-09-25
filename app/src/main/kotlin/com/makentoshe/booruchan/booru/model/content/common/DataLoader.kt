package com.makentoshe.booruchan.booru.model.content.common

abstract class DataLoader {

    protected val jobScheduler = JobScheduler(10)

    fun clearScheduler() {
        jobScheduler.jobDeque.forEach {
            it.cancel()
        }
        jobScheduler.jobDeque.clear()
    }

}