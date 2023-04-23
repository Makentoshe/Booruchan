package com.makentoshe.booruchan.screen.splash

sealed interface SplashState {
    object Loading : SplashState
    object Content : SplashState
}
