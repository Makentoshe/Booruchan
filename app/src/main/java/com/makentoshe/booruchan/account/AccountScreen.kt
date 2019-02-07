package com.makentoshe.booruchan.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.FragmentScreen
import com.makentoshe.booruchan.booru.model.DrawerController

class AccountScreen(
    private val booru: Booru,
    private val drawerController: DrawerController
) : FragmentScreen() {
    override val fragment: Fragment
        get() = AccountFragment().apply {
            arguments = Bundle().apply {
                putSerializable(Booru::class.java.simpleName, booru)
                putSerializable(DrawerController::class.java.simpleName, drawerController)
            }
        }
}