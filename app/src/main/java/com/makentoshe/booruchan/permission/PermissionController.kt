package com.makentoshe.booruchan.permission

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.makentoshe.booruchan.model.RequestCode
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.inject
import java.io.Serializable

/**
 * Class for checking an requesting permissions.
 */
class PermissionController(
    fragment: Fragment
) : PermissionRequestController(), Serializable, LifecycleObserver, KoinComponent {

    init {
        fragment.lifecycle.addObserver(this)
    }

    /* Contains a methods for requesting permissions */
    private val permissionFragmentWrapper = PermissionFragmentWrapper(fragment)

    private val requestObservable = BehaviorSubject.create<String>()
    private val receiveObservable = BehaviorSubject.create<Boolean>()

    private val disposable by inject<CompositeDisposable>()

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

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        handlePermissionRequest {
            val status = ContextCompat.checkSelfPermission(get(), it)
            if (status == PackageManager.PERMISSION_GRANTED) {
                sendPermissionResult(true)
            } else {
                permissionFragmentWrapper.requestPermissions(arrayOf(it), RequestCode.permission)
            }
        }
    }
}

class PermissionFragmentWrapper(private val fragment: Fragment) {
    fun requestPermissions(array: Array<out String>, code: Int) = fragment.requestPermissions(array, code)
}