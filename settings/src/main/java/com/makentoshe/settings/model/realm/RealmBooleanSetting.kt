package com.makentoshe.settings.model.realm

import com.makentoshe.settings.model.BooleanSetting
import io.realm.RealmObject

open class RealmBooleanSetting : RealmObject(), BooleanSetting {
    override var title: String = ""
    override var value: Boolean = false
}

