package com.makentoshe.booruchan

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.makentoshe.booruchan.postsample.PostSampleScreen
import com.makentoshe.booruchan.postsamples.PostSamplesScreen
import com.makentoshe.booruchan.postsamples.view.PermissionChecker
import com.makentoshe.booruchan.start.StartScreen
import com.makentoshe.repository.PostsRepository
import com.makentoshe.repository.SampleImageRepository
import com.makentoshe.repository.cache.Cache
import ru.terrakok.cicerone.Router

class AppActivity : AppCompatActivity() {

    private val navigator = Navigator(this, R.id.appcontainer)
    private val router = Booruchan.INSTANCE.router
    private val booruList = Booruchan.INSTANCE.booruList
    val permissionChecker = PermissionChecker.Factory().simpleBuild()

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
    }

    private fun Router.rootStartScreen() {
        newRootScreen(StartScreen())
    }

    private fun Router.rootPostSampleScreen() {
        newRootScreen(PostSampleScreen(0,
            PostsRepository(Booruchan.INSTANCE.booruList[0], Cache.create(12), 12, setOf()),
            SampleImageRepository(Booruchan.INSTANCE.booruList[0], Cache.create(3))
        ))
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
        permissionChecker.handlePermissionRequest {
            val status = ContextCompat.checkSelfPermission(this, it)
            if (status == PackageManager.PERMISSION_GRANTED) permissionChecker.sendPermissionResult(true)

            ActivityCompat.requestPermissions(this, arrayOf(it), it.hashCode())
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        var result = true
        grantResults.forEach {
            if (it == PackageManager.PERMISSION_DENIED) result = false
        }

        permissionChecker.sendPermissionResult(result)

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