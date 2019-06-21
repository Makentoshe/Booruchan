package com.makentoshe.boorupostview.presenter

import android.view.View
import android.widget.ImageView
import com.jakewharton.rxbinding3.view.clicks
import com.makentoshe.boorupostview.R
import com.makentoshe.boorupostview.fragment.PostsFragmentViewModel
import com.makentoshe.boorupostview.listener.MagnifyPanelSlideListener
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

    /** Observable for on icon click events */
    private val iconViewObservable = PublishSubject.create<Unit>()
    /** Observable for panel slide listener */
    private val slidingPanelObservable = PublishSubject.create<Float>()

    override fun bindOptionIcon(view: View) {
        view.clicks().safeSubscribe(iconViewObservable)
        //set magnify icon drawable
        val magnifyDrawable = view.context.getDrawable(com.makentoshe.style.R.drawable.avd_cross_magnify)
        val magnifyIcon = view.find<ImageView>(R.id.magnify_icon)
        magnifyIcon.setImageDrawable(magnifyDrawable)
        //change alpha on slide offset change event
        slidingPanelObservable.subscribe { slideOffset ->
            magnifyIcon.alpha = slideOffset
            magnifyIcon.visibility = if (slideOffset == 0f) View.GONE else View.VISIBLE
        }.let(disposables::add)
    }

    override fun bindSlidingPanel(view: SlidingUpPanelLayout) {
        view.addPanelSlideListener(MagnifyPanelSlideListener(slidingPanelObservable))
        // change panel state on icon view click event
        iconViewObservable.subscribe {
            view.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }.let(disposables::add)
        // change panel state on search started
        viewmodel.searchObservable.subscribe {
            view.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
        }.let(disposables::add)
    }
}
