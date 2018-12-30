package com.makentoshe.booruchan.booru

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.*
import org.jetbrains.anko.*

class BooruFragment : Fragment() {

    private lateinit var booruFragmentViewModel: BooruFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val booru = arguments?.getSerializable(Booru::class.java.simpleName) as Booru
        val factory = ViewModelFactory(booru = booru)
        booruFragmentViewModel = ViewModelProviders.of(this, factory)[BooruFragmentViewModel::class.java]
        booruFragmentViewModel.update(requireActivity(), childFragmentManager)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return BooruFragmentUI(booruFragmentViewModel).createView(AnkoContext.create(requireContext(), this))
    }
}