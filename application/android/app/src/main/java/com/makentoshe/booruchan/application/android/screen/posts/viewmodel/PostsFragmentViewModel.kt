package com.makentoshe.booruchan.application.android.screen.posts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import context.BooruContext

class PostsFragmentViewModel(private val booruContext: BooruContext) : ViewModel() {

    val title = booruContext.title

    class Factory(private val booruContext: BooruContext) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PostsFragmentViewModel(booruContext) as T
        }
    }
}