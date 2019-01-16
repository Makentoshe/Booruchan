package com.makentoshe.booruchan

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.*
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
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

class Mockbooru : Booru(MockbooruApi()) {
    override val title: String
        get() = javaClass.simpleName

    override fun customGet(request: String) = TODO("not implemented")
    override fun autocomplete(term: String) = TODO("not implemented")
    override fun getPosts(count: Int, page: Int, tags: Set<Tag>) = Posts(listOf())
    override fun getPreview(previewUrl: String) = TODO("not implemented")

    private fun createPost(): Post {
        return Post()
    }
}

class MockbooruApi : BooruApi {
    override fun getCustomRequest(request: String) = TODO("not implemented")
    override fun getAutocompleteRequest(term: String) = TODO("not implemented")
    override fun getPostsRequest(count: Int, page: Int, tags: Set<Tag>) = TODO("not implemented")
    override fun getPreviewRequest(previewUrl: String) = TODO("not implemented")
}