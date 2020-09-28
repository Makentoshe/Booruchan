package tag

import deserialize.EntityDeserializeException
import org.junit.Assert.assertEquals
import org.junit.Test
import tag.deserialize.XmlDanbooruTagDeserializer

class XmlDanbooruTagDeserializerTest {

    @Test
    fun `should deserialize tag xml`() {
        val xml = javaClass.classLoader.getResource("tag.xml")!!.readText()
        val deserializeResult = XmlDanbooruTagDeserializer().deserializeTag(xml)
        val deserializeSuccess = deserializeResult.getOrNull()!!

        // todo add asserts for all fields
        assertEquals(1593445, deserializeSuccess.tag.tagId)
    }

    @Test
    fun `should deserialize corrupted tag xml`() {
        val xml = javaClass.classLoader.getResource("tag-corrupted.xml")!!.readText()
        val deserializeResult = XmlDanbooruTagDeserializer().deserializeTag(xml)
        val deserializeFailure = deserializeResult.exceptionOrNull() as EntityDeserializeException

        assertEquals(7, deserializeFailure.raw.size)
    }
}
