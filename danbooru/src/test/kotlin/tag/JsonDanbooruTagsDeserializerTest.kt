package tag

import org.junit.Assert.assertEquals
import org.junit.Test

class JsonDanbooruTagsDeserializerTest {

    @Test
    fun `should deserialize list of tags`() {
        val json = javaClass.classLoader.getResource("list_tags.json")!!.readText()
        val tags = JsonDanbooruTagsDeserializer().deserialize(json)

        assertEquals(tags.tags.size, 20)
    }
}
