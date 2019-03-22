package com.makentoshe.booruchan.screen.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Post
import com.makentoshe.booruchan.api.Posts
import com.makentoshe.booruchan.api.Tag
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.repository.FileImageRepository
import com.makentoshe.booruchan.repository.PostsRepository
import com.makentoshe.booruchan.repository.PreviewImageRepository
import com.makentoshe.booruchan.repository.SampleImageRepository
import com.makentoshe.booruchan.repository.cache.ImageInternalCache
import com.makentoshe.booruchan.repository.cache.InternalCache
import com.makentoshe.booruchan.repository.cache.PostInternalCache
import com.makentoshe.booruchan.repository.decorator.CachedRepository
import com.makentoshe.booruchan.router
import com.makentoshe.booruchan.screen.posts.model.PostPageGridAdapter
import com.makentoshe.booruchan.screen.posts.view.PostPageUi
import com.makentoshe.booruchan.screen.samples.SampleScreen
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
import java.io.Serializable

class PostsPageFragment : Fragment() {

    private var position: Int
        get() = arguments!!.getInt(POS)
        set(value) = arguments().putInt(POS, value)

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var tags: Set<Tag>
        get() = arguments!!.get(TAGS) as Set<Tag>
        set(value) = arguments().putSerializable(TAGS, value as Serializable)

    //Repository returns list of posts by request
    private val postsRepository by lazy {
        val cache = PostInternalCache(requireContext())
        val source = PostsRepository(booru)
        CachedRepository(cache, source)
    }

    //Repository returns preview image by post
    private val previewsRepository by lazy {
        val cache = ImageInternalCache(requireContext(), InternalCache.Type.PREVIEW)
        val source = PreviewImageRepository(booru)
        CachedRepository(cache, source)
    }

    //Repository returns sample image by post
    //Used when preview image can not be decoded
    private val samplesRepository by lazy {
        val cache = ImageInternalCache(requireContext(), InternalCache.Type.SAMPLE)
        val source = SampleImageRepository(booru)
        CachedRepository(cache, source)
    }

    //Repository returns file image by post
    //Used when preview image and sample image can't be decoded
    private val filesRepository by lazy {
        val cache = ImageInternalCache(requireContext(), InternalCache.Type.FILE)
        val source = FileImageRepository(booru)
        CachedRepository(cache, source)
    }

    private val disposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostPageUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val disposable = Single.just(postsRepository)
            .subscribeOn(Schedulers.newThread())
            .map { it.get(Posts.Request(12, tags, position))!! }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { onError(view, it) }
            .subscribe { posts -> onComplete(view, posts) }
        disposables.add(disposable)
    }

    private fun onComplete(view: View, posts: List<Post>) {
        if (posts.isEmpty()) {
            onError(view, Exception(getString(R.string.posts_ran_out)))
//            parentFragment?.onActivityResult(RequestCode.postpage, position, Intent())
            return
        }

        val progress = view.find<ProgressBar>(R.id.posts_page_progress)
        val gridview = view.find<GridView>(R.id.posts_page_gridview)

        progress.visibility = View.GONE
        gridview.visibility = View.VISIBLE

        val adapter = PostPageGridAdapter(view.context, posts, previewsRepository, samplesRepository, filesRepository)
        //when subscribed on observable - the disposable will be return for storing and dispose in future
        adapter.setOnSubscribeListener {
            disposables.add(it)
        }
        gridview.adapter = adapter

        //click on grid element starts a new screen - samples,
        //where images in sample resolution will be displayed
        view.find<GridView>(R.id.posts_page_gridview).setOnItemClickListener { _, _, itempos, _ ->
            val position = this.position * 12 + itempos
            val screen = SampleScreen(position, booru, tags)
            router.navigateTo(screen)
        }
    }

    private fun onError(view: View, throwable: Throwable) {
        val progress = view.find<ProgressBar>(R.id.posts_page_progress)
        val message = view.find<TextView>(R.id.posts_page_textview)

        progress.visibility = View.GONE
        message.text = throwable.localizedMessage
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }

    companion object {
        private const val POS = "Position"
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"

        fun create(position: Int, booru: Booru, tags: Set<Tag>) = PostsPageFragment().apply {
            this.position = position
            this.booru = booru
            this.tags = tags
        }
    }
}