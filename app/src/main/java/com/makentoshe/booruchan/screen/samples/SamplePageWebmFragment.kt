package com.makentoshe.booruchan.screen.samples

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Post
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.repository.SampleImageRepository
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
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onLongClick

class SamplePageWebmFragment : Fragment() {

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var post: Post
        get() = arguments!!.get(POST) as Post
        set(value) = arguments().putSerializable(POST, value)

    private val samplesRepository by lazy {
        val cache = ImageInternalCache(requireContext(), InternalCache.Type.SAMPLE)
        val source = SampleImageRepository(booru)
        CachedRepository(cache, source)
    }

    private val disposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SamplePageWebmUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
        //hide progress bar
        view.find<View>(R.id.samples_progress).visibility = View.GONE

        videoView.setVideoURI(Uri.parse(url))
        videoView.setMediaController(MediaController(requireContext()))
        videoView.requestFocus(0)
        videoView.visibility = View.VISIBLE
        videoView.onLongClick { showOptionsList(booru, post) }
    }

    companion object {
        private const val POST = "Post"
        private const val BOORU = "Booru"
        fun create(booru: Booru, post: Post) = SamplePageWebmFragment().apply {
            this.booru = booru
            this.post = post
        }
    }

}