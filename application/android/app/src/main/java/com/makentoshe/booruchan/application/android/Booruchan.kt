package com.makentoshe.booruchan.application.android

import android.app.Application
import com.makentoshe.booruchan.application.android.di.ApplicationModule
import com.makentoshe.booruchan.application.android.di.ApplicationScope
import com.makentoshe.booruchan.application.android.di.InjectionActivityLifecycleCallback
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import toothpick.Toothpick
import toothpick.configuration.Configuration

class Booruchan : Application() {

    private val cicerone = Cicerone.create(Router())

    private val injectActivityLifecycleCallback = InjectionActivityLifecycleCallback()

    override fun onCreate() {
        super.onCreate()
        Toothpick.setConfiguration(getToothpickConfiguration())

        val scopes = Toothpick.openScopes(ApplicationScope::class)
        scopes.installModules(ApplicationModule(applicationContext, cicerone))
        scopes.inject(this)

        registerActivityLifecycleCallbacks(injectActivityLifecycleCallback)
    }

    private fun getToothpickConfiguration() = if (BuildConfig.DEBUG) {
        Configuration.forDevelopment().preventMultipleRootScopes()
    } else {
        Configuration.forProduction()
    }
}

