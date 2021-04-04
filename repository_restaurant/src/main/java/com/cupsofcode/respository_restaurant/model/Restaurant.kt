package com.cupsofcode.respository_restaurant.model

import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

data class Restaurant(
    val id: String,
    val name: String,
    val logo: String,
    val description: String,
    val distanceFromUser: Double,
    val nextOpenTime: ZonedDateTime?,
    val nextCloseTime: ZonedDateTime?,
    var isLiked: Boolean
) {
    fun isOpen(): Boolean {
        val currentTimeInSF = ZonedDateTime.now(ZoneId.of("America/Los_Angeles"))
        return currentTimeInSF.isAfter(nextOpenTime) && currentTimeInSF.isBefore(nextCloseTime)
    }
}