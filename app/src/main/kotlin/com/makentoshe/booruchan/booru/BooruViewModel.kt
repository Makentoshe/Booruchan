package com.makentoshe.booruchan.booru

import android.arch.lifecycle.*
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.menu.ActionMenuItemView
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.model.AutocompleteAdapter
import com.makentoshe.booruchan.booru.model.animator.ViewAnimator
import com.makentoshe.booruchan.booru.model.ContainerViewModel
import com.makentoshe.booruchan.booru.model.PanelViewModel
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.hideKeyboard
import com.makentoshe.booruchan.common.styles.Style

class BooruViewModel(private val booru: Boor) : ViewModel(), ContainerViewModel, PanelViewModel {

    private val animator: ViewAnimator by lazy {
        ViewAnimator()
    }

    override fun getBooru(): Boor {
        return booru
    }

    private val searchTermLiveData = MutableLiveData<String>()

    override fun changeSearchLabelState(activity: AppCompatActivity, style: Style) {
        if (animator.isDisplay()) {
            hideSearchLabel(activity, style)
        } else {
            showSearchLabel(activity, style)
        }
    }

    override fun showSearchLabel(activity: AppCompatActivity, style: Style) {
        val searchLabel = activity.findViewById<View>(R.id.booru_content_search)
        val alphaLabel = activity.findViewById<View>(R.id.booru_content_alpha)
        val icon = activity.findViewById<ActionMenuItemView>(R.id.action_show_search)
        animator.showSearchAndAlphaLabels(searchLabel, alphaLabel, icon, style)
    }

    override fun hideSearchLabel(activity: AppCompatActivity, style: Style) {
        val searchLabel = activity.findViewById<View>(R.id.booru_content_search)
        val alphaLabel = activity.findViewById<View>(R.id.booru_content_alpha)
        val icon = activity.findViewById<ActionMenuItemView>(R.id.action_show_search)
        animator.hideSearchAndAlphaLabels(searchLabel, alphaLabel, icon, style)
        hideKeyboard(activity)
    }

    override fun getAutocompleteAdapter(context: Context): AutocompleteAdapter {
        return AutocompleteAdapter(context, booru)
    }

    override fun addSearchTermObserver(owner: LifecycleOwner, observer: (String?) -> (Unit)) {
        searchTermLiveData.observe(owner, Observer<String> {
            observer.invoke(it)
        })
    }

    override fun addValueForObserver(value: String) {
        searchTermLiveData.value = value
    }

    override fun isUserLoggedIn(): Boolean {
        return true
    }

    override fun getServiceListAdapter(context: Context): ListAdapter {
        return ArrayAdapter.createFromResource(context, R.array.subservices, android.R.layout.simple_list_item_1)
    }

}

