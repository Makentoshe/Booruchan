package com.makentoshe.booruchan.application.android.screen.posts.view

import android.view.View
import com.makentoshe.booruchan.application.android.R
import com.makentoshe.booruchan.application.android.fragment.CoreFragment
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class PostsSlidingUpPanelListener(
    private val coreFragment: CoreFragment, private val view: View
) : SlidingUpPanelLayout.SimplePanelSlideListener() {

    override fun onPanelStateChanged(
        panel: View?, previousState: SlidingUpPanelLayout.PanelState?, newState: SlidingUpPanelLayout.PanelState?
    ) {
        if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED || newState == SlidingUpPanelLayout.PanelState.HIDDEN) {
            coreFragment.hideSoftKeyboard(view)
        }

        if (newState == SlidingUpPanelLayout.PanelState.DRAGGING) {
            if (previousState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                coreFragment.requireActivity().findViewById<View>(R.id.fragment_booru_navigation).visibility =
                    View.VISIBLE
            } else {
                coreFragment.requireActivity().findViewById<View>(R.id.fragment_booru_navigation).visibility = View.GONE
            }
        }
    }
}