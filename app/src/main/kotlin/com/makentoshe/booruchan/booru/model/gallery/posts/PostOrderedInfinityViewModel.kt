package com.makentoshe.booruchan.booru.model.gallery.posts

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v7.widget.RecyclerView
import com.makentoshe.booruchan.booru.model.gallery.common.AdapterDataLoaderBuilder
import com.makentoshe.booruchan.booru.model.gallery.common.Downloader
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.api.HttpClient

class PostOrderedInfinityViewModel(val booru: Boor, client: HttpClient) : ViewModel() {

    private lateinit var currentGalleryAdapter: PostOrderedInfinityAdapter
    private val downloader = Downloader(client)
    private val adapterLoaderBuilder = AdapterDataLoaderBuilder(downloader, booru)
    private var searchTerm = ""

    fun getGalleryAdapter(searchTerm: String? = ""): RecyclerView.Adapter<*> {
        return if (this::currentGalleryAdapter.isInitialized) {
            currentGalleryAdapter
        } else {
            newGalleryAdapter(searchTerm)
        }
    }

    fun newGalleryAdapter(searchTerm: String? = ""): RecyclerView.Adapter<*> {
        val adapter = PostOrderedInfinityAdapter(adapterLoaderBuilder.build(searchTerm!!))
        try {
            return adapter
        } finally {
            currentGalleryAdapter = adapter
            this.searchTerm = searchTerm
        }
    }

    fun getSearchTerm(): String {
        return searchTerm
    }

    class PostOrderedInfinityViewModelFactory(private val booru: Boor)
        : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass == PostOrderedInfinityViewModel::class.java) {
                return PostOrderedInfinityViewModel(booru, HttpClient()) as T
            }
            return super.create(modelClass)
        }

    }
}

