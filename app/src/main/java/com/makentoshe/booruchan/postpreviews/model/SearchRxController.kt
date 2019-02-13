package com.makentoshe.booruchan.postpreviews.model

import com.makentoshe.booruapi.Tag
import com.makentoshe.controllers.SimpleRxController
import io.reactivex.subjects.BehaviorSubject
import java.io.Serializable

class SearchRxController : SimpleRxController<Set<Tag>, Set<Tag>>(BehaviorSubject.create()), Serializable {

    fun newSearch(setOfTags: Set<Tag>) = action(setOfTags)

    override fun action(param: Set<Tag>) = observable.onNext(param)
}