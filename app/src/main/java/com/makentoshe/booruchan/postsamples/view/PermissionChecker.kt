package com.makentoshe.booruchan.postsamples.view

import com.makentoshe.controllers.RxController
import com.makentoshe.controllers.SimpleRxController
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import java.io.Serializable

class PermissionChecker private constructor(
    private val permissionRequestRxController: RxController<String, String>,
    private val permissionReceiveRxController: RxController<Boolean, Boolean>
): Serializable {

    fun requestPermisson(permission: String, action: (Boolean) -> Unit): Disposable {
        permissionRequestRxController.action(permission)
        return permissionReceiveRxController.subscribe(action)
    }

    fun handlePermissionRequest(action: (String) -> Unit): Disposable {
        return permissionRequestRxController.subscribe(action)
    }

    fun sendPermissionResult(result: Boolean) = permissionReceiveRxController.action(result)

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