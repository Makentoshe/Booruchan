package com.makentoshe.booruchan

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class CoroutineViewModel: ViewModel(), CoroutineScope {
    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }
}

abstract class FragmentViewModel: CoroutineViewModel() {
    open fun onUiRecreate() = Unit
}
