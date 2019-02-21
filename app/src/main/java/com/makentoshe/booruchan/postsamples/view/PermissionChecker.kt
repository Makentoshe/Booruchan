package com.makentoshe.booruchan.postsamples.view

import com.makentoshe.controllers.RxController
import com.makentoshe.controllers.SimpleRxController
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import org.jetbrains.anko.doAsync
import java.io.Serializable

/**
 * Class for checking an requesting permissions.
 */
class PermissionChecker private constructor(
    private val permissionRequestRxController: RxController<String, String>,
    private val permissionReceiveRxController: RxController<Boolean, Boolean>
) : Serializable {

    /**
     * Requests a [permission] and returns true result in the [action] if permission was granted and false otherwise.
     *
     * @param permission a Manifest permission such as [android.Manifest.permission.WRITE_EXTERNAL_STORAGE].
     * @param action a lambda with the permission request result will be called after checking the permission.
     * It calls after user choice about permission.
     * @return returns a [Disposable] which will be automatically disposed after [action] executing will be completed.
     */
    fun requestPermisson(permission: String, action: (Boolean) -> Unit): Disposable {
        permissionRequestRxController.action(permission)
        var disposable: Disposable? = null
        disposable = permissionReceiveRxController.subscribe {
            try {
                action(it)
            } finally {
                disposable?.dispose()
            }
        }
        return disposable
    }

    /**
     * Method process the any permission request. If the permission was already granted -
     * the [sendPermissionResult] must be called with the true param.
     *
     * @param action a lambda will be called when the one permission will be requested.
     * If there are more than one permissions the method will be called for each.
     * @return a [Disposable].
     */
    fun handlePermissionRequest(action: (String) -> Unit): Disposable {
        return permissionRequestRxController.subscribe(action)
    }

    /**
     * Calls when permission was granted or denied.
     *
     * @param result result of the last permission request.
     */
    fun sendPermissionResult(result: Boolean) = permissionReceiveRxController.action(result)

    /**
     * Clear receive disposables. The request disposables wist be dispose after receiving a result.
     */
    fun clear() {
        permissionReceiveRxController.clear()
        permissionRequestRxController.clear()
    }

    class Factory {
        fun build(
            requestController: RxController<String, String>,
            receiveController: RxController<Boolean, Boolean>
        ) = PermissionChecker(requestController, receiveController)

        fun simpleBuild(): PermissionChecker {
            val receiveController = PermissionReceiveRxController()
            val requestController = PermissionRequestRxController()
            return PermissionChecker(requestController, receiveController)
        }
    }


    class PermissionRequestRxController : SimpleRxController<String, String>(BehaviorSubject.create()) {
        override fun action(param: String) = observable.onNext(param)
    }

    class PermissionReceiveRxController : SimpleRxController<Boolean, Boolean>(BehaviorSubject.create()) {
        override fun action(param: Boolean) = observable.onNext(param)
    }

}