package com.makentoshe.booruchan

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.test.platform.app.InstrumentationRegistry
import io.mockk.mockk
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream

inline fun <reified T : Fragment> AppCompatActivity.containsFragment() {
    var contains = false
    supportFragmentManager.fragments.forEach { if (it is T) contains = true }
    assertTrue(contains)
}

inline fun <reified T : Fragment> Fragment.containsFragment() {
    var contains = false
    childFragmentManager.fragments.forEach { if (it is T) contains = true }
    assertTrue(contains)
}

inline fun <reified T : Fragment> AppCompatActivity.getFragment(): T {
    var fragment: Fragment? = null
    supportFragmentManager.fragments.forEach { if (it is T) fragment = it }
    assertNotNull(fragment)
    return fragment as T
}

fun isKeyboardShown(): Boolean {
    val inputMethodManager = InstrumentationRegistry
        .getInstrumentation()
        .targetContext
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    return inputMethodManager.isAcceptingText
}

inline fun <reified T : Fragment> Fragment.getFragment(): T {
    var fragment: Fragment? = null
    childFragmentManager.fragments.forEach { if (it is T) fragment = it }
    assertNotNull(fragment)
    return fragment as T
}