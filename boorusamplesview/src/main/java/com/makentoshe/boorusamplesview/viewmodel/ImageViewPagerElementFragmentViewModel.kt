package com.makentoshe.boorusamplesview.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.api.DefaultPostsRequest
import com.makentoshe.api.cache.CacheBuilder
import com.makentoshe.api.repository.RepositoryBuilder
import com.makentoshe.boorusamplesview.model.*
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Post
import com.makentoshe.boorulibrary.entitiy.Tag
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.io.File

/**
 * [ViewModel] component for [com.makentoshe.boorusamplesview.ImageViewPagerElementFragment]. Performs a downloads.
 *
 * @param repositoryBuilder for building a repositories
 * @param cacheBuilder for building a caches
 * @param request will be performed for getting a post data
 * @param imageDecoder performs image decoding from [ByteArray] to a [Bitmap].
 */
class ImageViewPagerElementFragmentViewModel(
    private val repositoryBuilder: RepositoryBuilder,
    private val cacheBuilder: CacheBuilder,
    private val request: DefaultPostsRequest,
    private val imageDecoder: ImageDecoder
) : ViewModel() {

    /** Container for [io.reactivex.disposables.Disposable] objects will be released on cleared lifecycle event */
    private val disposables = CompositeDisposable()

    /** Emitter for [Throwable] */
    private val errorSubject = BehaviorSubject.create<Throwable>()

    /** Observable for throwable */
    val errorObservable: Observable<Throwable>
        get() = errorSubject

    /** Emitter for [Bitmap] */
    private val imageSubject = BehaviorSubject.create<Pair<Post, Bitmap>>()

    /** Observable for [Bitmap] */
    val imageObservable: Observable<Pair<Post, Bitmap>>
        get() = imageSubject

    /** Emitter for a progress in range 0..1*/
    private val progressSubject = PublishSubject.create<Float>()

    /** Observable for a progress */
    val progressObservable: Observable<Float>
        get() = progressSubject

    init {
        onPostDownloaded { post ->
            when (File(post.sampleUrl).extension.toLowerCase()) {
                "webm" -> onWebmDownloaded(post) { imageSubject.onNext(post to it) }
                "gif" -> onGifDownloaded(post) { imageSubject.onNext(post to it) }
                else -> onImageDownloaded(post) { imageSubject.onNext(post to it) }
            }
        }
    }

    /** Perform post downloading from the repository */
    private fun onPostDownloaded(action: (Post) -> Unit) {
        PostsDownload(repositoryBuilder, cacheBuilder).execute(request) { p, t ->
            if (t != null || p == null) errorSubject.onNext(t ?: Exception("wtf")) else action(fixPost(p))
        }.let(disposables::add)
    }

    /** Fix Safebooru preview image url in post */
    private fun fixPost(post: Post): Post {
        // Safebooru can send a sample image url with png extension. It is invalid so tries to change it to valid
        if (File(post.previewUrl).extension != "png") return post

        val previewUrl = post.previewUrl.replaceRange(post.previewUrl.lastIndexOf("."), post.previewUrl.length, ".jpg")
        return post.makeCopy(previewUrl = previewUrl)
    }

    /** Performs an image downloading from the repository */
    private fun onImageDownloaded(post: Post, action: (Bitmap) -> Unit) {
        ByteArraySampleDownload(repositoryBuilder, cacheBuilder).also {
            it.downloadProgressObservable.safeSubscribe(progressSubject)
        }.execute(post) { b, t ->
            if (t != null || b == null) errorSubject.onNext(t ?: Exception("wtf")) else action(imageDecoder.decode(b))
        }.let(disposables::add)
    }

    /** Performs an gif animation preview image downloading from the repository */
    private fun onGifDownloaded(post: Post, action: (Bitmap) -> Unit) {
        ByteArrayPreviewDownload(repositoryBuilder, cacheBuilder).also {
            it.downloadProgressObservable.safeSubscribe(progressSubject)
        }.execute(post) { b, t ->
            if (t != null || b == null) errorSubject.onNext(t ?: Exception("wtf")) else action(imageDecoder.decode(b))
        }.let(disposables::add)
    }

    /** Performs a webm preview image downloading from the repository */
    private fun onWebmDownloaded(post: Post, action: (Bitmap) -> Unit) {
        ByteArrayPreviewDownload(repositoryBuilder, cacheBuilder).also {
            it.downloadProgressObservable.safeSubscribe(progressSubject)
        }.execute(post) { b, t ->
            if (t != null || b == null) errorSubject.onNext(t ?: Exception("wtf")) else action(imageDecoder.decode(b))
        }.let(disposables::add)
    }

    /** Release disposables */
    override fun onCleared() = disposables.clear()

    /**
     * Performs a [ImageViewPagerElementFragmentViewModel] component creation.
     *
     * @param booru current BooruAPI instance
     * @param tags for performing a some types of requests
     * @param position of the current page starts from 0
     * @param context context.
     */
    class Factory(
        private val booru: Booru,
        private val tags: Set<Tag>,
        private val position: Int,
        private val context: Context
    ) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val cacheBuilder = CacheBuilder(context)
            val repositoryBuilder = RepositoryBuilder(booru)
            val request = DefaultPostsRequest(1, tags, position)
            val imageDecoder = AndroidImageDecoder()
            return ImageViewPagerElementFragmentViewModel(repositoryBuilder, cacheBuilder, request, imageDecoder) as T
        }
    }
}
