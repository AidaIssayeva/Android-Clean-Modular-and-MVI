package com.cupsofcode.feed.mvi


data class FeedViewState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val restaurants: List<RestaurantUiModel> = emptyList(),
    val toolbarTitle: String = "", //this is not used yet, but the idea is to make toolbar appear when user scrolls and transition the text from header to toolbar
    val searchedCity: String = "San Francisco", //when we ask user's location, the location service can return to our viewmodel the city name, that we can pass to ui
    val emptyListMessage: String = ""
)

data class RestaurantUiModel(
    val id: Int,
    val distance: String,
    val name: String,
    val logo: String,
    val status: String,
    val description: String,
    val isLiked: Boolean
)