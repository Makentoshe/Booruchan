package com.makentoshe.booruchan.booru

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.makentoshe.booruchan.common.api.Boor

class BooruViewModelFactory(private val booru: Boor): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == BooruViewModel::class.java) {
            return BooruViewModel(booru) as T
        }
        return super.create(modelClass)
    }

}