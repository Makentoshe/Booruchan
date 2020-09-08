package tag

import org.junit.Assert
import org.junit.Test
import tag.network.JsonDanbooruTagResponse

class JsonDanbooruTagDeserializerTest {

    @Test
    fun `should deserialize single tag json`() {
        val json = javaClass.classLoader.getResource("tag.json")!!.readText()
        val tag = JsonDanbooruTagDeserializer().deserializeTag(JsonDanbooruTagResponse.Success(json))

        Assert.assertEquals(1591223, tag.tagId)
    }
}