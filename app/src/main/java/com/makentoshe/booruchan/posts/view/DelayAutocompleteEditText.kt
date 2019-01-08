package com.makentoshe.booruchan.posts.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatAutoCompleteTextView

@SuppressLint("ViewConstructor")
class DelayAutocompleteEditText(context: Context) : AppCompatAutoCompleteTextView(context) {

    @JvmField
    var delay: Long = DEFAULT_DELAY
    var progressBar: ProgressBar? = null

    private val handler =
        AutoCompleteHandler { msg ->
            super.performFiltering(msg.obj as CharSequence, msg.arg1)
        }

    override fun performFiltering(text: CharSequence, keyCode: Int) {
        progressBar?.visibility = View.VISIBLE
        handler.removeMessages(MESSAGE_TEXT_CHANGED)
        handler.sendMessageDelayed(handler.obtainMessage(MESSAGE_TEXT_CHANGED, text), delay)
    }

    override fun onFilterComplete(count: Int) {
        progressBar?.visibility = View.GONE
        super.onFilterComplete(count)
    }

    companion object {
        const val MESSAGE_TEXT_CHANGED = 100
        const val DEFAULT_DELAY: Long = 750

        class AutoCompleteHandler(private val action: (Message) -> Unit) : Handler() {
            override fun handleMessage(msg: Message) = action(msg)
        }
    }

}