package com.makentoshe.booruchan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.makentoshe.booruchan.navigation.FragmentNavigator
import com.makentoshe.booruchan.screen.start.StartScreen

class AppActivity : AppCompatActivity() {
    /* Uses for navigation between screens*/
    private val navigator = FragmentNavigator(this, R.id.appcontainer)

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

