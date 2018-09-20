package com.makentoshe.booruchan.booru.model

import android.content.Context
import android.view.View
import android.widget.ListAdapter
import android.widget.ListView
import com.makentoshe.booruchan.common.api.Boor

interface PanelViewModel {

    fun getBooru(): Boor

    fun isUserLoggedIn(): Boolean

    fun getServiceListAdapter(context: Context): ListAdapter

    fun setSelectedItemPositionToStart()

    fun onItemSelect(view: View, position: Int, listView: ListView)

}