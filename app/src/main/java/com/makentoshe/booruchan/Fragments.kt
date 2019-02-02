package com.makentoshe.booruchan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

interface Backpressable {
    fun onBackPressed(): Boolean
}

abstract class ViewModelFragment<VM : FragmentViewModel> : Fragment(), Backpressable {
    abstract fun buildViewModel(arguments: Bundle?): VM

    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = buildViewModel(arguments)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.onUiRecreate()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onBackPressed() = false

    fun putArguments(bundle: Bundle): ViewModelFragment<VM> {
        arguments = bundle
        return this
    }
}