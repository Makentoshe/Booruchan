package com.makentoshe.booruchan.postsamples.model

import android.os.Handler
import android.os.Looper
import com.makentoshe.controllers.RxController
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

interface NotificationController : RxController<String, String>

open class NotificationControllerImpl : NotificationController {

    private val observable = PublishSubject.create<String>()
    private val disposables = CompositeDisposable()

    override fun subscribe(action: (String) -> Unit): Disposable {
        return observable.subscribe {
            Handler(Looper.getMainLooper()).post {
                action(it)
            }
        }.also { disposables.add(it) }
    }

    override fun action(param: String) = observable.onNext(param)

    override fun clear() = disposables.clear()
}