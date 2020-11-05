package com.makentoshe.booruchan.application.android.di

import android.content.Context
import androidx.room.Room
import com.makentoshe.booruchan.application.android.BooruchanDatabase
import com.makentoshe.booruchan.core.context.BooruContext
import com.makentoshe.booruchan.danbooru.JsonDanbooruContext
import com.makentoshe.booruchan.gelbooru.XmlGelbooruContext
import io.ktor.client.*
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import toothpick.config.Module
import toothpick.ktp.binding.bind

class ApplicationModule(applicationContext: Context, cicerone: Cicerone<Router>) : Module() {

    private val gelbooruContext = XmlGelbooruContext()
    private val gelbooruDatabase =
        Room.databaseBuilder(applicationContext, BooruchanDatabase::class.java, gelbooruContext.title).build()

    private val danbooruContext = JsonDanbooruContext()
    private val danbooruDatabase =
        Room.databaseBuilder(applicationContext, BooruchanDatabase::class.java, danbooruContext.title).build()

    init {
        bind<Router>().toInstance(cicerone.router)
        bind<NavigatorHolder>().toInstance(cicerone.navigatorHolder)

        val booruContexts = listOf<BooruContext>(gelbooruContext, danbooruContext)
        bind<List<BooruContext>>().toInstance(booruContexts)

        bind<HttpClient>().toInstance(HttpClient())

        bind<BooruchanDatabase>().withName(gelbooruContext.title).toInstance(gelbooruDatabase)
        bind<BooruchanDatabase>().withName(danbooruContext.title).toInstance(danbooruDatabase)
    }
}