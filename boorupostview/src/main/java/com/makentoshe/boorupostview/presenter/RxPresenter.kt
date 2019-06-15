package com.makentoshe.boorupostview.presenter

import io.reactivex.disposables.CompositeDisposable

abstract class RxPresenter {
    abstract val disposables: CompositeDisposable
}