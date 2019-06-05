package com.makentoshe.navigation

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.Screen

abstract class Screen: Screen() {
    abstract val fragment: Fragment
}

