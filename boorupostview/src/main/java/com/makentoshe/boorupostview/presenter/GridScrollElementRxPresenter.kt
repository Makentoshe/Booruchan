package com.makentoshe.boorupostview.presenter

import android.view.View
import android.widget.GridView
import android.widget.ProgressBar
import android.widget.TextView
import com.makentoshe.api.NetworkExecutorBuilder
import com.makentoshe.api.PostsRepository
import com.makentoshe.api.SimpleStreamDownloadListener
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.booru.entity.PostsRequest
import com.makentoshe.boorulibrary.entitiy.Post
import com.makentoshe.boorulibrary.network.Response
import com.makentoshe.boorulibrary.network.StreamDownloadListener
import com.makentoshe.boorulibrary.network.request.Request
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

/**
 * Presenter realisation for the grid scroll element using reactive style.
 */
class GridScrollElementRxPresenter(
    override val disposables: CompositeDisposable,
    booru: Booru, request: PostsRequest
) : GridScrollElementPresenter, RxPresenter() {

    /** Used for successful network request */
    private val postsObservable = BehaviorSubject.create<List<Post>>()

    /** Used for unsuccessful network request */
    private val errorObservable = BehaviorSubject.create<Throwable>()

    init {
        //perform network request and returns a list of the posts or error
        Single.just(request).observeOn(Schedulers.io()).map {
            val networkExecutor = NetworkExecutorBuilder.buildStreamGet()
            return@map PostsRepository(booru, networkExecutor).get(it)
        }.subscribe { r, e ->
            if (e != null) return@subscribe errorObservable.onNext(e)
            if (r != null) return@subscribe postsObservable.onNext(r)
            errorObservable.onNext(Exception("wtf"))
        }.let(disposables::add)
    }

    override fun bindGridView(view: GridView) {
        // hide view on error
        errorObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
            view.visibility = View.GONE
        }.let(disposables::add)
        // show view on error and add new adapter
        postsObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
            view.visibility = View.VISIBLE
            //TODO add adapter here
        }.let(disposables::add)
    }

    override fun bindProgressBar(view: ProgressBar) {
        // hide view on error
        errorObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
            view.visibility = View.GONE
        }.let(disposables::add)
        // hide view on success
        postsObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
            view.visibility = View.GONE
        }.let(disposables::add)
    }

    override fun bindMessageView(view: TextView) {
        // show view on error and display an error message
        errorObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
            view.visibility = View.VISIBLE
            view.text = it.toString()
        }.let(disposables::add)
        // hide view on success
        postsObservable.observeOn(AndroidSchedulers.mainThread()).subscribe {
            view.visibility = View.GONE
        }.let(disposables::add)
    }
}