package com.makentoshe.booruimageview

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.api.DefaultPostsRequest
import com.makentoshe.api.NetworkExecutorBuilder
import com.makentoshe.api.SimpleStreamDownloadListener
import com.makentoshe.api.cache.CacheBuilder
import com.makentoshe.api.repository.RepositoryBuilder
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

/**
 * [ViewModel] component for [ImageViewPagerElementFragment]. Performs an image downloading.
 *
 * @param repositoryBuilder for building a repositories
 * @param cacheBuilder for building a caches
 * @param request will be performed for getting a post data
 * @param imageDecoder performs image decoding from [ByteArray] to a [Bitmap].
 */
class ImageViewPagerElementFragmentViewModel(
    repositoryBuilder: RepositoryBuilder,
    cacheBuilder: CacheBuilder,
    request: DefaultPostsRequest,
    imageDecoder: ImageDecoder,
    postId: Long
) : ViewModel() {

    /** Container for [io.reactivex.disposables.Disposable] objects will be released on cleared lifecycle event */
    private val disposables = CompositeDisposable()

    /** Emitter for [Throwable] */
    private val errorSubject = BehaviorSubject.create<Throwable>()

    /** Observable for throwable */
    val errorObservable: Observable<Throwable>
        get() = errorSubject

    /** Emitter for [Bitmap] */
    private val imageSubject = BehaviorSubject.create<Bitmap>()

    /** Observable for [Bitmap] */
    val imageObservable: Observable<Bitmap>
        get() = imageSubject

    /** Emitter for a progress in range 0..1*/
    private val progressSubject = PublishSubject.create<Float>()

    /** Observable for a progress */
    val progressObservable: Observable<Float>
        get() = progressSubject

    init {
        // listener for image downloading
        val streamDownloadListener = SimpleStreamDownloadListener().apply {
            var count = 0f
            onDownloading { bytes, total ->
                count += bytes.size
                progressSubject.onNext(count / total)
            }
        }
        // post downloading
        val postNetworkExecutor = NetworkExecutorBuilder.buildProxyGet(null)
        val postCache = cacheBuilder.buildPostsCache()
        val postRepository = repositoryBuilder.buildPostsRepository(postNetworkExecutor).wrapCache(postCache)
        // image downloading
        val imageNetworkExecutor = NetworkExecutorBuilder.buildSmartGet(null, streamDownloadListener)
        val imageCache = cacheBuilder.buildSampleCache()
        val imageRepository = repositoryBuilder.buildSampleRepository(imageNetworkExecutor).wrapCache(imageCache)
        // perform
        Single.just(request).observeOn(Schedulers.io()).map(postRepository::get).map { it.first() }
            .map(imageRepository::get).map(imageDecoder::decode).observeOn(AndroidSchedulers.mainThread())
            .subscribe { bitmap, throwable ->
                if (throwable != null) return@subscribe errorSubject.onNext(throwable)
                if (bitmap != null) return@subscribe imageSubject.onNext(bitmap)
                errorSubject.onNext(Exception("wtf"))
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
        private val context: Context,
        private val postId: Long
    ) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val cacheBuilder = CacheBuilder(context)
            val repositoryBuilder = RepositoryBuilder(booru)
            val request = DefaultPostsRequest(1, tags, position)
            val imageDecoder = AndroidImageDecoder()
            return ImageViewPagerElementFragmentViewModel(
                repositoryBuilder, cacheBuilder, request, imageDecoder, postId
            ) as T
        }
    }
}