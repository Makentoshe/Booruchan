package com.makentoshe.gifview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.api.NetworkExecutorBuilder
import com.makentoshe.api.SimpleStreamDownloadListener
import com.makentoshe.api.cache.CacheBuilder
import com.makentoshe.api.repository.Repository
import com.makentoshe.api.repository.RepositoryBuilder
import com.makentoshe.boorulibrary.entitiy.Post
import com.makentoshe.boorulibrary.network.StreamDownloadListener
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import pl.droidsonroids.gif.GifDrawable

class GifFragmentViewModel(
    private val repository: Repository<Post, ByteArray>, listener: SimpleStreamDownloadListener
) : ViewModel() {

    /** Container for [io.reactivex.disposables.Disposable] objects will be released on cleared lifecycle event */
    private val disposables = CompositeDisposable()

    /** Emitter for [Throwable] */
    private val errorSubject = BehaviorSubject.create<Throwable>()

    /** Observable for [Throwable] */
    val errorObservable: Observable<Throwable>
        get() = errorSubject

    /** Emitter for [GifDrawable] */
    private val successSubject = BehaviorSubject.create<GifDrawable>()

    /** Observable for [GifDrawable] */
    val successObservable: Observable<GifDrawable>
        get() = successSubject

    /** Emitter for downloading progress in range 0..1 */
    private val progressSubject = BehaviorSubject.create<Float>()

    /** Observable for downloading progress in range 0..1 */
    val progressObservable: Observable<Float>
        get() = progressSubject.observeOn(AndroidSchedulers.mainThread())

    init {
        var count = 0f
        listener.onDownloading { bytes, limit ->
            count += bytes.size
            progressSubject.onNext(count/limit)
        }
    }

    fun execute(post: Post) {
        Single.just(post)
            .observeOn(Schedulers.io())
            .map(repository::get)
            .map(::GifDrawable)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { d, t ->
                if (t != null || d == null) errorSubject.onNext(t ?: Exception("wtf")) else successSubject.onNext(d)
            }.let(disposables::add)
    }

    /** Releases a disposables */
    override fun onCleared() {
        disposables.clear()
    }

    class Factory(
        private val repositoryBuilder: RepositoryBuilder,
        private val cacheBuilder: CacheBuilder,
        private val onCreated: (GifFragmentViewModel) -> Unit = { }
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val listener = SimpleStreamDownloadListener()
            val networkExecutor = NetworkExecutorBuilder.buildSmartGet(null, listener)
            val cache = cacheBuilder.buildSampleCache()
            val repository = repositoryBuilder.buildSampleRepository(networkExecutor).wrapCache(cache)
            return GifFragmentViewModel(repository, listener).apply(onCreated) as T
        }
    }
}