package com.makentoshe.boorupostview.presenter

import android.view.View
import android.widget.GridView
import android.widget.ProgressBar
import android.widget.TextView
import com.makentoshe.boorupostview.model.GridScrollElementAdapter
import com.makentoshe.boorupostview.model.ItemsCountCalculator
import com.makentoshe.boorupostview.viewmodel.GridScrollElementFragmentViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Presenter component for the grid scroll element.
 * User interface should contains grid view, progress bar and message view.
 *
 * @param disposables contains a disposable instances.
 * @param viewModel is a component with a network request callbacks.
 * @param calculator used for a total grid view elements count calculation.
 */
class GridScrollElementPresenter(
    override val disposables: CompositeDisposable,
    private val viewModel: GridScrollElementFragmentViewModel,
    private val calculator: ItemsCountCalculator
) : RxPresenter() {

    /** Bind a [GridView] to a [viewModel] */
    fun bindGridView(view: GridView) {
        // hide view on error
        viewModel.errorObservable.subscribe {
            view.visibility = View.GONE
        }.let(disposables::add)
        // show view on success and add new adapter
        viewModel.postsObservable.observeOn(Schedulers.io()).map { posts ->
            GridScrollElementAdapter(posts, disposables, viewModel.controllerHolder)
        }.observeOn(AndroidSchedulers.mainThread()).subscribe { adapter ->
            if (adapter.count != calculator.getItemsCountTotal(view.context)) {
                viewModel.repeat(calculator.getItemsCountTotal(view.context))
            } else {
                view.visibility = View.VISIBLE
                view.adapter = adapter
            }
        }.let(disposables::add)
    }

    /** Bind a [ProgressBar] to a [viewModel] */
    fun bindProgressBar(view: ProgressBar) {
        // hide view on error
        viewModel.errorObservable.subscribe {
            view.visibility = View.GONE
        }.let(disposables::add)
        // hide view on success
        viewModel.postsObservable.subscribe {
            view.visibility = View.GONE
        }.let(disposables::add)
    }

    /** Bind a [TextView] as a message view to a [viewModel] */
    fun bindMessageView(view: TextView) {
        // show view on error and display an error message
        viewModel.errorObservable.subscribe {
            view.visibility = View.VISIBLE
            view.text = it.toString()
        }.let(disposables::add)
        // hide view on success
        viewModel.postsObservable.subscribe {
            view.visibility = View.GONE
        }.let(disposables::add)
    }
}
