package com.makentoshe.settings.model.realm

import com.makentoshe.settings.model.Setting
import com.makentoshe.settings.model.SettingController
import io.realm.Realm

/**
 * Setting controlling using Realm database.
 */
class RealmBooleanSettingController : SettingController<Boolean> {

    private val titleName = Setting<*>::title.name

    override fun put(key: String, value: Boolean) = Realm.getDefaultInstance().use { realm ->
        realm.beginTransaction()
        val realmSetting = realm.where(RealmBooleanSetting::class.java).equalTo(titleName, key).findFirst()
        if (realmSetting == null) realm.createObject(RealmBooleanSetting::class.java).also {
            it.title = key
            it.value = value
        } else realmSetting.value = value
        realm.commitTransaction()
    }

    override fun put(setting: Setting<Boolean>) = put(setting.title, setting.value)

    override fun get(key: String, default: Boolean): Boolean = Realm.getDefaultInstance().use { realm ->
        realm.beginTransaction()
        val value = realm.where(RealmBooleanSetting::class.java).equalTo(Setting<*>::title.name, key).findFirst()?.value
        realm.commitTransaction()
        if (value == null) put(key, default)
        return@use value ?: default
    }
}