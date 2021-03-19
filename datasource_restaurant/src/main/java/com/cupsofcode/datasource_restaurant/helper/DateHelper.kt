package com.cupsofcode.datasource_restaurant.helper

import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.*


object DateHelper {
    const val SFtimezone = "America/Los_Angeles"

    private val serverDateFormatter by lazy {
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    }


    fun getDateTime(serverDateString: String?): ZonedDateTime? {
        if (serverDateString == null) {
            return null
        }

        try {
            return LocalDateTime.parse(serverDateString, serverDateFormatter)
                .atOffset(ZoneOffset.UTC)
                .atZoneSameInstant(ZoneId.of(SFtimezone))
        } catch (e: Exception) {
        }

        return null
    }
}