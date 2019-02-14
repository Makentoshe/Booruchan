package com.makentoshe.booruchan.postpreview.view

import com.makentoshe.booruchan.postpreview.PostPageFragment
import com.makentoshe.booruchan.postpreview.PostPageFragmentViewModel
import com.makentoshe.booruchan.postpreview.PostsDownloadController
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.relativeLayout

class PostPageFragmentUi(
    private val viewModel: PostPageFragmentViewModel,
    private val postsDownloadController: PostsDownloadController
) : AnkoComponent<PostPageFragment> {

    override fun createView(ui: AnkoContext<PostPageFragment>) = with(ui) {
        relativeLayout {
            PostPageFragmentUiProgress(postsDownloadController).createView(AnkoContext.createDelegate(this))
            PostPageFragmentUiMessage(postsDownloadController).createView(AnkoContext.createDelegate(this))
            PostPageFragmentUiContent(viewModel, postsDownloadController).createView(AnkoContext.createDelegate(this))
        }
    }
}