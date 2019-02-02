package com.makentoshe.booruchan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

interface Backpressable {
    fun onBackPressed(): Boolean
}

abstract class ViewModelFragment<VM : FragmentViewModel> : Fragment(), Backpressable {
    protected lateinit var viewModel: VM
    private lateinit var argumentViewModel: ArgumentViewModel
    private var bundle = Bundle.EMPTY

    abstract fun buildViewModel(arguments: Bundle?): VM

    private fun buildArgumentViewModel(arguments: Bundle): ArgumentViewModel {
        val factory = ArgumentViewModelFactory(arguments)
        return ViewModelProviders.of(this, factory)[ArgumentViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        argumentViewModel = buildArgumentViewModel(bundle)
        viewModel = buildViewModel(argumentViewModel.arguments)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.onUiRecreate()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onBackPressed() = false

    fun putArguments(bundle: Bundle): ViewModelFragment<VM> {
        this.bundle = bundle
        return this
    }
}