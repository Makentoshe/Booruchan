package com.makentoshe.booruchan.screen.posts.page.view

import android.view.View
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.view.AnkoProgressBar
import org.jetbrains.anko.*

class PostPageUi : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>): View = with(ui) {
        relativeLayout {
            id = R.id.posts_page
            PostPageUiGrid().createView(AnkoContext.createDelegate(this))
            AnkoProgressBar(R.id.posts_page_progress).createView(AnkoContext.createDelegate(this))
            PostPageUiMessage().createView(AnkoContext.createDelegate(this))
        }
    }
}