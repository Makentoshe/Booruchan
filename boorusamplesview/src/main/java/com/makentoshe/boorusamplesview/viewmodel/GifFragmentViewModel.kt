package com.makentoshe.boorusamplesview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.api.NetworkExecutorBuilder
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
import pl.droidsonroids.gif.GifDrawable

class GifFragmentViewModel(private val repository: Repository<Post, ByteArray>) : ViewModel() {

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
            val networkExecutor = NetworkExecutorBuilder.buildSmartGet()
            val cache = cacheBuilder.buildSampleCache()
            val repository = repositoryBuilder.buildSampleRepository(networkExecutor).wrapCache(cache)
            return GifFragmentViewModel(repository).apply(onCreated) as T
        }
    }
}