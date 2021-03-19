package com.cupsofcode.datasource_restaurant

import com.cupsofcode.datasource_restaurant.helper.DateHelper
import com.cupsofcode.network.restaurants.RestaurantResponse
import com.cupsofcode.network.restaurants.StoreResponse
import com.cupsofcode.respository_restaurant.model.Restaurant

fun StoreResponse.toRestaurant(): Restaurant {
    return Restaurant(
        id = this.id,
        name = this.name,
        logo = if (this.cover_img_url.isNotEmpty()) {
            this.cover_img_url
        } else {
            this.header_img_url
        },
        description = this.description,
        distanceFromUser = this.distance_from_consumer,
        nextCloseTime = DateHelper.getDateTime(next_close_time),
        nextOpenTime = DateHelper.getDateTime(next_open_time)
    )
}