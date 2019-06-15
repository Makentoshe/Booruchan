package com.makentoshe.boorupostview.presenter

import androidx.viewpager.widget.ViewPager
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.boorupostview.listener.NewSearchStartedListener
import com.makentoshe.boorupostview.model.GridScrollViewPagerAdapter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

/**
 * Reactive presenter component for a grid scroll screen.
 *
 * @param adapterBuilder used for adapter creating.
 */
class PostsGridScrollFragmentRxPresenter(
    override val disposables: CompositeDisposable,
    private val adapterBuilder: GridScrollViewPagerAdapter.Builder,
    searchStartedListener: NewSearchStartedListener,
    initialTags: Set<Tag>
) : RxPresenter(), PostsGridScrollFragmentPresenter {

    /** Uses for search events */
    private val searchObservable = BehaviorSubject.create<Set<Tag>>()

    /** Returns a current set of the tags. */
    val tags: Set<Tag>
        get() = searchObservable.value.orEmpty()

    init {
        // subscribe on new search event from search started listener
        searchStartedListener.onNewSearchStarted(searchObservable::onNext)
        //start new search using initial tags
        searchObservable.onNext(initialTags)
    }

    override fun bindViewPager(view: ViewPager) {
        //on new search started: add an adapter for the view pager
        searchObservable.subscribe {
            view.adapter = adapterBuilder.build(it)
        }.let(disposables::add)
    }
}