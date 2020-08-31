package tag

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import tag.entity.GelbooruTag

class JsonGelbooruTagDeserializer : TagDeserializer<GelbooruTag> {

    private val jsonDeserializer = Json { isLenient = true }

    override fun deserialize(string: String): GelbooruTag {
        return GelbooruTag.create(jsonDeserializer.decodeFromString(string))
    }
}
