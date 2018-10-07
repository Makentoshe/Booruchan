package com.makentoshe.booruchan.booru.content.comments.vertical.pager

import android.arch.lifecycle.*
import android.arch.lifecycle.Observer
import com.makentoshe.booruchan.booru.content.model.Downloader
import com.makentoshe.booruchan.booru.content.model.PreviewLoader
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.api.HttpClient
import com.makentoshe.booruchan.common.api.entity.Comment
import com.makentoshe.booruchan.common.api.entity.Post
import com.makentoshe.booruchan.common.runOnUi
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch

class PageViewModel(private val booru: Boor,
                    private val client: HttpClient) : ViewModel() {

    private val liveData = MutableLiveData<List<Pair<Post, List<Comment>>>>()
    @JvmField val previewLoader = PreviewLoader(Downloader(client))

    fun getCommentedPosts(page: Int, lifecycleOwner: LifecycleOwner,
                          observer: (List<Pair<Post, List<Comment>>>?) -> (Unit)) {
        subscribeObserver(lifecycleOwner, observer)
        loadDataForObserver(page)
    }

    private fun subscribeObserver(lifecycleOwner: LifecycleOwner, observer: (List<Pair<Post, List<Comment>>>?) -> Unit) {
        liveData.observe(lifecycleOwner, Observer { observer(it) })
    }

    private fun loadDataForObserver(page: Int) {
        GlobalScope.launch {
            val value = booru.getListOfLastCommentedPosts(page, client)
            runOnUi { liveData.value = value }
        }
    }

    class Factory(private val booru: Boor): ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass == PageViewModel::class.java) {
                return PageViewModel(booru, HttpClient()) as T
            }
            return super.create(modelClass)
        }
    }

}