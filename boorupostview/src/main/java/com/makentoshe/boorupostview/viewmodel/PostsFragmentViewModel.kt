package com.makentoshe.boorupostview.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.api.ImageDiskCache
import com.makentoshe.api.PostDiskCache
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.boorupostview.BuildConfig
import com.makentoshe.boorupostview.NewSearchBroadcastReceiver
import com.makentoshe.boorupostview.listener.NewSearchStartedListener
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

/**
 * ViewModel component contains cache controlling on search event
 */
class PostsFragmentViewModel(
    application: Application, tags: Set<Tag>, listener: NewSearchStartedListener
) : AndroidViewModel(application) {

    /** Contains disposables should be released on clear lifecycle event */
    private val disposables = CompositeDisposable()

    /** Search event emitter */
    private val searchSubject =
        BehaviorSubject.create<Set<Tag>>()

    /** Search event observable */
    val searchObservable: Observable<Set<Tag>>
        get() = searchSubject

    init {
        // send action from listener to a subject
        listener.onNewSearchStarted(searchSubject::onNext)
        // starts new search on viewmodel create
        NewSearchBroadcastReceiver.sendBroadcast(application, tags)
        // clear caches on new search event
        searchSubject.observeOn(Schedulers.io()).subscribe {
            try {
                // clear posts
                PostDiskCache.build(application).clear()
                if (BuildConfig.DEBUG) Log.i(PostDiskCache::class.java.simpleName, "Clear cache")
                // clear previews
                ImageDiskCache.getPreviewCache(application).clear()
                if (BuildConfig.DEBUG) Log.i(ImageDiskCache::class.java.simpleName.plus(".Preview"), "Clear cache")
                // clear samples
                ImageDiskCache.getSampleCache(application).clear()
                if (BuildConfig.DEBUG) Log.i(ImageDiskCache::class.java.simpleName.plus(".Sample"), "Clear cache")
                // clear full size
                ImageDiskCache.getFileCache(application).clear()
                if (BuildConfig.DEBUG) Log.i(ImageDiskCache::class.java.simpleName.plus(".File"), "Clear cache")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.let(disposables::add)
    }

    /** Releases disposables */
    override fun onCleared() = disposables.clear()

    class Factory(
        private val application: Application,
        private val tags: Set<Tag>,
        private val listener: NewSearchStartedListener
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PostsFragmentViewModel(application, tags, listener) as T
        }
    }

}