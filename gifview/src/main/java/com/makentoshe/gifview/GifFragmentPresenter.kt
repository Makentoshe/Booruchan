package com.makentoshe.gifview

import android.view.View
import android.widget.ProgressBar
import com.makentoshe.style.CircularProgressBar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import pl.droidsonroids.gif.GifImageView

class GifFragmentPresenter(private val disposables: CompositeDisposable, private val viewmodel: GifFragmentViewModel) {

    fun bindIndeterminateProgressBar(view: ProgressBar) {
        // hide view on complete event
        PublishSubject.create<Any>()
            .mergeWith(viewmodel.errorObservable)
            .mergeWith(viewmodel.successObservable)
            .subscribe { view.visibility = View.GONE }
            .let(disposables::add)
    }

    fun bindCircularProgressBar(view: CircularProgressBar) {
        // hide view on complete event
        PublishSubject.create<Any>()
            .mergeWith(viewmodel.errorObservable)
            .mergeWith(viewmodel.successObservable)
            .subscribe { view.visibility = View.GONE }
            .let(disposables::add)
        // set progress on progress event
        viewmodel.progressObservable.subscribe { progress ->
            view.setProgress((progress * 100).toInt())
        }.let(disposables::add)
    }

    fun bindGifImageView(view: GifImageView) {
        // show view on success event
        viewmodel.successObservable.subscribe {
            view.visibility = View.VISIBLE
            view.setImageDrawable(it)
            it.start()
        }.let(disposables::add)
    }
}