package com.cupsofcode.respository_restaurant.model


data class Data<out T>(
    val content: T? = null,
    val error: Throwable? = null,
    val loading: Boolean = false
)