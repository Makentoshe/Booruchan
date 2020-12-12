package com.makentoshe.booruchan.application.android.di

import android.content.Context
import androidx.room.Room
import com.makentoshe.booruchan.application.android.FullContentDownloadExecutor
import com.makentoshe.booruchan.application.android.database.BooruchanDatabase
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

    private val httpClient = HttpClient()

    init {
        bind<Router>().toInstance(cicerone.router)
        bind<NavigatorHolder>().toInstance(cicerone.navigatorHolder)

        bind<HttpClient>().toInstance(httpClient)

        bind<List<BooruContext>>().toInstance(listOf(gelbooruContext, danbooruContext))
        bind<BooruchanDatabase>().withName(gelbooruContext.title).toInstance(gelbooruDatabase)
        bind<BooruchanDatabase>().withName(danbooruContext.title).toInstance(danbooruDatabase)

        val fullContentDownloadExecutorBuilder = FullContentDownloadExecutor.Builder(httpClient, applicationContext)
        bind<FullContentDownloadExecutor.Builder>().toInstance(fullContentDownloadExecutorBuilder)
    }
}