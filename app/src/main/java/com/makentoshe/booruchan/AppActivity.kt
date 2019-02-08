package com.makentoshe.booruchan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.makentoshe.booruchan.postsamples.PostSamplesScreen
import com.makentoshe.booruchan.start.StartScreen
import com.makentoshe.repository.PostsRepository
import com.makentoshe.repository.SampleImageRepository
import com.makentoshe.repository.cache.Cache

class AppActivity : AppCompatActivity() {

    private val navigator = Navigator(this, R.id.appcontainer)
    private val router = Booruchan.INSTANCE.router
    private val booruList = Booruchan.INSTANCE.booruList
    val requestPermissionController = RequestPermissionController()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(Booruchan.INSTANCE.style.id)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            router.newRootScreen(StartScreen())
//            Booruchan.INSTANCE.router.newRootScreen(
//                PostSamplesScreen(
//                    Booruchan.INSTANCE.booruList[0],
//                    2,
//                    PostsRepository(Booruchan.INSTANCE.booruList[0], Cache.create(12), 1, setOf()),
//                    SampleImageRepository(Booruchan.INSTANCE.booruList[0], Cache.create(3))
//                )
//            )
//            Booruchan.INSTANCE.router.newRootScreen(PostPageScreen(Booruchan.INSTANCE.boorus[0], 1))
//            Booruchan.INSTANCE.router.newRootScreen(PostsScreen(Booruchan.INSTANCE.boorus[0]))
//            Booruchan.INSTANCE.router.newRootScreen(BooruScreen(Booruchan.INSTANCE.boorus[0]))
        }
    }

    override fun onResume() {
        super.onResume()
        Booruchan.INSTANCE.navigatorHolder.setNavigator(navigator)
        requestPermissionController.subscribe {
            ActivityCompat.requestPermissions(this, arrayOf(it), PERMISSION_REQUEST_CODE_WRITE_EXTERNAL_STORAGE)
        }
    }

    override fun onPause() {
        super.onPause()
        Booruchan.INSTANCE.navigatorHolder.removeNavigator()
        requestPermissionController.clear()
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