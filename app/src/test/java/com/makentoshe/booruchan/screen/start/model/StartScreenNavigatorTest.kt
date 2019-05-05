package com.makentoshe.booruchan.screen.start.model

import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.navigation.Router
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject

class StartScreenNavigatorTest : AutoCloseKoinTest() {

    private val startScreenNavigator by inject<StartScreenNavigator>()
    private lateinit var router: Router

    @Before
    fun init() {
        router = spyk(Router())
        startKoin {
            modules(module {
                factory { HashSet<Tag>() }
                factory { router }
                factory { StartScreenNavigator(get<HashSet<Tag>>()) }
            })
        }
    }

    @Test
    fun shouldNavigateWithTheReplaceToTheSettingsScreen() {
        startScreenNavigator.navigateToSettingsScreen()
        verify { router.navigateWithReplace(any()) }
    }

    @Test
    fun shouldNavigateToTheBooruScreen() {
        val booru = mockk<Booru>()
        startScreenNavigator.navigateToBooruScreen(booru)
        verify { router.navigateTo(any()) }
    }

}