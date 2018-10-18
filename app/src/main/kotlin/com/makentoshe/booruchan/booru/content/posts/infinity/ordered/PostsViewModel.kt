package com.makentoshe.booruchan.booru.content.posts.infinity.ordered

import android.arch.lifecycle.ViewModelProvider
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import com.makentoshe.booruchan.booru.content.posts.BooruPostNavigator
import com.makentoshe.booruchan.booru.content.posts.ViewModel
import com.makentoshe.booruchan.booru.content.posts.infinity.ordered.model.AdapterDataLoaderBuilder
import com.makentoshe.booruchan.booru.content.posts.infinity.ordered.model.RecycleViewAdapter
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.settings.application.AppSettings

class PostsViewModel(@JvmField val booru: Boor,
                     @JvmField val appSettings: AppSettings) : ViewModel() {

    private lateinit var currentGalleryAdapter: RecycleViewAdapter
    private val adapterLoaderBuilder = AdapterDataLoaderBuilder(booru)
    private var searchTerm = ""
    private val navigator = BooruPostNavigator()

    fun getGalleryAdapter(searchTerm: String = ""): RecyclerView.Adapter<*> {
        return if (this::currentGalleryAdapter.isInitialized) {
            currentGalleryAdapter
        } else {
            newGalleryAdapter(searchTerm)
        }
    }

    fun newGalleryAdapter(searchTerm: String = ""): RecyclerView.Adapter<*> {
        val adapter = RecycleViewAdapter(adapterLoaderBuilder.build(searchTerm), this)
        try {
            return adapter
        } finally {
            currentGalleryAdapter = adapter
            this.searchTerm = searchTerm
        }
    }

    fun getSearchTerm() = searchTerm

    override fun startSampleActivity(activity: FragmentActivity, itemId: Int) =
            navigator.startSampleActivity(activity, itemId, booru, searchTerm)

    override fun onCleared() {
        currentGalleryAdapter.clear()
        super.onCleared()
        println("Clear VM")
    }

    class Factory(private val booru: Boor, private val appSettings: AppSettings) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : android.arch.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass == PostsViewModel::class.java) {
                return PostsViewModel(booru, appSettings) as T
            }
            return super.create(modelClass)
        }
    }
}

