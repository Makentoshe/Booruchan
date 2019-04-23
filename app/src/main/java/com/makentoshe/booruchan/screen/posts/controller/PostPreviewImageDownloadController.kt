package com.makentoshe.booruchan.screen.posts.controller

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.repository.Repository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

//interface PostsPreviewImageDownloadController : PostsPreviewImageDownloadEventListener {
//
//    fun start(post: Post)
//}

//interface PostsPreviewImageDownloadEventListener {
//
//    fun onSuccess(action: (Bitmap) -> Unit)
//
//    fun onError(action: (Throwable) -> Unit)
//}

//class PostsPreviewImageDownloadControllerFactory(
//    private val repository: Repository<Post, ByteArray>,
//    private val disposables: CompositeDisposable
//) {
//    fun buildController(): PostsPreviewImageDownloadController {
//        return PostsPreviewImageDownloadControllerImpl(repository, disposables)
//    }
//}

//class PostsPreviewImageDownloadControllerImpl(
//    private val repository: Repository<Post, ByteArray>,
//    private val disposables: CompositeDisposable
//) : PostsPreviewImageDownloadController {
//
//    private val observable = BehaviorSubject.create<Bitmap>()
//
//    override fun start(post: Post) {
//        val disposable = Single.just(repository)
//            .subscribeOn(Schedulers.io())
//            .map { it.get(post) }
//            .map { BitmapFactory.decodeByteArray(it, 0, it.size) }
//            .observeOn(AndroidSchedulers.mainThread())
//        disposable.toObservable().safeSubscribe(observable)
//    }
//
//    override fun onSuccess(action: (Bitmap) -> Unit) {
//        onComplete { p, _ -> if (p != null) action(p) }
//    }
//
//    override fun onError(action: (Throwable) -> Unit) {
//        onComplete { _, t -> if (t != null) action(t) }
//    }
//
//    private fun onComplete(action: (Bitmap?, Throwable?) -> Unit) {
//        val disposable = observable.subscribe({ action(it, null) }, { action(null, it) })
//        disposables.add(disposable)
//    }
//}
