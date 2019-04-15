package com.makentoshe.booruchan.screen.samples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.model.StreamDownloadListener
import com.makentoshe.booruchan.model.add
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.repository.StreamDownloadRepository
import com.makentoshe.booruchan.repository.cache.ImageInternalCache
import com.makentoshe.booruchan.repository.cache.InternalCache
import com.makentoshe.booruchan.repository.decorator.CachedRepository
import com.makentoshe.booruchan.repository.decorator.StreamDownloadRepositoryDecoratorFile
import com.makentoshe.booruchan.repository.decorator.StreamDownloadRepositoryDecoratorSample
import com.makentoshe.booruchan.screen.samples.model.loadFromRepository
import com.makentoshe.booruchan.screen.samples.model.onError
import com.makentoshe.booruchan.screen.samples.model.showOptionsList
import com.makentoshe.booruchan.screen.samples.view.SamplePageGifUi
import com.makentoshe.booruchan.view.CircularProgressBar
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onLongClick
import org.jetbrains.anko.support.v4.runOnUiThread
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView

class SamplePageGifFragment : Fragment() {

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var post: Post
        get() = arguments!!.get(POST) as Post
        set(value) = arguments().putSerializable(POST, value)

    private val samplesRepository by lazy {
        val cache = ImageInternalCache(requireContext(), InternalCache.Type.SAMPLE)
        val streamSource = StreamDownloadRepository(streamListener, booru)
        val source = StreamDownloadRepositoryDecoratorSample(streamSource)
        CachedRepository(cache, source)
    }

    private val filesRepository by lazy {
        val cache = ImageInternalCache(requireContext(), InternalCache.Type.FILE)
        val streamSource = StreamDownloadRepository(streamListener, booru)
        val source = StreamDownloadRepositoryDecoratorFile(streamSource)
        CachedRepository(cache, source)
    }

    private val streamListener by lazy { StreamDownloadListener() }

    private val disposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SamplePageGifUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val progressview = parentFragment!!.view!!.find<CircularProgressBar>(R.id.samples_progress_concrete)
        streamListener.onPartReceived { _, _, progress ->
            runOnUiThread { progressview.setProgress((100 * progress).toInt()) }
        }

        val disposable = loadFromRepository(post, samplesRepository) { b, t ->
            onSampleReceive(if (b != null) GifDrawable(b) else null, t)
        }
        disposables.add(disposable)
    }

    private fun onSampleReceive(drawable: GifDrawable?, throwable: Throwable?) {
        //get root view from parent fragment
        val pview = parentFragment!!.view!!

        if (throwable != null) return onError(pview, throwable)

        if (drawable != null) return onSuccess(pview, drawable)

        disposables.add = loadFromRepository(post, filesRepository) { b, t ->
            onFileReceive(if (b != null) GifDrawable(b) else null, t)
        }
    }

    private fun onFileReceive(drawable: GifDrawable?, throwable: Throwable?) {
        //get root view from parent fragment
        val pview = parentFragment!!.view!!

        if (throwable != null) return onError(pview, throwable)

        if (drawable != null) return onSuccess(pview, drawable)

        onError(pview, Throwable("Gif decode failed."))
    }

    /* Calls when gif image was successfully downloaded */
    private fun onSuccess(view: View, drawable: GifDrawable) {
        //hide progress bar
        view.find<View>(R.id.samples_progress).visibility = View.GONE
        //hide preview image
        view.find<ImageView>(R.id.samples_preview).visibility = View.GONE
        //setup gif view
        view.find<GifImageView>(R.id.samples_gif).apply {
            visibility = View.VISIBLE
            setImageDrawable(drawable)
            onLongClick { showOptionsList(booru, post) }
        }
        //start gif animation
        drawable.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    companion object {
        private const val BOORU = "Booru"
        private const val POST = "Post"
        fun create(booru: Booru, post: Post) = SamplePageGifFragment().apply {
            this.booru = booru
            this.post = post
        }
    }
}