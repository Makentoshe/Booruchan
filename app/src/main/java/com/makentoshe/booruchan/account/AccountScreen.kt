package com.makentoshe.booruchan.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.FragmentScreen
import com.makentoshe.booruchan.booru.model.DrawerController

class AccountScreen(private val data: Arguments) : FragmentScreen() {
    override val fragment: Fragment
        get() = AccountFragment().apply {
            arguments = Bundle().apply {
                putSerializable(Booru::class.java.simpleName, data.booru)
                putSerializable(DrawerController::class.java.simpleName, data.drawerController)
            }
        }

    data class Arguments(val booru: Booru, val drawerController: DrawerController)
}