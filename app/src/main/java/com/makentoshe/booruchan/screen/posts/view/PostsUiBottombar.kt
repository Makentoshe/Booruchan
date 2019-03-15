package com.makentoshe.booruchan.screen.posts.view

import android.view.Gravity
import android.view.View
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.style.style
import org.jetbrains.anko.*

class PostsUiBottombar : AnkoComponent<_RelativeLayout> {

    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        themedLinearLayout(style.toolbar) {
            id = R.id.posts_bottombar
            elevation = dip(10).toFloat()
            createLeft()
            createCenter()
            createRight()
        }.lparams(matchParent, dip(56)) {
            alignParentBottom()
        }
    }

    private fun _LinearLayout.createLeft() {
        frameLayout {
            id = R.id.posts_bottombar_left

            imageView {
                imageResource = R.drawable.ic_chevron_vector
                rotation = -90f
            }.lparams {
                gravity = Gravity.CENTER
            }

        }.lparams(height = matchParent) {
            weight = 1f
        }
    }

    private fun _LinearLayout.createCenter() {
        frameLayout {
            id = R.id.posts_bottombar_center

            themedTextView(style.toolbar) {
                id = R.id.posts_bottombar_center_textview
            }.lparams {
                gravity = Gravity.CENTER
            }

        }.lparams(height = matchParent) {
            weight = 1f
        }
    }

    private fun _LinearLayout.createRight() {
        frameLayout {
            id = R.id.posts_bottombar_right

            imageView {
                imageResource = R.drawable.ic_chevron_vector
                rotation = 90f
            }.lparams {
                gravity = Gravity.CENTER
            }

        }.lparams(height = matchParent) {
            weight = 1f
        }
    }
}