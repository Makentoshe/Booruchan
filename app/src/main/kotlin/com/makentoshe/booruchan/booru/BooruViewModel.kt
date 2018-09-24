package com.makentoshe.booruchan.booru

import android.arch.lifecycle.*
import android.arch.lifecycle.Observer
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.menu.ActionMenuItemView
import android.view.View
import android.widget.ListAdapter
import android.widget.ListView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.model.AutocompleteAdapter
import com.makentoshe.booruchan.booru.model.animator.ViewAnimator
import com.makentoshe.booruchan.booru.model.content.ContentViewModel
import com.makentoshe.booruchan.booru.model.panel.PanelViewModel
import com.makentoshe.booruchan.booru.model.panel.SelectableServiceAdapter
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.hideKeyboard
import com.makentoshe.booruchan.common.styles.Style
import org.jetbrains.anko.backgroundResource

class BooruViewModel(private val booru: Boor) : ViewModel(), ContentViewModel, PanelViewModel {

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

    override fun addSearchTermObserver(owner: LifecycleOwner, observer: (String) -> (Unit)) {
        searchTermLiveData.observe(owner, Observer<String> {
            observer.invoke(it!!)
        })
    }

    override fun removeSearchTermObservers(owner: LifecycleOwner) {
        searchTermLiveData.removeObservers(owner)
    }

    override fun addValueForObserver(value: String) {
        searchTermLiveData.value = value
    }

    override fun isUserLoggedIn(): Boolean {
        return true
    }

    override fun getServiceListAdapter(context: Context, style: Style): ListAdapter {
        val strings: Array<CharSequence> = context.resources.getTextArray(R.array.subservices)
        return SelectableServiceAdapter(context, android.R.layout.simple_list_item_1,
                strings.asList(), style.assentSecondaryColor)
    }

    private val selectedItemPositionLiveData = MutableLiveData<Int>()

    override fun addSelectedItemPositionObserver(owner: LifecycleOwner, observer: (Int?) -> (Unit)) {
        selectedItemPositionLiveData.observe(owner, Observer<Int> {
            observer(it)
        })
    }

    override fun setSelectedItemPositionToStart() {
        selectedItemPositionLiveData.value = 0
    }

    override fun onItemSelect(view: View, position: Int, listView: ListView, style: Style) {
        if (selectedItemPositionLiveData.value != position) {
            val prevView = listView.getViewByPosition(selectedItemPositionLiveData.value!!)
            prevView.backgroundResource = android.R.color.transparent
            view.backgroundResource = style.assentSecondaryColor
            selectedItemPositionLiveData.value = position
        }
    }

    private fun ListView.getViewByPosition(pos: Int): View {
        val firstListItemPosition = firstVisiblePosition
        val lastListItemPosition = firstListItemPosition + childCount - 1

        return if (pos < firstListItemPosition || pos > lastListItemPosition) {
            adapter.getView(pos, null, this)
        } else {
            val childIndex = pos - firstListItemPosition
            getChildAt(childIndex)
        }
    }

}

