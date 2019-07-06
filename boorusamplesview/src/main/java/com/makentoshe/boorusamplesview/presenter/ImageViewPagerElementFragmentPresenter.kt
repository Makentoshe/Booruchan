package com.makentoshe.boorusamplesview.presenter

import android.view.View
import android.widget.ProgressBar
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.makentoshe.boorusamplesview.viewmodel.ImageViewPagerElementFragmentViewModel
import com.makentoshe.style.CircularProgressBar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

/**
 * Presenter component for [com.makentoshe.boorusamplesview.ImageViewPagerElementFragment] instance.
 *
 * @param disposables [io.reactivex.disposables.Disposable] container.
 * @param viewmodel [androidx.lifecycle.ViewModel] component contains observables for some events.
 */
class ImageViewPagerElementFragmentPresenter(
    private val disposables: CompositeDisposable,
    private val viewmodel: ImageViewPagerElementFragmentViewModel
) {

    /** Binds a [SubsamplingScaleImageView] with a [viewmodel] */
    fun bindImageView(view: SubsamplingScaleImageView) {
        viewmodel.imageObservable.subscribe {
            view.setImage(ImageSource.bitmap(it.second).tilingDisabled())
            view.visibility = View.VISIBLE
        }.let(disposables::add)
    }

    /** Binds a [ProgressBar] with a [viewmodel] */
    fun bindIndeterminateBar(view: ProgressBar) {
        viewmodel.imageObservable.subscribe { view.visibility = View.GONE }.let(disposables::add)
        viewmodel.errorObservable.subscribe { view.visibility = View.GONE }.let(disposables::add)
    }

    /** Binds a [CircularProgressBar] with a [viewmodel] */
    fun bindProgressBar(view: CircularProgressBar) {
        viewmodel.progressObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
            view.setProgress((100 * it).toInt())
        }.let(disposables::add)
        viewmodel.imageObservable.subscribe { view.visibility = View.GONE }.let(disposables::add)
        viewmodel.errorObservable.subscribe { view.visibility = View.GONE }.let(disposables::add)
    }
}