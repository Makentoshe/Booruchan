package com.makentoshe.booruchan.booru

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.menu.ActionMenuItemView
import android.view.View
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.model.AutocompleteAdapter
import com.makentoshe.booruchan.booru.model.animator.ViewAnimator
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.hideKeyboard
import com.makentoshe.booruchan.common.styles.Style

class BooruViewModel(val booru: Boor) : ViewModel() {

    private val animator: ViewAnimator by lazy {
        ViewAnimator()
    }

    fun changeSearchLabelState(activity: AppCompatActivity, style: Style) {
        if (animator.isDisplay()) {
            hideSearchLabel(activity, style)
        } else {
            showSearchLabel(activity, style)
        }
    }

    fun showSearchLabel(activity: AppCompatActivity, style: Style) {
        val searchLabel = activity.findViewById<View>(R.id.booru_content_search)
        val alphaLabel = activity.findViewById<View>(R.id.booru_content_alpha)
        val icon = activity.findViewById<ActionMenuItemView>(R.id.action_show_search)
        animator.showSearchAndAlphaLabels(searchLabel, alphaLabel, icon, style)
    }

    fun hideSearchLabel(activity: AppCompatActivity, style: Style) {
        val searchLabel = activity.findViewById<View>(R.id.booru_content_search)
        val alphaLabel = activity.findViewById<View>(R.id.booru_content_alpha)
        val icon = activity.findViewById<ActionMenuItemView>(R.id.action_show_search)
        animator.hideSearchAndAlphaLabels(searchLabel, alphaLabel, icon, style)
        hideKeyboard(activity)
    }

    fun getAutocompleteAdapter(context: Context, boor: Boor): AutocompleteAdapter {
        return AutocompleteAdapter(context, boor)
    }

}
