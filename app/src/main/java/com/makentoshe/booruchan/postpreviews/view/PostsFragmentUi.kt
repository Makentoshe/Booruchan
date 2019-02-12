package com.makentoshe.booruchan.postpreviews.view

import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.postpreviews.PostsFragment
import com.makentoshe.booruchan.postpreviews.PostsFragmentViewModel
import com.makentoshe.booruchan.postpreviews.model.ClearIconController
import com.makentoshe.booruchan.postpreviews.model.OverflowController
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColorResource
import org.jetbrains.anko.relativeLayout

class PostsFragmentUI(
    private val postsFragmentViewModel: PostsFragmentViewModel,
    private val clearIconController: ClearIconController,
    private val overflowController: OverflowController
) : AnkoComponent<PostsFragment> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<PostsFragment>) = with(ui) {
        relativeLayout {
            backgroundColorResource = style.background.backgroundColorRes

            PostsFragmentUiToolbar(postsFragmentViewModel, overflowController)
                .createView(AnkoContext.createDelegate(this))

            PostsFragmentUiContent(
                postsFragmentViewModel,
                ui.owner.childFragmentManager,
                clearIconController,
                overflowController
            )
                .createView(AnkoContext.createDelegate(this))
        }
    }
}