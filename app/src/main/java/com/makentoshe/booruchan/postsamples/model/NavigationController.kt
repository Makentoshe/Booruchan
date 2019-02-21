package com.makentoshe.booruchan.postsamples.model

import android.view.MenuItem

interface NavigationController {
    /**
     * Returns to the previous screen. Same as onBackButton press.
     */
    fun exit()

    /**
     * @param menuItem is a currently selected menu item
     * @return true is click was handled of false otherwise.
     */
    fun onNavigationItemSelected(menuItem: MenuItem): Boolean
}