package com.cupsofcode.feed

import com.cupsofcode.respository_restaurant.model.Restaurant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

val currentTimeInSF = ZonedDateTime.now(ZoneId.of("America/Los_Angeles"))

val singleRestaurant = Restaurant(
    id = "123",
    name = "Samakmak",
    description = "Food",
    distanceFromUser = 0.34,
    nextOpenTime = currentTimeInSF.minusHours(3),
    nextCloseTime = currentTimeInSF.plusMinutes(5),
    logo = "",
    isLiked = false
)

val restaurants = listOf<Restaurant>(
    singleRestaurant,
    singleRestaurant.copy(
        id = "32",
        name = "Crema",
        distanceFromUser = 1.1,
        nextOpenTime = currentTimeInSF.plusHours(3),
        nextCloseTime = currentTimeInSF.plusHours(5)
    ),
    singleRestaurant.copy(
        id = "12",
        name = "My Mexico",
        distanceFromUser = 0.25,
        nextOpenTime = currentTimeInSF.minusHours(1),
        nextCloseTime = currentTimeInSF.plusHours(4)
    )

)

val sortedRestaurants = listOf<Restaurant>(
    singleRestaurant.copy(
        id = "12",
        name = "My Mexico",
        distanceFromUser = 0.25,
        nextOpenTime = currentTimeInSF.minusHours(1),
        nextCloseTime = currentTimeInSF.plusHours(4)
    ),
    singleRestaurant,
    singleRestaurant.copy(
        id = "32",
        name = "Crema",
        distanceFromUser = 1.1,
        nextOpenTime = currentTimeInSF.plusHours(3),
        nextCloseTime = currentTimeInSF.plusHours(5)
    )
)