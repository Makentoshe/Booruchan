package com.makentoshe.booruchan.posts.view

import android.view.Gravity
import android.view.View
import android.widget.RelativeLayout
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.posts.PostsFragmentViewModel
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import kotlin.random.Random

class PostsFragmentUiContentBottombar(
    private val postsFragmentViewModel: PostsFragmentViewModel
) : AnkoComponent<RelativeLayout> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<RelativeLayout>): View = with(ui) {
        relativeLayout {
            id = R.id.bottombar
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

            postsFragmentViewModel.viewPagerController.onPageGoto {
                visibility = if (it == 0) View.INVISIBLE else View.VISIBLE
            }

            onClick {
                val currentPage = postsFragmentViewModel.viewPagerController.value
                if (currentPage == 0) return@onClick
                postsFragmentViewModel.viewPagerController.gotoPage(currentPage - 1)
            }

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

            textView {
                postsFragmentViewModel.viewPagerController.onPageGoto {
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

            onClick {
                val currentPage = postsFragmentViewModel.viewPagerController.value
                postsFragmentViewModel.viewPagerController.gotoPage(currentPage + 1)
            }

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