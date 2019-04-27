package com.makentoshe.booruchan.screen.samples.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.api.Posts
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.model.add
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.repository.PostsRepository
import com.makentoshe.booruchan.repository.PreviewImageRepository
import com.makentoshe.booruchan.repository.cache.ImageInternalCache
import com.makentoshe.booruchan.repository.cache.InternalCache
import com.makentoshe.booruchan.repository.cache.PostInternalCache
import com.makentoshe.booruchan.repository.decorator.CachedRepository
import com.makentoshe.booruchan.screen.samples.model.onError
import com.makentoshe.booruchan.screen.samples.view.SamplePageUi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
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

    private val postsRepository by lazy {
        val cache = PostInternalCache(requireContext())
        val source = PostsRepository(booru)
        CachedRepository(cache, source)
    }

    private val previewsRepository by lazy {
        val cache = ImageInternalCache(requireContext(), InternalCache.Type.PREVIEW)
        val source = PreviewImageRepository(booru)
        CachedRepository(cache, source)
    }

    private val disposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SamplePageUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //get post data
        val disposable = Single.just(postsRepository)
            .subscribeOn(Schedulers.io())
            .map { it.get(Posts.Request(1, tags, position))!![0] }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::onSubscribe)
        disposables.add(disposable)
    }

    private fun onSubscribe(post: Post?, throwable: Throwable?) {
        if (throwable == null) onComplete(post!!) else onError(view!!, throwable)
    }

    private fun onComplete(post: Post) {
        val fragment = when (File(post.sampleUrl).extension) {
            "webm" -> onWebm(post)
            "gif" -> onGif(post)
            else -> onImage(post)
        }
        childFragmentManager.beginTransaction().add(R.id.samples_content, fragment).commit()
        onPostLoaded(post)
    }

    private fun onImage(post: Post): Fragment {
        return SamplePageImageFragment.create(booru, post)
    }

    private fun onGif(post: Post): Fragment {
        return SamplePageGifFragment.create(booru, post)
    }

    private fun onWebm(post: Post): Fragment {
        return SamplePageWebmFragment.create(
            booru,
            post,
            position
        )
    }

    /* Loads preview image */
    private fun onPostLoaded(post: Post) {
        disposables.add = Single.just(post)
            .subscribeOn(Schedulers.io())
            .map { previewsRepository.get(it) }
            .map { BitmapFactory.decodeByteArray(it, 0, it.size) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { preview, throwable ->
                val imageview = view!!.find<ImageView>(R.id.samples_preview)
                if (throwable != null) {
                    imageview.visibility = View.GONE
                } else {
                    imageview.visibility = View.VISIBLE
                    imageview.setImageBitmap(preview)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }

    companion object {
        private const val POSITION = "Position"
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"
        fun create(position: Int, booru: Booru, tags: Set<Tag>) = SamplePageFragment().apply {
            this.position = position
            this.booru = booru
            this.tags = tags
        }
    }
}