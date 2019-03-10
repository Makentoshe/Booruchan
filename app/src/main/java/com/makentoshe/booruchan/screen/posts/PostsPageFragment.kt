package com.makentoshe.booruchan.screen.posts

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.repository.AsyncRepositoryAccess
import com.makentoshe.booruchan.repository.CachedRepository
import com.makentoshe.booruchan.repository.PostsRepository
import com.makentoshe.booruchan.repository.cache.PostInternalCache
import com.makentoshe.booruchan.screen.arguments
import com.makentoshe.booruchan.screen.posts.inflator.PostPageErrorMessageInflater
import com.makentoshe.booruchan.screen.posts.inflator.PostPageGridViewInflater
import com.makentoshe.booruchan.screen.posts.model.BooruRequestBuilder
import com.makentoshe.booruchan.screen.posts.view.PostPageUi
import com.makentoshe.booruchan.screen.samples.SampleScreen
import io.reactivex.disposables.CompositeDisposable
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

    private val disposables = CompositeDisposable()

    private val asyncRepositoryAccess by lazy {
        val cache = PostInternalCache(requireContext())
        val source = PostsRepository(booru)
        val repository = CachedRepository(cache, source)
        AsyncRepositoryAccess(repository).apply {
            request(booruRequestBuilder.build(12))
        }
    }

    private val booruRequestBuilder by lazy {
        BooruRequestBuilder(tags, position)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        println("Fragment: $position")
        return PostPageUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //show grid with the images if all is ok
        disposables.add(asyncRepositoryAccess.onComplete {
            Handler(Looper.getMainLooper()).post {
                PostPageGridViewInflater(it).inflate(view)
            }
        })
        //show on error message when error event occur
        disposables.add(asyncRepositoryAccess.onError {
            Handler(Looper.getMainLooper()).post {
                PostPageErrorMessageInflater(it).inflate(view)
            }
        })
        //click on grid element starts a new screen - samples,
        //where images in sample resolution will be displayed
        view.find<GridView>(R.id.posts_page_gridview).setOnItemClickListener { _, _, itempos, _ ->
            val position = this.position * 12 + itempos
            val screen = SampleScreen(position, booru, tags)
            Booruchan.INSTANCE.router.navigateTo(screen)
        }
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