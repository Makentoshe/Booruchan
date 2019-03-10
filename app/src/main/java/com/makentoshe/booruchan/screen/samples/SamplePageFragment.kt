package com.makentoshe.booruchan.screen.samples

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.util.Consumer
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Post
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.repository.AsyncRepositoryAccess
import com.makentoshe.booruchan.repository.CachedRepository
import com.makentoshe.booruchan.repository.PostsRepository
import com.makentoshe.booruchan.repository.cache.PostInternalCache
import com.makentoshe.booruchan.screen.arguments
import com.makentoshe.booruchan.screen.samples.view.SamplePageUi
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import java.io.File
import java.io.Serializable

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

    private val asyncRepositoryAccess by lazy {
        val cache = PostInternalCache(requireContext())
        val source = PostsRepository(booru)
        val repository = CachedRepository(cache, source)
        AsyncRepositoryAccess(repository).apply {
            request(Booru.PostRequest(1, position, tags))
        }
    }

    private val disposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SamplePageUi()
            .createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        disposables.add(asyncRepositoryAccess.onError {
            SamplePageFragmentErrorConsumer(it).accept(view)
        })

        disposables.add(asyncRepositoryAccess.onComplete {
            SamplePageFragmentCompleteConsumer(it[0]).accept(view)
        })
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

open class SamplePageFragmentErrorConsumer(private val throwable: Throwable? = null) : Consumer<View> {
    override fun accept(t: View) {
        onError(t, throwable!!)
    }

    protected fun onError(view: View, throwable: Throwable) {
        val progress = view.find<View>(R.id.samples_progress)
        val message = view.find<TextView>(R.id.samples_message)

        Handler(Looper.getMainLooper()).post {
            progress.visibility = View.GONE
            message.visibility = View.VISIBLE
            message.text = throwable.localizedMessage
        }
    }
}

class SamplePageFragmentCompleteConsumer(
    private val post: Post
) : SamplePageFragmentErrorConsumer() {

    override fun accept(t: View) {
        when (File(post.sampleUrl).extension) {
            "webm" -> Unit
//            "gif" -> { }
            else -> onCompleteImage(t, post)
        }
    }

    private fun onCompleteImage(view: View, post: Post) {
        val imageview = view.find<ImageView>(R.id.samples_image)

        val request = Glide.with(view)
            .load(post.sampleUrl)
            .listener(object : SimpleRequestListener<Drawable>() {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Handler(Looper.getMainLooper()).post {
                        onError(view, e ?: RuntimeException("Something goes wrong"))
                    }
                    return super.onLoadFailed(e, model, target, isFirstResource)
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    Handler(Looper.getMainLooper()).post {
                        imageview.visibility = View.VISIBLE
                    }
                    return super.onResourceReady(resource, model, target, dataSource, isFirstResource)
                }
            })

        Handler(Looper.getMainLooper()).post {
            request.into(imageview)
        }
    }

    open class SimpleRequestListener<T> : RequestListener<T> {
        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<T>?, isFirstResource: Boolean) = false
        override fun onResourceReady(
            resource: T,
            model: Any?,
            target: Target<T>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ) = false
    }
}