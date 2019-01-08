package com.makentoshe.booruchan.postpage.model

import com.makentoshe.booruapi.Posts
import com.makentoshe.booruchan.posts.model.PostsRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import java.io.Serializable

class PostsDownloadController(private val postsRepository: PostsRepository) : Serializable {

    private var postsObservable = BehaviorSubject.create<Posts>()
    private val disposables = CompositeDisposable()

    val value: Posts?
        get() = postsObservable.value

    fun loadPosts(position: Int) {
        postsObservable.onNext(postsRepository.get(position))
    }

    fun subscribe(action: (Posts) -> Unit) {
        disposables.add(postsObservable.subscribe(action))
    }

    fun update() {
        disposables.clear()
    }
}
