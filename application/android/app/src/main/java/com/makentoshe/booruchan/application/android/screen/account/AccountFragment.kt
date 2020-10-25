package com.makentoshe.booruchan.application.android.screen.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class AccountFragment : Fragment() {

    companion object {
        fun build(): AccountFragment {
            return AccountFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return TextView(requireContext()).apply { text = "Account screen not implemented" }
    }
}