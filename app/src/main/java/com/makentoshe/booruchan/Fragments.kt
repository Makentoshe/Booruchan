package com.makentoshe.booruchan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.viewmodel.ViewModel

interface Backpressable {
    fun onBackPressed(): Boolean
}


abstract class Fragment<VM : ViewModel> : Fragment(), Backpressable {
    protected lateinit var viewModel: VM

    abstract fun buildViewModel(arguments: Bundle): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        this.viewModel = buildViewModel(arguments ?: Bundle.EMPTY)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.onCreateView(this)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onBackPressed() = false

}