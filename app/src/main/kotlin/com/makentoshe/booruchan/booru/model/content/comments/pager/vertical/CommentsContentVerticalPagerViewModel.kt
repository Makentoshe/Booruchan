package com.makentoshe.booruchan.booru.model.content.comments.pager.vertical

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.api.HttpClient
import com.makentoshe.booruchan.common.settings.application.AppSettings

class CommentsContentVerticalPagerViewModel(
        private val booru: Boor,
        client: HttpClient,
        @JvmField val appSettings: AppSettings) : ViewModel() {

    class Factory(private val booru: Boor, private val appSettings: AppSettings)
        : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass == CommentsContentVerticalPagerViewModel::class.java) {
                return CommentsContentVerticalPagerViewModel(booru, HttpClient(), appSettings) as T
            }
            return super.create(modelClass)
        }
    }

}