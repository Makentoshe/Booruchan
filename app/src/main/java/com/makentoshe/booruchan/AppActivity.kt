package com.makentoshe.booruchan

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.makentoshe.booruchan.postsamples.view.PermissionChecker
import com.makentoshe.booruchan.start.StartScreen
import ru.terrakok.cicerone.Router

class AppActivity : AppCompatActivity() {
    /* Uses for navigation between screens*/
    private val navigator = Navigator(this, R.id.appcontainer)
    /* Performs requesting an checking permissions */
    val permissionChecker = PermissionChecker.Factory().simpleBuild()
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
//    private fun Router.rootPostSamplesScreen() {
//        newRootScreen(
//            PostSamplesScreen(
//                2,
//                PostsRepository(Booruchan.INSTANCE.booruList[0], Cache.create(12), 12, setOf()),
//                SampleImageRepository(Booruchan.INSTANCE.booruList[0], Cache.create(3))
//            )
//        )
//    }
//
    private fun permissionCheckerSubscribe() {
        permissionChecker.handlePermissionRequest {
            val status = ContextCompat.checkSelfPermission(this, it)
            if (status == PackageManager.PERMISSION_GRANTED) permissionChecker.sendPermissionResult(true)
            ActivityCompat.requestPermissions(this, arrayOf(it), PERMISSION_REQUEST_CODE_WRITE_EXTERNAL_STORAGE)
        }
    }

    private fun notificationSubscribe() {
        innerNotificationRxController.subscribe {
            println("Sas")
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //the permission request will be always for one permission at the time.
        permissionChecker.sendPermissionResult(grantResults[0] == PackageManager.PERMISSION_GRANTED)
    }

    override fun onResume() {
        super.onResume()
        notificationSubscribe()
        permissionCheckerSubscribe()
        Booruchan.INSTANCE.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        permissionChecker.clear()
        innerNotificationRxController.clear()
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

