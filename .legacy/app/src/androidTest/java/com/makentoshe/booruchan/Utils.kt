package com.makentoshe.booruchan

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import com.makentoshe.booruchan.api.BooruFactory
import com.makentoshe.booruchan.screen.start.StartFragment
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue

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

fun mockBooruFactory(context: Context): BooruFactory {
    val factory = mockk<BooruFactory>()
    every { factory.buildBooru(Mockbooru::class.java, context) } returns Mockbooru(context)
    return factory
}

fun AppCompatActivity.setMockbooruFactory() {
    getFragment<StartFragment>().booruFactory = mockBooruFactory(this)
    //click on mocked booru
    Espresso.onView(ViewMatchers.withText(Mockbooru::class.java.simpleName)).perform(ViewActions.click())
}

fun toMatcher(v: View): Matcher<View> {
    return object : TypeSafeMatcher<View>() {
        override fun matchesSafely(item: View): Boolean {
            return item === v
        }

        override fun describeTo(description: Description) {
            description.appendText(v.toString())
        }
    }
}