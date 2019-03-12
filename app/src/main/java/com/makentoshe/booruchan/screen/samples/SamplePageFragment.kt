package com.makentoshe.booruchan.screen.samples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.repository.AsyncRepositoryAccess
import com.makentoshe.booruchan.repository.CachedRepository
import com.makentoshe.booruchan.repository.PostsRepository
import com.makentoshe.booruchan.repository.cache.PostInternalCache
import com.makentoshe.booruchan.screen.arguments
import com.makentoshe.booruchan.screen.samples.model.SamplePageFragmentCompleteConsumer
import com.makentoshe.booruchan.screen.samples.model.SamplePageFragmentErrorConsumer
import com.makentoshe.booruchan.screen.samples.view.SamplePageUi
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find
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
        //calls when posts request to repository does not works
        disposables.add(asyncRepositoryAccess.onError {
            SamplePageFragmentErrorConsumer(it).accept(view)
        })
        //calls when posts request to repository success
        disposables.add(asyncRepositoryAccess.onComplete {
            val post = it[0]
            SamplePageFragmentCompleteConsumer(post).accept(view)
            //setup on click - optional menu
            view.find<View>(R.id.samples_image).setOnLongClickListener {
                val tag = SampleOptionFragment::class.java.simpleName
                SampleOptionFragment.create(booru, post).show(childFragmentManager, tag)
                return@setOnLongClickListener true
            }
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

