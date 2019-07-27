package com.makentoshe.imageview

import android.view.View
import android.widget.ProgressBar
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.jakewharton.rxbinding3.view.longClicks
import com.makentoshe.imageview.download.ContextMenuBuilder
import com.makentoshe.style.CircularProgressBar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlin.math.roundToInt

class ImageFragmentPresenter(
    private val disposables: CompositeDisposable,
    private val viewmodel: ImageFragmentViewModel,
    private val popupmenuBuilder: ContextMenuBuilder
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
        // show context menu on long click
        view.longClicks().subscribe { popupmenuBuilder.show() }.let(disposables::add)
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
        // update progress
        viewmodel.progressObservable.subscribe { progress ->
            view.setProgress((progress * 100).roundToInt())
        }.let(disposables::add)
    }
}
