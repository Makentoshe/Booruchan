package com.makentoshe.booruchan

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.test.platform.app.InstrumentationRegistry
import com.makentoshe.booruapi.*
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

class Mockbooru : Booru(MockbooruApi()) {
    override fun getPosts(request: PostRequest): Posts {
        val posts = mutableListOf<Post>()
        (0 until request.count).forEach { posts.add(Post()) }
        return Posts(listOf())
    }

    override val title: String
        get() = javaClass.simpleName

    override fun customGet(request: String) = TODO("not implemented")
    override fun getPreview(previewUrl: String) = TODO("not implemented")

    override fun autocomplete(term: String): List<Tag> {
        val list = mutableListOf<Tag>()
        (0 until 10).forEach {
            list.add(Tag(name = "$term$it"))
        }
        return list
    }
}

class MockbooruApi : BooruApi {
    override fun getCustomRequest(request: String) = TODO("not implemented")
    override fun getAutocompleteRequest(term: String) = TODO("not implemented")
    override fun getPostsRequest(count: Int, page: Int, tags: Set<Tag>) = TODO("not implemented")
    override fun getPreviewRequest(previewUrl: String) = TODO("not implemented")
}