package com.makentoshe.booruchan.postpreviews.model

interface OverflowController {
    fun toCross()
    fun toMagnify()
    fun clickOverflowIcon()
    fun onOverflowStateChangedListener(action: OverflowRxController.OverflowListener.() -> Unit)
}