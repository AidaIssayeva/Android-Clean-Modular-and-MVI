package com.cupsofcode.feed.mvi


sealed class FeedIntent {
    data class Data(val restaurants: List<RestaurantUiModel>) : FeedIntent()

    data class Error(val error: Throwable) : FeedIntent()
    object ErrorDismissed : FeedIntent()

    object Loading : FeedIntent()

    data class RestaurantClicked(val restaurantId: String) : FeedIntent()

    data class LikeClicked(val restaurantId: String, val isLiked: Boolean) : FeedIntent()
}