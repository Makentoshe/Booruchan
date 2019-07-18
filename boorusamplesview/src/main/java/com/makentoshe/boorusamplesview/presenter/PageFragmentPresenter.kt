package com.makentoshe.boorusamplesview.presenter

import android.view.View
import android.widget.ProgressBar
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.makentoshe.boorusamplesview.model.TypeFragmentBuilder
import com.makentoshe.boorusamplesview.viewmodel.PageFragmentViewModel
import com.makentoshe.style.CircularProgressBar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

/**
 * Presenter component for [com.makentoshe.boorusamplesview.PageFragment] instance.
 *
 * @param disposables [io.reactivex.disposables.Disposable] container.
 * @param viewmodel [androidx.lifecycle.ViewModel] component contains observables for some events.
 */
class PageFragmentPresenter(
    private val disposables: CompositeDisposable,
    private val viewmodel: PageFragmentViewModel,
    private val typeFragmentBuilder: TypeFragmentBuilder
) {

    /** Binds a [SubsamplingScaleImageView] with a [viewmodel] */
    fun bindContainerView(view: View) {
        viewmodel.successObservable.subscribe {
            typeFragmentBuilder.execute(it, view)
        }.let(disposables::add)
    }

    /** Binds a [ProgressBar] with a [viewmodel] */
    fun bindIndeterminateBar(view: ProgressBar) {
        PublishSubject.create<Any>().mergeWith(viewmodel.errorObservable).mergeWith(viewmodel.successObservable)
            .subscribe { view.visibility = View.GONE }.let(disposables::add)
    }

    /** Binds a [CircularProgressBar] with a [viewmodel] */
    fun bindProgressBar(view: CircularProgressBar) {
        view.visibility = View.GONE
    }
}
