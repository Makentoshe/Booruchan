package com.makentoshe.booruchan.screen.posts.view

import android.view.View
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import org.jetbrains.anko.*

class PostPageUi : AnkoComponent<Fragment> {
    override fun createView(ui: AnkoContext<Fragment>): View = with(ui) {
        relativeLayout {
            id = R.id.posts_page
            PostPageUiGrid().createView(AnkoContext.createDelegate(this))
            PostPageUiProgress().createView(AnkoContext.createDelegate(this))
            PostPageUiMessage().createView(AnkoContext.createDelegate(this))
        }
    }
}