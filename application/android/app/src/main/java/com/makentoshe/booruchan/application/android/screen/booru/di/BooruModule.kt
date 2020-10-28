package com.makentoshe.booruchan.application.android.screen.booru.di

import com.makentoshe.booruchan.application.android.di.ApplicationScope
import com.makentoshe.booruchan.application.android.screen.booru.BooruFragment
import com.makentoshe.booruchan.application.android.screen.booru.navigation.BooruNavigation
import com.makentoshe.booruchan.core.context.BooruContext
import toothpick.Toothpick
import toothpick.config.Module
import toothpick.ktp.binding.bind
import toothpick.ktp.delegate.inject

class BooruModule(fragment: BooruFragment) : Module() {

    private val booruContexts by inject<List<BooruContext>>()

    init {
        Toothpick.openScope(ApplicationScope::class).inject(this)

        val booruContext = booruContexts.first { it.title == fragment.arguments.booruContextTitle }
        bind<BooruContext>().toInstance(booruContext)

        val navigation = BooruNavigation(fragment.childFragmentManager, booruContext)
        bind<BooruNavigation>().toInstance(navigation)
    }
}