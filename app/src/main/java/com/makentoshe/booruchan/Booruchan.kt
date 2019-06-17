package com.makentoshe.booruchan

import android.app.Application
import android.content.Context
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Posts
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.common.SchedulersProvider
import com.makentoshe.booruchan.model.StreamDownloadController
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.repository.stream.StreamRepositoryFactory
import com.makentoshe.booruchan.screen.posts.container.PostsModule
import com.makentoshe.booruchan.screen.posts.container.controller.CacheController
import com.makentoshe.booruchan.screen.posts.container.model.getItemsCountInRequest
import com.makentoshe.booruchan.screen.posts.page.PostsPageModule
import com.makentoshe.booruchan.screen.posts.page.controller.imagedownload.PreviewImageDownloadController
import com.makentoshe.booruchan.screen.posts.page.controller.postsdownload.PostsDownloadController
import com.makentoshe.booruchan.screen.sampleinfo.SampleInfoModule
import com.makentoshe.booruchan.screen.samples.SampleModule
import com.makentoshe.booruchan.screen.samples.model.SampleOptionsMenu
import com.makentoshe.booruchan.screen.webmplayer.WebmPlayerModule
import com.makentoshe.booruchan.style.SotisStyle
import com.makentoshe.booruchan.style.Style
import com.makentoshe.settings.SettingsInit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.get
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone

class Booruchan : Application(), KoinComponent {

    lateinit var style: Style
        private set

    override fun onCreate() {
        super.onCreate()
        SettingsInit().init(applicationContext)
        loadStyle()
        INSTANCE = this
        startKoin {
            androidLogger()
            androidContext(this@Booruchan)
            modules(
                appModule,
                PostsModule.module,
                SampleModule.module,
                PostsPageModule.module,
                WebmPlayerModule.module,
                SampleInfoModule.module
            )
        }
        initRxErrorHandler()
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

    override fun onTerminate() {
        super.onTerminate()
        //clear caches on app close.
        get<CacheController>().clearAll()
    }

    companion object {
        lateinit var INSTANCE: Booruchan
    }
}

val style = Booruchan.INSTANCE.style

val appModule = module {
    single { Cicerone.create(Router()) }
    single { style }
    single { get<Cicerone<Router>>().router }
    single { get<Cicerone<Router>>().navigatorHolder }

    /* Creates a factory for building repositories with the stream listeners */
    factory { (booru: Booru, controller: StreamDownloadController?) ->
        StreamRepositoryFactory(booru, controller)
    }

    /* Creates a posts request */
    factory { (tags: Set<Tag>, position: Int) ->
        val itemsCount = getItemsCountInRequest(get())
        Posts.Request(itemsCount, tags, position)
    }
    /* Creates a controller for downloading posts */
    factory { (booru: Booru, disposables: CompositeDisposable) ->
        val repositoryFactory = get<StreamRepositoryFactory> { parametersOf(booru, null) }
        PostsDownloadController.build(repositoryFactory, disposables)
    }
    /* Creates a controller for downloading preview images */
    factory { (booru: Booru, disposables: CompositeDisposable) ->
        val repositoryFactory = get<StreamRepositoryFactory> { parametersOf(booru, null) }
        PreviewImageDownloadController.build(repositoryFactory, disposables)
    }
    /* Creates a controller for stream downloading */
    factory { StreamDownloadController.create() }

    /* Creates a container for holding disposables */
    factory { CompositeDisposable() }

    /* Controller for samples shows options menu */
    factory { (b: Booru, p: Post) -> SampleOptionsMenu(b, p) }

    /* Provides schedulers for a foreground and a background tasks */
    single { SchedulersProvider(AndroidSchedulers.mainThread(), Schedulers.io()) }
}

typealias Boorus = List<Booru>