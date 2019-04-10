package com.makentoshe.booruchan.screen.webmplayer

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.android.material.snackbar.Snackbar
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Post
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.screen.samples.model.showOptionsList
import com.makentoshe.booruchan.view.setGestureListener
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoContext

class WebmPlayerFragment : Fragment() {

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var post: Post
        get() = arguments!!.get(POST) as Post
        set(value) = arguments().putSerializable(POST, value)

    private val disposables = CompositeDisposable()

    private val exoPlayer by lazy { createExoPlayerInstance() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return WebmPlayerUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //get webm uri
        val disposable = Single.just(post)
            .subscribeOn(Schedulers.io())
            .map { booru.headCustom().request(it.sampleUrl) }
            .map { it.url.toURI().toString() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::onSubscribe)
        disposables.add(disposable)
    }

    private fun onSubscribe(url: String?, throwable: Throwable?) {
        if (throwable == null) {
            onSuccess(view!!, url!!)
        } else {
            Snackbar.make(view!!, throwable.localizedMessage, Snackbar.LENGTH_LONG).show()
        }
    }

    /**
     * @param view is a root view of a [getParentFragment] method.
     */
    private fun onSuccess(view: View, url: String) {
        val playerview = view.findViewById<PlayerView>(R.id.samples_webm)
        playerview.player = exoPlayer.apply { prepare(createMediaSource(url)) }
        playerview.hideController()
        playerview.setGestureListener {
            onDown { playerview.performClick() }
            onLongPress { showOptionsList(booru, post) }
        }
        //show player view
        playerview.visibility = View.VISIBLE
        playerview.bringToFront()
    }

    //create media source from url
    private fun createMediaSource(url: String): MediaSource {
        val uri = Uri.parse(url)
        val useragent = Util.getUserAgent(requireContext(), requireContext().getString(R.string.app_name))
        val dataSourceFactory = DefaultDataSourceFactory(requireContext(), useragent)
        return ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
    }

    private fun createExoPlayerInstance(): ExoPlayer {
        val exoPlayer = ExoPlayerFactory.newSimpleInstance(requireContext())
        exoPlayer.repeatMode = Player.REPEAT_MODE_ALL
        return exoPlayer
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