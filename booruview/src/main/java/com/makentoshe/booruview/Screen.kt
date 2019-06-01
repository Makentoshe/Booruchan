package com.makentoshe.booruview

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.Screen

abstract class Screen: Screen() {
    abstract val fragment: Fragment
}
