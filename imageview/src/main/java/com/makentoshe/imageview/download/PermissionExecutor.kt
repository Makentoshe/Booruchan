package com.makentoshe.imageview.download

/** Interface for executor, performs a permission requesting */
interface PermissionExecutor {
    /** Request a [permission] and return a result in the callback */
    fun request(permission: String, response: (Boolean) -> Unit = {})
}