package com.makentoshe.boorupostview.presenter

import android.view.View
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.jakewharton.rxbinding3.view.clicks
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.boorupostview.NewSearchBroadcastReceiver
import com.makentoshe.boorupostview.listener.NewSearchStartedListener
import com.makentoshe.boorupostview.model.PostsViewPagerAdapter
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.find

/**
 * Reactive presenter component for a grid scroll screen.
 *
 * @param adapterBuilder used for adapter creating.
 */
class PostsViewPagerFragmentPresenter(
    override val disposables: CompositeDisposable,
    private val adapterBuilder: PostsViewPagerAdapter.Builder,
    searchStartedListener: NewSearchStartedListener,
    initialTags: Set<Tag>
) : RxPresenter() {

    /** Search events observable */
    private val searchObservable = BehaviorSubject.create<Set<Tag>>()
    /** Next page scroll event observable */
    private val nextpageObservable = PublishSubject.create<Unit>()
    /** Previous page scroll event observable */
    private val prevpageObservable = PublishSubject.create<Unit>()
    /** Page number observable */
    private val textpageObservable = BehaviorSubject.create<String>()
    /** Viewpager scroll state observable */
    private val pagestateObservable = PublishSubject.create<Int>()
    /** Returns a current set of the tags. */
    val tags: Set<Tag>
        get() = searchObservable.value.orEmpty()
    /** Contains flag used for search updating by swipe */
    @Volatile
    private var wasSwipe = false

    init {
        // clear caches on new search
        // subscribe on new search event from search started listener
        searchStartedListener.onNewSearchStarted(searchObservable::onNext)
        //start new search using initial tags
        searchObservable.onNext(initialTags)
    }

    fun bindViewPager(view: ViewPager) {
        // on new search started: add an adapter for the view pager
        searchObservable.subscribe {
            view.adapter = adapterBuilder.build(it)
            //was it a swipe gesture - does not reset page
            if (wasSwipe) {
                view.currentItem = textpageObservable.value?.toInt() ?: 0
                wasSwipe = false
            } else textpageObservable.onNext("0")
        }.let(disposables::add)
        // should scroll to previous
        prevpageObservable.subscribe { view.currentItem = view.currentItem - 1 }.let(disposables::add)
        // should scroll to next
        nextpageObservable.subscribe { view.currentItem = view.currentItem + 1 }.let(disposables::add)
        // should change page number
        view.pageSelectEvents().map { it.toString() }.safeSubscribe(textpageObservable)
        // pass state change events to the observable
        view.stateChangeEvents().safeSubscribe(pagestateObservable)
    }

    fun bindBottomBar(view: View) {
        view.find<View>(com.makentoshe.boorupostview.R.id.bottombar_left).clicks().safeSubscribe(prevpageObservable)
        view.find<View>(com.makentoshe.boorupostview.R.id.bottombar_right).clicks().safeSubscribe(nextpageObservable)
        val textview = view.find<TextView>(com.makentoshe.boorupostview.R.id.bottombar_center)
        textpageObservable.subscribe { textview.text = it }.let(disposables::add)
    }

    private fun ViewPager.pageSelectEvents(): Observable<Int> {
        val observable = PublishSubject.create<Int>()
        addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) = observable.onNext(position)
        })
        return observable
    }

    private fun ViewPager.stateChangeEvents(): Observable<Int> {
        val observable = PublishSubject.create<Int>()
        addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageScrollStateChanged(state: Int) = observable.onNext(state)
        })
        return observable
    }

    fun bindSwipeRefresh(view: SwipeRefreshLayout) {
        // start new search on refresh
        view.refreshEvents().subscribe {
            wasSwipe = true
            NewSearchBroadcastReceiver.sendBroadcast(view.context, tags)
        }.let(disposables::add)
        // hide refresh icon after search
        searchObservable.subscribe { view.isRefreshing = false }.let(disposables::add)
        // disable swipe gesture on viewpager drag
        pagestateObservable.subscribe { view.isEnabled = it == ViewPager.SCROLL_STATE_IDLE }.let(disposables::add)
    }

    private fun SwipeRefreshLayout.refreshEvents(): Observable<Unit> {
        val observable = PublishSubject.create<Unit>()
        setOnRefreshListener { observable.onNext(Unit) }
        return observable
    }
}