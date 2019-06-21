package com.makentoshe.boorupostview

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.fragment.app.FragmentActivity
import com.makentoshe.boorupostview.listener.OnPostSelectListener

/** Broadcast receives a clicked post index */
class PostSelectBroadcastReceiver : BroadcastReceiver(), OnPostSelectListener {

    /** On post select listener instance */
    private var listener: ((Int) -> Unit)? = null

    /** Setups a click event action */
    override fun onSelect(action: (Int) -> Unit) {
        listener = action
    }

    /** Performs data extraction from the intent */
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null || !intent.hasExtra(ACTION)) return
        val position = intent.getIntExtra(ACTION, 0)
        listener?.invoke(position)
    }

    companion object {

        /** Intent action for event */
        private val ACTION = PostSelectBroadcastReceiver::class.java.simpleName

        /** Sends broadcast as with selected tags as a new search event */
        fun sendBroadcast(context: Context, position: Int) =
            context.sendBroadcast(Intent(ACTION).putExtra(ACTION, position))

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