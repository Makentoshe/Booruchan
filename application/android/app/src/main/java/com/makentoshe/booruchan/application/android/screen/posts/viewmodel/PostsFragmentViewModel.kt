package com.makentoshe.booruchan.application.android.screen.posts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import context.BooruContext
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class PostsFragmentViewModel(
    private val booruContext: BooruContext,
    private val client: HttpClient
) : ViewModel() {

    val title = booruContext.title

    init {
        val postsContext = booruContext.posts { client.get(HttpRequestBuilder { takeFrom(it.url) }) }
        postsContext

    }

    class Factory(private val booruContext: BooruContext, private val client: HttpClient) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PostsFragmentViewModel(booruContext, client) as T
        }
    }
}