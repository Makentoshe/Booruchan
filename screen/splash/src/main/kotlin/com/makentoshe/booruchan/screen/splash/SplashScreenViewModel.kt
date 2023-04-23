package com.makentoshe.booruchan.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makentoshe.booruchan.library.feature.CoroutineExceptionHandlerDelegate
import com.makentoshe.booruchan.library.feature.DefaultCoroutineExceptionHandlerDelegate
import com.makentoshe.booruchan.library.feature.DefaultEventDelegate
import com.makentoshe.booruchan.library.feature.DefaultStateDelegate
import com.makentoshe.booruchan.library.feature.EventDelegate
import com.makentoshe.booruchan.library.feature.StateDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(

) : ViewModel(),
    CoroutineExceptionHandlerDelegate by DefaultCoroutineExceptionHandlerDelegate(),
    StateDelegate<SplashState> by DefaultStateDelegate(SplashState.Loading),
    EventDelegate<SplashEvent> by DefaultEventDelegate() {

    init {

    }

    fun handleEvent(event: SplashEvent) {
        println(event)
    }

}
