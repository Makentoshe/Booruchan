package com.makentoshe.booruchan.screen.posts.container

import androidx.lifecycle.ViewModel
import com.makentoshe.booruchan.BuildConfig
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.model.BooruHolder
import com.makentoshe.booruchan.screen.posts.container.controller.CacheController
import com.makentoshe.booruchan.screen.posts.container.controller.SearchController
import com.makentoshe.booruchan.model.TagsHolder
import io.reactivex.disposables.Disposable

class PostsViewModel(
    private val tagsHolder: TagsHolder,
    private val booruHolder: BooruHolder,
    private val searchController: SearchController,
    private val disposable: Disposable,
    private val cacheController: CacheController
) : ViewModel(), TagsHolder, BooruHolder, SearchController, Disposable {

    init {
        startSearch(set)
    }

    override val set: MutableSet<Tag>
        get() = tagsHolder.set

    override val booru: Booru
        get() = booruHolder.booru

    override fun startSearch(tags: Set<Tag>) {
        cacheController.clearAll()
        updateTags(tags)
        searchController.startSearch(tags)
    }

    private fun updateTags(set: Set<Tag>) {
        tagsHolder.set.clear()
        tagsHolder.set.addAll(set)
    }

    override fun onSearchStarted(action: (Set<Tag>) -> Unit) {
        searchController.onSearchStarted(action)
    }

    override fun isDisposed() = disposable.isDisposed

    override fun dispose() = disposable.dispose()

    fun init() {
        if (BuildConfig.DEBUG) println("Log $this")
    }

    override fun onCleared() = dispose()

}