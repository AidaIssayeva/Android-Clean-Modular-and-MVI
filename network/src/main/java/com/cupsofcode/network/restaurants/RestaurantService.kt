package com.cupsofcode.network.restaurants

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RestaurantService {

    @GET("/v1/store_feed")
    fun getRestaurants(
        @Query("lat") lat: Double,
        @Query("lng") long: Double,
        @Query("offset")  offset: Int,
        @Query("limit")  limit: Int
    ): Single<RestaurantResponse>

    @GET("/v2/restaurant/{restaurantId}")
    fun getRestaurantDetails(@Path("restaurantId") restaurantId: Int): Single<RestaurantResponse>

}