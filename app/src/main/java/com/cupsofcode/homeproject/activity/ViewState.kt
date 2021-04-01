package com.cupsofcode.homeproject.activity


data class ViewState(
    val error: Throwable? = null
)

sealed class ActivityIntent {

}