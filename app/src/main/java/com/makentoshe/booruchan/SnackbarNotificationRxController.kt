package com.makentoshe.booruchan

import com.google.android.material.snackbar.Snackbar
import com.makentoshe.controllers.SimpleRxController
import io.reactivex.subjects.BehaviorSubject
import java.io.Serializable

interface NotificationInterface : Serializable {
    fun notify(notify: SnackbarNotificationRxController.NotificationMessage)

    interface NotificationMessage : Serializable {
        val message: String
    }
}

class SnackbarNotificationRxController : NotificationInterface,
    SimpleRxController<SnackbarNotificationRxController.NotificationMessage, SnackbarNotificationRxController.NotificationMessage>(
        BehaviorSubject.create()
    ), Serializable {

    override fun notify(notify: NotificationMessage) = action(notify)

    override fun action(param: NotificationMessage) = observable.onNext(param)

    data class NotificationMessage(
        override val message: String,
        val duration: Int = Snackbar.LENGTH_LONG,
        val action: NotificationAction? = null
    ) : NotificationInterface.NotificationMessage


    data class NotificationAction(val title: String, val listener: (Snackbar) -> Unit)

}