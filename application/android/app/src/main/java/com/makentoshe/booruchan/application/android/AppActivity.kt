package com.makentoshe.booruchan.application.android

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.makentoshe.booruchan.application.android.start.StartScreen
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import toothpick.ktp.delegate.inject

class AppActivity : AppCompatActivity() {

    private val navigator = SupportAppNavigator(this, R.id.app_activity_container)
    private val navigatorHolder by inject<NavigatorHolder>()
    private val router by inject<Router>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)

        if (savedInstanceState != null) return

        when (intent.action) {
            Intent.ACTION_MAIN -> intentMainAction()
        }
    }

    private fun intentMainAction() {
        Toast.makeText(this, "SAS", Toast.LENGTH_LONG).show()
        val screen = StartScreen()
        router.newRootScreen(screen)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }
}
