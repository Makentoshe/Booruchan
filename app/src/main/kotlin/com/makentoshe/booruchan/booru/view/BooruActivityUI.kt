package com.makentoshe.booruchan.booru.view

import android.arch.lifecycle.ViewModelProviders
import android.support.annotation.IntDef
import android.support.v4.app.FragmentActivity
import android.view.View
import android.widget.FrameLayout
import com.makentoshe.booruchan.booru.BooruViewModel
import com.makentoshe.booruchan.booru.ContentViewModel
import com.makentoshe.booruchan.booru.PanelViewModel
import com.makentoshe.booruchan.booru.view.content.BooruActivityUIContent
import com.makentoshe.booruchan.booru.view.panel.BooruActivityUIPanel
import com.makentoshe.booruchan.common.StyleableAnkoComponent
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.styles.Style
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.support.v4.drawerLayout

//fixme UI decreased speed
class BooruActivityUI(style: Style) : StyleableAnkoComponent<BooruActivity>(style) {

    private fun getPanelViewModel(owner: FragmentActivity, booru: Boor): PanelViewModel {
        val factory = PanelViewModel.Factory(booru)
        return ViewModelProviders.of(owner, factory)[PanelViewModel::class.java]
    }

    private fun getContentViewModel(owner: FragmentActivity, booru: Boor): ContentViewModel {
        val factory = ContentViewModel.Factory(booru)
        return ViewModelProviders.of(owner, factory)[ContentViewModel::class.java]
    }

    override fun createView(ui: AnkoContext<BooruActivity>): View = with(ui) {
        val booruViewModel = ViewModelProviders.of(owner)[BooruViewModel::class.java]
        val panelViewModel = getPanelViewModel(ui.owner, booruViewModel.booru)
        val contentViewModel = getContentViewModel(ui.owner, booruViewModel.booru)
        val view = drawerLayout {
            BooruActivityUIContent(style, booruViewModel, contentViewModel, panelViewModel, this).createView(ui)
            BooruActivityUIPanel(style, panelViewModel, this).createView(ui)
        }
        view.layoutParams = FrameLayout.LayoutParams(matchParent, matchParent)
        return@with view
    }



}
