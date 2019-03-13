package com.makentoshe.booruchan.permission

interface PermissionRequester {
    /**
     * Requests a [permission] and returns true result in the [result] if permission was granted and false otherwise.
     *
     * @param permission a Manifest permission such as [android.Manifest.permission.WRITE_EXTERNAL_STORAGE].
     * @param result a lambda with the permission request result will be called after checking the permission.
     * It calls after user choice about permission.
     */
    fun requestPermission(permission: String, result: (Boolean) -> Unit)

}

