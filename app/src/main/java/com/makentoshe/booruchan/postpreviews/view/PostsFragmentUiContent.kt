package com.makentoshe.booruchan.postpreviews.view

import android.view.View
import android.widget.RelativeLayout
import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postpreviews.model.AdapterBuilder
import com.makentoshe.booruchan.postpreviews.model.*
import org.jetbrains.anko.*

class PostsFragmentUiContent(
    private val fragmentManager: FragmentManager,
    private val clearIconController: ClearIconController,
    private val overflowController: OverflowController,
    private val tagsController: TagsController,
    private val searchController: SearchController,
    private val viewPagerController: ViewPagerController,
    private val adapterBuilder: AdapterBuilder
) : AnkoComponent<RelativeLayout> {

    override fun createView(ui: AnkoContext<RelativeLayout>): View = with(ui) {
        relativeLayout {
            lparams(matchParent, matchParent) { below(R.id.postpreview_toolbar_container) }

            PostsFragmentUiContentCover(overflowController)
                .createView(AnkoContext.createDelegate(this))

            PostsFragmentUiContentSearch(
                adapterBuilder,
                clearIconController,
                overflowController,
                tagsController,
                searchController
            ).createView(AnkoContext.createDelegate(this))

            PostsFragmentUiContentViewpager(
                fragmentManager, searchController,
                viewPagerController, adapterBuilder
            ).createView(AnkoContext.createDelegate(this))

            PostsFragmentUiContentBottombar(viewPagerController)
                .createView(AnkoContext.createDelegate(this))
        }
    }
}