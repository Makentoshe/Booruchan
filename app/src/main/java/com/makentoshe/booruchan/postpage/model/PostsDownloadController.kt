package com.makentoshe.booruchan.postpage.model

import android.annotation.SuppressLint
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruchan.posts.model.PostsRepository
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import java.io.Serializable

class PostsDownloadController(private val postsRepository: PostsRepository) : Serializable {

    private var postsObservable = BehaviorSubject.create<Posts>()
    private val postsObservers = mutableListOf<Disposable>()

    val value: Posts?
        get() = postsObservable.value

    fun loadPosts(position: Int) {
        postsObservable.onNext(postsRepository.get(position + 1))
    }

    @SuppressLint("CheckResult")
    fun subscribe(action: (Posts) -> Unit) {
        postsObservers.add(postsObservable.subscribe(action))
    }

    fun update() {
        postsObservers.forEach { it.dispose() }
        postsObservers.forEach {
            println(it.isDisposed)
        }
        postsObservers.clear()
//        postsObservable = BehaviorSubject.create()
//        if (value != null) postsObservable.onNext(value)
    }
}
