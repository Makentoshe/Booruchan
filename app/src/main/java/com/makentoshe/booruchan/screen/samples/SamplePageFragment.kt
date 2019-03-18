package com.makentoshe.booruchan.screen.samples

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Post
import com.makentoshe.booruchan.api.Posts
import com.makentoshe.booruchan.api.Tag
import com.makentoshe.booruchan.repository.CachedRepository
import com.makentoshe.booruchan.repository.PostsRepository
import com.makentoshe.booruchan.repository.SampleImageRepository
import com.makentoshe.booruchan.repository.cache.ImageInternalCache
import com.makentoshe.booruchan.repository.cache.InternalCacheType
import com.makentoshe.booruchan.repository.cache.PostInternalCache
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.screen.samples.view.SamplePageUi
import com.makentoshe.booruchan.view.setGestureListener
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onLongClick
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView
import java.io.File
import java.io.Serializable
import java.util.concurrent.TimeUnit

class SamplePageFragment : Fragment() {

    private var position: Int
        get() = arguments!!.getInt(POSITION)
        set(value) = arguments().putInt(POSITION, value)

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var tags: Set<Tag>
        get() = arguments!!.get(TAGS) as Set<Tag>
        set(value) = arguments().putSerializable(TAGS, value as Serializable)

    private val postsRepository by lazy {
        val cache = PostInternalCache(requireContext())
        val source = PostsRepository(booru)
        CachedRepository(cache, source)
    }

    private val samplesRepository by lazy {
        val cache = ImageInternalCache(requireContext(), InternalCacheType.SAMPLE)
        val source = SampleImageRepository(booru)
        CachedRepository(cache, source)
    }

    private val disposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SamplePageUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val disposable = Single.just(postsRepository)
            .subscribeOn(Schedulers.newThread())
            .map { it.get(Posts.Request(1, tags, position))!![0] }
            .timeout(3, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { onError(view, it) }
            .subscribe { post -> onComplete(view, post) }
        disposables.add(disposable)
    }

    private fun onError(view: View, throwable: Throwable) {
        view.find<View>(R.id.samples_progress).visibility = View.GONE
        val messageview = view.find<TextView>(R.id.samples_message)
        messageview.visibility = View.VISIBLE
        messageview.text = throwable.localizedMessage
        throwable.printStackTrace()
    }

    private fun onComplete(view: View, post: Post) {
        when (File(post.sampleUrl).extension) {
            "webm" -> onWebm(view, post)
            "gif" -> onGif(view, post)
            else -> onImage(view, post)
        }
    }

    private fun onImage(view: View, post: Post) {
        val disposable = Single.just(post)
            .subscribeOn(Schedulers.newThread())
            .timeout(3, TimeUnit.SECONDS)
            .map { samplesRepository.get(it) }
            .map { BitmapFactory.decodeByteArray(it, 0, it.size) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { onError(view, it) }
            .subscribe { bitmap ->
                view.find<View>(R.id.samples_progress).visibility = View.GONE
                val imageview = view.find<SubsamplingScaleImageView>(R.id.samples_image)
                imageview.visibility = View.VISIBLE
                imageview.setImage(ImageSource.bitmap(bitmap))
                imageview.onLongClick { showOptionsList(post) }
            }
        disposables.add(disposable)
    }

    private fun onGif(view: View, post: Post) {
        val disposable = Single.just(post)
            .subscribeOn(Schedulers.newThread())
            .timeout(3, TimeUnit.SECONDS)
            .map { samplesRepository.get(it) }
            .map { GifDrawable(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { onError(view, it) }
            .subscribe { gifdrawable ->
                view.find<View>(R.id.samples_progress).visibility = View.GONE
                val gifview = view.find<GifImageView>(R.id.samples_gif)
                gifview.visibility = View.VISIBLE
                gifview.setImageDrawable(gifdrawable)
                gifdrawable.start()
                gifview.onLongClick { showOptionsList(post) }
            }
        disposables.add(disposable)
    }

    private fun onWebm(view: View, post: Post) {
        val playerview = view.findViewById<PlayerView>(R.id.samples_webm)
        try {
            val uri = Uri.parse(post.sampleUrl)
            val useragent = Util.getUserAgent(requireContext(), requireContext().getString(R.string.app_name))
            val dataSourceFactory = DefaultDataSourceFactory(requireContext(), useragent)
            val mediaSource = ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
            val exoPlayer = ExoPlayerFactory.newSimpleInstance(context).also { it.prepare(mediaSource) }
            playerview.player = exoPlayer
            view.find<View>(R.id.samples_progress).visibility = View.GONE
            playerview.visibility = View.VISIBLE
            playerview.setGestureListener {
                onDown { playerview.performClick() }
                onLongPress { showOptionsList(post) }
            }
        } catch (e: Exception) {
            onError(view, e)
        }
    }

    private fun showOptionsList(post: Post) {
        SampleOptionFragment.create(booru, post)
            .show(childFragmentManager, SampleOptionFragment::class.java.simpleName)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }

    companion object {
        private const val POSITION = "Position"
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"
        fun create(
            position: Int,
            booru: Booru,
            tags: Set<Tag>
        ) = SamplePageFragment().apply {
            this.position = position
            this.booru = booru
            this.tags = tags
        }
    }
}