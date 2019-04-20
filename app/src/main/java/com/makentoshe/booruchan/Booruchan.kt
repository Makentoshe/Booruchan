package com.makentoshe.booruchan

import android.app.Application
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.gelbooru.Gelbooru
import com.makentoshe.booruchan.api.rule34.Rule34
import com.makentoshe.booruchan.api.safebooru.Safebooru
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.screen.booru.buildBooruScope
import com.makentoshe.booruchan.screen.posts.buildPostsScope
import com.makentoshe.booruchan.screen.settings.AppSettings
import com.makentoshe.booruchan.screen.settings.common.buildSettingsDefaultScope
import com.makentoshe.booruchan.screen.settings.container.buildSettingsScope
import com.makentoshe.booruchan.screen.settings.page.SettingsScreenBuilder
import com.makentoshe.booruchan.screen.settings.page.buildSettingsPageScope
import com.makentoshe.booruchan.screen.settings.webm.buildWebmSettingsScope
import com.makentoshe.booruchan.screen.start.buildStartScope
import com.makentoshe.booruchan.style.SotisStyle
import com.makentoshe.booruchan.style.Style
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone

class Booruchan : Application() {

    private val cicerone = Cicerone.create(Router())

    private val booruList = ArrayList<Class<out Booru>>().apply {
        add(Gelbooru::class.java)
        add(Safebooru::class.java)
        add(Rule34::class.java)
    }

    private val appModule: Module = module {
        single { AppSettings }
        single { cicerone.router }
        single { cicerone.navigatorHolder }
        factory { SettingsScreenBuilder() }

        buildStartScope(booruList)
        buildSettingsScope()
        buildSettingsPageScope()
        buildSettingsDefaultScope()
        buildWebmSettingsScope()
        buildBooruScope()
        buildPostsScope()
    }

    lateinit var style: Style
        private set

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        startKoin {
            androidLogger()
            androidContext(this@Booruchan)
            modules(appModule)
        }
        initRxErrorHandler()
        loadStyle()
    }

    private fun initRxErrorHandler() {
        RxJavaPlugins.setErrorHandler { e ->
            if (e is UndeliverableException) Unit
            e.printStackTrace()
        }
    }

    private fun loadStyle() {
        style = SotisStyle()
    }

    companion object {
        lateinit var INSTANCE: Booruchan
    }
}

val style = Booruchan.INSTANCE.style

