package com.makentoshe.booruchan.booru.model.content.comments

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v7.widget.RecyclerView
import com.makentoshe.booruchan.booru.model.content.common.Downloader
import com.makentoshe.booruchan.booru.view.content.comments.ProgressBarController
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.api.HttpClient
import com.makentoshe.booruchan.common.settings.application.AppSettings

class CommentsContentViewModel(@JvmField val booru: Boor,
                               @JvmField val appSettings: AppSettings,
                               client: HttpClient) : ViewModel() {

    private val downloader = Downloader(client)
    private val dataLoader = CommentsContentDataLoader(downloader, booru)
    private lateinit var adapter: CommentsContentAdapter

    fun newGalleryAdapter(controller: ProgressBarController): RecyclerView.Adapter<*> {
        adapter = CommentsContentAdapter(dataLoader, controller, appSettings.getStyle())
        return adapter
    }

    fun getGalleryAdapter(controller: ProgressBarController): RecyclerView.Adapter<*> {
        return if (this::adapter.isInitialized) {
            adapter
        } else {
            newGalleryAdapter(controller)
        }
    }

    class Factory(private val booru: Boor, private val appSettings: AppSettings)
        : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass == CommentsContentViewModel::class.java) {
                return CommentsContentViewModel(booru, appSettings, HttpClient()) as T
            }
            return super.create(modelClass)
        }
    }

}