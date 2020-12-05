package com.makentoshe.booruchan.application.android.screen.samples.di

import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.makentoshe.booruchan.application.android.R
import com.makentoshe.booruchan.application.android.di.ApplicationScope
import com.makentoshe.booruchan.application.android.screen.posts.model.PostPreviewArenaStorage
import com.makentoshe.booruchan.application.android.screen.samples.SampleVideoFragment
import com.makentoshe.booruchan.application.android.screen.samples.viewmodel.SampleVideoFragmentViewModel
import com.makentoshe.booruchan.application.core.arena.post.PostContentArena
import com.makentoshe.booruchan.core.context.BooruContext
import io.ktor.client.*
import io.reactivex.rxjava3.disposables.CompositeDisposable
import toothpick.Toothpick
import toothpick.config.Module
import toothpick.ktp.binding.bind
import toothpick.ktp.delegate.inject
import java.io.File
import javax.inject.Qualifier

@Qualifier
annotation class SampleVideoScope

class SampleVideoModule(fragment: SampleVideoFragment) : Module() {

    private val booruContexts by inject<List<BooruContext>>()
    private val client by inject<HttpClient>()

    init {
        Toothpick.openScope(ApplicationScope::class).inject(this)
        val booruContext = booruContexts.first { it.javaClass == fragment.arguments.booruclass }
        bind<CompositeDisposable>().withName(SampleVideoScope::class).toInstance(CompositeDisposable())

        val sampleViewModel = getSampleViewModel(fragment, booruContext)
        bind<SampleVideoFragmentViewModel>().toInstance(sampleViewModel)

        bind<SimpleExoPlayer>().toInstance(getExoPlayer(fragment))
    }

    private fun getPreviewArena(booruContext: BooruContext, fragment: Fragment): PostContentArena {
        val cacheDir = File(fragment.requireContext().cacheDir, booruContext.title)
        return PostContentArena(client, PostPreviewArenaStorage(cacheDir))
    }

    private fun getSampleViewModel(
        fragment: SampleVideoFragment, booruContext: BooruContext
    ): SampleVideoFragmentViewModel {
        val previewArena = getPreviewArena(booruContext, fragment)
        val imageFactory = SampleVideoFragmentViewModel.Factory(fragment.arguments.post, previewArena)
        return ViewModelProviders.of(fragment, imageFactory)[SampleVideoFragmentViewModel::class.java]
    }

    private fun getExoPlayer(fragment: SampleVideoFragment): SimpleExoPlayer {
        // TODO there are some codecs(OMX.qcom.video.decoder.avc), that cannot be played by exoplayer
        // https://gelbooru.com/index.php?page=post&s=view&id=5711153&tags=webm
        val uri = Uri.parse(fragment.arguments.post.sampleContent.url)
        // TODO Move UserAgent to ApplicationScope
        val useragent = Util.getUserAgent(fragment.requireContext(), fragment.requireContext().getString(R.string.app_name))
        val dataSourceFactory = DefaultDataSourceFactory(fragment.requireContext(), useragent)
        val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(uri))

        val player = SimpleExoPlayer.Builder(fragment.requireContext()).build()
        player.repeatMode = Player.REPEAT_MODE_ALL
        player.setMediaSource(mediaSource)
        player.prepare()
        return player
    }
}
