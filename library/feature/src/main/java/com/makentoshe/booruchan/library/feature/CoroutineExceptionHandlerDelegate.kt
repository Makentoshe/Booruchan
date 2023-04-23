package com.makentoshe.booruchan.library.feature

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

interface CoroutineExceptionHandlerDelegate {
    fun buildCoroutineExceptionHandler(action: CoroutineContext.(Throwable) -> Unit): CoroutineExceptionHandler
}

class DefaultCoroutineExceptionHandlerDelegate: CoroutineExceptionHandlerDelegate {

    override fun buildCoroutineExceptionHandler(action: CoroutineContext.(Throwable) -> Unit): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { coroutineContext, throwable -> coroutineContext.action(throwable) }
    }

}