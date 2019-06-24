package com.makentoshe.booruchan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.makentoshe.api.repository.BooruRepository
import com.makentoshe.booruchan.navigation.FragmentNavigator
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.screen.BooruFragmentNavigator
import com.makentoshe.booruchan.screen.PostsFragmentNavigator
import com.makentoshe.booruchan.screen.StartFragmentNavigator
import com.makentoshe.booruchan.screen.StartScreen
import com.makentoshe.settings.RealmSettingsBuilder
import com.makentoshe.style.OnBackFragment
import io.realm.Realm
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder

class AppActivity : AppCompatActivity() {
    /* Uses for navigation between screens*/
    private val navigator = FragmentNavigator(this, R.id.appcontainer)

    private val router: Router by inject()
    private val navigatorHolder: NavigatorHolder by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(style.main)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val postsFragmentNavigator = PostsFragmentNavigator()
            val booruFragmentNavigator = BooruFragmentNavigator(Cicerone.create(Router()), postsFragmentNavigator)
            val startFragmentNavigator = StartFragmentNavigator(router, booruFragmentNavigator)

            val startScreen = StartScreen(
                RealmSettingsBuilder(Realm.getDefaultConfiguration()!!),
                startFragmentNavigator,
                BooruRepository())
            router.newRootScreen(startScreen)
        }
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (resolveFragmentOnBackPressed(it)) return
        }

        super.onBackPressed()
    }

    private fun resolveFragmentOnBackPressed(fragment: Fragment): Boolean {
        if (fragment is OnBackFragment && fragment.onBackPressed()) return true
        else fragment.childFragmentManager.fragments.forEach {
            if (resolveFragmentOnBackPressed(it)) return true
        }
        return false
    }

}

