package com.makentoshe.booruchan.library.feature

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking

interface NavigationDelegate<Destination> {

    val navigationFlow: StateFlow<Destination>

    fun updateNavigation(action: () -> Destination)
}

class DefaultNavigationDelegate<Destination>(
    initialState: Destination,
) : NavigationDelegate<Destination> {

    private val _navigationFlow = MutableStateFlow(initialState)
    override val navigationFlow = _navigationFlow.asStateFlow()

    override fun updateNavigation(action: () -> Destination) {
        runBlocking { _navigationFlow.emit(action()) }
    }

}
