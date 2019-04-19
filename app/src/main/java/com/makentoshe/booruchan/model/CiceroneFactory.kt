package com.makentoshe.booruchan.model

import com.makentoshe.booruchan.navigation.Router
import ru.terrakok.cicerone.Cicerone

class CiceroneFactory {
    fun build(router: Router): Cicerone<Router> {
        return Cicerone.create(router)
    }

    fun build() = Cicerone.create()!!
}