package com.makentoshe.booruchan.postpreviews.view

import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.booru.model.DrawerController
import com.makentoshe.booruchan.postpreviews.PostsFragment
import com.makentoshe.booruchan.postpreviews.PostsFragmentViewModel
import com.makentoshe.booruchan.postpreviews.model.*
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColorResource
import org.jetbrains.anko.relativeLayout

class PostsFragmentUI(
    private val postsFragmentViewModel: PostsFragmentViewModel,
    private val clearIconController: ClearIconController,
    private val overflowController: OverflowController,
    private val tagsController: TagsController,
    private val searchController: SearchController,
    private val drawerController: DrawerController,
    private val viewPagerController: ViewPagerController
) : AnkoComponent<PostsFragment> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<PostsFragment>) = with(ui) {
        relativeLayout {
            backgroundColorResource = style.background.backgroundColorRes

            PostsFragmentUiToolbar(postsFragmentViewModel, overflowController, drawerController)
                .createView(AnkoContext.createDelegate(this))

            PostsFragmentUiContent(
                postsFragmentViewModel,
                ui.owner.childFragmentManager,
                clearIconController,
                overflowController,
                tagsController, searchController, viewPagerController
            ).createView(AnkoContext.createDelegate(this))
        }
    }
}