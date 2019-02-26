package com.makentoshe.booruchan.postsamples.model

abstract class PermissionCheckController : PermissionChecker {

    /**
     * Method process the any permission request. If the permission was already granted -
     * the [sendPermissionResult] must be called with the true param.
     *
     * @param action a lambda will be called when the one permission will be requested.
     * If there are more than one permissions the method will be called for each.
     */
    abstract fun handlePermissionRequest(action: (String) -> Unit)

    /**
     * Calls when permission was granted or denied.
     *
     * @param result result of the last permission request.
     */
    abstract fun sendPermissionResult(result: Boolean)

    open fun clear() = Unit

}