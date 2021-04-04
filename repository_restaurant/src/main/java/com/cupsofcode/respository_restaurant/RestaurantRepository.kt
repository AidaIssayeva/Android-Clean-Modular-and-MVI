package com.cupsofcode.respository_restaurant

import com.cupsofcode.respository_restaurant.model.Restaurant
import io.reactivex.Completable
import io.reactivex.Observable


interface RestaurantRepository {

    fun getNearbyRestaurants(
        lat: Double,
        long: Double,
        forceRefresh: Boolean = false
    ): Observable<List<Restaurant>>

    fun likeUnlike(restaurantId: String, liked: Boolean): Completable

}