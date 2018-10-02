package com.makentoshe.booruchan.booru.model.content.comments

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.api.HttpClient
import com.makentoshe.booruchan.common.settings.application.AppSettings

class CommentsContentViewModel(@JvmField val booru: Boor,
                               @JvmField val appSettings: AppSettings,
                               client: HttpClient) : ViewModel() {

    class Factory(private val booru: Boor, private val appSettings: AppSettings)
        : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass == CommentsContentViewModel::class.java) {
                return CommentsContentViewModel(booru, appSettings, HttpClient()) as T
            }
            return super.create(modelClass)
        }
    }

}