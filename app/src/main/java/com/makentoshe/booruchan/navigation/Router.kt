package com.makentoshe.booruchan.navigation

import ru.terrakok.cicerone.Router
import java.io.Serializable

class Router : Router(), Serializable {
    fun navigateWithReplace(screen: Screen) {
        executeCommands(ForwardWithReplace(screen))
    }
}