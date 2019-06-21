package com.makentoshe.startview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/** Viewmodel component for [StartFragment]. Contains a [StartFragmentNavigator] instance. */
class StartFragmentViewModel(val navigator: StartFragmentNavigator) : ViewModel() {

    class Factory(private val navigator: StartFragmentNavigator?) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return StartFragmentViewModel(navigator!!) as T
        }
    }
}