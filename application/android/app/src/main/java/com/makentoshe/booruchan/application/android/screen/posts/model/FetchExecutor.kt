package com.makentoshe.booruchan.application.android.screen.posts.model

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executor

class FetchExecutor(private val coroutineScope: CoroutineScope): Executor {
    override fun execute(command: Runnable) {
        coroutineScope.launch { command.run() }
    }
}