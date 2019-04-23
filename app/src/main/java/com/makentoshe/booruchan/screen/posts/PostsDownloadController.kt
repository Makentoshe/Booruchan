package com.makentoshe.booruchan.screen.posts

import com.makentoshe.booruchan.api.Posts
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.repository.Repository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

interface PostsDownloadController : PostsDownloadEventListener {

    fun start()
}

interface PostsDownloadEventListener {

    fun onSuccess(action: (List<Post>) -> Unit)

    fun onError(action: (Throwable) -> Unit)
}

class PostsDownloadControllerImpl(
    private val repository: Repository<Posts.Request, List<Post>>,
    private val request: Posts.Request,
    private val disposables: CompositeDisposable
) : PostsDownloadController {

    private val observable = BehaviorSubject.create<List<Post>>()

    override fun start() {
        val disposable = Single.just(repository)
            .subscribeOn(Schedulers.io())
            .map { it.get(request) }
            .observeOn(AndroidSchedulers.mainThread())
        disposable.toObservable().safeSubscribe(observable)
    }

    override fun onSuccess(action: (List<Post>) -> Unit) {
        onComplete { p, _ -> if (p != null) action(p) }
    }

    override fun onError(action: (Throwable) -> Unit) {
        onComplete { _, t -> if (t != null) action(t) }
    }

    private fun onComplete(action: (List<Post>?, Throwable?) -> Unit) {
        val disposable = observable.subscribe({ action(it, null) }, { action(null, it) })
        disposables.add(disposable)
    }
}
