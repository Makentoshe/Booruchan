package com.makentoshe.booruchan.common.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff
import android.os.Handler
import android.os.Message
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.ProgressBar
import android.support.v7.widget.AppCompatAutoCompleteTextView
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import com.makentoshe.booruchan.booru.BooruViewModel
import com.makentoshe.booruchan.booru.model.ContainerViewModel
import com.makentoshe.booruchan.common.Activity
import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.hideKeyboard
import com.makentoshe.booruchan.common.styles.Style
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

class DelayAutocompleteEditText(context: Context, attrs: AttributeSet? = null)
    : AppCompatAutoCompleteTextView(context, attrs) {

    init {
        runBlocking {
            launch {
                initSelecting()
            }.join()
        }
    }

    private var autoCompleteDelay = DEFAULT_AUTOCOMPLETE_DELAY
    private var progressBar: ProgressBar? = null

    fun setAutoCompleteDelay(autoCompleteDelay: Long) {
        this.autoCompleteDelay = autoCompleteDelay
    }

    fun setActionSearch(viewModel: ContainerViewModel): DelayAutocompleteEditText {
        setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.addValueForObserver(text.toString())
                (this.context as Activity).let {
                    viewModel.hideSearchLabel(it, it.getAppSettings().getStyle())
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        return this
    }

    fun setClearIcon(clearIcon: ImageView, style: Style): DelayAutocompleteEditText = runBlocking {
        launch {
            clearIcon.apply {
                val drawable = ContextCompat.getDrawable(context, style.avdFromMagnifyToCross)
                drawable?.setColorFilter(ContextCompat.getColor(context, android.R.color.black), PorterDuff.Mode.SRC_ATOP)
                setImageDrawable(drawable)
                setOnClickListener {
                    this@DelayAutocompleteEditText.setText("")
                }
            }
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) {
                        clearIcon.visibility = View.VISIBLE
                    } else {
                        clearIcon.visibility = View.GONE
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }.join()
        return@runBlocking this@DelayAutocompleteEditText
    }

    fun setProgressBar(progressBar: ProgressBar) {
        this.progressBar = progressBar
    }

    @SuppressLint("SetTextI18n")
    private fun initSelecting() {
        var previousString = ""
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                previousString = s.toString()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        setOnItemClickListener { parent, _, position, _ ->
            stopAutocompleteSearch()
            val selectedString = parent.getItemAtPosition(position) as String
            //если в поле ввода содержится часть слова из подсказки - вводят первое слово
            if (selectedString.contains(previousString)) {
                setText(selectedString)
            } else {
                val previousStringSplit = previousString.split(" ")
                //определяем лимит - если слово не закончено - не учитываем его
                val limit = if (previousString[previousString.length - 1] == ' ') {
                    previousStringSplit.size
                } else { //не закончено
                    previousStringSplit.size - 1 //не учитываем
                }
                val resultStringBuilder = StringBuilder(previousStringSplit[0])
                for (i in 1 until limit) {
                    resultStringBuilder.append(" ").append(previousStringSplit[i])
                }
                if (previousString[previousString.length - 1] != ' ') {
                    resultStringBuilder.append(" ")
                }
                resultStringBuilder.append(selectedString)
                setText(resultStringBuilder)
            }
            setSelection(text.count())
            progressBar?.visibility = View.GONE
        }
    }

    private val handler = CustomHandler(object : Consumer<Message> {
        @Throws(Exception::class)
        override fun accept(t: Message) {
            super@DelayAutocompleteEditText.performFiltering(t.obj as CharSequence, t.arg1)
        }
    })

    override fun performFiltering(text: CharSequence, keyCode: Int) {
        progressBar?.visibility = View.VISIBLE
        handler.removeMessages(MESSAGE_TEXT_CHANGED)
        handler.sendMessageDelayed(handler.obtainMessage(MESSAGE_TEXT_CHANGED, text), autoCompleteDelay)
    }

    fun stopAutocompleteSearch() {
        handler.removeMessages(MESSAGE_TEXT_CHANGED)
    }

    override fun onFilterComplete(count: Int) {
        progressBar?.visibility = View.INVISIBLE
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
