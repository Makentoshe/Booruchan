package com.makentoshe.booruchan.application.android.screen.samples.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruchan.application.core.arena.Arena
import com.makentoshe.booruchan.core.post.Image
import com.makentoshe.booruchan.core.post.Post

class SampleFragmentViewModel(
    private val post: Post,
    private val previewArena: Arena<Image, ByteArray>
) : ViewModel() {

//    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            println("----------------start----------------")
//            println(previewArena.suspendFetch(post.previewImage))
//        }
//    }

    class Factory(
        private val previewArena: Arena<Image, ByteArray>,
        private val post: Post
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SampleFragmentViewModel(post, previewArena) as T
        }
    }
}