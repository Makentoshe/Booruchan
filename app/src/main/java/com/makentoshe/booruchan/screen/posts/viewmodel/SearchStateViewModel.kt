package com.makentoshe.booruchan.screen.posts.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.repository.cache.ImageInternalCache
import com.makentoshe.booruchan.repository.cache.InternalCache
import com.makentoshe.booruchan.repository.cache.PostInternalCache
import com.makentoshe.booruchan.screen.posts.controller.SearchController
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Performs tags and searching controlling.
 *
 * @param tagsViewModel contains set of the tags.
 * @param searchController notifies all subscribers that the new search was started.
 */
class SearchStateViewModel(
    application: Application, initialTasSet: Set<Tag>,
    private val tagsViewModel: TagsViewModel,
    private val searchController: SearchController
) : AndroidViewModel(application), SearchState {

    private val disposables = CompositeDisposable()

    override fun clearDisposables() {
        disposables.clear()
    }

    init {
        //start new search on screen creates for the first time
        startSearch(initialTasSet)
    }

    override fun startSearch(tags: Set<Tag>) {
        clearCache()
        //update tags
        tagsViewModel.set.clear()
        tagsViewModel.set.addAll(tags)
        //notify all subscribers
        searchController.startSearch(tags)
    }

    private fun clearCache() {
        GlobalScope.launch {
            PostInternalCache(
                getApplication()
            ).clear()
            ImageInternalCache(
                getApplication(),
                InternalCache.Type.SAMPLE
            ).clear()
            ImageInternalCache(
                getApplication(),
                InternalCache.Type.PREVIEW
            ).clear()
            ImageInternalCache(
                getApplication(),
                InternalCache.Type.FILE
            ).clear()
        }
    }

    override fun onSearchStarted(action: (Set<Tag>) -> Unit): Disposable {
        val disposable = searchController.onSearchStart(action)
        disposables.add(disposable)
        return disposable
    }

    override fun onCleared() {
        disposables.clear()
    }
}