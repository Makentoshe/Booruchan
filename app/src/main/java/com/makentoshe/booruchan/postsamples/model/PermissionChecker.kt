package com.makentoshe.booruchan.postsamples.model

import com.makentoshe.controllers.RxController
import com.makentoshe.controllers.SimpleRxController
import io.reactivex.subjects.BehaviorSubject

interface PermissionChecker {
    /**
     * Requests a [permission] and returns true result in the [action] if permission was granted and false otherwise.
     *
     * @param permission a Manifest permission such as [android.Manifest.permission.WRITE_EXTERNAL_STORAGE].
     * @param result a lambda with the permission request result will be called after checking the permission.
     * It calls after user choice about permission.
     */
    fun requestPermission(permission: String, result: (Boolean) -> Unit)

    class Builder(
        private val requestController: RxController<String, String> = PermissionRequestRxController(),
        private val receiveController: RxController<Boolean, Boolean> = PermissionReceiveRxController()
    ) {

        fun build(): PermissionCheckController {
            return PermissionCheckerImpl(requestController, receiveController)
        }

        class PermissionRequestRxController : SimpleRxController<String, String>(BehaviorSubject.create()) {
            override fun action(param: String) = observable.onNext(param)
        }

        class PermissionReceiveRxController : SimpleRxController<Boolean, Boolean>(BehaviorSubject.create()) {
            override fun action(param: Boolean) = observable.onNext(param)
        }
    }

}

