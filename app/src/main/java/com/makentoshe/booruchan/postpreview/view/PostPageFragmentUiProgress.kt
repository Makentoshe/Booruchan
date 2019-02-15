package com.makentoshe.booruchan.postpreview.view

import android.graphics.PorterDuff
import android.view.View
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postpreview.model.PostsDownloadController
import org.jetbrains.anko.*

class PostPageFragmentUiProgress(
    private val postsDownloadController: PostsDownloadController
) : AnkoComponent<_RelativeLayout> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<_RelativeLayout>): View = with(ui.owner) {
        progressBar {
            id = R.id.postpreviewpage_progressbar
            isIndeterminate = true
            indeterminateDrawable.setColorFilter(
                style.toolbar.getPrimaryColor(context),
                PorterDuff.Mode.SRC_ATOP
            )
            postsDownloadController.addOnPostsReceiveListener {
                visibility = View.GONE
            }
        }.lparams {
            centerInParent()
        }
    }
}