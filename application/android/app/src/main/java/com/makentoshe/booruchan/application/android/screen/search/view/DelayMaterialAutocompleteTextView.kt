package com.makentoshe.booruchan.application.android.screen.search.view

import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.widget.ProgressBar
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class DelayMaterialAutocompleteTextView(
    context: Context, attributeSet: AttributeSet?
) : MaterialAutoCompleteTextView(context, attributeSet) {

    var delay: Long = DEFAULT_DELAY
    var progressBar: ProgressBar? = null

    private val handler = AutoCompleteHandler { msg -> super.performFiltering(msg.obj as CharSequence, msg.arg1) }

    override fun performFiltering(text: CharSequence, keyCode: Int) {
        progressBar?.visibility = VISIBLE
        handler.removeMessages(MESSAGE_TEXT_CHANGED)
        handler.sendMessageDelayed(handler.obtainMessage(MESSAGE_TEXT_CHANGED, text), delay)
    }

    override fun onFilterComplete(count: Int) {
        progressBar?.visibility = GONE
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