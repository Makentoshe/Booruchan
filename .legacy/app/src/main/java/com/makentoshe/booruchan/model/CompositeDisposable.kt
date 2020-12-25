package com.makentoshe.booruchan.model

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun CompositeDisposable.add(lambda: () -> Disposable) = add(lambda())

var CompositeDisposable.add: Disposable
    set(value) { add(value) }
    get() = this
