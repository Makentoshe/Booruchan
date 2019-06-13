package com.makentoshe.boorupostview

import io.reactivex.disposables.CompositeDisposable

abstract class PostsContentFragmentRxPresenter : PostsContentFragmentPresenter {
    abstract val disposables: CompositeDisposable
}