package com.makentoshe.booruchan.screen.posts

import android.view.Gravity
import android.view.View
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import org.jetbrains.anko.*

class PostsUiBottombar : AnkoComponent<_RelativeLayout> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<_RelativeLayout>): View =
        with(ui.owner) {
        linearLayout {
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
            id = R.id.postpreview_bottombar_left

//            viewPagerController.onPageChangedListener {
//                visibility = if (it == 0) View.INVISIBLE else View.VISIBLE
//            }

//            onClick { viewPagerController.prevPage() }

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
            id = R.id.postpreview_bottombar_center

            textView {
                id = R.id.postpreview_bottombar_center_textview

//                viewPagerController.onPageChangedListener {
//                    text = it.toString()
//                    textColorResource = style.toolbar.onPrimaryColorRes
//                }

            }.lparams {
                gravity = Gravity.CENTER
            }

        }.lparams(height = matchParent) {
            weight = 1f
        }
    }

    private fun _LinearLayout.createRight() {
        frameLayout {
            id = R.id.postpreview_bottombar_right

//            onClick { viewPagerController.nextPage() }

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