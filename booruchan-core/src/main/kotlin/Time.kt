import java.time.ZonedDateTime

interface Time {
    val raw: String
    val time: ZonedDateTime
}