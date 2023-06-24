package com.makentoshe.booruchan.permission

import io.reactivex.disposables.Disposable


abstract class PermissionRequestController : PermissionRequester {

    /**
     * Method process the any permission request. If the permission was already granted -
     * the [sendPermissionResult] must be called with the true param.
     *
     * @param action a lambda will be called when the one permission will be requested.
     * If there are more than one permissions the method will be called for each.
     * @return a disposable. When permission requests handling does not requires
     * just call [Disposable.dispose] method.
     */
    abstract fun handlePermissionRequest(action: (String) -> Unit)

    /**
     * Calls when permission was granted or denied.
     *
     * @param result result of the last permission request.
     */
    abstract fun sendPermissionResult(result: Boolean)

}