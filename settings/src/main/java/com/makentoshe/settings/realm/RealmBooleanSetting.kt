package com.makentoshe.settings.realm

import com.makentoshe.settings.BooleanSetting
import io.realm.RealmObject

/**
 * [BooleanSetting] based on [RealmObject]
 */
open class RealmBooleanSetting : RealmObject(), BooleanSetting {
    override var title: String = ""
    override var value: Boolean = false
}

