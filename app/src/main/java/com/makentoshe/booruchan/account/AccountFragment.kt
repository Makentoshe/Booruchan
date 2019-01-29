package com.makentoshe.booruchan.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.Booru

class AccountFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val booru = arguments?.getSerializable(Booru::class.java.simpleName) as Booru
//        val factory = ViewModelFactory(booru = booru)
//        postsFragmentViewModel = ViewModelProviders.of(this, factory)[PostsFragmentViewModel::class.java]
//        postsFragmentViewModel.onUiRecreate()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return TextView(context).apply {
            text = "Account"
        }
    }
}