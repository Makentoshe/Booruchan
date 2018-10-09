package com.makentoshe.booruchan.booru.content.posts.infinity.ordered.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.content.posts.infinity.ordered.PostsViewModel
import com.makentoshe.booruchan.booru.content.view.ContentFragment
import org.jetbrains.anko.AnkoContext
import java.lang.ref.WeakReference

class PostsFragment : ContentFragment() {

    lateinit var viewModel: PostsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = PostsViewModel.Factory(booru, appSettings)
        viewModel = ViewModelProviders.of(this, factory)[PostsViewModel::class.java]
        println("Create ${this::class.java.simpleName}")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PostsFragmentUI(viewModel).createView(AnkoContext.create(activity!!, this))
    }

    override fun onSearchStarted(): WeakReference<(String) -> Unit> {
        return WeakReference<(String) -> Unit>({
            activity?.findViewById<RecyclerView>(R.id.booru_content_gallery)?.apply {
                adapter = viewModel.newGalleryAdapter(it)
                scrollToPosition(0)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Destroy ${this::class.java.simpleName}")
    }

}