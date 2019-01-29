package com.makentoshe.booruchan.start

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruchan.ViewModelFactory
import com.makentoshe.booruchan.ViewModelFragment
import org.jetbrains.anko.AnkoContext

class StartFragment : ViewModelFragment<StartFragmentViewModel>() {

    override fun buildViewModel(arguments: Bundle?): StartFragmentViewModel {
        val factory = ViewModelFactory()
        return ViewModelProviders.of(this, factory)[StartFragmentViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return StartFragmentUI(viewModel).createView(AnkoContext.create(requireContext(), this))
    }
}