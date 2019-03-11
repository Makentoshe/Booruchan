package com.makentoshe.booruchan.screen.sampleinfo

import android.view.View

class SampleInfoUiToolbarAnimator(
    private val tagsview: View,
    private val infoview: View,
    private val commview: View
) {
    fun onPageScrolled(position: Int, offset: Float) {
        when (position) {
            0 -> infoScroll(offset)
            1 -> tagsScroll(offset)
            2 -> commScroll(offset)
        }
    }

    private fun infoScroll(offset: Float) {
        infoview.visibility = View.VISIBLE
        if (offset == 0f) {
            tagsview.visibility = View.GONE
        } else {
            tagsview.visibility = View.VISIBLE
            tagsview.alpha = offset
            infoview.alpha = 1 - offset
        }
    }

    private fun tagsScroll(offset: Float) {
        tagsview.visibility = View.VISIBLE
        if (offset == 0f) {
            commview.visibility = View.GONE
        } else {
            commview.visibility = View.VISIBLE
            commview.alpha = offset
            tagsview.alpha = 1 - offset
        }
        println("tags $offset")
    }

    private fun commScroll(offset: Float) {
        commview.visibility = View.VISIBLE
        if (offset == 0f) {
            tagsview.visibility = View.GONE
        }
        println("comm $offset")
    }
}