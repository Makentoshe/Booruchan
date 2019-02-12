package com.makentoshe.booruchan.booru

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.Fragment
import com.makentoshe.booruchan.booru.view.BooruFragmentUI
import org.jetbrains.anko.AnkoContext

class BooruFragment : androidx.fragment.app.Fragment() {

    private lateinit var viewModel: BooruFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val arguments = Companion.arguments
        val factory = BooruFragmentViewModel.Factory(arguments.booru, arguments.tags)
        viewModel = ViewModelProviders.of(this, factory)[BooruFragmentViewModel::class.java]

        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.onCreateView(this)
        return BooruFragmentUI(viewModel)
            .createView(AnkoContext.create(requireContext(), this))
    }

    companion object {

        fun create(booru: Booru, tags: HashSet<Tag>): androidx.fragment.app.Fragment {
            arguments = Arguments(booru, tags)
            return BooruFragment()
        }

        private lateinit var arguments: Arguments
        private data class Arguments(val booru: Booru, val tags: Set<Tag>)
    }
}