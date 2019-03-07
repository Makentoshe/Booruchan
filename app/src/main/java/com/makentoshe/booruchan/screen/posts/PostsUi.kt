package com.makentoshe.booruchan.screen.posts

import android.view.View
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.screen.BooruToolbarUi
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.relativeLayout

class PostsUi : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>): View = with(ui) {
        relativeLayout {
            BooruToolbarUi()
                .createView(AnkoContext.createDelegate(this))

        }
    }
}