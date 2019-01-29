package com.makentoshe.booruchan.posts.view

import android.graphics.Color
import android.view.View
import android.widget.RelativeLayout
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.posts.model.OverflowState
import com.makentoshe.booruchan.posts.PostsFragmentViewModel
import com.makentoshe.booruchan.posts.animations.CoverHideAnimator
import com.makentoshe.booruchan.posts.animations.CoverShowAnimator
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
        postsFragmentViewModel.uiController.addOverflowListener {
            onTransition {
                when (it.finishState) {
                    OverflowState.Cross ->
                        CoverShowAnimator(this@addOverflowListener, it.transitionDuration).animate()
                    OverflowState.Magnify ->
                        CoverHideAnimator(this@addOverflowListener, it.transitionDuration).animate()
                }
            }
        }
    }

    private fun onCoverClick(ignored: View) {
        val overflowController = postsFragmentViewModel.uiController.overflowController
        if (overflowController.value == OverflowState.Cross) {
            overflowController.newState(OverflowState.Transition(OverflowState.Magnify))
        }
    }
}