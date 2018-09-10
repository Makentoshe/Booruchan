package com.makentoshe.booruchan.view

import android.content.Context
import android.os.Handler
import android.os.Message
import android.widget.ProgressBar
import android.support.v7.widget.AppCompatAutoCompleteTextView
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.styles.Style

class DelayAutocompleteEditText(context: Context, attrs: AttributeSet)
    : AppCompatAutoCompleteTextView(context, attrs) {

    private var autoCompleteDelay = DEFAULT_AUTOCOMPLETE_DELAY
    private var progressBar: ProgressBar? = null
    private var clearIcon: ImageView? = null

    fun setAutoCompleteDelay(autoCompleteDelay: Long) {
        this.autoCompleteDelay = autoCompleteDelay
    }

    fun init(style: Style) {
        initClearIcon(style)
    }

    private fun initClearIcon(style: Style) {
        try {
            clearIcon = (parent as View).findViewById(R.id.DelayAutocompleteEditTextClear)
            clearIcon?.apply {
                setImageResource(style.crossIcon)
                setOnClickListener {
                    this@DelayAutocompleteEditText.setText("")
                }
            }
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) {
                        clearIcon?.visibility = View.VISIBLE
                    } else {
                        clearIcon?.visibility = View.GONE
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private val handler = CustomHandler(object : Consumer<Message> {
        @Throws(Exception::class)
        override fun accept(t: Message) {
            super@DelayAutocompleteEditText.performFiltering(t.obj as CharSequence, t.arg1)
        }
    })

    override fun performFiltering(text: CharSequence, keyCode: Int) {
        if (progressBar != null) {
            progressBar!!.visibility = View.VISIBLE
        }
        handler.removeMessages(MESSAGE_TEXT_CHANGED)
        handler.sendMessageDelayed(handler.obtainMessage(MESSAGE_TEXT_CHANGED, text), autoCompleteDelay)
    }

    fun stopAutocompleteSearch() {
        handler.removeMessages(MESSAGE_TEXT_CHANGED)
    }

    override fun onFilterComplete(count: Int) {
        if (progressBar != null) {
            progressBar!!.visibility = View.INVISIBLE
        }
        super.onFilterComplete(count)
    }

    private class CustomHandler constructor(private val consumer: Consumer<Message>) : Handler() {

        override fun handleMessage(msg: Message) {
            try {
                consumer.accept(msg)
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

        }
    }

    private interface Consumer<T> {

        fun accept(t: T)

    }

    companion object {

        private val MESSAGE_TEXT_CHANGED = 100
        private val DEFAULT_AUTOCOMPLETE_DELAY = 750L
    }
}
