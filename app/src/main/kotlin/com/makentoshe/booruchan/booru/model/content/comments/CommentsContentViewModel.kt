package com.makentoshe.booruchan.booru.model.content.comments

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v7.widget.RecyclerView
import com.makentoshe.booruchan.booru.model.content.common.Downloader
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.api.HttpClient
import com.makentoshe.booruchan.common.runOnUiSuspend
import com.makentoshe.booruchan.common.settings.application.AppSettings
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import java.util.*

class CommentsContentViewModel(@JvmField val booru: Boor,
                               @JvmField val appSettings: AppSettings,
                               client: HttpClient) : ViewModel() {

    private val downloader = Downloader(client)
    private val dataLoader = CommentsContentDataLoader(downloader, booru)
    private lateinit var adapter: CommentsContentAdapter
    @Volatile
    private var page = 1

    fun newRecycleViewAdapter(pageIncrement: Int = 0,
                              previousIdsArray: IntArray = IntArray(0),
                              action: (RecyclerView.Adapter<CommentsContentAdapter.ViewHolder>) -> Unit) {

        page += pageIncrement
        dataLoader.getPostIds(page) {
            val array = IntArray(it.size + previousIdsArray.size)
            System.arraycopy(previousIdsArray, 0, array, 0, previousIdsArray.size)
            System.arraycopy(it, 0, array, previousIdsArray.size, it.size)
            adapter = CommentsContentAdapter(dataLoader, array)
            action(adapter)
        }
    }

    fun getRecycleViewAdapter(
            action: (RecyclerView.Adapter<CommentsContentAdapter.ViewHolder>) -> Unit) {

        if (this::adapter.isInitialized) {
            action(adapter)
        } else {
            newRecycleViewAdapter(action = action)
        }
    }

    fun updateAdapter(
            action: (RecyclerView.Adapter<CommentsContentAdapter.ViewHolder>) -> Unit) {
        adapter.clear()
        newRecycleViewAdapter(pageIncrement = 1, previousIdsArray = adapter.postIdsList, action = action)
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