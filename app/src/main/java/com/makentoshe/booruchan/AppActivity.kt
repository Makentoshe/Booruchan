package com.makentoshe.booruchan

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.makentoshe.booruchan.posts.model.PostsRepository
import com.makentoshe.repository.cache.CacheImpl

class AppActivity : AppCompatActivity() {

    private val navigator = Navigator(this, R.id.appcontainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(Booruchan.INSTANCE.style.id)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            Booruchan.INSTANCE.router.newRootScreen(StartScreen())
            Booruchan.INSTANCE.router.newRootScreen(
                PostSamplesScreen(
                    Booruchan.INSTANCE.booruList[0],
                    3,
                    PostsRepository(Booruchan.INSTANCE.booruList[0], CacheImpl(12), 1, setOf())
                )
            )
//            Booruchan.INSTANCE.router.newRootScreen(PostPageScreen(Booruchan.INSTANCE.boorus[0], 1))
//            Booruchan.INSTANCE.router.newRootScreen(PostsScreen(Booruchan.INSTANCE.boorus[0]))
//            Booruchan.INSTANCE.router.newRootScreen(BooruScreen(Booruchan.INSTANCE.boorus[0]))
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

    override fun onBackPressed() {
        val list = supportFragmentManager.fragments
        for (i in list.lastIndex downTo 0) {
            val fragment = list[i]
            if (fragment is BackPressableFragment && fragment.onBackPressed()) return
        }
        super.onBackPressed()
    }
}