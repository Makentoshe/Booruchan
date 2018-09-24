package com.makentoshe.booruchan.booru.model.content

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.model.AutocompleteAdapter
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.styles.Style

interface ContentViewModel {

    fun getBooru(): Boor

    fun addSearchTermObserver(owner: LifecycleOwner, observer: (String) -> (Unit))

    fun addValueForObserver(value: String)

    fun changeSearchLabelState(activity: AppCompatActivity, style: Style)

    fun showSearchLabel(activity: AppCompatActivity, style: Style)

    fun hideSearchLabel(activity: AppCompatActivity, style: Style)

    fun getAutocompleteAdapter(context: Context): AutocompleteAdapter

    fun addSelectedItemPositionObserver(owner: LifecycleOwner, observer: (Int?) -> (Unit))

    fun getSubtitleResByIndex(context: Context, index: Int): String {
        val subtitles = context.resources.getStringArray(R.array.subservices)
        return subtitles[index]
    }

    fun removeSearchTermObservers(owner: LifecycleOwner)
}