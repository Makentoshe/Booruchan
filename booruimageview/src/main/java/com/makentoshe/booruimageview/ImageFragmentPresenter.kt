package com.makentoshe.booruimageview

import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

class ImageFragmentPresenter(
    private val disposables: CompositeDisposable,
    private val fragmentManager: FragmentManager,
    private val navigator: BooruImageScreenNavigator,
    private val position: Int
) {

    private val closeObservable = PublishSubject.create<Unit>()

    private val slideObservable = PublishSubject.create<Float>()

    fun bindViewPager(view: ViewPager) {
        view.adapter = ImageViewPagerAdapter(fragmentManager)
        closeObservable.subscribe { navigator.close() }.let(disposables::add)
        if (view.currentItem == 0) view.currentItem = position
    }

    fun bindSlidingUpPanel(view: SlidingUpPanelLayout) {
        view.addPanelSlideListener(object : SlidingUpPanelLayout.SimplePanelSlideListener() {
            override fun onPanelSlide(panel: View?, slideOffset: Float) = if (slideOffset == 0f) {
                closeObservable.onNext(Unit)
            } else {
                slideObservable.onNext(slideOffset)
            }
        })
    }

    fun bindView(view: View) {
        slideObservable.subscribe(view::setAlpha).let(disposables::add)
    }
}