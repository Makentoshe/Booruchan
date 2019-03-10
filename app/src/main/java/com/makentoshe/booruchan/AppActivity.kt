package com.makentoshe.booruchan

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.makentoshe.booruchan.navigation.FragmentNavigator
import com.makentoshe.booruchan.repository.cache.PostInternalCache
import com.makentoshe.booruchan.screen.start.StartScreen
import ru.terrakok.cicerone.Router

class AppActivity : AppCompatActivity() {
    /* Uses for navigation between screens*/
    private val navigator = FragmentNavigator(this, R.id.appcontainer)

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

    override fun onResume() {
        super.onResume()
        Booruchan.INSTANCE.navigatorHolder.setNavigator(navigator)
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
        PostInternalCache(this).clear()
    }

//    override fun onBackPressed() {
//        val fragment = supportFragmentManager.fragments.last()
//
//        if (fragment is Backpressable && fragment.onBackPressed()) return
//
//        super.onBackPressed()
//    }

}

