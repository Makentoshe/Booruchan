package com.makentoshe.boorupostview

import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.api.NetworkExecutorBuilder
import com.makentoshe.api.PostsRepository
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.booru.entity.Posts
import com.makentoshe.boorulibrary.booru.entity.PostsRequest
import com.makentoshe.boorulibrary.entitiy.Post
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.boorulibrary.network.request.RequestBuilder
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import org.jetbrains.anko.AnkoContext
import java.io.Serializable

class PostsPanelFragment : Fragment() {

    private var booru: Booru
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(BOORU, value)
        get() = arguments!!.get(BOORU) as Booru

    private var tags: Set<Tag>
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(TAGS, value as Serializable)
        get() = arguments!!.get(TAGS) as Set<Tag>

    private val broadcastReceiver = PostsFragmentBroadcastReceiver()

    private val searchobservable = BehaviorSubject.create<Set<Tag>>()

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) return
        if (savedInstanceState.containsKey(PostsFragmentBroadcastReceiver.START_NEW_SEARCH)) {
            val tags = savedInstanceState.get(PostsFragmentBroadcastReceiver.START_NEW_SEARCH) as Set<Tag>
            searchobservable.onNext(tags)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val filter = IntentFilter(PostsFragmentBroadcastReceiver.START_NEW_SEARCH)
        requireActivity().registerReceiver(broadcastReceiver, filter)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostsPanelFragmentUi().createView(AnkoContext.create(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        broadcastReceiver.onNewSearchStarted(searchobservable::onNext)

        searchobservable.observeOn(Schedulers.io()).map(::onSearchStart)
            .subscribe(::onPostsReceived).let(disposables::add)
    }

    private fun onSearchStart(tags: Collection<Tag>): List<Post> {
        val executor = NetworkExecutorBuilder().build()
        val request = object: PostsRequest {
            override val count = 12
            override val page = 0
            override val tags = HashSet(tags)
        }
        return PostsRepository(booru, executor).get(request)
    }

    private fun onPostsReceived(posts: List<Post>) {
        posts.forEach(::println)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val value = if (searchobservable.hasValue()) searchobservable.value!! else return
        outState.putSerializable(PostsFragmentBroadcastReceiver.START_NEW_SEARCH, value as Serializable)
    }

    override fun onDetach() {
        super.onDetach()
        requireActivity().unregisterReceiver(broadcastReceiver)
    }

    companion object {
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"
        fun build(booru: Booru, tags: Set<Tag>): Fragment {
            val fragment = PostsPanelFragment()
            fragment.booru = booru
            fragment.tags = tags
            return fragment
        }
    }
}