package com.makentoshe.settings.realm

import com.makentoshe.settings.Setting
import com.makentoshe.settings.SettingController
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Controls boolean value using Realm database
 */
class RealmBooleanSettingController(private val config: RealmConfiguration) : SettingController<Boolean> {

    private val realmInstance: Realm
        get() = Realm.getInstance(config)

    private val titleName = Setting<*>::title.name

    override fun put(key: String, value: Boolean) = realmInstance.use { realm ->
        realm.beginTransaction()
        val realmSetting = realm.where(RealmBooleanSetting::class.java).equalTo(titleName, key).findFirst()
        if (realmSetting == null) realm.createObject(RealmBooleanSetting::class.java).also {
            it.title = key
            it.value = value
        } else {
            realmSetting.value = value
        }
        realm.commitTransaction()
    }

    override fun put(setting: Setting<Boolean>) = put(setting.title, setting.value)

    override fun get(key: String, default: Boolean): Boolean = realmInstance.use { realm ->
        realm.beginTransaction()
        val value = realm.where(RealmBooleanSetting::class.java).equalTo(Setting<*>::title.name, key).findFirst()?.value
        realm.commitTransaction()
        if (value == null) put(key, default)
        return@use value ?: default
    }
}