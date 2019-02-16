package com.makentoshe.booruchan.postpreviews.model

import com.makentoshe.booruapi.Tag
import com.makentoshe.controllers.SimpleRxController
import io.reactivex.subjects.BehaviorSubject

class TagController : SimpleRxController<Tag, Tag>(BehaviorSubject.create()) {

    override fun action(param: Tag) = observable.onNext(param)
}