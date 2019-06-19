package com.makentoshe.boorupostview.presenter

import android.view.View
import androidx.appcompat.widget.Toolbar
import com.sothree.slidinguppanel.SlidingUpPanelLayout

/**
 * Presenter component for the posts fragment screen.
 */
interface PostsFragmentPresenter {

    /** Bind an option icon. This icon performs a panel open/close action on click. */
    fun bindOptionIcon(view: View)

    /** Bind a [SlidingUpPanelLayout]. Contains a posts viewer and search layout. */
    fun bindSlidingPanel(view: SlidingUpPanelLayout)
}