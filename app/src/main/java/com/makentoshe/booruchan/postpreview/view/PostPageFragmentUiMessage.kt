package com.makentoshe.booruchan.postpreview.view

import android.view.Gravity
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.makentoshe.booruapi.Posts
import com.makentoshe.booruchan.DownloadResult
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postpreview.PostPageFragmentViewModel
import org.jetbrains.anko.*
import java.lang.StringBuilder

class PostPageFragmentUiMessage(private val viewModel: PostPageFragmentViewModel) :
    AnkoComponent<_RelativeLayout> {
    override fun createView(ui: AnkoContext<_RelativeLayout>) = with(ui.owner) {
        frameLayout {

            setOnClickListener { onClick(ui.owner) }

            textView {
                id = R.id.postpreviewpage_messageview
                visibility = View.GONE
                gravity = Gravity.CENTER

                viewModel.addOnPostsReceiveListener { onPostsReceive(it) }
            }
        }.lparams(matchParent, matchParent) { centerInParent() }
    }

    private fun TextView.onPostsReceive(result: DownloadResult<Posts>) {
        if (result.data == null) {
            visibility = View.VISIBLE

            val errorMessage = context.getString(R.string.posts_download_error)
            val tapMessage = context.getString(R.string.tap_for_retry)

            val sb = StringBuilder(errorMessage).append("\n").append(result.exception).append("\n").append(tapMessage)
            text = sb.toString()
        }
    }

    private fun onClick(view: View) {
        val progressbar = view.findViewById<ProgressBar>(R.id.postpreviewpage_progressbar)
        progressbar.visibility = View.VISIBLE

        val messageview = view.findViewById<TextView>(R.id.postpreviewpage_messageview)
        messageview.visibility = View.GONE

        viewModel.loadPosts()
    }
}