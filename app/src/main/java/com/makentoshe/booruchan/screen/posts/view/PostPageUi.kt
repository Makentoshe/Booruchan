package com.makentoshe.booruchan.screen.posts.view

import android.view.View
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import org.jetbrains.anko.*

class PostPageUi : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>): View = with(ui) {
        gridView {
            id = R.id.posts_page

            context.configuration(orientation = Orientation.PORTRAIT) {
                numColumns = 3
            }
            context.configuration(orientation = Orientation.LANDSCAPE) {
                numColumns = 6
            }

            lparams(matchParent, matchParent)
        }
    }
}