package com.makentoshe.booruchan.screen.samples

import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Post
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.repository.PreviewImageRepository
import com.makentoshe.booruchan.repository.cache.ImageInternalCache
import com.makentoshe.booruchan.repository.cache.InternalCache
import com.makentoshe.booruchan.repository.decorator.CachedRepository
import com.makentoshe.booruchan.screen.samples.model.onError
import com.makentoshe.booruchan.screen.samples.model.showOptionsList
import com.makentoshe.booruchan.screen.samples.view.SamplePageWebmUi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onLongClick
import org.jetbrains.anko.sdk27.coroutines.onPrepared
import org.jetbrains.anko.support.v4.onPageChangeListener
import java.io.ByteArrayInputStream

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

    private val previewsRepository by lazy {
        val cache = ImageInternalCache(requireContext(), InternalCache.Type.PREVIEW)
        val source = PreviewImageRepository(booru)
        CachedRepository(cache, source)
    }

    private val disposables = CompositeDisposable()

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

    private fun onSuccess(view: View, url: String) {
        val videoView = view.findViewById<VideoView>(R.id.samples_webm)
        videoView.setupVideoView(view, Uri.parse(url))
        val viewpager = requireActivity().find<ViewPager>(R.id.samples_container_viewpager)
        videoView.setupController(viewpager)
    }

    private fun VideoView.setupController(viewPager: ViewPager) {
        if (viewPager.currentItem == position) {
            //set media controller for current visible fragment
            setMediaController(MediaController(requireContext()).apply { setAnchorView(this) })
            visibility = View.VISIBLE
            seekTo(1)
        }
        //on page change listener for controlling media controller
        viewPager.onPageChangeListener {
            onPageSelected {
                if (it == position) {
                    setMediaController(MediaController(requireContext()).apply { setAnchorView(this) })
                    visibility = View.VISIBLE
                    seekTo(1)
                } else {
                    visibility = View.INVISIBLE
                    stopPlayback()
                    setMediaController(null)
                }
            }
        }
    }

    private fun VideoView.setupVideoView(root: View, uri: Uri) {
        setVideoURI(uri)
        onLongClick { showOptionsList(booru, post) }
        //hide progress bar
        root.find<View>(R.id.samples_progress).visibility = View.GONE
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