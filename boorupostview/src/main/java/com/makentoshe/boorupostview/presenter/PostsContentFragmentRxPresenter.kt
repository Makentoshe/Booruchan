package com.makentoshe.boorupostview.presenter

import io.reactivex.disposables.CompositeDisposable

abstract class PostsContentFragmentRxPresenter : PostsContentFragmentPresenter {
    abstract val disposables: CompositeDisposable
}