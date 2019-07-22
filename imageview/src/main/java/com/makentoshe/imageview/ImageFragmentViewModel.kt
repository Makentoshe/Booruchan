package com.makentoshe.imageview

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.api.NetworkExecutorBuilder
import com.makentoshe.api.SimpleStreamDownloadListener
import com.makentoshe.api.cache.CacheBuilder
import com.makentoshe.api.repository.Repository
import com.makentoshe.api.repository.RepositoryBuilder
import com.makentoshe.boorulibrary.entitiy.Post
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class ImageFragmentViewModel(
    private val repository: Repository<Post, ByteArray>,
    private val imageDecoder: ImageDecoder,
    private val simpleStreamDownloadListener: SimpleStreamDownloadListener
) : ViewModel() {

    /** Container for [io.reactivex.disposables.Disposable] objects will be released on destroy lifecycle event */
    private val disposables = CompositeDisposable()

    /** Emitter for [Throwable] */
    private val errorSubject = BehaviorSubject.create<Throwable>()

    /** Observable for [Throwable] */
    val errorObservable: Observable<Throwable>
        get() = errorSubject

    /** Emitter for [Bitmap] */
    private val successSubject = BehaviorSubject.create<Bitmap>()

    /** Observable for [Bitmap] */
    val successObservable: Observable<Bitmap>
        get() = successSubject

    /** Emitter for progress events in range 0..1 */
    private val progressSubject = BehaviorSubject.create<Float>()

    /** Observable for progress events in range 0..1 */
    val progressObservable: Observable<Float>
        get() = progressSubject.observeOn(AndroidSchedulers.mainThread())

    init {
        var count = 0f
        simpleStreamDownloadListener.onDownloading { bytes, total ->
            count += bytes.size
            progressSubject.onNext(count / total)
        }
    }

    /** Starts a fetching request using a [post] param */
    fun execute(post: Post) {
        Single.just(post)
            .observeOn(Schedulers.io())
            .map(repository::get)
            .map(imageDecoder::decode)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { b, t ->
                if (t != null || b == null) errorSubject.onNext(t ?: Exception("wtf")) else successSubject.onNext(b)
            }.let(disposables::add)
    }

    /** Releases a disposables */
    override fun onCleared() {
        disposables.clear()
    }

    /**
     * Uses for an [ImageFragmentViewModel] instance creating
     *
     * @param repositoryBuilder builds a repository instance
     * @param cacheBuilder builds a cache instance
     * @param imageDecoder decodes an image from byte array
     * @param onCreated calls on viewmodel was created.
     */
    class Factory(
        private val repositoryBuilder: RepositoryBuilder,
        private val cacheBuilder: CacheBuilder,
        private val imageDecoder: ImageDecoder,
        private val onCreated: (ImageFragmentViewModel) -> Unit
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val listener = SimpleStreamDownloadListener()
            val networkExecutor = NetworkExecutorBuilder.buildSmartGet(null, listener)
            val cache = cacheBuilder.buildSampleCache()
            val repository = repositoryBuilder.buildSampleRepository(networkExecutor).wrapCache(cache)
            return ImageFragmentViewModel(repository, imageDecoder, listener).apply(onCreated) as T
        }
    }
}