package com.makentoshe.booruchan.application.android

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executor

class FetchExecutor(private val coroutineScope: CoroutineScope): Executor {
    override fun execute(command: Runnable) {
        println(Thread.currentThread())
        coroutineScope.launch {
            println(Thread.currentThread())
            command.run()
        }
    }
}