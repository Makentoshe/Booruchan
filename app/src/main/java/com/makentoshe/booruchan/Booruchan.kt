package com.makentoshe.booruchan

import android.app.Application
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Posts
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.repository.factory.CachedRepositoryFactory
import com.makentoshe.booruchan.screen.booru.BooruModule
import com.makentoshe.booruchan.screen.posts.container.PostsModule
import com.makentoshe.booruchan.screen.posts.container.model.getItemsCountInRequest
import com.makentoshe.booruchan.screen.posts.page.PostsPageModule
import com.makentoshe.booruchan.screen.samples.SampleModule
import com.makentoshe.booruchan.screen.settings.AppSettings
import com.makentoshe.booruchan.screen.settings.page.SettingsScreenBuilder
import com.makentoshe.booruchan.screen.settings.settingsModule
import com.makentoshe.booruchan.screen.start.startModule
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

    private val appModule: Module = module {
        single { AppSettings }
        single { cicerone.router }
        single { cicerone.navigatorHolder }
        factory { SettingsScreenBuilder() }

        /* Creates a factory, creates cached repositories */
        factory { (booru: Booru) -> CachedRepositoryFactory(booru, get()) }

        /* Creates a posts request */
        factory { (tags: Set<Tag>, position: Int) ->
            val itemsCount = getItemsCountInRequest(get())
            Posts.Request(itemsCount, tags, position)
        }
    }

    lateinit var style: Style
        private set

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        startKoin {
            androidLogger()
            androidContext(this@Booruchan)
            modules(
                appModule,
                startModule,
                settingsModule,
                BooruModule.module,
                PostsModule.module,
                PostsPageModule.module,
                SampleModule.module
            )
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

val appModule = module {
    single { Cicerone.create(Router()) }
    single { AppSettings }
    single { get<Cicerone<Router>>().router }
    single { get<Cicerone<Router>>().navigatorHolder }
    factory { SettingsScreenBuilder() }
}
