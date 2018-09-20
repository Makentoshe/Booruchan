package com.makentoshe.booruchan.booru.model

import android.content.Context
import android.widget.ListAdapter
import com.makentoshe.booruchan.common.api.Boor

interface PanelViewModel {

    fun getBooru(): Boor

    fun isUserLoggedIn(): Boolean

    fun getServiceListAdapter(context: Context): ListAdapter

}