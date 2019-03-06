package com.makentoshe.booruchan.postinfo

import android.content.Context
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import com.makentoshe.booruapi.Post
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.Inflater
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick
import ru.terrakok.cicerone.Router

class PostInfoInflaterListView(private val post: Post) : Inflater {

    override fun inflate(view: View) {
        view.find<ListView>(R.id.postinfo_content).apply {
            adapter = buildAdapter(view.context, post)
        }
    }

    private fun buildAdapter(context: Context, post: Post): ListAdapter {
        val idString = context.getString(R.string.id).plus(": ").plus(post.id)
        val scoreString = context.getString(R.string.score).plus(": ").plus(post.score)
        val ratingString = context.getString(R.string.rating).plus(": ").plus(post.rating)

        val array = arrayOf(idString, scoreString, ratingString)
        return ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, array)
    }
}

class PostInfoInflaterToolbarBack(private val router: Router): Inflater {
    override fun inflate(view: View) {
        view.find<View>(R.id.toolbar_back).onClick { router.exit() }
    }
}