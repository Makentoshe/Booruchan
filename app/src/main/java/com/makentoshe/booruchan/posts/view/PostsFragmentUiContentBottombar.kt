package com.makentoshe.booruchan.posts.view

import android.view.View
import android.widget.RelativeLayout
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.posts.PostsFragmentViewModel
import org.jetbrains.anko.*

class PostsFragmentUiContentBottombar(
    private val postsFragmentViewModel: PostsFragmentViewModel
) : AnkoComponent<RelativeLayout> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<RelativeLayout>): View = with(ui) {
        relativeLayout {
            id = R.id.bottombar
            elevation = dip(4).toFloat()
            backgroundColorResource = style.toolbar.primaryColorRes
            lparams(width = matchParent, height = dip(56)) { alignParentBottom() }
        }
    }
}