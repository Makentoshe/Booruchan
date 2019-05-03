package com.makentoshe.booruchan.screen.sampleinfo.model

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import com.makentoshe.booruchan.api.component.post.Post

class SampleInfoInfoSourceLongClick(private val post: Post) : AdapterView.OnItemLongClickListener {

    override fun onItemLongClick(parent: AdapterView<*>, view: View, position: Int, id: Long): Boolean {
        val data = parent.getItemAtPosition(position) as String

        if (isSource(post, data)) return startBrowser(view.context, post.source)

        return false
    }

    private fun isSource(post: Post, item: String): Boolean {
        return item.contains(post.source) && post.source.isNotBlank()
    }

    private fun startBrowser(context: Context, url: String): Boolean {
        val intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(url))
        ContextCompat.startActivity(context, intent, null)
        return true
    }
}