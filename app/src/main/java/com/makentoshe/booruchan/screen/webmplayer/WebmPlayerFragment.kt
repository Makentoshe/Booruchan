package com.makentoshe.booruchan.screen.webmplayer

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.android.material.snackbar.Snackbar
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.screen.samples.model.SampleOptionsMenu
import com.makentoshe.booruchan.view.setGestureListener
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class WebmPlayerFragment : Fragment() {

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var post: Post
        get() = arguments!!.get(POST) as Post
        set(value) = arguments().putSerializable(POST, value)

    private val disposables by inject<CompositeDisposable>()

    private val viewModel by viewModel<WebmPlayerViewModel> { parametersOf(booru, post, disposables) }

    private val sampleOptionsMenu by inject<SampleOptionsMenu> { parametersOf(booru, post) }

    private val exoPlayer by currentScope.inject<SimpleExoPlayer>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return WebmPlayerUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.onSuccess { onSuccess(view, it) }
        viewModel.onError { onError(view, it) }
    }

    private fun onError(view: View, throwable: Throwable) {
        Snackbar.make(view, throwable.localizedMessage, Snackbar.LENGTH_LONG).show()
    }

    /**
     * @param view is a root view of a [getParentFragment] method.
     */
    private fun onSuccess(view: View, url: String) {
        val playerview = view.findViewById<PlayerView>(R.id.samples_webm)

        exoPlayer.prepare(createMediaSource(url))
        exoPlayer.addListener(WebmPlayerListener(view))
        playerview.player = exoPlayer

        playerview.setGestureListener {
            onDown { playerview.performClick() }
            onLongPress { sampleOptionsMenu.show(this@WebmPlayerFragment) }
        }

        playerview.hideController()
        playerview.visibility = View.VISIBLE
    }

    //create media source from url
    private fun createMediaSource(url: String): MediaSource {
        val uri = Uri.parse(url)
        val useragent = Util.getUserAgent(requireContext(), requireContext().getString(R.string.app_name))
        val dataSourceFactory = DefaultDataSourceFactory(requireContext(), useragent)
        return ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
        exoPlayer.release()
    }

    companion object {
        private const val POST = "Post"
        private const val BOORU = "Booru"
        fun create(booru: Booru, post: Post) = WebmPlayerFragment().apply {
            this.booru = booru
            this.post = post
        }
    }
}
