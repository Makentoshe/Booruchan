package tag

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import tag.entity.DanbooruTag
import tag.entity.DanbooruTags
import tag.entity.Tags

class JsonDanbooruTagsDeserializer : TagsDeserializer<DanbooruTag> {

    private val jsonDeserializer = Json { isLenient = true }

    override fun deserialize(string: String): Tags<DanbooruTag> {
        val list = jsonDeserializer.decodeFromString<List<Map<String, String>>>(string)
        return DanbooruTags(list.map(DanbooruTag.Companion::create))
    }
}