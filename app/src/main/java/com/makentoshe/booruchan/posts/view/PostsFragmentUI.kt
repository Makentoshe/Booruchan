package com.makentoshe.booruchan.posts.view

import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.posts.PostsFragment
import com.makentoshe.booruchan.posts.PostsFragmentViewModel
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColorResource
import org.jetbrains.anko.relativeLayout

class PostsFragmentUI(private val postsFragmentViewModel: PostsFragmentViewModel) : AnkoComponent<PostsFragment> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<PostsFragment>) = with(ui) {
        relativeLayout {
            backgroundColorResource = style.background.backgroundColorRes
            PostsFragmentUiToolbar(postsFragmentViewModel).createView(AnkoContext.createDelegate(this))
            PostsFragmentUiContent(postsFragmentViewModel).createView(AnkoContext.createDelegate(this))
        }
    }
}