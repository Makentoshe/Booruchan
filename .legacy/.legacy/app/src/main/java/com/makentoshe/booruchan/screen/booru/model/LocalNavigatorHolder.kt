package com.makentoshe.booruchan.screen.booru.model

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruchan.navigation.FragmentNavigator

class LocalNavigatorHolder(private val navigator: LocalNavigator) : ViewModel(), LocalNavigator {

    override fun setNavigator(navigator: FragmentNavigator) {
        this.navigator.setNavigator(navigator)
    }

    override fun removeNavigator() {
        navigator.removeNavigator()
    }

    override fun navigateToPosts() {
        navigator.navigateToPosts()
    }

    override fun navigateToAccount() {
        navigator.navigateToAccount()
    }

    class Factory(private val navigator: LocalNavigator) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = LocalNavigatorHolder(navigator)
            viewModel.navigator.navigateToPosts()
            return viewModel as T
        }
    }

    companion object {
        fun create(fragment: Fragment, navigator: LocalNavigator): LocalNavigator {
            val factory = LocalNavigatorHolder.Factory(navigator)
            return ViewModelProviders.of(fragment, factory)[LocalNavigatorHolder::class.java]
        }
    }
}