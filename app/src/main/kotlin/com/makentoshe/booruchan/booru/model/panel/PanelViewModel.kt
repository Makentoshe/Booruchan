package com.makentoshe.booruchan.booru.model.panel

import android.content.Context
import android.view.View
import android.widget.ListAdapter
import android.widget.ListView
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.styles.Style

interface PanelViewModel {

    fun getBooru(): Boor

    fun isUserLoggedIn(): Boolean

    fun getServiceListAdapter(context: Context, style: Style): ListAdapter

    fun setSelectedItemPositionToStart()

    fun onItemSelect(view: View, position: Int, listView: ListView, style: Style)

}