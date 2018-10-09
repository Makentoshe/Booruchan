package com.makentoshe.booruchan.booru.content.comments.vertical.pager

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.makentoshe.booruchan.common.settings.application.AppSettings

class CommentsViewModel(@JvmField val appSettings: AppSettings) : ViewModel() {

    class Factory(private val appSettings: AppSettings) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass == CommentsViewModel::class.java) {
                return CommentsViewModel(appSettings) as T
            }
            return super.create(modelClass)
        }
    }

}