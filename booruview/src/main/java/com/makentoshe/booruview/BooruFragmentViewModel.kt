package com.makentoshe.booruview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Viewmodel component contains a navigator instance for performs a navigation action to another screens
 */
class BooruFragmentViewModel(val navigator: BooruFragmentNavigator) : ViewModel() {

    class Factory(private val booruFragmentNavigator: BooruFragmentNavigator?) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return BooruFragmentViewModel(booruFragmentNavigator!!) as T
        }
    }
}