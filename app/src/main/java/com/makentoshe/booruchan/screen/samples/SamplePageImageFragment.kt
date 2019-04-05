package com.makentoshe.booruchan.screen.samples

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Post
import com.makentoshe.booruchan.model.StreamDownloadListener
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.repository.StreamDownloadRepository
import com.makentoshe.booruchan.repository.cache.ImageInternalCache
import com.makentoshe.booruchan.repository.cache.InternalCache
import com.makentoshe.booruchan.repository.decorator.CachedRepository
import com.makentoshe.booruchan.repository.decorator.StreamDownloadRepositoryDecorator
import com.makentoshe.booruchan.screen.samples.model.onError
import com.makentoshe.booruchan.screen.samples.model.showOptionsList
import com.makentoshe.booruchan.screen.samples.view.SamplePageImageUi
import com.makentoshe.booruchan.view.CircularProgressBar
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onLongClick
import org.jetbrains.anko.support.v4.runOnUiThread

class SamplePageImageFragment : Fragment() {

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var post: Post
        get() = arguments!!.get(POST) as Post
        set(value) = arguments().putSerializable(POST, value)

    private val samplesRepository by lazy {
        val cache = ImageInternalCache(requireContext(), InternalCache.Type.SAMPLE)
        val streamSource = StreamDownloadRepositoryDecorator(StreamDownloadRepository(streamListener, booru))
        CachedRepository(cache, streamSource)
    }

    private val streamListener by lazy {
        StreamDownloadListener()
    }

    private val disposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SamplePageImageUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val progressview = parentFragment!!.view!!.find<CircularProgressBar>(R.id.samples_progress_concrete)
        streamListener.onPartReceived { _, _, progress ->
            runOnUiThread { progressview.setProgress((100 * progress).toInt()) }
        }
        val disposable = Single.just(post)
            .subscribeOn(Schedulers.newThread())
            .map { samplesRepository.get(it) }
            .map { BitmapFactory.decodeByteArray(it, 0, it.size) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::onSubscribe)
        disposables.add(disposable)
    }

    private fun onSubscribe(bitmap: Bitmap?, throwable: Throwable?) {
        val pview = parentFragment!!.view!!
        if (throwable == null) onSuccess(pview, bitmap!!) else onError(pview, throwable)
    }

    private fun onSuccess(view: View, bitmap: Bitmap) {
        //hide progress bar
        view.find<View>(R.id.samples_progress).visibility = View.GONE
        //hide preview image
        view.find<ImageView>(R.id.samples_preview).visibility = View.GONE
        //setup full image
        view.find<SubsamplingScaleImageView>(R.id.samples_image).apply {
            visibility = View.VISIBLE
            setImage(ImageSource.bitmap(bitmap))
            onLongClick { showOptionsList(booru, post) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    companion object {
        private const val BOORU = "Booru"
        private const val POST = "Post"
        fun create(booru: Booru, post: Post) = SamplePageImageFragment().apply {
            this.booru = booru
            this.post = post
        }
    }
}