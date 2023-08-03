package com.makentoshe.booruchan.screen.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makentoshe.booruchan.library.feature.CoroutineDelegate
import com.makentoshe.booruchan.library.feature.DefaultCoroutineDelegate
import com.makentoshe.booruchan.library.feature.DefaultEventDelegate
import com.makentoshe.booruchan.library.feature.DefaultNavigationDelegate
import com.makentoshe.booruchan.library.feature.DefaultStateDelegate
import com.makentoshe.booruchan.library.feature.EventDelegate
import com.makentoshe.booruchan.library.feature.NavigationDelegate
import com.makentoshe.booruchan.library.feature.StateDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashScreenViewModel @Inject constructor(
) : ViewModel(),
    StateDelegate<SplashScreenState> by DefaultStateDelegate(SplashScreenState.InitialState),
    EventDelegate<SplashScreenEvent> by DefaultEventDelegate(),
    NavigationDelegate<SplashScreenDestination> by DefaultNavigationDelegate() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            delay(750) // 0.75 sec
            updateNavigation { SplashScreenDestination.HomeScreen }
        }
    }

}
