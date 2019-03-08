package com.makentoshe.booruchan.postinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.Booruchan
import com.makentoshe.booruchan.PostInternalCache
import com.makentoshe.booruchan.repository.CachedRepository
import com.makentoshe.booruchan.repository.PostsRepository
import org.jetbrains.anko.AnkoContext
import java.io.Serializable

class PostInfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostInfoUi().createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val router = Booruchan.INSTANCE.router
        PostInfoInflaterToolbarBack(router).inflate(view)

        val cache = PostInternalCache(requireContext(), "posts")
        val repository = PostsRepository(booru)
//        val cachedRepository = CachedRepository(cache, repository)
//        val posts = cachedRepository.get(Booru.PostRequest(1, position, tags))
//        PostInfoInflaterListView(posts!![0]).inflate(view)
    }

    private var tags: Set<Tag>
        get() = arguments!!.get(TAGS) as Set<Tag>
        set(value) = arguments().putSerializable(TAGS, value as Serializable)

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var position: Int
        get() = arguments!!.getInt(POSITION)
        set(value) = arguments().putInt(POSITION, value)

    private fun arguments(): Bundle {
        return arguments ?: Bundle().also { arguments = it }
    }

    companion object {
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"
        private const val POSITION = "Position"

        fun create(booru: Booru, tags: Set<Tag>, position: Int) = PostInfoFragment().apply {
            this.position = position
            this.booru = booru
            this.tags = tags
        }
    }
}

