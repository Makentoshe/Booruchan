package com.makentoshe.booruchan.screen.posts.view

import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.view.updateLayoutParams
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.screen.BooruToolbarUi
import com.makentoshe.booruchan.screen.ToolbarIcon
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.alignParentRight
import org.jetbrains.anko.centerVertically

class PostsUiToolbar : AnkoComponent<ViewGroup> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui.owner) {
        BooruToolbarUi().createView(AnkoContext.createDelegate(this)).also{
            id = R.id.posts_toolbar
            if (it !is ViewGroup) return@with it
            it.searchToolbarIcon()
        }
    }

    private fun ViewGroup.searchToolbarIcon() {
        val id = R.id.posts_toolbar_search
        val icon = style.drawable.static.magnify
        val color = style.toolbar.getOnPrimaryColor(context)
        ToolbarIcon(id, icon, color)
            .createView(AnkoContext.createDelegate(this))
            .updateLayoutParams<RelativeLayout.LayoutParams> {
                alignParentRight()
                centerVertically()
            }
    }

}