package com.makentoshe.booruchan.booru.model.content.posts

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v7.widget.RecyclerView
import com.makentoshe.booruchan.booru.model.content.common.AdapterDataLoaderBuilder
import com.makentoshe.booruchan.booru.model.content.common.Downloader
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.api.HttpClient
import com.makentoshe.booruchan.common.settings.application.AppSettings

class PostsContentViewModel(@JvmField val booru: Boor,
                            @JvmField val appSettings: AppSettings,
                            client: HttpClient) : ViewModel() {

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

    override fun onCleared() {
        currentGalleryAdapter.clear()
        super.onCleared()
        println("Clear VM")
    }

    class Factory(private val booru: Boor, private val appSettings: AppSettings)
        : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass == PostsContentViewModel::class.java) {
                return PostsContentViewModel(booru, appSettings, HttpClient()) as T
            }
            return super.create(modelClass)
        }

    }
}

