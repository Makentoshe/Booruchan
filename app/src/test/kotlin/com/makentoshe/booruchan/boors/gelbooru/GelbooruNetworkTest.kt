package com.makentoshe.booruchan.boors.gelbooru

import com.makentoshe.booruchan.boors.HttpClient
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.ByteArrayInputStream

@RunWith(JUnit4::class)
class GelbooruNetworkTest {

    private val instance = Gelbooru()

    @Test
    fun `get autocomplete tips`() = runBlocking {
        val json = "[\"hatsune_miku\",\"hatsuyuki_(kantai_collection)\"," +
                "\"hatsune_miku_(append)\",\"hatsune_miku_(cosplay)\"," +
                "\"hatsuzuki_(kantai_collection)\",\"hatsukaze_(kantai_collection)\"," +
                "\"hatsushimo_(kantai_collection)\",\"hatsuharu_(kantai_collection)\"," +
                "\"hatsune_mikuo\",\"hatsuzuki_527\"]"
        val stream = ByteArrayInputStream(json.toByteArray())
        val mockedClient = mockk<HttpClient>()
        every {
            mockedClient.get(instance.getApi().getAutocompleteSearchRequest("any term")).stream()
        } returns stream
        val result = instance.getAutocompleteSearchVariations(mockedClient, "any term")
        assertEquals(10, result.size)
    }

}