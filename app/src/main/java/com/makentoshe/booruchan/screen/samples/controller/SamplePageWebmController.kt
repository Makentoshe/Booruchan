package com.makentoshe.booruchan.screen.samples.controller

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.screen.settings.AppSettings
import com.makentoshe.booruchan.screen.webmplayer.WebmPlayerScreen
import org.jetbrains.anko.find
import org.koin.core.KoinComponent
import org.koin.core.get

class SamplePageWebmController(
    private val booru: Booru,
    private val post: Post,
    private val fragmentManager: FragmentManager
) : KoinComponent {

    fun bindView(view: View) {
        if (AppSettings.getWebmPlayingOnPlace(get())) {
            bindViewToCurrentScreen(view)
        } else {
            bindViewToNewScreen(view)
        }
    }

    private fun bindViewToCurrentScreen(view: View) {
        fragmentManager.beginTransaction()
            .add(R.id.samples_webm_container, WebmPlayerScreen(booru, post).fragment)
            .commit()
        view.find<View>(R.id.samples_progress).visibility = View.GONE
        view.find<ImageView>(R.id.samples_preview).imageAlpha = 0
    }

    private fun bindViewToNewScreen(view: View) {
        view.find<View>(R.id.samples_progress).visibility = View.GONE
        val messageview = view.find<TextView>(R.id.samples_message)
        messageview.setText(R.string.tap_to_play)
        messageview.visibility = View.VISIBLE
        messageview.bringToFront()
        view.setOnClickListener {
            val router = get<Router>()
            router.navigateTo(WebmPlayerScreen(booru, post))
        }
    }
}