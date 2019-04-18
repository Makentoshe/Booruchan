package com.makentoshe.booruchan


import android.app.Application
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.gelbooru.Gelbooru
import com.makentoshe.booruchan.api.rule34.Rule34
import com.makentoshe.booruchan.api.safebooru.Safebooru
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.screen.settings.AppSettings
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
import java.util.*
import kotlin.collections.ArrayList

class Booruchan : Application() {

    private val cicerone = Cicerone.create(Router())

    val booruList = ArrayList<Class<out Booru>>()

    private val appModule: Module = module {
        single { AppSettings }
        single { cicerone.router }
        single { cicerone.navigatorHolder }
        single { ArrayList<Class<out Booru>>() }
    }

    lateinit var style: Style
        private set

    val router = cicerone.router

    lateinit var settings: AppSettings
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
        loadBooru()
        loadSettings()
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

    private fun loadBooru() {
        this.booruList.add(Gelbooru::class.java)
        this.booruList.add(Safebooru::class.java)
        this.booruList.add(Rule34::class.java)
    }

    private fun loadSettings() {
        settings = AppSettings
    }

    companion object {

        lateinit var INSTANCE: Booruchan
    }
}

val style = Booruchan.INSTANCE.style

val router = Booruchan.INSTANCE.router

