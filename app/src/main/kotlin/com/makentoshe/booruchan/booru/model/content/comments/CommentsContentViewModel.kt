package com.makentoshe.booruchan.booru.model.content.comments

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v4.util.ArraySet
import android.support.v7.widget.RecyclerView
import com.makentoshe.booruchan.booru.model.content.common.Downloader
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.api.HttpClient
import com.makentoshe.booruchan.common.settings.application.AppSettings

class CommentsContentViewModel(@JvmField val booru: Boor,
                               @JvmField val appSettings: AppSettings,
                               client: HttpClient) : ViewModel() {

    private val downloader = Downloader(client)
    private val dataLoader = CommentsContentDataLoader(downloader, booru)
    private lateinit var adapter: CommentsContentAdapter
    @Volatile
    private var page = 1

    fun newRecycleViewAdapter(pageIncrement: Int = 0,
                              previousIdsArray: List<Int> = ArrayList(),
                              action: (RecyclerView.Adapter<CommentsContentAdapter.ViewHolder>) -> Unit) {

        page += pageIncrement
        dataLoader.getPostIds(page) {
            val list = ArrayList<Int>()
            list.addAll(previousIdsArray)
            adapter = CommentsContentAdapter(dataLoader, list.addWithCheck(it))
            action(adapter)
        }
    }

    private fun MutableList<Int>.addWithCheck(arr: IntArray): List<Int> {
        for (int in arr) {
            if (this.contains(int)) continue
            else this.add(int)
        }
        return this
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