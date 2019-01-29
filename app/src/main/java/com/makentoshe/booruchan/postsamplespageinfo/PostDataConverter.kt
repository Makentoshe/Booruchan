package com.makentoshe.booruchan.postsamplespageinfo

import android.content.Context
import com.makentoshe.booruapi.Post
import com.makentoshe.booruchan.R
import java.lang.StringBuilder

class PostDataConverter(private val post: Post) {

    fun getId(context: Context): String {
        return buildData(context, ID).append(post.id).toString()
    }

    fun getScore(context: Context): String {
        return buildData(context, SCORE).append(post.score).toString()
    }

    fun getRating(context: Context): String {
        return buildData(context, RATING).append(post.rating).toString()
    }

    private fun getTitles(context: Context): Array<String> {
        return context.resources.getStringArray(R.array.post_properties)
    }

    private fun buildData(context: Context, param: Int): StringBuilder {
        val title = getTitles(context)[param]
        return StringBuilder(title).append(": ")
    }

    companion object {
        private val ID = 0
        private val POSTED_TIME = 1
        private val POSTED_USER = 2
        private val SIZE = 3
        private val SOURCE = 4
        private val RATING = 5
        private val SCORE = 6
    }
}