package com.makentoshe.booruchan.booru.content.comments.vertical.pager.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.makentoshe.booruchan.booru.content.comments.vertical.pager.CommentsViewModel
import com.makentoshe.booruchan.booru.content.view.ContentFragment
import org.jetbrains.anko.AnkoContext
import java.lang.ref.WeakReference

class CommentsFragment : ContentFragment() {

    private lateinit var viewModel: CommentsViewModel

    override fun onSearchStarted(): WeakReference<(String) -> Unit> {
        return WeakReference<(String) -> Unit> {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = CommentsViewModel.Factory(appSettings)
        viewModel = ViewModelProviders.of(this, factory)[CommentsViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return CommentsFragmentUI(viewModel).createView(AnkoContext.create(activity!!, this))
    }

}