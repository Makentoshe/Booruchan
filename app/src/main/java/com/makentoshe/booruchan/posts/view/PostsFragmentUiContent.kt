package com.makentoshe.booruchan.posts.view

import android.view.View
import android.widget.RelativeLayout
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.posts.PostsFragmentViewModel
import org.jetbrains.anko.*

class PostsFragmentUiContent(
    private val postsFragmentViewModel: PostsFragmentViewModel
) : AnkoComponent<RelativeLayout> {

    override fun createView(ui: AnkoContext<RelativeLayout>): View = with(ui) {
        relativeLayout {
            lparams(matchParent, matchParent) { below(R.id.toolbar_container) }
            PostsFragmentUiContentCover(postsFragmentViewModel).createView(AnkoContext.createDelegate(this))
            PostsFragmentUiContentSearch(postsFragmentViewModel).createView(AnkoContext.createDelegate(this))
            PostsFragmentUiContentViewpager(postsFragmentViewModel).createView(AnkoContext.createDelegate(this))
            PostsFragmentUiContentBottombar(postsFragmentViewModel).createView(AnkoContext.createDelegate(this))
        }
    }
}