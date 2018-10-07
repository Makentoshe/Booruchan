package com.makentoshe.booruchan.booru.view.content.comments.vertical.pager

import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.makentoshe.booruchan.booru.ContentViewModel
import org.jetbrains.anko.AnkoContext
import java.util.*

class CommentPageFragment : Fragment() {

    private var page = -1
    private lateinit var viewModel: CommentPageFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        page = arguments?.getInt(ARG_PAGE)!!
        initViewModels()
    }

    private fun initViewModels() {
        val contentViewModel = ViewModelProviders.of(activity!!)[ContentViewModel::class.java]
        val factory = CommentPageFragmentViewModel.Factory(contentViewModel.booru)
        viewModel = ViewModelProviders.of(this, factory)[CommentPageFragmentViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return CommentPageFragmentUI(page, viewModel).createView(AnkoContext.create(activity!!, this))
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Destroy fragment")
    }

    companion object {
        private const val ARG_PAGE = "ArgPage"

        fun new(page: Int): CommentPageFragment {
            val fragment = CommentPageFragment()
            val args = Bundle()
            args.putInt(ARG_PAGE, page)
            fragment.arguments = args
            return fragment
        }
    }

}