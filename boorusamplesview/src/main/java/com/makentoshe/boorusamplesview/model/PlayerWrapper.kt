package com.makentoshe.boorusamplesview.model

import android.view.View

/** Interface for webm video player. Generic parameter [V] is a [View] where player will be attached */
interface PlayerWrapper<V : View> {
    /** Updates player's source */
    fun source(url: String): PlayerWrapper<V>

    /** Attaches a player to the [V] */
    fun attachToView(view: V)

    /** Detaches a player from the [V] */
    fun detachFromView(view: V)

    /** Release player */
    fun release()
}