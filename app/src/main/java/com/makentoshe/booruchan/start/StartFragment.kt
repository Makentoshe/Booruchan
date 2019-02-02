package com.makentoshe.booruchan.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruchan.StartFragmentViewModelFactory
import com.makentoshe.booruchan.ViewModelFragment
import org.jetbrains.anko.AnkoContext

class StartFragment : ViewModelFragment<StartFragmentViewModel>() {

    override fun buildViewModel(arguments: Bundle): StartFragmentViewModel {
        val factory = StartFragmentViewModelFactory()
        return ViewModelProviders.of(this, factory)[StartFragmentViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return StartFragmentUI(viewModel).createView(AnkoContext.create(requireContext(), this))
    }
}