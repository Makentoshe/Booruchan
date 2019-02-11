package com.makentoshe.booruchan

import com.makentoshe.controllers.SimpleRxController
import io.reactivex.subjects.BehaviorSubject

class NotificationRxController() :
    SimpleRxController<NotificationRxController.Notification, NotificationRxController.Notification>(
        BehaviorSubject.create()
    ) {

    override fun action(param: Notification) = observable.onNext(param)

    class Notification() {

    }
}