package com.makentoshe.boorupostview.presenter

import io.reactivex.disposables.CompositeDisposable

abstract class PostsFragmentRxPresenter : PostsFragmentPresenter {
    abstract val disposables: CompositeDisposable
}