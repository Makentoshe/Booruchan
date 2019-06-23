package com.makentoshe.boorupostview.presenter

import android.view.View
import android.widget.ImageView
import com.jakewharton.rxbinding3.view.clicks
import com.makentoshe.boorupostview.listener.MagnifyPanelSlideListener
import com.makentoshe.boorupostview.viewmodel.PostsFragmentViewModel
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.find

/**
 * Reactive presenter component for posts screen.
 */
class PostsFragmentRxPresenter(
    override val disposables: CompositeDisposable,
    private val viewmodel: PostsFragmentViewModel
) : PostsFragmentPresenter, RxPresenter() {

    /** Observable for on magnify icon click events */
    private val magnifyObservable = PublishSubject.create<Unit>()
    /** Observable for on cross icon click events */
    private val crossObservable = PublishSubject.create<Unit>()
    /** Observable for panel slide listener */
    private val slidingPanelObservable = PublishSubject.create<Float>()

    override fun bindOptionIcon(view: View) {
        val magnifyIcon = view.find<ImageView>(com.makentoshe.boorupostview.R.id.magnify_icon)
        magnifyIcon.clicks().safeSubscribe(magnifyObservable)
        val crossIcon = view.find<ImageView>(com.makentoshe.boorupostview.R.id.cross_icon)
        crossIcon.clicks().safeSubscribe(crossObservable)
        //change alpha on slide offset change event
        slidingPanelObservable.subscribe { slideOffset ->
            magnifyIcon.alpha = slideOffset
            magnifyIcon.visibility = if (slideOffset == 0f) View.GONE else View.VISIBLE
            crossIcon.alpha = 1 - slideOffset
            crossIcon.visibility = if (slideOffset == 1f) View.GONE else View.VISIBLE
        }.let(disposables::add)
    }

    override fun bindSlidingPanel(view: SlidingUpPanelLayout) {
        view.addPanelSlideListener(MagnifyPanelSlideListener(slidingPanelObservable))
        // change panel state on magnify icon view click event
        magnifyObservable.subscribe {
            view.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }.let(disposables::add)
        // change panel state on cross icon view click event
        crossObservable.subscribe {
            view.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
        }.let(disposables::add)
        // change panel state on search started
        viewmodel.searchObservable.subscribe {
            view.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
        }.let(disposables::add)
    }
}
