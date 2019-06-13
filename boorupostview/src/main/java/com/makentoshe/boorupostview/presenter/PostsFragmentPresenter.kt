package com.makentoshe.boorupostview.presenter

import android.view.View
import androidx.appcompat.widget.Toolbar
import com.sothree.slidinguppanel.SlidingUpPanelLayout

interface PostsFragmentPresenter {

    fun bindToolbar(view: Toolbar)

    fun bindOptionIcon(view: View)

    fun bindSlidingPanel(view: SlidingUpPanelLayout)
}