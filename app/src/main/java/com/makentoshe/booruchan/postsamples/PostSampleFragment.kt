package com.makentoshe.booruchan.postsamples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Post
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.*
import com.makentoshe.booruchan.posts.model.PostsRepository

class PostSampleFragment : Fragment() {

    private lateinit var viewModel: PostsSampleFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val booru = arguments!!.getSerializable(Booru::class.java.simpleName) as Booru
        val startPosition = arguments!!.getInt(Int::class.java.simpleName)
        val postsRepository = arguments!!.getSerializable(PostsRepository::class.java.simpleName) as PostsRepository
        val factory = ViewModelFactory(booru = booru, position = startPosition, postsRepository = postsRepository)
        viewModel = ViewModelProviders.of(this, factory)[PostsSampleFragmentViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return TextView(context).apply {
            text = "${viewModel.getPost(viewModel.startPosition)}"
            setOnLongClickListener {
                viewModel.startNewSearch()
                return@setOnLongClickListener true
            }
        }
    }

}

class PostsSampleFragmentViewModel(
    private val booru: Booru,
    val startPosition: Int,
    private val postsRepository: PostsRepository
) : ViewModel() {

    /**
     * @param position post index start from 0.
     */
    fun getPost(position: Int): Post {
        val posts = postsRepository.get(position / postsRepository.count)
        return posts[position % postsRepository.count]
    }

    fun startNewSearch() {
        val set = hashSetOf(Tag("hatsune_miku"))
        Booruchan.INSTANCE.router.newChain(BooruScreen(booru, set))
    }
}