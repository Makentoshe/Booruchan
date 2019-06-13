package com.makentoshe.boorupostview

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.fragment.app.Fragment
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.boorupostview.listener.NewSearchStartedListener

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
    }
}

