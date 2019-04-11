package com.makentoshe.booruchan.screen.sampleinfo.model

import android.content.Context
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Post

class PostMapper {

    private val sep = ": "

    fun mapToPostId(post: Post, context: Context): String {
        val idstr = context.getString(R.string.id)
        return StringBuilder(idstr).append(sep).append(post.id).toString()
    }

    fun mapToPostScore(post: Post, context: Context): String {
        val scorestr = context.getString(R.string.score)
        return StringBuilder(scorestr).append(sep).append(post.score).toString()
    }

    fun mapToRating(post: Post, context: Context): String {
        val ratingstr = context.getString(R.string.rating)
        return StringBuilder(ratingstr).append(sep).append(post.rating.name.toLowerCase()).toString()
    }

    fun mapToSource(post: Post, context: Context): String {
        val sourceStr = context.getString(R.string.source)
        return java.lang.StringBuilder(sourceStr).append(sep).append(post.source).toString()
    }
}