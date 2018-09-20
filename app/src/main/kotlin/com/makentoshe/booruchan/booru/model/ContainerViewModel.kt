package com.makentoshe.booruchan.booru.model

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.styles.Style

interface ContainerViewModel {

    fun getBooru(): Boor

    fun addSearchTermObserver(owner: LifecycleOwner, observer: (String?) -> (Unit))

    fun addValueForObserver(value: String)

    fun changeSearchLabelState(activity: AppCompatActivity, style: Style)

    fun showSearchLabel(activity: AppCompatActivity, style: Style)

    fun hideSearchLabel(activity: AppCompatActivity, style: Style)

    fun getAutocompleteAdapter(context: Context): AutocompleteAdapter

    fun addSelectedItemPositionObserver(owner: LifecycleOwner, observer: (Int?) -> (Unit))
}