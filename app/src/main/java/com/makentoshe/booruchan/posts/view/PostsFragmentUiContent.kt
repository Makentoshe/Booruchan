package com.makentoshe.booruchan.posts.view

import android.view.View
import android.widget.RelativeLayout
import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.posts.PostsFragmentViewModel
import org.jetbrains.anko.*

class PostsFragmentUiContent(
    private val postsFragmentViewModel: PostsFragmentViewModel,
    private val fragmentManager: FragmentManager
) : AnkoComponent<RelativeLayout> {

    override fun createView(ui: AnkoContext<RelativeLayout>): View = with(ui) {
        relativeLayout {
            lparams(matchParent, matchParent) { below(R.id.postpreview_toolbar_container) }

            PostsFragmentUiContentCover(postsFragmentViewModel)
                .createView(AnkoContext.createDelegate(this))

            PostsFragmentUiContentSearch(postsFragmentViewModel)
                .createView(AnkoContext.createDelegate(this))

            PostsFragmentUiContentViewpager(postsFragmentViewModel, fragmentManager)
                .createView(AnkoContext.createDelegate(this))

            PostsFragmentUiContentBottombar(postsFragmentViewModel)
                .createView(AnkoContext.createDelegate(this))
        }
    }
}