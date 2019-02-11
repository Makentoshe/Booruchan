package com.makentoshe.booruchan

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.makentoshe.booruchan.postsample.PostSampleScreen
import com.makentoshe.booruchan.postsamples.PostSamplesScreen
import com.makentoshe.booruchan.postsamples.view.PermissionChecker
import com.makentoshe.booruchan.start.StartScreen
import com.makentoshe.repository.PostsRepository
import com.makentoshe.repository.SampleImageRepository
import com.makentoshe.repository.cache.Cache
import ru.terrakok.cicerone.Router

class AppActivity : AppCompatActivity() {
    /* Uses for navigation between screens*/
    private val navigator = Navigator(this, R.id.appcontainer)
    /* Performs requesting an checking permissions */
    val permissionChecker = PermissionChecker.Factory().simpleBuild()
    /* Shows Snackbar messages */
    val notificationController = NotificationRxController()

    private val router = Booruchan.INSTANCE.router
    private val booruList = Booruchan.INSTANCE.booruList

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(Booruchan.INSTANCE.style.id)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            router.rootStartScreen()
//            router.rootPostSamplesScreen()
//            router.rootPostSampleScreen()
//
//            Booruchan.INSTANCE.router.newRootScreen(PostPageScreen(Booruchan.INSTANCE.boorus[0], 1))
//            Booruchan.INSTANCE.router.newRootScreen(PostsScreen(Booruchan.INSTANCE.boorus[0]))
//            Booruchan.INSTANCE.router.newRootScreen(BooruScreen(Booruchan.INSTANCE.boorus[0]))
        }
        val view = findViewById<View>(R.id.appcontainer)
        Snackbar.make(view, "SAS", Snackbar.LENGTH_LONG).show()
    }

    private fun Router.rootStartScreen() {
        newRootScreen(StartScreen())
    }

    private fun Router.rootPostSampleScreen() {
        newRootScreen(
            PostSampleScreen(
                0,
                PostsRepository(Booruchan.INSTANCE.booruList[0], Cache.create(12), 12, setOf()),
                SampleImageRepository(Booruchan.INSTANCE.booruList[0], Cache.create(3))
            )
        )
    }

    private fun Router.rootPostSamplesScreen() {
        newRootScreen(
            PostSamplesScreen(
                2,
                PostsRepository(Booruchan.INSTANCE.booruList[0], Cache.create(12), 12, setOf()),
                SampleImageRepository(Booruchan.INSTANCE.booruList[0], Cache.create(3))
            )
        )
    }

    override fun onStart() {
        super.onStart()
        permissionCheckerSubscribe()
    }

    private fun permissionCheckerSubscribe() {
        permissionChecker.handlePermissionRequest {
            val status = ContextCompat.checkSelfPermission(this, it)
            if (status == PackageManager.PERMISSION_GRANTED) permissionChecker.sendPermissionResult(true)
            ActivityCompat.requestPermissions(this, arrayOf(it), PERMISSION_REQUEST_CODE_WRITE_EXTERNAL_STORAGE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //the permission request will be always for one permission at the time.
        permissionChecker.sendPermissionResult(grantResults[0] == PackageManager.PERMISSION_GRANTED)
    }

    override fun onStop() {
        super.onStop()
        permissionChecker.clear()
    }

    override fun onResume() {
        super.onResume()
        Booruchan.INSTANCE.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        Booruchan.INSTANCE.navigatorHolder.removeNavigator()
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

