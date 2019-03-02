package com.makentoshe.booruchan.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruchan.Booruchan
import org.jetbrains.anko.AnkoContext

class StartFragment : Fragment() {

    private lateinit var viewModel: StartFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val router = Booruchan.INSTANCE.router
        val list = Booruchan.INSTANCE.booruList
        val factory = StartFragmentViewModel.Factory(router, list)
        viewModel = ViewModelProviders.of(this, factory)[StartFragmentViewModel::class.java]
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return StartFragmentUI(viewModel).createView(AnkoContext.create(requireContext(), this))
    }
}