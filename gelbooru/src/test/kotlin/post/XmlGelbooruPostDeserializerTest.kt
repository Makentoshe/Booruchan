package post

import deserialize.EntityDeserializeException
import org.junit.Assert.assertEquals
import org.junit.Test
import post.deserialize.XmlGelbooruPostDeserializer

class XmlGelbooruPostDeserializerTest {

    @Test
    fun `should parse xml`() {
        val xml = javaClass.classLoader.getResource("post.xml")!!.readText()
        val result = XmlGelbooruPostDeserializer().deserializePost(xml)
        val successResult = result.getOrNull()!!

        // TODO add asserts for all fields (feelsbadman)
        assertEquals(1, successResult.post.postId)
    }

    @Test
    fun `should parse corrupted xml with required param to the failure`() {
        val xml = javaClass.classLoader.getResource("post-corrupted.xml")!!.readText()
        val result = XmlGelbooruPostDeserializer().deserializePost(xml)
        val failureResult = result.exceptionOrNull() as EntityDeserializeException
        // TODO mb add asserts for all fields?
        assertEquals(23, failureResult.raw.size)
    }
}