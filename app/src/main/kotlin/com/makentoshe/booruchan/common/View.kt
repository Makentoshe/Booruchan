package com.makentoshe.booruchan.common

import android.content.Context
import android.support.annotation.IntDef
import android.util.AttributeSet
import android.view.View
import com.sothree.slidinguppanel.SlidingUpPanelLayout

interface BackdropView {

    @IntDef(State.EXPANDED, State.COLLAPSED)
    @Retention(AnnotationRetention.SOURCE)
    annotation class BackdropState

    fun collapse()

    fun expand()

    fun addStateListener(@BackdropState listener: (Int) -> Unit)

    object State {
        @get:BackdropState
        const val EXPANDED = 1
        @get:BackdropState
        const val COLLAPSED = 2
    }

}

class BackdropImpl(context: Context, attributeSet: AttributeSet?, defStyle: Int)
    : SlidingUpPanelLayout(context, attributeSet, defStyle), BackdropView {

    override fun addStateListener(listener: (Int) -> Unit) {
        addPanelSlideListener(object: SimplePanelSlideListener() {
            override fun onPanelStateChanged(panel: View?, previousState: PanelState?, newState: PanelState?) {
                if (newState == PanelState.EXPANDED) listener(BackdropView.State.EXPANDED)
                if (newState == PanelState.COLLAPSED) listener(BackdropView.State.COLLAPSED)
            }
        })
    }

    constructor(context: Context) : this(context, null, 0)

    override fun collapse() {
        panelState = PanelState.COLLAPSED
    }

    override fun expand() {
        panelState = PanelState.EXPANDED
    }
}

