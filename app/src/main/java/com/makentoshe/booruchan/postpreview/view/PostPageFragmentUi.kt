package com.makentoshe.booruchan.postpreview.view

import com.makentoshe.booruchan.postpreview.PostPageFragment
import com.makentoshe.booruchan.postpreview.PostPageFragmentViewModel
import org.jetbrains.anko.*

class PostPageFragmentUi(
    private val viewModel: PostPageFragmentViewModel
) : AnkoComponent<PostPageFragment> {

    override fun createView(ui: AnkoContext<PostPageFragment>) = with(ui) {
        relativeLayout {
            PostPageFragmentUiProgress(viewModel).createView(AnkoContext.createDelegate(this))
            PostPageFragmentUiMessage(viewModel).createView(AnkoContext.createDelegate(this))
            PostPageFragmentUiContent(viewModel).createView(AnkoContext.createDelegate(this))
        }
    }
}