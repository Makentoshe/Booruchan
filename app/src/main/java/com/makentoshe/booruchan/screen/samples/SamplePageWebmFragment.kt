package com.makentoshe.booruchan.screen.samples

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player.REPEAT_MODE_ALL
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Post
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.screen.samples.model.onError
import com.makentoshe.booruchan.screen.samples.model.showOptionsList
import com.makentoshe.booruchan.screen.samples.view.SamplePageWebmUi
import com.makentoshe.booruchan.screen.settings.AppSettings
import com.makentoshe.booruchan.view.setGestureListener
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class SamplePageWebmFragment : Fragment() {

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var post: Post
        get() = arguments!!.get(POST) as Post
        set(value) = arguments().putSerializable(POST, value)

    private var position: Int
        get() = arguments!!.getInt(POSITION)
        set(value) = arguments().putInt(POSITION, value)

    private val disposables = CompositeDisposable()

    private val exoPlayer by lazy { createExoPlayerInstance() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SamplePageWebmUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (AppSettings.getWebmPlayingOnPlace(requireContext()).not()) {
            val pview = parentFragment!!.view!!
            pview.find<View>(R.id.samples_progress).visibility = View.GONE
            val message = pview.find<TextView>(R.id.samples_message)
            message.setText(R.string.tap_to_play)
            message.visibility = View.VISIBLE
            message.bringToFront()
        } else {
            //get webm uri
            val disposable = Single.just(post)
                .subscribeOn(Schedulers.io())
                .map { booru.headCustom().request(it.sampleUrl) }
                .map { it.url.toURI().toString() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::onSubscribe)
            disposables.add(disposable)
        }
    }

    private fun onSubscribe(url: String?, throwable: Throwable?) {
        val pview = parentFragment!!.view!!
        if (throwable == null) onSuccess(pview, url!!) else onError(pview, throwable)
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
        //hide progress bar
        view.find<View>(R.id.samples_progress).visibility = View.GONE
        //hide preview image
        view.find<ImageView>(R.id.samples_preview).visibility = View.GONE
        //show player view
        playerview.visibility = View.VISIBLE
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
        exoPlayer.repeatMode = REPEAT_MODE_ALL
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
        private const val POSITION = "Position"
        fun create(booru: Booru, post: Post, position: Int) = SamplePageWebmFragment().apply {
            this.booru = booru
            this.post = post
            this.position = position
        }
    }
}