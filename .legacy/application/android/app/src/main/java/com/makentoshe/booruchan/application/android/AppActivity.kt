package com.makentoshe.booruchan.application.android

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import com.makentoshe.booruchan.application.android.navigation.SupportAppNavigator
import com.makentoshe.booruchan.application.android.screen.start.navigation.StartScreen
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import toothpick.ktp.delegate.inject

class AppActivity : AppCompatActivity() {

    private val navigator = SupportAppNavigator(this, R.id.app_activity_container)
    private val navigatorHolder by inject<NavigatorHolder>()
    private val router by inject<Router>()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)

        if (savedInstanceState != null) return

        when (intent.action) {
            Intent.ACTION_MAIN -> intentMainAction()
        }
    }

    private fun intentMainAction() {
        router.newRootScreen(StartScreen())
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    /** Empty click listener for interrupting touch events between fragments */
    fun emptyClickListener(view: View) = Unit
}
