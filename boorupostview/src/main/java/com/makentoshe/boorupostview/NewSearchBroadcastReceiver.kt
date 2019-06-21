package com.makentoshe.boorupostview

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.fragment.app.FragmentActivity
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.boorupostview.listener.NewSearchStartedListener
import java.io.Serializable

/**
 * Broadcast receiver for a search event.
 */
class NewSearchBroadcastReceiver : BroadcastReceiver(), NewSearchStartedListener {

    /** On search event listener */
    private var listener: ((Set<Tag>) -> Unit)? = null

    /** Performs a data extraction from the intent */
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.hasExtra(ACTION)) {
            val tags = intent.getSerializableExtra(ACTION) as Set<Tag>
            listener?.invoke(tags)
        }
    }

    /** Setups a search event action */
    override fun onNewSearchStarted(action: (Set<Tag>) -> Unit) {
        listener = action
    }

    companion object {

        /** Intent action for event */
        private const val ACTION = "NewSearchStarted"

        /** Sends broadcast as with selected tags as a new search event */
        fun sendBroadcast(context: Context, tags: Set<Tag>) {
            val intent = Intent(ACTION)
            intent.putExtra(ACTION, tags as Serializable)
            context.sendBroadcast(intent)
        }

        /** Registers or create and registers a new broadcast receiver */
        fun registerReceiver(
            activity: FragmentActivity, receiver: NewSearchBroadcastReceiver? = null
        ): NewSearchBroadcastReceiver {
            val receiver = receiver ?: NewSearchBroadcastReceiver()
            val filter = IntentFilter(ACTION)
            activity.registerReceiver(receiver, filter)
            return receiver
        }

    }
}

