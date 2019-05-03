package com.makentoshe.booruchan.screen.webmplayer

import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.post.Post
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named

object WebmPlayerModule {

    val module = org.koin.dsl.module {

        viewModel { (b: Booru, p: Post, d: CompositeDisposable) ->
            WebmPlayerViewModel(b, p, d)
        }

        scope(named<WebmPlayerFragment>()) {
            scoped<SimpleExoPlayer> {
                ExoPlayerFactory.newSimpleInstance(get()).apply { repeatMode = Player.REPEAT_MODE_ALL }
            }
        }
    }

}