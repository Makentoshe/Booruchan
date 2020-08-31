package tag

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import tag.entity.DanbooruTag

class JsonDanbooruTagDeserializer : TagDeserializer<DanbooruTag> {

    private val jsonDeserializer = Json { isLenient = true }

    override fun deserialize(string: String): DanbooruTag {
        return DanbooruTag.create(jsonDeserializer.decodeFromString<Map<String, String>>(string))
    }
}