package com.makentoshe.booruchan.booru.model.content.common

import kotlinx.coroutines.experimental.Job
import java.util.*

class JobScheduler(private val maxSize: Int) {

    val jobDeque = ArrayDeque<Job>()

    fun addJob(job: Job) {
        if (jobDeque.size >= maxSize) {
            jobDeque.pollFirst()?.cancel()
        }
        jobDeque.addLast(job)
    }

}