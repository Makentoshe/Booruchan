package post

import deserialize.EntityDeserializeException
import org.junit.Assert.assertEquals
import org.junit.Test
import post.deserialize.XmlDanbooruPostDeserializer

class XmlDanbooruPostDeserializerTest {

    @Test
    fun `should parse xml post`() {
        val xml = javaClass.classLoader.getResource("post.xml")!!.readText()
        val deserializeResult = XmlDanbooruPostDeserializer().deserializePost(xml)

        // TODO add asserts for all fields (feelsbadman)
        assertEquals(4086764, deserializeResult.getOrNull()!!.post.postId)
    }

    @Test
    fun `should parse xml corrupted post`() {
        val xml = javaClass.classLoader.getResource("post-corrupted.xml")!!.readText()
        val deserializeResult = XmlDanbooruPostDeserializer().deserializePost(xml)
        val failedDeserialize = deserializeResult.exceptionOrNull() as EntityDeserializeException
        // TODO mb add asserts for all fields?
        assertEquals(44, failedDeserialize.raw.size)
    }
}