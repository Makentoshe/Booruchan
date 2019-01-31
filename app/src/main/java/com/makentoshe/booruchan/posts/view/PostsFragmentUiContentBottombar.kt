package com.makentoshe.booruchan.posts.view

import android.view.Gravity
import android.view.View
import android.widget.RelativeLayout
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.posts.PostsFragmentViewModel
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class PostsFragmentUiContentBottombar(
    private val postsFragmentViewModel: PostsFragmentViewModel
) : AnkoComponent<RelativeLayout> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<RelativeLayout>): View = with(ui) {
        relativeLayout {
            id = R.id.postpreview_bottombar
            elevation = dip(4).toFloat()
            backgroundColorResource = style.toolbar.primaryColorRes
            lparams(width = matchParent, height = dip(56)) { alignParentBottom() }
            linearLayout {
                createLeft()
                createCenter()
                createRight()
            }.lparams(matchParent, matchParent)
        }
    }

    private fun _LinearLayout.createLeft() {
        frameLayout {
            id = R.id.postpreview_bottombar_left
            postsFragmentViewModel.onPageChangeListener {
                visibility = if (it == 0) View.INVISIBLE else View.VISIBLE
            }

            onClick { postsFragmentViewModel.gotoPrevPage() }

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

                postsFragmentViewModel.onPageChangeListener {
                    text = it.toString()
                    textColorResource = style.toolbar.onPrimaryColorRes
                }

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

            onClick { postsFragmentViewModel.gotoNextPage() }

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