package com.makentoshe.booruchan


import android.app.Application
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.gelbooru.Gelbooru
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.screen.settings.AppSettings
import com.makentoshe.booruchan.style.SotisStyle
import com.makentoshe.booruchan.style.Style
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.functions.Consumer
import io.reactivex.plugins.RxJavaPlugins
import ru.terrakok.cicerone.Cicerone
import java.util.*

class Booruchan : Application() {

    private var cicerone = Cicerone.create(Router())

    val booruList = ArrayList<Class<out Booru>>()

    lateinit var style: Style
        private set

    val navigatorHolder = cicerone.navigatorHolder

    val router = cicerone.router

    lateinit var settings: AppSettings
        private set

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        cicerone = Cicerone.create(Router())
        initRxErrorHandler()
        loadStyle()
        loadBooru()
        loadSettings()
    }

    private fun initRxErrorHandler() {
        RxJavaPlugins.setErrorHandler(Consumer { e ->
            if (e is UndeliverableException) {
                /*
                    Just ignore this exception - in the postpage screen throws each time after device rotation
                     */
                return@Consumer
            }
        })
    }

    private fun loadStyle() {
        style = SotisStyle()
    }

    private fun loadBooru() {
        this.booruList.add(Gelbooru::class.java)
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

val appSettings = Booruchan.INSTANCE.settings
