package com.makentoshe.boorupostview.presenter

import android.view.View
import android.widget.GridView
import android.widget.ProgressBar
import android.widget.TextView
import com.makentoshe.boorupostview.viewmodel.GridScrollElementFragmentViewModel
import com.makentoshe.boorupostview.model.GridScrollElementAdapter
import com.makentoshe.boorupostview.model.GridElementViewModelHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

/**
 * Presenter realisation for the grid scroll element using reactive style.
 */
class GridScrollElementRxPresenter(
    override val disposables: CompositeDisposable,
    private val viewModel: GridScrollElementFragmentViewModel,
    private val builder: GridElementViewModelHolder.Builder
) : GridScrollElementPresenter, RxPresenter() {

    override fun bindGridView(view: GridView) {
        // hide view on error
        viewModel.errorObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
            view.visibility = View.GONE
        }.let(disposables::add)
        // show view on success and add new adapter
        viewModel.postsObservable.map { posts ->
            GridScrollElementAdapter(posts, disposables, builder.build(posts))
        }.observeOn(AndroidSchedulers.mainThread()).subscribe { adapter ->
            view.adapter = adapter
            view.visibility = View.VISIBLE
        }.let(disposables::add)
    }

    override fun bindProgressBar(view: ProgressBar) {
        // hide view on error
        viewModel.errorObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
            view.visibility = View.GONE
        }.let(disposables::add)
        // hide view on success
        viewModel.postsObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
            view.visibility = View.GONE
        }.let(disposables::add)
    }

    override fun bindMessageView(view: TextView) {
        // show view on error and display an error message
        viewModel.errorObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
            view.visibility = View.VISIBLE
            view.text = it.toString()
        }.let(disposables::add)
        // hide view on success
        viewModel.postsObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
            view.visibility = View.GONE
        }.let(disposables::add)
    }
}
