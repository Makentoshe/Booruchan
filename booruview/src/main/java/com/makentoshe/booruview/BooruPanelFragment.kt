package com.makentoshe.booruview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.jetbrains.anko.AnkoContext

class BooruPanelFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return BooruPanelFragmentUi().createView(AnkoContext.create(requireContext()))
    }

    companion object {

        fun build(): Fragment {
            val fragment = BooruPanelFragment()
            return fragment
        }
    }

}