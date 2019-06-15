package com.makentoshe.boorupostview.presenter

import io.reactivex.disposables.CompositeDisposable

/**
 * Presenter component which contains a disposable container
 */
abstract class RxPresenter {

    /** Container for the disposables */
    abstract val disposables: CompositeDisposable
}