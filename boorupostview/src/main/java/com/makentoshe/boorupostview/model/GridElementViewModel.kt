package com.makentoshe.boorupostview.model

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.api.*
import com.makentoshe.boorulibrary.booru.safebooru.SafebooruPost
import com.makentoshe.boorulibrary.entitiy.Post
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.io.File

/**
 * Viewmodel for a grid view single element.
 *
 * @param post is a post associated with the current grid view element.
 * @param cacheBuilder is a builder for any type caches where all images stores.
 * @param repositoryBuilder is a builder for any type repositories for image downloading.
 * @param imageDecoder is an image decoder from byte array to a bitmap object.
 */
class GridElementViewModel(
    private val post: Post,
    cacheBuilder: CacheBuilder,
    repositoryBuilder: ImageRepositoryBuilder,
    private val imageDecoder: ImageDecoder
) : ViewModel() {

    /** Contains a disposables must be released on cleared lifecycle event */
    private val disposables = CompositeDisposable()

    /** Component performs a network executions */
    private val networkExecutor = NetworkExecutorBuilder.buildSmartGet()

    /** Emitter for successful events */
    private val successSubject = BehaviorSubject.create<Bitmap>()

    /** Observable for successful events*/
    val successObservable: Observable<Bitmap>
        get() = successSubject

    /** Emitter for error events */
    private val errorSubject = BehaviorSubject.create<Throwable>()

    /** Observable for error events */
    val errorObservable: Observable<Throwable>
        get() = errorSubject

    init {
        // preview repository for preview image downloading
        val previewRepository = repositoryBuilder.buildPreviewRepository(networkExecutor)
        val previewCache = cacheBuilder.buildPreviewCache()

        // Starts a preview image downloading. Result will be sent to the observables
        Single.just(RepositoryCache(previewCache, previewRepository))
            .observeOn(Schedulers.io())
            .map(::downloadAndDecode).observeOn(AndroidSchedulers.mainThread())
            .subscribe { bitmap, throwable ->
                if (throwable != null) return@subscribe errorSubject.onNext(throwable)
                if (bitmap != null) return@subscribe successSubject.onNext(bitmap)
                errorSubject.onNext(Exception("wtf"))
            }.let(disposables::add)
    }

    /** Performs a byte array downloading and tries to decode to the Bitmap instance */
    private fun downloadAndDecode(repository: Repository<Post, ByteArray>): Bitmap {
        val post = when (post) {
            is SafebooruPost -> fixPost(post)
            else -> post
        }
        val bytes = repository.get(post) ?: throw Exception("Repository returned null byte array")
        return imageDecoder.decode(bytes)
    }

    /** Fix Safebooru preview image url in post */
    private fun fixPost(post: Post): Post {
        // Safebooru can send a preview image url with png extension. It is invalid so tries to change it to valid
        if (File(this.post.previewUrl).extension != "png") return post

        val previewUrl = post.previewUrl.replaceRange(post.previewUrl.lastIndexOf("."), post.previewUrl.length, ".jpg")
        return post.makeCopy(previewUrl = previewUrl)
    }

    /** Clear all disposables */
    override fun onCleared() = disposables.clear()

    class Factory(
        private val post: Post, private val cacheBuilder: CacheBuilder,
        private val imageRepositoryBuilder: ImageRepositoryBuilder,
        private val imageDecoder: ImageDecoder
    ) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return GridElementViewModel(post, cacheBuilder, imageRepositoryBuilder, imageDecoder) as T
        }
    }
}