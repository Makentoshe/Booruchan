package com.makentoshe.booruchan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.booru.BooruFragmentViewModel
import com.makentoshe.booruchan.posts.PostsFragmentViewModel
import com.makentoshe.booruchan.start.StartFragmentViewModel

class ViewModelFactory(
    private val booru: Booru? = null
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            StartFragmentViewModel::class.java -> StartFragmentViewModel() as T
            BooruFragmentViewModel::class.java -> BooruFragmentViewModel(booru!!) as T
            PostsFragmentViewModel::class.java -> PostsFragmentViewModel(booru!!) as T
            else -> super.create(modelClass)
        }
    }
}