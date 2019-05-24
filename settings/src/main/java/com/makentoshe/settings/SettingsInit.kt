package com.makentoshe.settings

import android.content.Context
import io.realm.Realm

class SettingsInit {

    fun init(applicationContext: Context) {
        Realm.init(applicationContext)
    }

}