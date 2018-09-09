package com.makentoshe.booruchan

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.makentoshe.booruchan.appsettings.AppSettings

@SuppressLint("Registered")
abstract class Activity: AppCompatActivity() {

    fun getAppSettings(): AppSettings {
        return (application as Booruchan).appSettings
    }

    private var currentStyleVal: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        currentStyleVal = getAppSettings().getStyleVal()
        setTheme(currentStyleVal)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        if (currentStyleVal != getAppSettings().getStyleVal()) {
            recreate()
        }
    }

    fun hideKeyboard() {
        val imm = getSystemService(android.app.Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}