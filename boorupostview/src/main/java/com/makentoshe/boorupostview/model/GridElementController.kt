package com.makentoshe.boorupostview.model

import android.graphics.Bitmap
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
 * Controller class for [android.widget.GridView] element. Stores in [GridElementControllerHolder] instance.
 * Performs an image downloading and sends a result to one of the observables.
 *
 * @param post is a source info for request.
 * @param repositoryBuilder creates a repository for requesting an image
 * @param cacheBuilder create a cache for caching a requested image
 * @param imageDecoder decodes a byte array to an android [android.graphics.Bitmap].
 * @param disposables disposable container.
 */
class GridElementController(
    private val post: Post,
    private val repositoryBuilder: ImageRepositoryBuilder,
    private val cacheBuilder: CacheBuilder,
    private val imageDecoder: ImageDecoder,
    private val disposables: CompositeDisposable
) {

    /** Component performs a network executions */
    private val networkExecutor = NetworkExecutorBuilder.buildSmartGet(null, NetworkExecutorBuilder.buildGet())

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
}
