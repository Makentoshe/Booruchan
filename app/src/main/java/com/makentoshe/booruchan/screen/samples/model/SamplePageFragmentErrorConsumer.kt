package com.makentoshe.booruchan.screen.samples.model

import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.core.util.Consumer
import com.makentoshe.booruchan.R
import org.jetbrains.anko.find

open class SamplePageFragmentErrorConsumer(private val throwable: Throwable? = null) :
    Consumer<View> {
    override fun accept(t: View) {
        onError(t, throwable!!)
    }

    protected fun onError(view: View, throwable: Throwable) {
        val progress = view.find<View>(R.id.samples_progress)
        val message = view.find<TextView>(R.id.samples_message)

        Handler(Looper.getMainLooper()).post {
            progress.visibility = View.GONE
            message.visibility = View.VISIBLE
            message.text = throwable.localizedMessage
        }
    }
}