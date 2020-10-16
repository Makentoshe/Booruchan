package deserialize

import org.jsoup.nodes.Element
import java.util.regex.Pattern

/** Class should be implemented by each xml deserializer. */
abstract class XmlGelbooruDeserializer {

    private val escapeMap = mapOf("<" to "&lt", ">" to "&gt")

    /** Allows to normalize attribute values, e.g. escape some characters */
    protected fun normalize(element: Element): Element {
        element.attributes().map { it.setValue(escapeValue(it.value)) }
        return element
    }

    /** Allows to check that the [string] is an valid xml */
    protected fun isValidXml(string: String): Boolean {
        val htmlPattern: Pattern = Pattern.compile(".*<[^>]+>.*", Pattern.DOTALL)
        return htmlPattern.matcher(string).matches()
    }

    private fun escapeValue(value: String): String {
        var newValue = value
        escapeMap.forEach { (char, replace) -> newValue = newValue.replace(char, replace) }
        return newValue
    }
}