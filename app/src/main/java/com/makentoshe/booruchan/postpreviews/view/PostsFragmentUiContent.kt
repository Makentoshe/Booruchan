package com.makentoshe.booruchan.postpreviews.view

import android.view.View
import android.widget.RelativeLayout
import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postpreviews.PostsFragmentViewModel
import com.makentoshe.booruchan.postpreviews.ViewPagerViewModel
import com.makentoshe.booruchan.postpreviews.model.*
import org.jetbrains.anko.*

class PostsFragmentUiContent(
    private val postsFragmentViewModel: PostsFragmentViewModel,
    private val fragmentManager: FragmentManager,
    private val clearIconController: ClearIconController,
    private val overflowController: OverflowController,
    private val tagsController: TagsController,
    private val searchController: SearchController,
    private val viewPagerController: ViewPagerController
) : AnkoComponent<RelativeLayout> {

    override fun createView(ui: AnkoContext<RelativeLayout>): View = with(ui) {
        relativeLayout {
            lparams(matchParent, matchParent) { below(R.id.postpreview_toolbar_container) }

            PostsFragmentUiContentCover(overflowController)
                .createView(AnkoContext.createDelegate(this))

            PostsFragmentUiContentSearch(
                postsFragmentViewModel,
                clearIconController,
                overflowController,
                tagsController,
                searchController
            ).createView(AnkoContext.createDelegate(this))

            PostsFragmentUiContentViewpager(
                postsFragmentViewModel,
                fragmentManager,
                searchController,
                viewPagerController
            )
                .createView(AnkoContext.createDelegate(this))

            PostsFragmentUiContentBottombar(viewPagerController)
                .createView(AnkoContext.createDelegate(this))
        }
    }
}