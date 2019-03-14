package com.makentoshe.booruchan.screen.posts.view

import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.view.updateLayoutParams
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.booru.view.BooruToolbarUi
import org.jetbrains.anko.*

class PostsUiToolbar : AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui.owner) {
        BooruToolbarUi().createView(AnkoContext.createDelegate(this)).also {
            id = R.id.posts_toolbar
            it as RelativeLayout
            PostsUiToolbarSearch()
                .createView(AnkoContext.createDelegate(it))
                .updateLayoutParams<RelativeLayout.LayoutParams> {
                    alignParentRight()
                    centerVertically()
                }
        }
    }
}