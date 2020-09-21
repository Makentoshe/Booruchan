package tag.deserialize

import org.junit.Assert.assertEquals
import org.junit.Test
import tag.network.XmlGelbooruTagResponse

class XmlGelbooruTagDeserializerTest {

    @Test
    fun `should deserialize tag`() {
        val xml = javaClass.classLoader.getResource("tag.xml")!!.readText()
        val deserialize = XmlGelbooruTagDeserializer().deserializeTag(XmlGelbooruTagResponse.Success(xml))
        val successDeserialize = deserialize as XmlGelbooruTagDeserialize.Success

        // todo add asserts for all fields
        assertEquals(651094, successDeserialize.tag.tagId)
    }

    @Test
    fun `should deserialize variant tag`() {
        val xml = javaClass.classLoader.getResource("tag-variant.xml")!!.readText()
        val deserialize = XmlGelbooruTagDeserializer().deserializeTag(XmlGelbooruTagResponse.Success(xml))
        val successDeserialize = deserialize as XmlGelbooruTagDeserialize.Success

        // todo add asserts for all fields
        assertEquals(651094, successDeserialize.tag.tagId)
    }

    @Test
    fun `should deserialize corrupted tag`() {
        val xml = javaClass.classLoader.getResource("tag-corrupted.xml")!!.readText()
        val deserialize = XmlGelbooruTagDeserializer().deserializeTag(XmlGelbooruTagResponse.Success(xml))
        val failureDeserialize = deserialize as XmlGelbooruTagDeserialize.Failure

        assertEquals(5, failureDeserialize.raw.size)
    }
}