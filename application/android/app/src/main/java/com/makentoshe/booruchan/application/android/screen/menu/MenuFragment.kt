package com.makentoshe.booruchan.application.android.screen.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class MenuFragment : Fragment() {

    companion object {
        fun build() : MenuFragment {
            return MenuFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return TextView(requireContext()).apply { text = "Menu screen not implemented" }
    }
}
