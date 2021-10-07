package com.cupsofcode.homeproject.activity

import com.google.android.play.core.review.ReviewInfo


data class ViewState(
    val error: Throwable? = null,
    val reviewInfo: ReviewInfo? = null
)

sealed class ActivityIntent {

    data class InAppReviewRequested(val reviewInfo: ReviewInfo) : ActivityIntent()

    object InAppReviewCompleted : ActivityIntent()

    data class Error(val throwable: Throwable) : ActivityIntent()

    object OnReviewClicked : ActivityIntent()

    object NoOp : ActivityIntent()
}