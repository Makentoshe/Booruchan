package com.makentoshe.booruchan.navigation

import ru.terrakok.cicerone.Router

class Router : Router() {
    fun navigateWithReplace(screen: Screen) {
        executeCommands(ForwardWithReplace(screen))
    }
}