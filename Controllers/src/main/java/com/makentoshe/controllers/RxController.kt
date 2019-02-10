package com.makentoshe.controllers

import io.reactivex.disposables.Disposable
import java.io.Serializable

interface RxController<P, A>: Serializable {
    fun subscribe(action: (P) -> Unit): Disposable

    fun action(param: A)

    fun clear()
}