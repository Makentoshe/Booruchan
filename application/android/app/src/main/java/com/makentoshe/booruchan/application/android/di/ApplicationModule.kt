package com.makentoshe.booruchan.application.android.di

import android.content.Context
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import toothpick.config.Module
import toothpick.ktp.binding.bind

class ApplicationModule(applicationContext: Context, cicerone: Cicerone<Router>) : Module() {
    init {
        bind<Router>().toInstance(cicerone.router)
        bind<NavigatorHolder>().toInstance(cicerone.navigatorHolder)
    }
}