package tag

import org.junit.Assert
import org.junit.Test

class JsonDanbooruTagDeserializerTest {

    @Test
    fun `should deserialize single tag json`() {
        val json = javaClass.classLoader.getResource("tag.json")!!.readText()
        val tag = JsonDanbooruTagDeserializer().deserializeTag(json)

        Assert.assertEquals(1591223, tag.tagId)
    }
}