package com.makentoshe.booruchan

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.makentoshe.booruchan.postsamples.PostSamplesScreen
import com.makentoshe.booruchan.postsamples.model.PermissionCheckerImpl
import com.makentoshe.booruchan.start.StartScreen
import ru.terrakok.cicerone.Router

class AppActivity : AppCompatActivity() {
    /* Uses for navigation between screens*/
    private val navigator = Navigator(this, R.id.appcontainer)
     /* Shows Snackbar messages */
    private val innerNotificationRxController = SnackbarNotificationRxController()
    /* Special interface with single method for set notification to the controller */
    val snackbarNotificationController: NotificationInterface = innerNotificationRxController

    private val router = Booruchan.INSTANCE.router
    private val booruList = Booruchan.INSTANCE.booruList

    private val root: View by lazy { findViewById<View>(R.id.appcontainer) }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(Booruchan.INSTANCE.style.id)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            router.rootStartScreen()
//            router.rootPostSamplesScreen()
//            router.rootPostSampleScreen()

//            Booruchan.INSTANCE.router.newRootScreen(PostPageScreen(Booruchan.INSTANCE.boorus[0], 1))
//            Booruchan.INSTANCE.router.newRootScreen(PostsScreen(Booruchan.INSTANCE.boorus[0]))
//            Booruchan.INSTANCE.router.newRootScreen(BooruScreen(Booruchan.INSTANCE.boorus[0]))
        }
    }

    private fun Router.rootStartScreen() {
        newRootScreen(StartScreen())
    }

    //    private fun Router.rootPostSampleScreen() {
//        newRootScreen(
//            PostSampleScreen(
//                0,
//                PostsRepository(Booruchan.INSTANCE.booruList[0], Cache.create(12), 12, setOf()),
//                SampleImageRepository(Booruchan.INSTANCE.booruList[0], Cache.create(3))
//            )
//        )
//    }
//
    private fun Router.rootPostSamplesScreen() {
        newRootScreen(PostSamplesScreen(Booruchan.INSTANCE.booruList[0], setOf(), 2))
    }

    private fun notificationSubscribe() {
        innerNotificationRxController.subscribe {
            val duration = if (it.duration < -2) Snackbar.LENGTH_LONG else it.duration
            val snackbar = Snackbar.make(root, it.message, duration)
            val action = it.action
            if (action != null) {
                snackbar.setAction(action.title) {
                    action.listener(snackbar)
                }
            }
            snackbar.show()
        }
    }

    override fun onResume() {
        super.onResume()
        Booruchan.INSTANCE.navigatorHolder.setNavigator(navigator)
    }

    override fun onStart() {
        super.onStart()
        notificationSubscribe()
    }

    override fun onStop() {
        super.onStop()
        innerNotificationRxController.clear()
    }

    override fun onPause() {
        super.onPause()
        Booruchan.INSTANCE.navigatorHolder.removeNavigator()
    }

    // mb useless
    override fun onLowMemory() {
        super.onLowMemory()
        //clear previews cache
        PreviewsInternalCache<Any>(this, "previews").clear()
        //clear posts cache
        PostInternalCache(this, "posts").clear()
    }

//    override fun onBackPressed() {
//        val fragment = supportFragmentManager.fragments.last()
//
//        if (fragment is Backpressable && fragment.onBackPressed()) return
//
//        super.onBackPressed()
//    }

    companion object {
        private val PERMISSION_REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 0
    }
}

