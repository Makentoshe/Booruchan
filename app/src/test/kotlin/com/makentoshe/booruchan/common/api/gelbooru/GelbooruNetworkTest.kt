package com.makentoshe.booruchan.common.api.gelbooru

import com.makentoshe.booruchan.common.api.HttpClient
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.ByteArrayInputStream
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

@RunWith(JUnit4::class)
class GelbooruNetworkTest {

    private val instance = Gelbooru()

    @Test
    fun `load autocomplete tips`() {
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

    @Test
    fun `load posts`() {
        val xml  = "<?xml version=\"1.1\" encoding=\"UTF-8\" ?><posts count=\"4125493\" offset=\"0\">\n" +
                "<post height=\"1066\" score=\"0\" file_url=\"https://simg3.gelbooru.com/images/f7/7f/f77f25b48a429e80bb3f504af7b5fb16.png\" parent_id=\"\" sample_url=\"https://simg3.gelbooru.com/samples/f7/7f/sample_f77f25b48a429e80bb3f504af7b5fb16.jpg\" sample_width=\"850\" sample_height=\"567\" preview_url=\"https://simg3.gelbooru.com/thumbnails/f7/7f/thumbnail_f77f25b48a429e80bb3f504af7b5fb16.jpg\" rating=\"e\" tags=\"1girl animal_ears areolae bare_shoulders basket belt black_elbow_gloves black_fingerless_gloves black_gloves breasts bunny_ears carrot easter elbow_gloves fake_animal_ears fingerless_gloves fishnet gloves lips mobilepron navel nipples nude playboy ponytail pubic_hair pussy red_hair tengen_toppa_gurren_lagann thighhighs thighs vibrator white_belt yellow_eyes yoko_littner\" id=\"4393656\" width=\"1599\" change=\"1536165214\" md5=\"f77f25b48a429e80bb3f504af7b5fb16\" creator_id=\"3975\" has_children=\"false\" created_at=\"Wed Sep 05 11:32:33 -0500 2018\" status=\"active\" source=\"\" has_notes=\"false\" has_comments=\"true\" preview_width=\"150\" preview_height=\"100\"/>\n" +
                "</posts>"
        val stream =  ByteArrayInputStream(xml.toByteArray())
        val mockedClient = mockk<HttpClient>()
        every {
            mockedClient.get(instance.getApi().getPostsByTagsRequest(1, "", 1)).stream()
        } returns stream
        instance.getPostsByTags(1, "", 1, mockedClient) {
            assertEquals(1, it.count())
        }
    }

    @Test
    fun `load list of comments`() {
        val xml = "<?xml version=\"1.1\" encoding=\"UTF-8\" ?>" +
                "<comments type=\"array\">\n" +
                "<comment created_at=\"2018-09-21 11:33\" post_id=\"4412644\" body=\"source or link to video plz!! T.T\" creator=\"Anonymous\" id=\"2283115\" creator_id=\"9455\"/>\n" +
                "</comments>"
        val stream =  ByteArrayInputStream(xml.toByteArray())
        val mockedClient = mockk<HttpClient>()
        every {
            mockedClient.get(instance.getApi().getListOfLastCommentsRequest()).stream()
        } returns stream
        instance.getListOfLastComments(mockedClient) {
            assertEquals(1, it.count())
        }
    }

    @Test
    fun `load last commented posts ids`() = runBlocking {
        val path = "${File("").canonicalPath}\\src\\test\\kotlin\\com\\makentoshe\\booruchan\\common\\api\\gelbooru\\GelbooruCommentsTestSource.html"
        val html = String(Files.readAllBytes(Paths.get(path)))
        val stream = ByteArrayInputStream(html.toByteArray())
        val mockedClient = mockk<HttpClient>()
        every {
            mockedClient.get(instance.getApi().getListOfCommentsViewRequest(1)).stream()
        } returns stream
        instance.getListOfLastCommentedPostIds(1, mockedClient) {
            assertEquals(3, it.count())
        }
    }

    @Test
    fun `load post by id`() = runBlocking {
        val xml  = "<?xml version=\"1.1\" encoding=\"UTF-8\" ?><posts count=\"4125493\" offset=\"0\">\n" +
                "<post height=\"1066\" score=\"0\" file_url=\"https://simg3.gelbooru.com/images/f7/7f/f77f25b48a429e80bb3f504af7b5fb16.png\" parent_id=\"\" sample_url=\"https://simg3.gelbooru.com/samples/f7/7f/sample_f77f25b48a429e80bb3f504af7b5fb16.jpg\" sample_width=\"850\" sample_height=\"567\" preview_url=\"https://simg3.gelbooru.com/thumbnails/f7/7f/thumbnail_f77f25b48a429e80bb3f504af7b5fb16.jpg\" rating=\"e\" tags=\"1girl animal_ears areolae bare_shoulders basket belt black_elbow_gloves black_fingerless_gloves black_gloves breasts bunny_ears carrot easter elbow_gloves fake_animal_ears fingerless_gloves fishnet gloves lips mobilepron navel nipples nude playboy ponytail pubic_hair pussy red_hair tengen_toppa_gurren_lagann thighhighs thighs vibrator white_belt yellow_eyes yoko_littner\" id=\"4393656\" width=\"1599\" change=\"1536165214\" md5=\"f77f25b48a429e80bb3f504af7b5fb16\" creator_id=\"3975\" has_children=\"false\" created_at=\"Wed Sep 05 11:32:33 -0500 2018\" status=\"active\" source=\"\" has_notes=\"false\" has_comments=\"true\" preview_width=\"150\" preview_height=\"100\"/>\n" +
                "</posts>"
        val stream =  ByteArrayInputStream(xml.toByteArray())
        val mockedClient = mockk<HttpClient>()
        every {
            mockedClient.get(instance.getApi().getPostByIdRequest(1)).stream()
        } returns stream
        instance.getPostById(1, mockedClient) {
            assertNotNull(it)
            assertEquals("4393656", it.id.toString())
        }
    }

}