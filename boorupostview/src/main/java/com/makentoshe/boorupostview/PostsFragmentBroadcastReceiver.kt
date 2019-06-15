package com.makentoshe.boorupostview

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.boorupostview.listener.NewSearchStartedListener
import java.io.Serializable

/**
 * Broadcast receiver for a new search event.
 */
class PostsFragmentBroadcastReceiver : BroadcastReceiver(), NewSearchStartedListener {

    private var listener: ((Set<Tag>) -> Unit)? = null

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.hasExtra(START_NEW_SEARCH)) {
            val tags = intent.getSerializableExtra(START_NEW_SEARCH) as Set<Tag>
            listener?.invoke(tags)
        }
    }

    override fun onNewSearchStarted(action: (Set<Tag>) -> Unit) {
        listener = action
    }

    companion object {
        internal const val START_NEW_SEARCH = "NewSearchStarted"

        fun sendBroadcast(context: Context, tags: Set<Tag>) {
            val intent = Intent(START_NEW_SEARCH)
            intent.putExtra(START_NEW_SEARCH, tags as Serializable)
            context.sendBroadcast(intent)
        }

        fun registerReceiver(
            activity: FragmentActivity, receiver: PostsFragmentBroadcastReceiver? = null
        ): PostsFragmentBroadcastReceiver {
            val receiver = receiver ?: PostsFragmentBroadcastReceiver()
            val filter = IntentFilter(START_NEW_SEARCH)
            activity.registerReceiver(receiver, filter)
            return receiver
        }

    }
}

