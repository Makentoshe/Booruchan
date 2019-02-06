package com.makentoshe.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


/**
 * [ViewModel] class with coroutines supporting. We can call coroutines using provided [CoroutineScope].
 * When called [onCleared] the all child coroutines will be canceled.
 */
abstract class CoroutineViewModel : AbstractViewModel(), CoroutineScope {
    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}

