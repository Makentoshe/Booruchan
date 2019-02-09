package com.makentoshe.controllers

import io.reactivex.disposables.Disposable

interface RxController<P, A> {
    fun subscribe(action: (P) -> Unit): Disposable

    fun action(param: A)

    fun clear()
}