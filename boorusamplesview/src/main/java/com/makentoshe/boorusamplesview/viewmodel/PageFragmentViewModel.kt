package com.makentoshe.boorusamplesview.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.api.cache.CacheBuilder
import com.makentoshe.api.repository.RepositoryBuilder
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.booru.entity.PostsRequest
import com.makentoshe.boorulibrary.entitiy.Post
import com.makentoshe.boorusamplesview.model.AndroidImageDecoder
import com.makentoshe.boorusamplesview.model.ImageDecoder
import com.makentoshe.boorusamplesview.model.PostsDownload
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import java.io.File

/**
 * [ViewModel] component for [com.makentoshe.boorusamplesview.PageFragment]. Performs a downloads.
 *
 * @param imageDecoder performs image decoding from [ByteArray] to a [Bitmap].
 */
class PageFragmentViewModel(
    private val imageDecoder: ImageDecoder,
    private val postsDownload: PostsDownload
) : ViewModel() {

    /** Container for [io.reactivex.disposables.Disposable] objects will be released on cleared lifecycle event */
    private val disposables = CompositeDisposable()

    /** Emitter for [Throwable] */
    private val errorSubject = BehaviorSubject.create<Throwable>()

    /** Observable for throwable */
    val errorObservable: Observable<Throwable>
        get() = errorSubject

    /** Emitter for [Post] */
    private val successSubject = BehaviorSubject.create<Post>()

    /** Observable for [Post] */
    val successObservable: Observable<Post>
        get() = successSubject

    fun execute(request: PostsRequest) = postsDownload.execute(request) { p, t ->
        if (t != null || p == null) errorSubject.onNext(t ?: Exception("wtf")) else successSubject.onNext(fixPost(p))
    }.let(disposables::add).let { Unit }

    /** Fix Safebooru preview image url in post */
    private fun fixPost(post: Post): Post {
        // Safebooru can send a sample image url with png extension. It is invalid so tries to change it to valid
        if (File(post.previewUrl).extension != "png") return post

        val previewUrl = post.previewUrl.replaceRange(post.previewUrl.lastIndexOf("."), post.previewUrl.length, ".jpg")
        return post.makeCopy(previewUrl = previewUrl)
    }

    /** Release disposables */
    override fun onCleared() = disposables.clear()

    /**
     * Performs a [PageFragmentViewModel] component creation.
     *
     * @param booru current BooruAPI instance
     * @param context context.
     */
    class Factory(
        private val booru: Booru,
        private val context: Context
    ) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val cacheBuilder = CacheBuilder(context)
            val repositoryBuilder = RepositoryBuilder(booru)
            val imageDecoder = AndroidImageDecoder()
            val postDownload = PostsDownload(repositoryBuilder, cacheBuilder)
            return PageFragmentViewModel(imageDecoder, postDownload) as T
        }
    }
}
