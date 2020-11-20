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

    /** Contains and manages source */
    private val sourceSubject = BehaviorSubject.create<PostsDataSource>()

    /** Retries initial request for [sourceSubject] */
    private val retryLoadSourceSubject = PublishSubject.create<Unit>()
    val retryLoadSourceObserver: Observer<Unit> = retryLoadSourceSubject

    /** Indicates that the initial batch of data was already loaded */
    private val initialSignalSubject = BehaviorSubject.create<Result<*>>()
    val initialSignal: Observable<Result<*>> = initialSignalSubject

    private val postsAdapterSubject = BehaviorSubject.create<PostsPagedAdapter>()
    val postsAdapterObservable: Observable<PostsPagedAdapter> = postsAdapterSubject

    private val postsTagsSearchSubject = PublishSubject.create<Tags>()
    val postsTagsSearchObserver: Observer<Tags> = postsTagsSearchSubject

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

        retryLoadSourceSubject.subscribe {
            sourceSubject.value?.retryLoadInitial()
        }.let(disposables::add)

        postsTagsSearchSubject.onNext(tagsFromText(emptySet()))
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

