package tag

import org.junit.Assert.assertEquals
import org.junit.Test
import tag.deserialize.XmlDanbooruTagDeserialize
import tag.deserialize.XmlDanbooruTagDeserializer
import tag.network.XmlDanbooruTagResponse

class XmlDanbooruTagDeserializerTest {

    @Test
    fun `should deserialize tag xml`() {
        val xml = javaClass.classLoader.getResource("tag.xml")!!.readText()
        val deserialize = XmlDanbooruTagDeserializer().deserializeTag(XmlDanbooruTagResponse.Success(xml))
        val successDeserialize = deserialize as XmlDanbooruTagDeserialize.Success

        // todo add asserts for all fields
        assertEquals(1593445, successDeserialize.tag.tagId)
    }

    @Test
    fun `should deserialize corrupted tag xml`() {
        val xml = javaClass.classLoader.getResource("tag-corrupted.xml")!!.readText()
        val deserialize = XmlDanbooruTagDeserializer().deserializeTag(XmlDanbooruTagResponse.Success(xml))
        val failureDeserialize = deserialize as XmlDanbooruTagDeserialize.Failure

        assertEquals(7, failureDeserialize.raw.size)
    }
}
