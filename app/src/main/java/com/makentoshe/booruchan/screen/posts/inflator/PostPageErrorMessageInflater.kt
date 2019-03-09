package com.makentoshe.booruchan.screen.posts.inflator

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.makentoshe.booruchan.Inflater
import com.makentoshe.booruchan.R
import org.jetbrains.anko.find

class PostPageErrorMessageInflater(
    private val throwable: Throwable
) : Inflater {
    override fun inflate(view: View) {
        val progress = view.find<ProgressBar>(R.id.posts_page_progress)
        progress.visibility = View.GONE

        val message = view.find<TextView>(R.id.posts_page_textview)
        message.text = throwable.localizedMessage
    }
}