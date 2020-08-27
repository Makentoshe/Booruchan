import java.time.ZonedDateTime

interface CreationTime {
    val raw: String
    val time: ZonedDateTime
}