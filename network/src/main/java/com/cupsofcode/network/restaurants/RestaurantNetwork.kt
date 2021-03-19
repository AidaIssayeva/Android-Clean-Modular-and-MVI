package com.cupsofcode.network.restaurants

import io.reactivex.Single


interface RestaurantNetwork {
     fun getRestaurants(
        lat: Double,
        long: Double,
        offset: Int,
        limit: Int
    ): Single<List<StoreResponse>>

     fun getRestaurantDetails(restaurantId: Int): Single<RestaurantResponse>
}