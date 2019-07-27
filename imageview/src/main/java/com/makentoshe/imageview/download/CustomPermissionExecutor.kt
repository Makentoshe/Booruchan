package com.makentoshe.imageview.download

import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.CompositeDisposable

class CustomPermissionExecutor(
    private val rxpermission: RxPermissions,
    private val disposable: CompositeDisposable
) : PermissionExecutor {

    override fun request(permission: String, response: (Boolean) -> Unit) {
        rxpermission.request(permission).subscribe(response).let(disposable::add)
    }
}