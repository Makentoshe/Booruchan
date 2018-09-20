package com.makentoshe.booruchan.booru.model.content.common

import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class JobSchedulerTest {

    @Test
    fun `should cancel job when it removes from deque`() {
        val jobScheduler = JobScheduler(1)

        val job = GlobalScope.launch {
            delay(1000)
        }
        jobScheduler.addJob(job)
        jobScheduler.addJob(GlobalScope.launch {})
        assertTrue(job.isCancelled)
        assertEquals(1, jobScheduler.jobDeque.size)
    }

    @Test
    fun `should remove from deque when add too more jobs`() {
        val jobScheduler = JobScheduler(2)
        for (i in 0 until 5 step 1) {
            jobScheduler.addJob(GlobalScope.launch {})
        }
        assertEquals(2, jobScheduler.jobDeque.size)
    }

}