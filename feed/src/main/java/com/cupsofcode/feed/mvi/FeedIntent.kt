package com.cupsofcode.feed.mvi

import com.cupsofcode.respository_restaurant.model.Restaurant


sealed class FeedIntent {
    data class Data(val restaurants: List<RestaurantUiModel>) : FeedIntent()

    data class Error(val error: Throwable) : FeedIntent()
    object ErrorDismissed : FeedIntent()

    object Loading: FeedIntent()

    data class RestaurantClicked(val restaurantId: Int) : FeedIntent()
}