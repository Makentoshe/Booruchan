package com.makentoshe.screen.boorulist.viewmodel

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
class HomeScreenViewModel @Inject constructor(

) : ViewModel(), CoroutineDelegate by DefaultCoroutineDelegate(),
    StateDelegate<HomeScreenState> by DefaultStateDelegate(HomeScreenState.InitialState),
    EventDelegate<HomeScreenEvent> by DefaultEventDelegate(),
    NavigationDelegate<HomeScreenDestination> by DefaultNavigationDelegate() {

    init {
        internalLogInfo("OnViewModelConstruct")
    }

    fun handleEvent(event: HomeScreenEvent) = when (event) {
        else -> {}
    }

}