package com.makentoshe.booruchan.sample.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.makentoshe.booruchan.sample.PageViewModel
import com.makentoshe.booruchan.sample.SampleViewModel
import org.jetbrains.anko.AnkoContext

class PageFragment : Fragment() {

    private lateinit var viewModel: PageViewModel
    private lateinit var sampleViewModel: SampleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sampleViewModel = ViewModelProviders.of(activity!!)[SampleViewModel::class.java]
        val factory = PageViewModel.Factory(arguments!!, sampleViewModel.booru)
        viewModel = ViewModelProviders.of(this, factory)[PageViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PageFragmentUI(viewModel, sampleViewModel).createView(AnkoContext.create(activity!!, this))
    }

    companion object {
        const val ARG_POSITION = "PositionArg"
        const val ARG_TAGS = "TagsArg"

        fun new(position: Int, tagsString: String): PageFragment {
            val fragment = PageFragment()
            val args = Bundle()
            args.putInt(ARG_POSITION, position)
            args.putString(ARG_TAGS, tagsString)
            fragment.arguments = args
            return fragment
        }
    }

}