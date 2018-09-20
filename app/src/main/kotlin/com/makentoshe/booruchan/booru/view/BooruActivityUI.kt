package com.makentoshe.booruchan.booru.view

import android.arch.lifecycle.ViewModelProviders
import android.support.annotation.IntDef
import android.view.View
import android.widget.FrameLayout
import com.makentoshe.booruchan.booru.BooruViewModel
import com.makentoshe.booruchan.common.StyleableAnkoComponent
import com.makentoshe.booruchan.common.styles.Style
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.support.v4.drawerLayout

//todo fix UI decreased speed
class BooruActivityUI(style: Style) : StyleableAnkoComponent<BooruActivity>(style) {

    companion object {
        const val GALLERY_POST_ORD_INF = 0x00000000
        const val GALLERY_COMMENT = 0x10000000
    }

    @IntDef(GALLERY_POST_ORD_INF, GALLERY_COMMENT)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Gallery



    override fun createView(ui: AnkoContext<BooruActivity>): View = with(ui) {
        val viewModel = ViewModelProviders.of(ui.owner)[BooruViewModel::class.java]
        val view = drawerLayout {
            BooruActivityUIContent(style, viewModel, this).createView(ui)
            BooruActivityUIPanel(style, viewModel, this).createView(ui)
        }
        view.layoutParams = FrameLayout.LayoutParams(matchParent, matchParent)
        return@with view
    }



}
