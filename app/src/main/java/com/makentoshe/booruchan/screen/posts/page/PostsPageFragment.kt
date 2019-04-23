package com.makentoshe.booruchan.screen.posts.page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.repository.FileImageRepository
import com.makentoshe.booruchan.repository.PreviewImageRepository
import com.makentoshe.booruchan.repository.SampleImageRepository
import com.makentoshe.booruchan.repository.cache.ImageInternalCache
import com.makentoshe.booruchan.repository.cache.InternalCache
import com.makentoshe.booruchan.repository.decorator.CachedRepository
import com.makentoshe.booruchan.screen.posts.page.controller.PostsPageContentController
import com.makentoshe.booruchan.screen.posts.page.view.PostPageUi
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.AnkoContext
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import java.io.Serializable

class PostsPageFragment : Fragment() {

    init {
        //provide current fragment instance to the scope
        currentScope.get<Fragment>(named(PostsPageModule.FRAGMENT)) { parametersOf(this) }
    }

    private var position: Int
        get() = arguments!!.getInt(POS)
        set(value) = arguments().putInt(POS, value)

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var tags: Set<Tag>
        get() = arguments!!.get(TAGS) as Set<Tag>
        set(value) = arguments().putSerializable(TAGS, value as Serializable)

    private val viewmodel by viewModel<PostsPageViewModel> { parametersOf(booru, tags, position) }

    private val contentController by currentScope.inject<PostsPageContentController>()

    /* Disposable that must be disposed on the onDestroy method */
    private val disposable: Disposable by currentScope.inject<CompositeDisposable>(named(PostsPageModule.DISPOSABLE))

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

    override fun onCreate(savedInstanceState: Bundle?) {
        viewmodel.init()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostPageUi()
            .createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        contentController.bindView(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.dispose()
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

