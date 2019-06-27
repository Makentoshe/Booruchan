package com.makentoshe.settings

import androidx.test.platform.app.InstrumentationRegistry
import com.makentoshe.settings.realm.RealmBooleanSetting
import com.makentoshe.settings.realm.RealmBooleanSettingController
import io.realm.Realm
import io.realm.RealmConfiguration
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RealmBooleanSettingControllerTest {

    private lateinit var settingController: RealmBooleanSettingController
    private lateinit var config: RealmConfiguration
    private lateinit var realm: Realm

    @Before
    fun init() {
        Realm.init(InstrumentationRegistry.getInstrumentation().context)
        config = RealmConfiguration.Builder().inMemory().name("InstrumentationTest").build()
        settingController = RealmBooleanSettingController(config)
        //hold realm instance
        realm = Realm.getInstance(config)
    }

    @Test
    fun shouldPutStringBooleanValue() {
        val title = "Setting"
        val value = false

        settingController.put(title, value)

        val r = Realm.getInstance(config)
        val e = r.where(RealmBooleanSetting::class.java).equalTo(Setting<*>::title.name, title).findFirst()!!

        assertEquals(title, e.title)
        assertEquals(value, e.value)
    }

    @Test
    fun shouldPutSettingValue() {
        val setting = RealmBooleanSetting()
        setting.title = "Setting"
        setting.value = false

        settingController.put(setting)

        val r = Realm.getInstance(config)
        val e = r.where(RealmBooleanSetting::class.java).equalTo(Setting<*>::title.name, setting.title).findFirst()!!

        assertEquals(setting.title, e.title)
        assertEquals(setting.value, e.value)
    }

    @Test
    fun shouldGetValueByTitle() {
        val title = "Setting"
        val value = false

        val r = Realm.getInstance(config)
        r.beginTransaction()
        r.createObject(RealmBooleanSetting::class.java).also {
            it.value = value
            it.title = title
        }
        r.commitTransaction()

        val result = settingController.get(title, !value)

        assertEquals(value, result)
    }

    @After
    fun after() {
        realm.close()
    }
}