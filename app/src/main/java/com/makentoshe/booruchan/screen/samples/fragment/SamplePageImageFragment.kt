package com.makentoshe.booruchan.screen.samples.fragment

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
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.model.StreamDownloadController
import com.makentoshe.booruchan.model.add
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.repository.cache.CachedRepository
import com.makentoshe.booruchan.repository.cache.ImageInternalCache
import com.makentoshe.booruchan.repository.cache.InternalCache
import com.makentoshe.booruchan.repository.stream.StreamDownloadRepository
import com.makentoshe.booruchan.repository.stream.StreamDownloadRepositoryDecoratorFile
import com.makentoshe.booruchan.repository.stream.StreamDownloadRepositoryDecoratorSample
import com.makentoshe.booruchan.repository.stream.StreamRepositoryFactory
import com.makentoshe.booruchan.screen.samples.model.loadFromRepository
import com.makentoshe.booruchan.screen.samples.model.onError
import com.makentoshe.booruchan.screen.samples.model.showOptionsList
import com.makentoshe.booruchan.screen.samples.view.SamplePageImageUi
import com.makentoshe.booruchan.view.CircularProgressBar
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onLongClick
import org.jetbrains.anko.support.v4.runOnUiThread
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

class SamplePageImageFragment : Fragment() {

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var post: Post
        get() = arguments!!.get(POST) as Post
        set(value) = arguments().putSerializable(POST, value)

    private val samplesRepository by lazy {
        val factory = get<StreamRepositoryFactory> { parametersOf(booru, streamListener) }
        factory.buildSamplesRepository()
    }

    private val filesRepository by lazy {
        val cache = ImageInternalCache(requireContext(), InternalCache.Type.FILE)
        val streamSource = StreamDownloadRepositoryDecoratorFile(
            StreamDownloadRepository(
                streamListener,
                booru
            )
        )
        CachedRepository(cache, streamSource)
    }

    private val streamListener by lazy { StreamDownloadController.create() }

    private val disposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SamplePageImageUi().createView(AnkoContext.create(requireContext(), this))
    }

    /* Starts loading image file */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val pview = parentFragment!!.view!!
        val progressview = pview.find<CircularProgressBar>(R.id.samples_progress_concrete)
        streamListener.onPartReceived { _, _, progress ->
            runOnUiThread { progressview.setProgress((100 * progress).toInt()) }
        }
        disposables.add = loadFromRepository(post, samplesRepository) { b, t -> onSampleReceived(b, t) }
        //on long click to the view
        view.onLongClick { showOptionsList(booru, post) }
        //and on the preview
        pview.find<ImageView>(R.id.samples_preview).onLongClick { showOptionsList(booru, post) }
    }

    /* Calls on loading finished. It can be success or failed*/
    private fun onSampleReceived(byteArray: ByteArray?, throwable: Throwable?) {
        val pview = parentFragment!!.view!!
        //if error
        if (throwable != null) return onError(pview, throwable)
        //if byte array was downloaded and decoded to a bitmap
        val bitmap = byteArray?.toBitmap()
        if (bitmap != null) return onSuccess(pview, bitmap)
        //if bitmap was downloaded but decode was failed
        //second try with files repository
        disposables.add = loadFromRepository(post, filesRepository) { b, t -> onFileReceived(b, t) }
    }

    private fun onFileReceived(byteArray: ByteArray?, throwable: Throwable?) {
        val pview = parentFragment!!.view!!
        //if error
        if (throwable != null) return onError(pview, throwable)
        //if byte array was downloaded and decoded to a bitmap
        val bitmap = byteArray?.toBitmap()
        if (bitmap != null) return onSuccess(pview, bitmap)
        else onError(pview, Throwable("Image decode failed."))
    }

    /* Calls on image was successfully loaded. Setup the image into the view*/
    private fun onSuccess(view: View, bitmap: Bitmap) {
        //hide progress bar
        view.find<View>(R.id.samples_progress).visibility = View.GONE
        //hide preview image
        view.find<ImageView>(R.id.samples_preview).visibility = View.GONE
        //setup full image
        view.find<SubsamplingScaleImageView>(R.id.samples_image).apply {
            visibility = View.VISIBLE
            setImage(ImageSource.bitmap(bitmap))
        }
    }

    /* Decode bitmap from byte array */
    private fun ByteArray.toBitmap(): Bitmap? {
        return BitmapFactory.decodeByteArray(this, 0, size)
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

//class StreamRepositoryFactory(
//    private val booru: Booru,
//    private val streamDownloadListener: StreamDownloadListener
//) : RepositoryFactory {
//
//    override fun buildPostsRepository(): Repository<Posts.Request, List<Post>> {
//        val streamSource =
//    }
//
//    override fun buildPreviewsRepository(): Repository<Post, ByteArray> {
//        return StreamDownloadRepositoryDecoratorSample(StreamDownloadRepository(streamListener, booru))
//    }
//
//    override fun buildSamplesRepository(): Repository<Post, ByteArray> {
//        TODO("not implemented")
//    }
//}
