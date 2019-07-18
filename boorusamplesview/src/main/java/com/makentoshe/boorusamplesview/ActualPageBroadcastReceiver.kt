package com.makentoshe.boorusamplesview

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.fragment.app.FragmentActivity
import com.makentoshe.boorusamplesview.listener.ActualPageListener

class ActualPageBroadcastReceiver : BroadcastReceiver(), ActualPageListener {

    private var listener: ((Int) -> Unit)? = null

    override fun setOnActualPageChangeListener(action: (Int) -> Unit) {
        listener = action
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null || !intent.hasExtra(ACTION)) return
        val page = intent.getIntExtra(ACTION, -1)
        if (page == -1) return else onActualPageChange(page)
    }

    private fun onActualPageChange(page: Int) {
        listener?.invoke(page)
    }

    companion object {
        private const val ACTION = "ActualPageAction"

        /** Sends broadcast with selected page */
        fun sendBroadcast(context: Context, page: Int) =
            context.sendBroadcast(Intent(ACTION).putExtra(ACTION, page))

        /** Registers or create and registers a new broadcast receiver */
        fun registerReceiver(
            activity: FragmentActivity, receiver: ActualPageBroadcastReceiver? = null
        ): ActualPageBroadcastReceiver {
            val receiver = receiver ?: ActualPageBroadcastReceiver()
            activity.registerReceiver(receiver, IntentFilter(ACTION))
            return receiver
        }
    }
}