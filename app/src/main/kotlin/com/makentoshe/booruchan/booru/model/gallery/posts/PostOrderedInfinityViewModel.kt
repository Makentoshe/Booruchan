package com.makentoshe.booruchan.booru.model.gallery.posts

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v7.widget.RecyclerView
import com.makentoshe.booruchan.common.api.Boor

class PostOrderedInfinityViewModel(val booru: Boor) : ViewModel() {

    fun getGalleryAdapter(): RecyclerView.Adapter<*>? {
        return PostOrderedInfinityAdapter(this)
    }

    class PostOrderedInfinityViewModelFactory(private val booru: Boor)
        : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass == PostOrderedInfinityViewModel::class.java) {
                return PostOrderedInfinityViewModel(booru) as T
            }
            return super.create(modelClass)
        }

    }
}