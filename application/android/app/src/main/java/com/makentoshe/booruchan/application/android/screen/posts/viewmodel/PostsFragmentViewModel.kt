package com.makentoshe.booruchan.application.android.screen.posts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.makentoshe.booruchan.application.android.FetchExecutor
import com.makentoshe.booruchan.application.android.MainExecutor
import com.makentoshe.booruchan.application.android.screen.posts.model.PostsDataSource
import com.makentoshe.booruchan.application.android.screen.posts.model.PostsPagedAdapter
import com.makentoshe.booruchan.application.core.arena.Arena
import com.makentoshe.booruchan.core.post.Image
import com.makentoshe.booruchan.core.post.Post
import com.makentoshe.booruchan.core.post.Tags
import com.makentoshe.booruchan.core.post.deserialize.PostDeserialize
import com.makentoshe.booruchan.core.post.deserialize.PostsDeserialize
import com.makentoshe.booruchan.core.post.network.PostsFilter
import com.makentoshe.booruchan.core.post.tagsFromText
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject

class PostsFragmentViewModel(
    private val postsArena: Arena<PostsFilter, PostsDeserialize<Post>>,
    private val previewArena: Arena<Image, ByteArray>,
    private val filterBuilder: PostsFilter.Builder,
    private val disposables: CompositeDisposable
) : ViewModel() {

    /** Contains and manages current posts source, required for the adapter */
    private val sourceSubject = BehaviorSubject.create<PostsDataSource>()

    /** Retries initial request for [sourceSubject]. Valid only when [initialSignal] returns [Result.Failure] */
    private val retryInitialLoadSubject = PublishSubject.create<Unit>()
    val retryInitialLoadObserver: Observer<Unit> = retryInitialLoadSubject

    /** Indicates that the loading of the initial batch of data was finished */
    private val initialSignalSubject = BehaviorSubject.create<Result<*>>()
    val initialSignal: Observable<Result<*>> = initialSignalSubject

    /** Returns a new adapter when new tags ware defined through [postsTagsSearchObserver] */
    private val postsAdapterSubject = BehaviorSubject.create<PostsPagedAdapter>()
    val postsAdapterObservable: Observable<PostsPagedAdapter> = postsAdapterSubject

    /** Defines a new search for a new tags */
    private val postsTagsSearchSubject = BehaviorSubject.create<Tags>()
    val postsTagsSearchObserver: Observer<Tags> = postsTagsSearchSubject

    /** Defines a new search for the previous tags. This action allows to keep data in actual state */
    private val refreshInitialLoadSubject = PublishSubject.create<Unit>()
    val refreshInitialLoadObserver: Observer<Unit> = refreshInitialLoadSubject

    init {
        postsTagsSearchSubject.map { tags ->
            val tagsFilter = filterBuilder.tags.build(tags)
            PostsDataSource(postsArena, filterBuilder, viewModelScope, tagsFilter)
        }.doOnNext(sourceSubject::onNext).subscribe { source ->
            val adapter = PostsPagedAdapter(previewArena, viewModelScope, source)
            adapter.submitList(getPagedList(source))
            postsAdapterSubject.onNext(adapter)
        }.let(disposables::add)

        sourceSubject.subscribe { source ->
            source.initialSignal.safeSubscribe(initialSignalSubject)
        }.let(disposables::add)

        retryInitialLoadSubject.subscribe {
            sourceSubject.value?.retryLoadInitial()
        }.let(disposables::add)

        // Starts an initial request without any tags by default
        postsTagsSearchSubject.onNext(tagsFromText(emptySet()))

        // Starts new search on refresh action
        refreshInitialLoadSubject.subscribe {
            postsTagsSearchSubject.onNext(postsTagsSearchSubject.value ?: return@subscribe)
        }.let(disposables::add)
    }

    private fun getPagedList(source: PostsDataSource): PagedList<Result<PostDeserialize<Post>>> {
        val config = PagedList.Config.Builder().setEnablePlaceholders(false).setPageSize(30).build()
        val pagedListBuilder = PagedList.Builder(source, config)
        pagedListBuilder.setNotifyExecutor(MainExecutor())
        pagedListBuilder.setFetchExecutor(FetchExecutor(viewModelScope))
        return pagedListBuilder.build()
    }

    override fun onCleared() {
        disposables.clear()
    }

    class Factory(
        private val postsArena: Arena<PostsFilter, PostsDeserialize<Post>>,
        private val previewArena: Arena<Image, ByteArray>,
        private val postsFilterBuilder: PostsFilter.Builder,
        private val disposables: CompositeDisposable
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PostsFragmentViewModel(postsArena, previewArena, postsFilterBuilder, disposables) as T
        }
    }
}

