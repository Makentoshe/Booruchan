package com.makentoshe.boorupostview.view

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import com.makentoshe.style.AnkoSlidingUpPanelLayout
import com.makentoshe.style.slidingUpPanel
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar

class PostsFragmentUi : AnkoComponent<Context> {
    override fun createView(ui: AnkoContext<Context>) = with(ui) {
        verticalLayout {

            createToolbarView()

            slidingUpPanel {
                id = com.makentoshe.boorupostview.R.id.slidingPanel
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
        val style = attr(com.makentoshe.style.R.attr.toolbar_style).data
        val height = dimen(com.makentoshe.style.R.dimen.toolbar_height)

        themedRelativeLayout(style) {
            id = com.makentoshe.boorupostview.R.id.toolbar
            minimumHeight = height

            toolbar {
                id = com.makentoshe.boorupostview.R.id.toolbar_view
            }.lparams(matchParent, wrapContent) {
                bottomMargin = dimen(com.makentoshe.style.R.dimen.toolbar_bottom_margin)
            }

            createMagnifyCrossIcon()

        }.lparams(matchParent, wrapContent)
    }

    private fun _RelativeLayout.createMagnifyCrossIcon() {
        val height = dimen(com.makentoshe.style.R.dimen.toolbar_height)
        relativeLayout {
            id = com.makentoshe.boorupostview.R.id.magnify_cross_view

            imageView {
                id = com.makentoshe.boorupostview.R.id.magnify_icon
                padding = height / 4
                imageResource = com.makentoshe.style.R.drawable.avd_cross_magnify
            }.lparams(matchParent, matchParent) {
                gravity = Gravity.CENTER
            }

            imageView {
                id = com.makentoshe.boorupostview.R.id.cross_icon
                padding = height / 4
                imageResource = com.makentoshe.style.R.drawable.avd_magnify_cross
                alpha = 0f
                visibility = View.GONE
            }.lparams(matchParent, matchParent) {
                gravity = Gravity.CENTER
            }

        }.lparams(height, height) {
            alignParentEnd()
            centerVertically()
        }
    }

    private fun AnkoSlidingUpPanelLayout.createContentView() = frameLayout {
        id = com.makentoshe.boorupostview.R.id.contentview
    }.lparams(matchParent, matchParent)

    private fun AnkoSlidingUpPanelLayout.createPanelView() = frameLayout {
        id = com.makentoshe.boorupostview.R.id.panelview
    }.lparams(matchParent, matchParent)

}