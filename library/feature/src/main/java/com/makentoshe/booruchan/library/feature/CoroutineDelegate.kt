package com.makentoshe.booruchan.library.feature

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

interface CoroutineDelegate {
    fun coroutineExceptionHandler(action: CoroutineContext.(Throwable) -> Unit): CoroutineExceptionHandler
}

class DefaultCoroutineDelegate: CoroutineDelegate {

    override fun coroutineExceptionHandler(action: CoroutineContext.(Throwable) -> Unit): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { coroutineContext, throwable -> coroutineContext.action(throwable) }
    }

}