package com.makentoshe.boorupostview.view

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.ViewManager
import androidx.annotation.StyleRes
import com.makentoshe.boorupostview.anko.AnkoSlidingUpPanelLayout
import com.makentoshe.boorupostview.R
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.custom.ankoView

class PostsFragmentUi : AnkoComponent<Context> {
    override fun createView(ui: AnkoContext<Context>) = with(ui) {
        verticalLayout {

            createToolbarView()

            slidingUpPanel {
                id = R.id.slidingPanel
                setGravity(Gravity.BOTTOM)
                coveredFadeColor = Color.TRANSPARENT
                shadowHeight = 0
                panelHeight = 0
                panelState = SlidingUpPanelLayout.PanelState.EXPANDED

                createContentView()
                createPanelView()
            }
        }
    }

    private fun _LinearLayout.createToolbarView() {
        val style = attr(R.attr.toolbar_style).data
        val height = dimen(R.dimen.toolbar_height)

        themedRelativeLayout(style) {
            id = R.id.toolbar
            minimumHeight = height

            toolbar {
                id = R.id.toolbar_view
            }.lparams(matchParent, wrapContent) {
                bottomMargin = dimen(R.dimen.toolbar_bottom_margin)
            }

            createMagnifyIcon()

        }.lparams(matchParent, wrapContent)
    }

    private fun _RelativeLayout.createMagnifyIcon() {
        val height = dimen(R.dimen.toolbar_height)
        frameLayout {
            id = R.id.magnify_view

            imageView {
                id = R.id.magnify_icon
                padding = height / 4
            }.lparams(matchParent, matchParent) {
                gravity = Gravity.CENTER
            }

        }.lparams(height, height) {
            alignParentEnd()
            centerVertically()
        }
    }

    private fun AnkoSlidingUpPanelLayout.createContentView() = frameLayout {
        id = R.id.contentview
    }.lparams(matchParent, matchParent)

    private fun AnkoSlidingUpPanelLayout.createPanelView() = frameLayout {
        id = R.id.panelview
    }.lparams(matchParent, matchParent)

    private fun ViewManager.slidingUpPanel(@StyleRes theme: Int = 0, init: AnkoSlidingUpPanelLayout.() -> Unit): SlidingUpPanelLayout {
        return ankoView({ AnkoSlidingUpPanelLayout(it) }, theme, init)
    }
}