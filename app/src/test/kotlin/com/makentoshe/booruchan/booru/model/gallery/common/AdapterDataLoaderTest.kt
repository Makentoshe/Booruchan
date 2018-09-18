package com.makentoshe.booruchan.booru.model.gallery.common

import com.makentoshe.booruchan.common.api.HttpClient
import com.makentoshe.booruchan.common.api.gelbooru.Gelbooru
import io.mockk.*
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.ByteArrayInputStream

@RunWith(JUnit4::class)
class AdapterDataLoaderTest {

    private lateinit var adapterDataLoader: AdapterDataLoader
    private val mockedUrl = "Mocked url"

    @Before
    fun init() {
        val searchTerm = "SAS"
        val xml = "<?xml version=\"1.1\" encoding=\"UTF-8\" ?><posts count=\"4125493\" offset=\"0\">\n" +
                "<post height=\"1066\" score=\"0\" file_url=\"https://simg3.gelbooru.com/images/f7/7f/f77f25b48a429e80bb3f504af7b5fb16.png\" parent_id=\"\" sample_url=\"https://simg3.gelbooru.com/samples/f7/7f/sample_f77f25b48a429e80bb3f504af7b5fb16.jpg\" sample_width=\"850\" sample_height=\"567\" preview_url=\"https://simg3.gelbooru.com/thumbnails/f7/7f/thumbnail_f77f25b48a429e80bb3f504af7b5fb16.jpg\" rating=\"e\" tags=\"1girl animal_ears areolae bare_shoulders basket belt black_elbow_gloves black_fingerless_gloves black_gloves breasts bunny_ears carrot easter elbow_gloves fake_animal_ears fingerless_gloves fishnet gloves lips mobilepron navel nipples nude playboy ponytail pubic_hair pussy red_hair tengen_toppa_gurren_lagann thighhighs thighs vibrator white_belt yellow_eyes yoko_littner\" id=\"4393656\" width=\"1599\" change=\"1536165214\" md5=\"f77f25b48a429e80bb3f504af7b5fb16\" creator_id=\"3975\" has_children=\"false\" created_at=\"Wed Sep 05 11:32:33 -0500 2018\" status=\"active\" source=\"\" has_notes=\"false\" has_comments=\"true\" preview_width=\"150\" preview_height=\"100\"/>\n" +
                "</posts>"

        val mockedClient = mockk<HttpClient>()
        val gelbooru = Gelbooru()
        every {
            mockedClient.get(gelbooru.getApi().getPostsByTagsRequest(3, searchTerm, 1)).stream()
        } returns ByteArrayInputStream(xml.toByteArray())
        val downloader = Downloader(mockedClient)

        every {
            mockedClient.get(mockedUrl).stream()
        } returns ByteArrayInputStream("".toByteArray())

        adapterDataLoader = AdapterDataLoader(searchTerm, downloader, gelbooru)
    }

    @Test
    fun `should return search term`() {
        assertEquals("SAS", adapterDataLoader.searchTerm)
    }

    @Test
    fun `should return posts data`() = runBlocking {
        var bool = false
        adapterDataLoader.getPostsData(1) {
            assertEquals(1, it.count())
            assertEquals(4125493, it.count)
            bool = true
            return@getPostsData
        }
        delay(1000)
        assertTrue(bool)
    }

}