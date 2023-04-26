package com.makentoshe.booruchan.screen.boorucontent.viewmodel

import androidx.lifecycle.ViewModel
import com.makentoshe.booruchan.feature.boorulist.domain.usecase.GetBooruContextUseCase
import com.makentoshe.booruchan.library.feature.CoroutineDelegate
import com.makentoshe.booruchan.library.feature.DefaultCoroutineDelegate
import com.makentoshe.booruchan.library.feature.DefaultEventDelegate
import com.makentoshe.booruchan.library.feature.DefaultStateDelegate
import com.makentoshe.booruchan.library.feature.EventDelegate
import com.makentoshe.booruchan.library.feature.StateDelegate
import com.makentoshe.booruchan.library.logging.internalLogInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BoorucontentViewModel @Inject constructor(
    private val getBooruContext: GetBooruContextUseCase,
) : ViewModel(),
    CoroutineDelegate by DefaultCoroutineDelegate(),
    EventDelegate<BoorucontentEvent> by DefaultEventDelegate(),
    StateDelegate<BoorucontentState> by DefaultStateDelegate(BoorucontentState.InitialState) {

    fun handleEvent(event: BoorucontentEvent) = when(event) {
        is BoorucontentEvent.Initialize -> initialize(event)
    }

    private fun initialize(event: BoorucontentEvent) {
        internalLogInfo("initialize event invoked: $event")
    }
}