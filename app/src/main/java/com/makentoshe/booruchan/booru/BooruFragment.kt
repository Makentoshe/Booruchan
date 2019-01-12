package com.makentoshe.booruchan.booru

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.*
import org.jetbrains.anko.*

class BooruFragment : Fragment() {

    private lateinit var booruFragmentViewModel: BooruFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        booruFragmentViewModel = buildViewModel(arguments!!)
        booruFragmentViewModel.update(requireActivity(), childFragmentManager)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return BooruFragmentUI(booruFragmentViewModel).createView(AnkoContext.create(requireContext(), this))
    }

    private fun buildViewModel(arguments: Bundle) : BooruFragmentViewModel {
        val booru = arguments.getSerializable(Booru::class.java.simpleName) as Booru
        //if was called from sample - the search must be started with this tags
        val tags = arguments.getSerializable(Tag::class.java.simpleName) as Set<Tag>

        val factory = ViewModelFactory(booru = booru, tags = tags)
        return ViewModelProviders.of(this, factory)[BooruFragmentViewModel::class.java]
    }
}