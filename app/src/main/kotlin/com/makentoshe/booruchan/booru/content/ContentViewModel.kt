package com.makentoshe.booruchan.booru.content

import android.arch.lifecycle.*
import android.content.Context
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.content.model.AutocompleteAdapter
import com.makentoshe.booruchan.common.api.Boor

class ContentViewModel(@JvmField val booru: Boor) : ViewModel() {

    private val searchTermLiveData = MutableLiveData<String>()

    fun addSearchTermObserver(owner: LifecycleOwner, observer: (String) -> (Unit)) {
        searchTermLiveData.observe(owner, Observer<String> {
            observer.invoke(it!!)
        })
    }

    fun addValueForObserver(value: String) {
        searchTermLiveData.value = value
    }

    fun removeSearchTermObservers(owner: LifecycleOwner) {
        searchTermLiveData.removeObservers(owner)
    }

    fun getAutocompleteAdapter(context: Context): AutocompleteAdapter {
        return AutocompleteAdapter(context, booru)
    }

    fun getSubtitleResByIndex(context: Context, index: Int): String {
        val subtitles = context.resources.getStringArray(R.array.subservices)
        return subtitles[index]
    }

    class Factory(private val booru: Boor): ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass == ContentViewModel::class.java) {
                return ContentViewModel(booru) as T
            }
            return super.create(modelClass)
        }

    }

}