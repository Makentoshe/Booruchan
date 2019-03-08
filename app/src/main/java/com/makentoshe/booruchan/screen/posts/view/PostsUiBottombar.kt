package com.makentoshe.booruchan.screen.posts.view

import android.view.Gravity
import android.view.View
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import org.jetbrains.anko.*

class PostsUiBottombar : AnkoComponent<_RelativeLayout> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        linearLayout {
            id = R.id.posts_bottombar
            elevation = dip(10).toFloat()
            backgroundColorResource = style.toolbar.primaryColorRes
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
                imageResource = style.drawable.static.chevron
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

            textView {
                textColorResource = style.toolbar.onPrimaryColorRes
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
                imageResource = style.drawable.static.chevron
                rotation = 90f
            }.lparams {
                gravity = Gravity.CENTER
            }

        }.lparams(height = matchParent) {
            weight = 1f
        }
    }
}