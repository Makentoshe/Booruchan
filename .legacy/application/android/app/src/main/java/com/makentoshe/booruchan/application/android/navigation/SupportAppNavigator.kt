package com.makentoshe.booruchan.application.android.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import ru.terrakok.cicerone.android.support.FragmentParams
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward

/** Fragment adds to top of manager instead default replacing */
class SupportAppNavigator(activity: FragmentActivity, container: Int) : SupportAppNavigator(activity, container) {

    /** Fragment forward applies screen to the top and does not hides(replaces) previous */
    override fun fragmentForward(command: Forward) {
        val screen = command.screen as SupportAppScreen

        val fragmentParams = screen.fragmentParams
        val fragment = if (fragmentParams == null) createFragment(screen) else null

        forwardFragmentInternal(command, screen, fragmentParams, fragment)
    }


    private fun forwardFragmentInternal(
        command: Command, screen: SupportAppScreen, fragmentParams: FragmentParams?, fragment: Fragment?
    ) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        setupFragmentTransaction(command, fragmentManager.findFragmentById(containerId), fragment, fragmentTransaction)
        addFragmentInternal(fragmentTransaction, screen, fragmentParams, fragment)
        fragmentTransaction.addToBackStack(screen.screenKey).commit()
        localStackCopy.add(screen.screenKey)
    }


    private fun addFragmentInternal(
        transaction: FragmentTransaction, screen: SupportAppScreen, params: FragmentParams?, fragment: Fragment?
    ) {
        if (params != null) {
            transaction.add(containerId, params.fragmentClass, params.arguments)
        } else if (fragment != null) {
            transaction.add(containerId, fragment)
        } else {
            throw IllegalArgumentException("Either 'params' or 'fragment' shouldn't be null for " + screen.screenKey)
        }
    }

}

