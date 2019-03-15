package com.makentoshe.booruchan

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.makentoshe.booruchan.navigation.FragmentNavigator
import com.makentoshe.booruchan.repository.cache.PostInternalCache
import com.makentoshe.booruchan.screen.start.StartScreen
import com.makentoshe.booruchan.style.style
import ru.terrakok.cicerone.Router

class AppActivity : AppCompatActivity() {
    /* Uses for navigation between screens*/
    private val navigator = FragmentNavigator(this, R.id.appcontainer)
    private val router = Booruchan.INSTANCE.router

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(style.main)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            router.newRootScreen(StartScreen())
        }
    }

    override fun onResume() {
        super.onResume()
        Booruchan.INSTANCE.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        Booruchan.INSTANCE.navigatorHolder.removeNavigator()
    }

}

