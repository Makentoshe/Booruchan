package com.makentoshe.booruchan.postsamples

import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruchan.postsamples.model.NavigationController
import com.makentoshe.viewmodel.ViewModel
import ru.terrakok.cicerone.Router

class NavigationControllerImpl(private val router: Router) : NavigationController {

    override fun exit() = router.exit()

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

        return true
    }

}