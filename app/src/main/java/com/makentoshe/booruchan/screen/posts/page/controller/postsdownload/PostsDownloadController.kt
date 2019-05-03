package com.makentoshe.booruchan.screen.posts.page.controller.postsdownload

import com.makentoshe.booruchan.api.Posts
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.repository.RepositoryFactory
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.ReplaySubject

/**
 * Controller starts posts downloading
 */
interface PostsDownloadController : PostsDownloadListener {
    /**
     * Starts posts downloading using request.
     */
    fun start(request: Posts.Request)

    companion object {

        fun build(
            repositoryFactory: RepositoryFactory,
            disposables: CompositeDisposable
        ): PostsDownloadController = PostsDownloadControllerImpl(repositoryFactory, disposables)
    }

    /**
     * An object performs posts downloading.
     *
     * @param repositoryFactory is a factory that creates source.
     * @param disposables is a disposable container where the disposables stores.
     */
    private class PostsDownloadControllerImpl(
        private val repositoryFactory: RepositoryFactory,
        private val disposables: CompositeDisposable
    ) : PostsDownloadController {

        private val observable = ReplaySubject.create<List<Post>>()

        override fun start(request: Posts.Request) {
            val repository = repositoryFactory.buildPostsRepository()
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
}