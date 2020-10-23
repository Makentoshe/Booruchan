package com.makentoshe.booruchan.application.android.start.di

import JsonDanbooruContext
import XmlGelbooruContext
import context.BooruContext
import toothpick.config.Module
import toothpick.ktp.binding.bind

class StartModule: Module() {

    private val booruContexts = listOf<BooruContext>(XmlGelbooruContext(), JsonDanbooruContext())

    init {
        bind<List<BooruContext>>().toInstance(booruContexts)
    }
}