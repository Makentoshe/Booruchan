package com.makentoshe.boorupostview.fragment

import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.boorupostview.PostsFragmentBroadcastReceiver
import com.makentoshe.boorupostview.listener.NewSearchStartedListener
import com.makentoshe.boorupostview.view.PostsGridScrollFragmentUi
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import org.jetbrains.anko.AnkoContext
import java.io.Serializable

/**
 * Performs posts displaying using viewpager and grid view
 */
class PostsGridScrollFragment : Fragment(), PostsContainerFragment {

    private var booru: Booru
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(BOORU, value)
        get() = arguments!!.get(BOORU) as Booru

    private var tags: Set<Tag>
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(TAGS, value as Serializable)
        get() = arguments!!.get(TAGS) as Set<Tag>

    /** Broadcast receiver for receiving a new search events */
    private val broadcastReceiver = PostsFragmentBroadcastReceiver()

    private val disposables = CompositeDisposable()

    private lateinit var presenter: PostsGridScrollFragmentRxPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val filter = IntentFilter(PostsFragmentBroadcastReceiver.START_NEW_SEARCH)
        requireActivity().registerReceiver(broadcastReceiver, filter)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostsGridScrollFragmentUi().createView(AnkoContext.create(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tags: Set<Tag> = if (savedInstanceState == null) emptySet() else extractTagsFromState(savedInstanceState)
        presenter = PostsGridScrollFragmentRxPresenter(disposables, broadcastReceiver, tags)

        val viewpager = view.findViewById<ViewPager>(com.makentoshe.boorupostview.R.id.viewpager)
        presenter.bindViewPager(viewpager)
    }

    private fun extractTagsFromState(state: Bundle): Set<Tag> {
        val key = PostsFragmentBroadcastReceiver.START_NEW_SEARCH

        return if (state.containsKey(key)) state.get(key) as Set<Tag> else emptySet()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(PostsFragmentBroadcastReceiver.START_NEW_SEARCH, presenter.tags as Serializable)
    }

    override fun onDetach() {
        super.onDetach()
        requireActivity().unregisterReceiver(broadcastReceiver)
    }

    companion object {
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"
        fun build(booru: Booru, tags: Set<Tag>): Fragment {
            val fragment = PostsGridScrollFragment()
            fragment.booru = booru
            fragment.tags = tags
            return fragment
        }
    }
}

interface PostsGridScrollFragmentPresenter {

    fun bindViewPager(view: ViewPager)
}

abstract class RxPresenter {
    abstract val disposables: CompositeDisposable
}

class PostsGridScrollFragmentRxPresenter(
    override val disposables: CompositeDisposable,
    searchStartedListener: NewSearchStartedListener,
    initialTags: Set<Tag>
) : RxPresenter(), PostsGridScrollFragmentPresenter {

    private val searchObservable = BehaviorSubject.create<Set<Tag>>()

    val tags: Set<Tag>
        get() = searchObservable.value.orEmpty()

    init {
        searchStartedListener.onNewSearchStarted(searchObservable::onNext)

        searchObservable.onNext(initialTags)
    }

    override fun bindViewPager(view: ViewPager) {
        searchObservable.subscribe {
            println(it)
        }.let(disposables::add)
    }
}
