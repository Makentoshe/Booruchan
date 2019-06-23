package com.makentoshe.boorupostview.model

import android.graphics.Bitmap
import androidx.collection.LongSparseArray
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
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


interface GridElementControllerHolder : Repository<Pair<Post, CompositeDisposable>, GridElementController> {

    fun remove(key: Post)

    class Builder(
        private val repositoryBuilder: ImageRepositoryBuilder,
        private val cacheBuilder: CacheBuilder,
        private val imageDecoder: ImageDecoder
    ) {
        fun build(fragment: Fragment): GridElementControllerHolderImpl {
            val factory = GridElementControllerHolderImpl.Factory(repositoryBuilder, cacheBuilder, imageDecoder)
            return ViewModelProviders.of(fragment, factory)[GridElementControllerHolderImpl::class.java]
        }
    }

}

/**
 * Viewmodel component stores all [GridElementController] objects used in current time.
 */
class GridElementControllerHolderImpl(
    private val repositoryBuilder: ImageRepositoryBuilder,
    private val cacheBuilder: CacheBuilder,
    private val imageDecoder: ImageDecoder
) : ViewModel(), GridElementControllerHolder {

    /** Local storage */
    private val map = LongSparseArray<GridElementController>()

    /** Returns from local storage. If does not exists - create */
    override fun get(key: Pair<Post, CompositeDisposable>): GridElementController? {
        val value = map[key.first.id]
        if (value != null) return value
        val controller = GridElementController(key.first, repositoryBuilder, cacheBuilder, imageDecoder, key.second)
        map.put(key.first.id, controller)
        return get(key)
    }

    /** Removes from local storage */
    override fun remove(key: Post) = map.remove(key.id)

    class Factory(
        private val repositoryBuilder: ImageRepositoryBuilder,
        private val cacheBuilder: CacheBuilder,
        private val imageDecoder: ImageDecoder
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return GridElementControllerHolderImpl(repositoryBuilder, cacheBuilder, imageDecoder) as T
        }
    }

}