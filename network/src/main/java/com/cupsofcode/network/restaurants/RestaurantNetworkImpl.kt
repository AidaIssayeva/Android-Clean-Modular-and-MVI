package com.cupsofcode.network.restaurants

import io.reactivex.Single
import javax.inject.Inject


class RestaurantNetworkImpl @Inject constructor(private val apiService: RestaurantService) :
    RestaurantNetwork {
    override fun getRestaurants(
        lat: Double,
        long: Double,
        offset: Int,
        limit: Int
    ): Single<List<StoreResponse>> {
        return apiService.getRestaurants(lat, long, offset, limit)
            .map { it.stores }
    }

    override fun getRestaurantDetails(restaurantId: Int): Single<RestaurantResponse> {
        TODO("Not yet implemented")
    }
}