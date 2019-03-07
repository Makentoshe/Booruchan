package com.makentoshe.booruchan.screen.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.navigation.Screen

class AccountScreen : Screen() {
    override val fragment: Fragment
        get() = AccountFragment()
}

class AccountFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return TextView(requireContext()).apply {
            text = "Account"
        }
    }
}