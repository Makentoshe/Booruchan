package com.makentoshe.booruchan.booru.view.content.comments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.makentoshe.booruchan.booru.model.content.comments.CommentsContentViewModel
import com.makentoshe.booruchan.booru.model.content.posts.PostsContentViewModel
import com.makentoshe.booruchan.booru.view.content.ContentFragment
import com.makentoshe.booruchan.booru.view.content.posts.PostsFragment
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.settings.application.AppSettings
import org.jetbrains.anko.AnkoContext
import java.lang.ref.WeakReference

class CommentsFragment : ContentFragment() {

    lateinit var viewModel: CommentsContentViewModel

    override fun onSearchStarted(): WeakReference<(String) -> Unit> {
        return WeakReference<(String) -> Unit> {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = CommentsContentViewModel.Factory(booru, appSettings)
        viewModel = ViewModelProviders.of(this, factory)[CommentsContentViewModel::class.java]
        println("Create ${this::class.java.simpleName}")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return CommentsFragmentUI(viewModel).createView(AnkoContext.create(activity!!, this))
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Destroy ${this::class.java.simpleName}")
    }


}