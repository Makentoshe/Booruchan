package tag

import org.junit.Assert.assertEquals
import org.junit.Test

class JsonDanbooruTagsDeserializerTest {

    @Test
    fun `should deserialize tags json`() {
        val json = javaClass.classLoader.getResource("tags.json")!!.readText()
        val tags = JsonDanbooruTagsDeserializer().deserializeTags(json)

        assertEquals(tags.tags.size, 20)
    }
}
