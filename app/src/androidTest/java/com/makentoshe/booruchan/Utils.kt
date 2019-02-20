package com.makentoshe.booruchan

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.test.platform.app.InstrumentationRegistry
import com.makentoshe.booruapi.*
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

class Mockbooru : Booru(mockk()) {
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    var postsErr = false
    var previewsErr = false

    override fun getPosts(request: PostRequest): Posts {
        if (postsErr) throw Exception()
        val posts = mutableListOf<Post>()
        (0 until request.count).forEach { posts.add(Post()) }
        return Posts(posts)
    }

    override val title: String
        get() = javaClass.simpleName

    override fun customGet(request: String) = TODO("not implemented")
    override fun getPreview(previewUrl: String): InputStream {
        val bytes = ByteArrayOutputStream().also {
            (context.getDrawable(R.drawable.a) as BitmapDrawable)
                .bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }.toByteArray()
        return ByteArrayInputStream(bytes)
    }

    override fun autocomplete(term: String): List<Tag> {
        val list = mutableListOf<Tag>()
        (0 until 10).forEach {
            list.add(Tag(name = "$term$it"))
        }
        return list
    }

    class Flags(val preview: Boolean = true, val posts: Boolean = true)
}