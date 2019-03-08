package com.makentoshe.booruchan.screen.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.ImageInternalCache
import com.makentoshe.booruchan.repository.AsyncRepository
import com.makentoshe.booruchan.repository.CachedRepository
import com.makentoshe.booruchan.repository.PostsRepository
import com.makentoshe.booruchan.repository.PreviewImageRepository
import com.makentoshe.booruchan.repository.cache.PostInternalCache
import com.makentoshe.booruchan.screen.arguments
import com.makentoshe.booruchan.screen.posts.inflator.PostPageUiInflater
import com.makentoshe.booruchan.screen.posts.model.BooruRequestBuilder
import com.makentoshe.booruchan.screen.posts.view.PostPageUi
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.GlobalScope
import org.jetbrains.anko.AnkoContext
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

    private val disposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostPageUi()
            .createView(AnkoContext.create(requireContext(), this))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val cache = PostInternalCache(requireContext())
        val source = PostsRepository(booru)
        val repository = CachedRepository(cache, source)
        val asyncRepository = AsyncRepository(
            GlobalScope.coroutineContext,
            repository
        )
        val request = BooruRequestBuilder(tags, position).build(12)

        val previewsCache = ImageInternalCache(requireContext(), "preview")
        val previewsSource = PreviewImageRepository(booru)
        val previewsRepository = CachedRepository(previewsCache, previewsSource)

        PostPageUiInflater(
            asyncRepository,
            previewsRepository,
            request,
            disposables
        ).inflate(view)
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