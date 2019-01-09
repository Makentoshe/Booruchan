package com.makentoshe.booruchan.postsamples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.*

class PostSampleFragment : Fragment(), BackPressableFragment {

    private lateinit var viewModel: PostsSampleFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val booru = arguments!!.getSerializable(Booru::class.java.simpleName) as Booru
        val factory = ViewModelFactory(booru = booru)
        viewModel = ViewModelProviders.of(this, factory)[PostsSampleFragmentViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return TextView(context).apply {
            text = "Sas"
        }
    }

    override fun onBackPressed(): Boolean {
        viewModel.returnToBackScreen()
        return true
    }

}

class PostsSampleFragmentViewModel(
    private val booru: Booru
) : ViewModel() {

    fun returnToBackScreen() {
        Booruchan.INSTANCE.router.replaceScreen(BooruScreen(booru))
    }

}