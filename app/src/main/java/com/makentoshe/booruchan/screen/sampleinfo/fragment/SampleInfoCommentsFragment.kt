package com.makentoshe.booruchan.screen.sampleinfo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.comment.Comment
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.screen.posts.page.controller.imagedownload.DownloadListener
import com.makentoshe.booruchan.screen.sampleinfo.controller.SampleInfoCommentsViewController
import com.makentoshe.booruchan.screen.sampleinfo.SampleInfoCommentsViewModel
import com.makentoshe.booruchan.screen.sampleinfo.view.SampleInfoCommentsUi
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SampleInfoCommentsFragment : Fragment() {

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var post: Post
        get() = arguments!!.get(POST) as Post
        set(value) = arguments().putSerializable(POST, value)

    private val disposables by inject<CompositeDisposable>()

    private val commentsDownloadListener: DownloadListener<List<Comment>> by viewModel<SampleInfoCommentsViewModel> {
        parametersOf(booru, post, disposables)
    }

    private val viewController by currentScope.inject<SampleInfoCommentsViewController> {
        parametersOf(view!!, post, commentsDownloadListener)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SampleInfoCommentsUi().createView(AnkoContext.Companion.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewController.bind(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    companion object {
        private const val BOORU = "Booru"
        private const val POST = "Post"
        fun create(booru: Booru, post: Post) = SampleInfoCommentsFragment().apply {
            this.booru = booru
            this.post = post
        }
    }
}
