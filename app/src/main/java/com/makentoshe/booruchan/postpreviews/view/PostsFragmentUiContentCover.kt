package com.makentoshe.booruchan.postpreviews.view

import android.graphics.Color
import android.view.View
import android.widget.RelativeLayout
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postpreviews.animations.CoverHideAnimator
import com.makentoshe.booruchan.postpreviews.animations.CoverShowAnimator
import com.makentoshe.booruchan.postpreviews.model.OverflowController
import com.makentoshe.booruchan.postpreviews.model.OverflowRxController
import org.jetbrains.anko.*

class PostsFragmentUiContentCover(
    private val overflowController: OverflowController
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
        overflowController.onOverflowStateChangedListener {
            onTransition {
                when (it.finishState) {
                    OverflowRxController.OverflowState.Cross ->
                        CoverShowAnimator(this@addOverflowListener, it.transitionDuration).animate()
                    OverflowRxController.OverflowState.Magnify ->
                        CoverHideAnimator(this@addOverflowListener, it.transitionDuration).animate()
                }
            }
        }
    }

    private fun onCoverClick(ignored: View) = overflowController.toMagnify()
}