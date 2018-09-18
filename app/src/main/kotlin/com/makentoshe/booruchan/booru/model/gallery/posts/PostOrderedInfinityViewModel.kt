package com.makentoshe.booruchan.booru.model.gallery.posts

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v7.widget.RecyclerView
import com.makentoshe.booruchan.common.api.Boor

class PostOrderedInfinityViewModel(val booru: Boor) : ViewModel() {

    private lateinit var currentGalleryAdapter: PostOrderedInfinityAdapter

    fun getGalleryAdapter(searchTerm: String? = ""): RecyclerView.Adapter<*> {
        return if (this::currentGalleryAdapter.isInitialized) {
            currentGalleryAdapter
        } else {
            newGalleryAdapter(searchTerm)
        }
    }

    fun newGalleryAdapter(searchTerm: String? = ""): RecyclerView.Adapter<*> {
        val adapter = PostOrderedInfinityAdapter(this, searchTerm!!)
        try {
            return adapter
        } finally {
            currentGalleryAdapter = adapter
        }
    }

    fun getSearchTerm(): String {
        return if (this::currentGalleryAdapter.isInitialized) {
            currentGalleryAdapter.searchTerm
        } else {
            ""
        }
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