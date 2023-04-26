package com.makentoshe.booruchan.library.feature

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.runBlocking

interface NavigationDelegate<Destination> {

    val navigationFlow: SharedFlow<Destination>

    fun updateNavigation(action: () -> Destination)
}

class DefaultNavigationDelegate<Destination> : NavigationDelegate<Destination> {

    private val _navigationFlow = MutableSharedFlow<Destination>()
    override val navigationFlow: SharedFlow<Destination> = _navigationFlow

    override fun updateNavigation(action: () -> Destination) {
        runBlocking { _navigationFlow.emit(action()) }
    }

}
