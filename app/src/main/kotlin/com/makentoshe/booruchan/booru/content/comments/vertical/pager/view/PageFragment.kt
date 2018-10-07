package com.makentoshe.booruchan.booru.content.comments.vertical.pager.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.makentoshe.booruchan.booru.content.ContentViewModel
import com.makentoshe.booruchan.booru.content.comments.vertical.pager.PageViewModel
import org.jetbrains.anko.AnkoContext

class PageFragment : Fragment() {

    private var page = -1
    private lateinit var viewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        page = arguments?.getInt(ARG_PAGE)!!
        initViewModels()
    }

    private fun initViewModels() {
        val contentViewModel = ViewModelProviders.of(activity!!)[ContentViewModel::class.java]
        val factory = PageViewModel.Factory(contentViewModel.booru)
        viewModel = ViewModelProviders.of(this, factory)[PageViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PageFragmentUI(page, viewModel).createView(AnkoContext.create(activity!!, this))
    }

    companion object {
        private const val ARG_PAGE = "ArgPage"

        fun new(page: Int): PageFragment {
            val fragment = PageFragment()
            val args = Bundle()
            args.putInt(ARG_PAGE, page)
            fragment.arguments = args
            return fragment
        }
    }

}