package com.makentoshe.booruchan.postpreviews.view

import android.view.View
import android.widget.RelativeLayout
import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postpreviews.PostsFragmentViewModel
import com.makentoshe.booruchan.postpreviews.model.ClearIconController
import org.jetbrains.anko.*

class PostsFragmentUiContent(
    private val postsFragmentViewModel: PostsFragmentViewModel,
    private val fragmentManager: FragmentManager,
    private val clearIconController: ClearIconController
) : AnkoComponent<RelativeLayout> {

    override fun createView(ui: AnkoContext<RelativeLayout>): View = with(ui) {
        relativeLayout {
            lparams(matchParent, matchParent) { below(R.id.postpreview_toolbar_container) }

            PostsFragmentUiContentCover(postsFragmentViewModel)
                .createView(AnkoContext.createDelegate(this))

            PostsFragmentUiContentSearch(postsFragmentViewModel, clearIconController)
                .createView(AnkoContext.createDelegate(this))

            PostsFragmentUiContentViewpager(postsFragmentViewModel, fragmentManager)
                .createView(AnkoContext.createDelegate(this))

            PostsFragmentUiContentBottombar(postsFragmentViewModel)
                .createView(AnkoContext.createDelegate(this))
        }
    }
}