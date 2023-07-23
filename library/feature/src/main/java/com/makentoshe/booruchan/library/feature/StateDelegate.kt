package com.makentoshe.booruchan.library.feature

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface StateDelegate<State> {

    val stateFlow: StateFlow<State>

    val state: State

    fun updateState(reduce: State.() -> State)
}

class DefaultStateDelegate<State>(initialState: State) : StateDelegate<State> {

    private val _stateFlow = MutableStateFlow(initialState)
    override val stateFlow = _stateFlow.asStateFlow()

    override val state
        get() = stateFlow.value

    override fun updateState(reduce: State.() -> State) {
        while (true) {
            val prevValue = _stateFlow.value
            val nextValue = reduce(prevValue)
            if (_stateFlow.compareAndSet(prevValue, nextValue)) return
        }
    }
}
