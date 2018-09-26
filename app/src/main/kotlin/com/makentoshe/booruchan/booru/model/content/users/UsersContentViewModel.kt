package com.makentoshe.booruchan.booru.model.content.users

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.api.HttpClient

class UsersContentViewModel(@JvmField val booru: Boor, client: HttpClient) : ViewModel() {


    class Factory(private val booru: Boor) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass == UsersContentViewModel::class.java) {
                return UsersContentViewModel(booru, HttpClient()) as T
            }
            return super.create(modelClass)
        }
    }

}