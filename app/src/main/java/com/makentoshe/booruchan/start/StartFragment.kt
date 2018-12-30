package com.makentoshe.booruchan.start

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruchan.ViewModelFactory
import org.jetbrains.anko.AnkoContext

class StartFragment : Fragment() {

    private lateinit var startFragmentViewModel: StartFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ViewModelFactory()
        startFragmentViewModel = ViewModelProviders.of(this, factory)[StartFragmentViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return StartFragmentUI(startFragmentViewModel).createView(AnkoContext.create(requireContext(), this))
    }
}