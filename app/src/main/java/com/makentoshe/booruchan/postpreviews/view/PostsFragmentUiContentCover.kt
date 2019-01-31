package com.makentoshe.booruchan.postpreviews.view

import android.graphics.Color
import android.view.View
import android.widget.RelativeLayout
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postpreviews.PostsFragmentViewModel
import com.makentoshe.booruchan.postpreviews.animations.CoverHideAnimator
import com.makentoshe.booruchan.postpreviews.animations.CoverShowAnimator
import com.makentoshe.booruchan.postpreviews.model.OverflowController
import org.jetbrains.anko.*

class PostsFragmentUiContentCover(
    private val postsFragmentViewModel: PostsFragmentViewModel
) : AnkoComponent<RelativeLayout> {

    override fun createView(ui: AnkoContext<RelativeLayout>): View = with(ui) {
        frameLayout {
            id = R.id.postpreview_cover
            visibility = View.GONE
            alpha = 0f
            backgroundColor = Color.argb(150, 0, 0, 0)
            setOnClickListener(::onCoverClick)
            addOverflowListener()
            lparams(matchParent, matchParent)
        }
    }

    private fun _FrameLayout.addOverflowListener() {
        postsFragmentViewModel.addOnOverflowStateChangedListener {
            onTransition {
                when (it.finishState) {
                    OverflowController.OverflowState.Cross ->
                        CoverShowAnimator(this@addOverflowListener, it.transitionDuration).animate()
                    OverflowController.OverflowState.Magnify ->
                        CoverHideAnimator(this@addOverflowListener, it.transitionDuration).animate()
                }
            }
        }
    }

    private fun onCoverClick(ignored: View) {
        if (postsFragmentViewModel.overflowState == OverflowController.OverflowState.Cross) {
            postsFragmentViewModel.clickOverflowIcon()
        }
    }
}