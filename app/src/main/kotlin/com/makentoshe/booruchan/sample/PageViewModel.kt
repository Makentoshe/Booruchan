package com.makentoshe.booruchan.sample

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.api.entity.Post
import com.makentoshe.booruchan.sample.view.PageFragment
import java.io.File

class PageViewModel(@JvmField val booru: Boor, @JvmField val tags: String,
                    @JvmField val position: Int) : ViewModel() {

    suspend fun loadPostData(): Post =
            booru.getPostsByTags(1, tags, position).getPost(0)

    suspend fun loadPostImage(post: Post): Bitmap {
        return when (File(post.sampleUrl).extension.toLowerCase()) {
            "gif" -> BitmapFactory.decodeStream(booru.client.get(post.previewUrl).stream())
            "webm" -> BitmapFactory.decodeStream(booru.client.get(post.previewUrl).stream())
            else -> BitmapFactory.decodeStream(booru.client.get(post.sampleUrl).stream())
        }
    }


    class Factory(private val arguments: Bundle, private val booru: Boor) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass == PageViewModel::class.java) {
                val pos = arguments.getInt(PageFragment.ARG_POSITION)
                val tags = arguments.getString(PageFragment.ARG_TAGS)!!
                return PageViewModel(booru, tags, pos) as T
            }
            return super.create(modelClass)
        }

    }

}