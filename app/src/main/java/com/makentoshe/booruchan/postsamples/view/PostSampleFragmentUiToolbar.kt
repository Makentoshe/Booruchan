package com.makentoshe.booruchan.postsamples.view

import android.view.View
import android.widget.LinearLayout
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postsamples.PostsSampleFragmentViewModel
import org.jetbrains.anko.*

class PostSampleFragmentUiToolbar(private val viewModel: PostsSampleFragmentViewModel) : AnkoComponent<LinearLayout> {

    private val style = Booruchan.INSTANCE.style

    override fun createView(ui: AnkoContext<LinearLayout>): View = with(ui) {
        relativeLayout {
            lparams(width = matchParent, height = dip(56))
            id = R.id.postpreview_toolbar_container
            backgroundColorResource = style.toolbar.primaryColorRes
            elevation = dip(4).toFloat()

        }
    }


}