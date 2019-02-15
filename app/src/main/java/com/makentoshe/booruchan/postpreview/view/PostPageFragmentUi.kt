package com.makentoshe.booruchan.postpreview.view

import com.makentoshe.booruchan.postpreview.PostPageFragment
import com.makentoshe.booruchan.postpreview.model.AdapterBuilder
import com.makentoshe.booruchan.postpreview.model.NavigationController
import com.makentoshe.booruchan.postpreview.model.PostsDownloadController
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.relativeLayout

class PostPageFragmentUi(
    private val postsDownloadController: PostsDownloadController,
    private val adapterBuilder: AdapterBuilder,
    private val navigationController: NavigationController
) : AnkoComponent<PostPageFragment> {

    override fun createView(ui: AnkoContext<PostPageFragment>) = with(ui) {
        relativeLayout {
            PostPageFragmentUiProgress(postsDownloadController).createView(AnkoContext.createDelegate(this))
            PostPageFragmentUiMessage(postsDownloadController).createView(AnkoContext.createDelegate(this))
            PostPageFragmentUiContent(
                postsDownloadController,
                adapterBuilder,
                navigationController
            ).createView(AnkoContext.createDelegate(this))
        }
    }
}