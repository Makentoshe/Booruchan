package com.makentoshe.booruchan.booru.view.content.comments.vertical.pager

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.makentoshe.booruchan.booru.model.content.comments.pager.vertical.CommentsContentVerticalPagerViewModel
import com.makentoshe.booruchan.booru.view.content.ContentFragment
import org.jetbrains.anko.AnkoContext
import java.lang.ref.WeakReference

class CommentsVerticalPagerFragment : ContentFragment() {

    lateinit var viewModel: CommentsContentVerticalPagerViewModel

    override fun onSearchStarted(): WeakReference<(String) -> Unit> {
        return WeakReference<(String) -> Unit> {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = CommentsContentVerticalPagerViewModel.Factory(booru)
        viewModel = ViewModelProviders.of(this, factory)[CommentsContentVerticalPagerViewModel::class.java]
        println("Create ${this::class.java.simpleName}")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return CommentsVerticalPagerFragmentUI(viewModel).createView(AnkoContext.create(activity!!, this))
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Destroy ${this::class.java.simpleName}")
    }


}