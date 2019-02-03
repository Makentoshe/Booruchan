package com.makentoshe.booruchan.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.FragmentViewModel
import com.makentoshe.booruchan.ViewModelFragment

class AccountFragment : ViewModelFragment<AccountFragmentViewModel>() {
    override fun buildViewModel(arguments: Bundle): AccountFragmentViewModel {
        return ViewModelProviders.of(this)[AccountFragmentViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return TextView(context).apply {
            text = "Account"
        }
    }
}

class AccountFragmentViewModel : FragmentViewModel()