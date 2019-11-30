package net.nshiba.roomchangequerydynamicallysample.utils

import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

private val dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

fun ZonedDateTime.formatString(): String {
    return this.format(dateTimeFormatter)
}

object ZonedDateTimeUtils {

    private val zoneId: ZoneId = ZoneId.of("Asia/Tokyo")

    fun parse(dateText: String): ZonedDateTime {
        return LocalDateTime.parse(dateText, dateTimeFormatter).atZone(zoneId)
    }

    fun format(dateTime: ZonedDateTime, formatPattern: String? = null): String {
        val formatter = if (formatPattern == null) {
            dateTimeFormatter
        } else {
            DateTimeFormatter.ofPattern(formatPattern)
        }
        return dateTime.format(formatter)
    }

    fun of(year: Int, month: Int, day: Int, hour: Int, minute: Int): ZonedDateTime {
        return ZonedDateTime.of(year, month, day, hour, minute, 0, 0, zoneId)
    }

    fun now(): ZonedDateTime {
        return ZonedDateTime.now(zoneId)
    }
}
