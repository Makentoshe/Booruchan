package com.makentoshe.boorusamplesview.presenter

import android.view.View
import android.widget.ProgressBar
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.makentoshe.boorusamplesview.viewmodel.ImageFragmentViewModel
import com.makentoshe.style.CircularProgressBar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

class ImageFragmentPresenter(
    private val disposables: CompositeDisposable,
    private val viewmodel: ImageFragmentViewModel
) {

    fun bindImageView(view: SubsamplingScaleImageView) {
        // set image and show view on success event
        viewmodel.successObservable.map(ImageSource::bitmap).subscribe {
            view.setImage(it)
            view.visibility = View.VISIBLE
        }.let(disposables::add)
        // hide view on error event
        viewmodel.errorObservable.subscribe {
            view.visibility = View.VISIBLE
        }.let(disposables::add)
    }

    fun bindIndeterminateProgressBar(view: ProgressBar) {
        // hides on any complete event
        PublishSubject.create<Any>()
            .mergeWith(viewmodel.successObservable)
            .mergeWith(viewmodel.errorObservable)
            .subscribe { view.visibility = View.GONE }
            .let(disposables::add)
    }

    fun bindCircularProgressBar(view: CircularProgressBar) {
        // hides on any complete event
        PublishSubject.create<Any>()
            .mergeWith(viewmodel.successObservable)
            .mergeWith(viewmodel.errorObservable)
            .subscribe { view.visibility = View.GONE }
            .let(disposables::add)
    }
}
