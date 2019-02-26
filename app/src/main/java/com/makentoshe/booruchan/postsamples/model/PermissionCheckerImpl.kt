package com.makentoshe.booruchan.postsamples.model

import com.makentoshe.controllers.RxController
import io.reactivex.disposables.Disposable
import java.io.Serializable

/**
 * Class for checking an requesting permissions.
 */
class PermissionCheckerImpl internal constructor(
    private val permissionRequestRxController: RxController<String, String>,
    private val permissionReceiveRxController: RxController<Boolean, Boolean>
) : Serializable, PermissionCheckController() {

    override fun requestPermission(permission: String, result: (Boolean) -> Unit) {
        permissionRequestRxController.action(permission)
        var disposable: Disposable? = null
        disposable = permissionReceiveRxController.subscribe {
            try {
                result(it)
            } finally {
                disposable?.dispose()
            }
        }
    }

    override fun handlePermissionRequest(action: (String) -> Unit) {
        permissionRequestRxController.subscribe(action)
    }

    override fun sendPermissionResult(result: Boolean) = permissionReceiveRxController.action(result)

    /**
     * Clear receive disposables. The request disposables wist be disposed after receiving a result.
     */
    override fun clear() {
        permissionReceiveRxController.clear()
        permissionRequestRxController.clear()
    }
}