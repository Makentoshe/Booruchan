package com.makentoshe.booruchan.booru

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.view.View
import android.widget.ListAdapter
import android.widget.ListView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.model.panel.SelectableServiceAdapter
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.styles.Style
import org.jetbrains.anko.backgroundResource

class PanelViewModel(@JvmField val booru: Boor): ViewModel() {

    private val selectedItemPositionLiveData = MutableLiveData<Int>()

    fun isUserLoggedIn(): Boolean {
        return true
    }

    fun getServiceListAdapter(context: Context, style: Style): ListAdapter {
        val strings: Array<CharSequence> = context.resources.getTextArray(R.array.subservices)
        return SelectableServiceAdapter(context, android.R.layout.simple_list_item_1,
                strings.asList(), style.assentSecondaryColor)
    }

    fun setSelectedItemPositionToStart() {
        selectedItemPositionLiveData.value = 0
    }

    fun onItemSelect(view: View, position: Int, listView: ListView, style: Style) {
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

    fun addSelectedItemPositionObserver(owner: LifecycleOwner, observer: (Int?) -> (Unit)) {
        selectedItemPositionLiveData.observe(owner, Observer<Int> {
            observer(it)
        })
    }

    class Factory(private val booru: Boor): ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass == PanelViewModel::class.java) {
                return PanelViewModel(booru) as T
            }
            return super.create(modelClass)
        }

    }

}