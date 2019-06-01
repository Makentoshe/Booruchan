package com.makentoshe.booruchan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.makentoshe.booruchan.navigation.FragmentNavigator
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.screen.start.StartScreen
import org.koin.android.ext.android.inject
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
            router.newRootScreen(StartScreen(router))
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

}

