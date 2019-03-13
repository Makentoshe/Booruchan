package com.makentoshe.booruchan.screen.posts

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

class FragmentDoOnCreateOnce : ViewModel() {

    private open class Factory(
        private val action: () -> Unit
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = FragmentDoOnCreateOnce()
            action()
            return viewModel as T
        }
    }

    companion object {
        fun create(fragment: Fragment, action: () -> Unit): FragmentDoOnCreateOnce {
            val factory = Factory(action)
            return ViewModelProviders.of(
                fragment,
                factory
            )[FragmentDoOnCreateOnce::class.java]
        }
    }
}