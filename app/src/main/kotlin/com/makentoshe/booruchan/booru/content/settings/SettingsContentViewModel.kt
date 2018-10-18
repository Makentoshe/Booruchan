package com.makentoshe.booruchan.booru.content.settings

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.makentoshe.booruchan.common.api.Boor

class SettingsContentViewModel(@JvmField val booru: Boor) : ViewModel() {


    class Factory(private val booru: Boor) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass == SettingsContentViewModel::class.java) {
                return SettingsContentViewModel(booru) as T
            }
            return super.create(modelClass)
        }
    }
}