package com.makentoshe.booruchan.postpreviews.view

import android.view.View
import android.widget.RelativeLayout
import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postpreviews.PostsFragmentViewModel
import com.makentoshe.booruchan.postpreviews.model.ClearIconController
import com.makentoshe.booruchan.postpreviews.model.OverflowController
import org.jetbrains.anko.*

class PostsFragmentUiContent(
    private val postsFragmentViewModel: PostsFragmentViewModel,
    private val fragmentManager: FragmentManager,
    private val clearIconController: ClearIconController,
    private val overflowController: OverflowController
) : AnkoComponent<RelativeLayout> {

    override fun createView(ui: AnkoContext<RelativeLayout>): View = with(ui) {
        relativeLayout {
            lparams(matchParent, matchParent) { below(R.id.postpreview_toolbar_container) }

            PostsFragmentUiContentCover(overflowController)
                .createView(AnkoContext.createDelegate(this))

            PostsFragmentUiContentSearch(postsFragmentViewModel, clearIconController, overflowController)
                .createView(AnkoContext.createDelegate(this))

            PostsFragmentUiContentViewpager(postsFragmentViewModel, fragmentManager)
                .createView(AnkoContext.createDelegate(this))

            PostsFragmentUiContentBottombar(postsFragmentViewModel)
                .createView(AnkoContext.createDelegate(this))
        }
    }
}