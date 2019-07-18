package com.makentoshe.boorusamplesview.presenter

import android.view.View
import androidx.viewpager.widget.ViewPager
import com.makentoshe.boorusamplesview.ActualPageBroadcastReceiver
import com.makentoshe.boorusamplesview.BooruSampleScreenNavigator
import com.makentoshe.boorusamplesview.model.ViewPagerAdapter
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

/**
 * Presenter component for [ImageFragment]. Performs binding view and a viewmodel.
 *
 * @param disposables [io.reactivex.disposables.Disposable] container for release.
 * @param navigator performs a navigation from current screen to others
 * @param position current page position starts from 0, used for correctly page displaying
 * @param adapter for a viewpager widget where images will be displayed
 */
class BaseFragmentPresenter(
    private val disposables: CompositeDisposable,
    private val navigator: BooruSampleScreenNavigator,
    private val position: Int,
    private val adapter: ViewPagerAdapter
) {

    /** Observable for a close events. Invokes for screen closing */
    private val closeObservable = PublishSubject.create<Unit>()

    /** Observable for sliding events. Each time the sliding offset was changes the observable will be invoked */
    private val slideObservable = PublishSubject.create<Float>()

    init {
        // should close screen on invoke
        closeObservable.subscribe { navigator.close() }.let(disposables::add)
    }

    /** Binds a [ViewPager] */
    fun bindViewPager(view: ViewPager) {
        // set adapter
        view.adapter = adapter
        // manages the viewpager position
        if (view.currentItem == 0) view.currentItem = position
        view.pageSelectEvents().subscribe {
            ActualPageBroadcastReceiver.sendBroadcast(view.context, it)
        }.let(disposables::add)
    }

    private fun ViewPager.pageSelectEvents(): Observable<Int> {
        val observable = PublishSubject.create<Int>()
        addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) = observable.onNext(position)
        })
        return observable
    }

    /** Binds a [SlidingUpPanelLayout] */
    fun bindSlidingUpPanel(view: SlidingUpPanelLayout) {
        // listener for invoking a close events and slide events
        view.addPanelSlideListener(object : SlidingUpPanelLayout.SimplePanelSlideListener() {
            override fun onPanelSlide(panel: View?, slideOffset: Float) = if (slideOffset == 0f) {
                closeObservable.onNext(Unit)
            } else {
                slideObservable.onNext(slideOffset)
            }
        })
    }

    /** Binds a root view */
    fun bindView(view: View) {
        // should change root view transparent on slide offset change
        slideObservable.subscribe(view::setAlpha).let(disposables::add)
    }
}