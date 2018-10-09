package com.makentoshe.booruchan.booru.content.posts.infinity.ordered.model.animator

import android.support.v7.view.menu.ActionMenuItemView
import android.view.View
import com.makentoshe.booruchan.common.styles.Style

class ViewAnimator {

    private var lockToHide = true
    private var lockToShow = false
    private var isDisplay = false
    @Volatile
    private var firstIsFinish = false

    private val alphaLabelAnimator: AlphaLabelAnimator by lazy {
        AlphaLabelAnimator()
    }

    private val searchLabelAnimator: SearchLabelAnimator by lazy {
        SearchLabelAnimator()
    }

    private val iconAnimator: IconAnimator by lazy {
        IconAnimator()
    }

    fun showSearchAndAlphaLabels(searchLabel: View, alphaLabel: View, icon: ActionMenuItemView, style: Style) {
        if (lockToShow) return
        isDisplay = true
        lockToShow = true
        val lambda = {
            if (firstIsFinish) {
                lockToHide = false
                firstIsFinish = false
            } else {
                firstIsFinish = true
            }
        }
        iconAnimator.toCross(icon, style)
        alphaLabelAnimator.show(alphaLabel, lambda)
        searchLabelAnimator.show(searchLabel, lambda)
    }

    fun hideSearchAndAlphaLabels(searchLabel: View, alphaLabel: View, icon: ActionMenuItemView, style: Style) {
        if (lockToHide) return
        isDisplay = false
        lockToHide = true
        val lambda = {
            if (firstIsFinish) {
                lockToShow = false
                firstIsFinish = false
            } else {
                firstIsFinish = true
            }
        }
        iconAnimator.toMagnify(icon, style)
        alphaLabelAnimator.hide(alphaLabel, lambda)
        searchLabelAnimator.hide(searchLabel, lambda)
    }

    fun isDisplay(): Boolean {
        return isDisplay
    }

    companion object {
        var DURATION = 200L
    }

}