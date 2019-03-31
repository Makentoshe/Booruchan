package com.makentoshe.booruchan.screen.samples

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
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
import com.makentoshe.booruchan.screen.samples.view.SamplePageWebmUi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.jetbrains.anko.findOptional
import org.jetbrains.anko.support.v4.__ViewPager_OnPageChangeListener

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

    private var onPageChangeListener: ViewPager.OnPageChangeListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SamplePageWebmUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //get webm uri
        val disposable = Single.just(post)
            .subscribeOn(Schedulers.newThread())
            .map { booru.getCustom(mapOf("Range" to "bytes=0-1")).request(it.sampleUrl) }
            .map { it.url.toURI().toString() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::onSubscribe)
        disposables.add(disposable)
    }

    private fun onSubscribe(url: String?, throwable: Throwable?) {
        val pview = parentFragment!!.view!!
        if (throwable == null) onSuccess(pview, url!!) else onError(pview, throwable)
    }

    /**
     * @param view is a root view of a [getParentFragment] method.
     */
    private fun onSuccess(view: View, url: String) {
        val viewpager = requireActivity().find<ViewPager>(R.id.samples_container_viewpager)

        val disposable = Single.just(initOnPageChangeListener(view, url)).subscribe { l, t ->
            viewpager.addOnPageChangeListener(l)
        }
        disposables.add(disposable)

        if (position == viewpager.currentItem) initVideoPlayer(view, url)

        val playerview = view.findViewById<PlayerView>(R.id.samples_webm)
        playerview.visibility = View.VISIBLE
    }

    private fun initOnPageChangeListener(view: View, url: String): ViewPager.OnPageChangeListener {
        //use apply to avoid smart cast
        return __ViewPager_OnPageChangeListener().apply {
            onPageSelected {
                if (position == it) initVideoPlayer(view, url) else uninitVideoPlayer(view)
            }
        }.apply { onPageChangeListener = this }
    }

    @Synchronized
    private fun initVideoPlayer(view: View, url: String) {
        val mediaSource = createMediaSource(url)
        val playerview = view.findViewById<PlayerView>(R.id.samples_webm)
        playerview.player = createExoPlayerInstance(mediaSource)
    }

    private fun uninitVideoPlayer(view: View) {
        val playerview = view.find<PlayerView>(R.id.samples_webm)
        playerview.player?.release()
    }

    //create media source from url
    private fun createMediaSource(url: String): MediaSource {
        val uri = Uri.parse(url)
        val useragent = Util.getUserAgent(requireContext(), requireContext().getString(R.string.app_name))
        val dataSourceFactory = DefaultDataSourceFactory(requireContext(), useragent)
        return ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
    }

    private fun createExoPlayerInstance(source: MediaSource): ExoPlayer {
        val exoPlayer = ExoPlayerFactory.newSimpleInstance(requireContext())
        exoPlayer.repeatMode = REPEAT_MODE_ALL
        exoPlayer.prepare(source)
        return exoPlayer
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
        onPageChangeListener.let {
            if (it != null) {
                val viewpager = requireActivity().findOptional<ViewPager>(R.id.samples_container_viewpager)
                viewpager?.removeOnPageChangeListener(it)
            }
        }
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