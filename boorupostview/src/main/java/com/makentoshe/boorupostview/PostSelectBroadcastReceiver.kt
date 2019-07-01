package com.makentoshe.boorupostview

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.fragment.app.FragmentActivity
import com.makentoshe.boorulibrary.entitiy.Post
import com.makentoshe.boorupostview.listener.OnPostSelectListener

/** Broadcast receives a clicked post index */
class PostSelectBroadcastReceiver : BroadcastReceiver(), OnPostSelectListener {

    /** On post select listener instance */
    private var listener: ((Int, Post) -> Unit)? = null

    /** Setups a click event action */
    override fun onSelect(action: (Int, Post) -> Unit) {
        listener = action
    }

    /** Performs data extraction from the intent */
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null || !intent.hasExtra(ACTION)) return
        val position = intent.getIntExtra(ACTION, 0)
        val post = intent.getSerializableExtra(POST) as Post
        listener?.invoke(position, post)
    }

    companion object {

        /** Intent actions for event */
        private val ACTION = PostSelectBroadcastReceiver::class.java.simpleName
        private val POST = Post::class.java.simpleName

        /** Sends broadcast as with selected position and a post as a new post select event */
        fun sendBroadcast(context: Context, position: Int, post: Post) =
            context.sendBroadcast(Intent(ACTION).putExtra(ACTION, position).putExtra(POST, post))

        /** Registers or create and registers a new broadcast receiver */
        fun registerReceiver(
            activity: FragmentActivity, receiver: PostSelectBroadcastReceiver? = null
        ): PostSelectBroadcastReceiver {
            val receiver = receiver ?: PostSelectBroadcastReceiver()
            activity.registerReceiver(receiver, IntentFilter(ACTION))
            return receiver
        }

    }
}