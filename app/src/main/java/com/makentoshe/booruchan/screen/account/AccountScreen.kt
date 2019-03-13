package com.makentoshe.booruchan.screen.account

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.navigation.Screen

class AccountScreen(private val booru: Booru) : Screen() {
    override val fragment: Fragment
        get() = AccountFragment.create(booru)
}