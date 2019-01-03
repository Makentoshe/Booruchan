package com.makentoshe.booruchan.posts.view

import android.graphics.Color
import android.view.View
import android.widget.RelativeLayout
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.posts.PostsFragmentViewModel
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.viewPager

class PostsFragmentUiContentViewpager(
    private val postsFragmentViewModel: PostsFragmentViewModel
) : AnkoComponent<RelativeLayout> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<RelativeLayout>): View = with(ui) {
        relativeLayout {
            lparams(matchParent, matchParent) {
                below(R.id.toolbar_container)
                above(R.id.bottombar)
            }
            viewPager {
                id = R.id.content_viewpager
                backgroundColor = Color.CYAN
                postsFragmentViewModel.onNewSearchStarted {
                    println(it)
                }
            }
        }
    }
}