package com.makentoshe.booruchan.permission

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import java.io.Serializable

/**
 * Class for checking an requesting permissions.
 */
class PermissionController(lifecycle: Lifecycle) : PermissionRequestController(), Serializable, LifecycleObserver {

    init {
        lifecycle.addObserver(this)
    }

    private val requestObservable = BehaviorSubject.create<String>()
    private val receiveObservable = BehaviorSubject.create<Boolean>()

    private val disposable = CompositeDisposable()

    override fun requestPermission(permission: String, result: (Boolean) -> Unit) {
        requestObservable.onNext(permission)
        //subscribe on result receive
        var disposable: Disposable? = null
        disposable = receiveObservable.subscribe {
            try {
                result(it)
            } finally {
                //when result was received and handled - unsubscribe
                disposable?.dispose()
            }
        }
    }

    override fun handlePermissionRequest(action: (String) -> Unit) {
        disposable.add(requestObservable.subscribe(action))
    }

    override fun sendPermissionResult(result: Boolean) = receiveObservable.onNext(result)

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() = disposable.clear()

}