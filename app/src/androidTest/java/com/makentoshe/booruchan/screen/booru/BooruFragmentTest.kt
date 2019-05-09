package com.makentoshe.booruchan.screen.booru

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.TestActivity
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.appModule
import com.makentoshe.booruchan.navigation.FragmentNavigator
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.screen.account.AccountFragment
import com.makentoshe.booruchan.screen.booru.model.LocalNavigator
import com.makentoshe.booruchan.screen.posts.container.PostsFragment
import com.makentoshe.booruchan.screen.posts.container.PostsModule
import com.makentoshe.booruchan.screen.posts.page.PostsPageModule
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone

class BooruFragmentTest {

    @get:Rule
    val rule = ActivityTestRule<TestActivity>(TestActivity::class.java, false, false)
    private val instrumentation = InstrumentationRegistry.getInstrumentation()

    private lateinit var activity: TestActivity
    private lateinit var fragment: BooruFragment
    private lateinit var localNavigator: LocalNavigator

    private val booru = mockk<Booru>().apply {
        every { title } returns "Testbooru"
        every { nsfw } returns false
    }

    @Before
    fun init() {
        stopKoin()
        startKoin {
            androidContext(instrumentation.context)
            modules(appModule, PostsModule.module, PostsPageModule.module, module {

                factory { (fa: FragmentActivity, fm: FragmentManager) ->
                    FragmentNavigator(fa, R.id.booru_drawer_content, fm)
                }

                viewModel { (b: Booru, t: Set<Tag>) ->
                    val router = Router()
                    val localRouter = LocalRouter(b, t, router)
                    val cicerone = Cicerone.create(router)
                    spyk(LocalNavigatorViewModel(cicerone, localRouter)).also {
                        localNavigator = it
                    }
                }
            })
        }

        activity = rule.launchActivity(null)
        instrumentation.runOnMainSync {
            fragment = BooruFragment.create(booru, setOf())
            activity.supportFragmentManager.beginTransaction().add(R.id.appcontainer, fragment).commitNow()
        }
    }

    @Test
    fun shouldNavigateToPostsScreenAtStartup() {
        val f = fragment.childFragmentManager.fragments[0]
        assertEquals(PostsFragment::class, f::class)
    }

    @Test
    fun shouldNavigateToAccountScreen() {
        instrumentation.runOnMainSync {
            localNavigator.navigateToAccount()
        }
        Thread.sleep(1000)
        val f = fragment.childFragmentManager.fragments[0]
        assertEquals(AccountFragment::class, f::class)
    }

    @Test
    fun shouldNavigateToPostsScreen() {
        instrumentation.runOnMainSync {
            localNavigator.navigateToAccount()
        }
        Thread.sleep(1000)
        val fa = fragment.childFragmentManager.fragments[0]
        assertEquals(AccountFragment::class, fa::class)

        instrumentation.runOnMainSync {
            localNavigator.navigateToPosts()
        }
        Thread.sleep(1000)
        val fb = fragment.childFragmentManager.fragments[0]
        assertEquals(PostsFragment::class, fb::class)
    }
}