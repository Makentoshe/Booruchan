package com.makentoshe.booruchan.sample

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.api.HttpClient
import com.makentoshe.booruchan.common.api.entity.Post
import com.makentoshe.booruchan.sample.view.PageFragment

class PageViewModel(@JvmField val booru: Boor, @JvmField val tags: String,
                    @JvmField val position: Int, private val client: HttpClient) : ViewModel() {

    suspend fun loadPostData(): Post =
            booru.getPostsByTags(1, tags, position, client).getPost(0)

    suspend fun loadPostImage(post: Post): Bitmap =
            BitmapFactory.decodeStream(client.get(post.sampleUrl).stream())

    class Factory(private val arguments: Bundle, private val booru: Boor) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass == PageViewModel::class.java) {
                val pos = arguments.getInt(PageFragment.ARG_POSITION)
                val tags = arguments.getString(PageFragment.ARG_TAGS)
                return PageViewModel(booru, tags, pos, HttpClient()) as T
            }
            return super.create(modelClass)
        }

    }

}