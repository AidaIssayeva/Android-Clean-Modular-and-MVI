package com.cupsofcode.network.restaurants


data class RestaurantResponse(val stores: List<StoreResponse>)

data class StoreResponse(
    val id: Int,
    val name: String,
    val header_img_url: String,
    val description: String,
    val cover_img_url: String,
    val distance_from_consumer: Double,
    val next_close_time: String,
    val next_open_time: String
)