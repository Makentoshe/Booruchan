package com.makentoshe.booruchan.library.feature

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

interface EventDelegate<Event> {

    val events: SharedFlow<Event>

    suspend fun sendEvent(event: Event)
}

class DefaultEventDelegate<Event> : EventDelegate<Event> {

    private val _events = MutableSharedFlow<Event>()
    override val events = _events.asSharedFlow()

    override suspend fun sendEvent(event: Event) {
        _events.emit(event)
    }
}
