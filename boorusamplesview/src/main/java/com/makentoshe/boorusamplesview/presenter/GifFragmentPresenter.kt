package com.makentoshe.boorusamplesview.presenter

import android.view.View
import android.widget.ProgressBar
import com.makentoshe.boorusamplesview.viewmodel.GifFragmentViewModel
import com.makentoshe.style.CircularProgressBar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import pl.droidsonroids.gif.GifImageView

class GifFragmentPresenter(private val disposables: CompositeDisposable, private val viewmodel: GifFragmentViewModel) {

    fun bindIndeterminateProgressBar(view: ProgressBar) {
        PublishSubject.create<Any>()
            .mergeWith(viewmodel.errorObservable)
            .mergeWith(viewmodel.successObservable)
            .subscribe { view.visibility = View.GONE }
            .let(disposables::add)
    }

    fun bindCircularProgressBar(view: CircularProgressBar) {
        PublishSubject.create<Any>()
            .mergeWith(viewmodel.errorObservable)
            .mergeWith(viewmodel.successObservable)
            .subscribe { view.visibility = View.GONE }
            .let(disposables::add)
    }

    fun bindGifImageView(view: GifImageView) {
        viewmodel.successObservable.subscribe {
            view.visibility = View.VISIBLE
            view.setImageDrawable(it)
            it.start()
        }.let(disposables::add)
    }
}