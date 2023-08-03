package com.makentoshe.booruchan.screen.source.viewmodel

import androidx.lifecycle.ViewModel
import com.makentoshe.booruchan.library.feature.CoroutineDelegate
import com.makentoshe.booruchan.library.feature.DefaultCoroutineDelegate
import com.makentoshe.booruchan.library.feature.DefaultEventDelegate
import com.makentoshe.booruchan.library.feature.DefaultNavigationDelegate
import com.makentoshe.booruchan.library.feature.DefaultStateDelegate
import com.makentoshe.booruchan.library.feature.EventDelegate
import com.makentoshe.booruchan.library.feature.NavigationDelegate
import com.makentoshe.booruchan.library.feature.StateDelegate
import com.makentoshe.booruchan.library.logging.internalLogInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SourceScreenViewModel @Inject constructor(

) : ViewModel(), CoroutineDelegate by DefaultCoroutineDelegate(),
    StateDelegate<SourceScreenState> by DefaultStateDelegate(SourceScreenState.InitialState),
    EventDelegate<SourceScreenEvent> by DefaultEventDelegate(),
    NavigationDelegate<SourceScreenDestination> by DefaultNavigationDelegate() {

    override fun handleEvent(event: SourceScreenEvent) = when(event){
        is SourceScreenEvent.Initialize -> initialize(event)
    }

    private fun initialize(event: SourceScreenEvent.Initialize) {
        internalLogInfo("invoke initialize for Source(${event.sourceId})")

    }

}
