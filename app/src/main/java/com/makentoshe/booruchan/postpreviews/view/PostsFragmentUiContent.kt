package com.makentoshe.booruchan.postpreviews.view

import android.view.View
import android.widget.RelativeLayout
import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postpreviews.PostsFragmentViewModel
import com.makentoshe.booruchan.postpreviews.model.ClearIconController
import com.makentoshe.booruchan.postpreviews.model.OverflowController
import com.makentoshe.booruchan.postpreviews.model.SearchController
import com.makentoshe.booruchan.postpreviews.model.TagsController
import org.jetbrains.anko.*

class PostsFragmentUiContent(
    private val postsFragmentViewModel: PostsFragmentViewModel,
    private val fragmentManager: FragmentManager,
    private val clearIconController: ClearIconController,
    private val overflowController: OverflowController,
    private val tagsController: TagsController,
    private val searchController: SearchController
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

            PostsFragmentUiContentViewpager(postsFragmentViewModel, fragmentManager, searchController)
                .createView(AnkoContext.createDelegate(this))

            PostsFragmentUiContentBottombar(postsFragmentViewModel)
                .createView(AnkoContext.createDelegate(this))
        }
    }
}