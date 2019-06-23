package com.makentoshe.boorupostview.view

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.viewPager

class PostsViewPagerFragmentUi : AnkoComponent<Context> {

    override fun createView(ui: AnkoContext<Context>) = with(ui) {
        relativeLayout {
            viewPager {
                id = com.makentoshe.boorupostview.R.id.viewpager
            }.lparams(matchParent, matchParent) {
                above(createBottomBar())
            }
        }
    }

    private fun _RelativeLayout.createBottomBar(): View {
        return themedLinearLayout(attr(com.makentoshe.style.R.attr.toolbar_style).resourceId) {
            id = com.makentoshe.boorupostview.R.id.bottombar
            left(); center(); right()
        }.lparams(matchParent, dimen(com.makentoshe.style.R.dimen.toolbar_height)) {
            alignParentBottom()
        }
    }

    private fun _LinearLayout.left() = imageView {
        id = com.makentoshe.boorupostview.R.id.bottombar_left
        imageResource = com.makentoshe.style.R.drawable.ic_chevron
        gravity = Gravity.CENTER
        rotation = -90f
        padding = dip(8)
    }.lparams(matchParent, matchParent) { weight = 1f }

    private fun _LinearLayout.center() = textView {
        id = com.makentoshe.boorupostview.R.id.bottombar_center
        text = "0"
        gravity = Gravity.CENTER
    }.lparams(matchParent, matchParent) { weight = 1f }

    private fun _LinearLayout.right() = imageView {
        id = com.makentoshe.boorupostview.R.id.bottombar_right
        imageResource = com.makentoshe.style.R.drawable.ic_chevron
        gravity = Gravity.CENTER
        rotation = 90f
        padding = dip(8)
    }.lparams(matchParent, matchParent) { weight = 1f }

}