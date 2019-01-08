package com.makentoshe.booruchan.posts.model

sealed class OverflowState {
    @JvmField
    val transitionDuration = 200L

    object Magnify : OverflowState()
    object Cross : OverflowState()
    class Transition(val finishState: OverflowState) : OverflowState()
}