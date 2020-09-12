package tag

import org.junit.Assert
import org.junit.Test
import tag.deserialize.XmlDanbooruTagsDeserializer
import tag.network.XmlDanbooruTagsResponse

class XmlDanbooruTagsDeserializerTest {

    @Test
    fun `should deserialize tags xml`() {
        val xml = javaClass.classLoader.getResource("tags.xml")!!.readText()
        val tags = XmlDanbooruTagsDeserializer().deserializeTags(XmlDanbooruTagsResponse.Success(xml))

        Assert.assertEquals(tags.tags.size, 20)
    }
}