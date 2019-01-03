package com.makentoshe.booruchan.postpage.model

import android.annotation.SuppressLint
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruchan.posts.model.PostsRepository
import io.reactivex.subjects.BehaviorSubject
import java.io.Serializable

class PostsDownloadController(private val postsRepository: PostsRepository): Serializable {

    private val postsObservable = BehaviorSubject.create<Posts>()

    val value: Posts?
        get() = postsObservable.value

    fun loadPosts(position: Int) {
        postsObservable.onNext(postsRepository.get(position + 1))
    }

    @SuppressLint("CheckResult")
    fun subscribe(action: (Posts) -> Unit) {
        postsObservable.subscribe(action)
    }
}