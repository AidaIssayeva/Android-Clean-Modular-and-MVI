package com.cupsofcode.feed.epoxy

import android.content.Context
import android.view.Gravity
import com.airbnb.epoxy.TypedEpoxyController
import com.cupsofcode.feed.R
import com.cupsofcode.ui_commons.epoxy_rows.feedRow
import com.cupsofcode.ui_commons.epoxy_rows.textRow
import com.cupsofcode.feed.mvi.FeedIntent
import com.cupsofcode.feed.mvi.FeedViewState
import com.cupsofcode.ui_commons.epoxy_rows.feedHeaderRow
import io.reactivex.subjects.PublishSubject
import java.text.DecimalFormat


class FeedEpoxyAdapter(
    private val intentsSubject: PublishSubject<FeedIntent>,
    private val context: Context
) :
    TypedEpoxyController<FeedViewState>() {

    override fun buildModels(data: FeedViewState?) {
        data?.apply {
            if (!isLoading) {
                feedHeaderRow {
                    val header = context.getString(R.string.feed_header_text, searchedCity)
                    id(header)
                    body(header)
                    textSizeRes(R.dimen.big_text_size)
                    imgRes(R.drawable.golden_gate_bridge)
                }

                if(emptyListMessage.isNotEmpty()) {
                    textRow {
                        id(emptyListMessage)
                        body(emptyListMessage)
                        gravity(Gravity.CENTER)
                        textSizeRes(R.dimen.title_size)
                    }
                }

                restaurants.forEach { restaurant ->
                    feedRow {
                        id(restaurant.id)
                        name(restaurant.name)
                        logo(restaurant.logo)
                        description(restaurant.description)
                        status(restaurant.status)
                        distance(restaurant.distance)
                        likedStatus(restaurant.isLiked)
                        onClickListener { _ ->
                            intentsSubject.onNext(FeedIntent.RestaurantClicked(restaurantId = restaurant.id))
                        }
                        likeClickListener { _ ->
                            intentsSubject.onNext(FeedIntent.LikeClicked(restaurantId = restaurant.id, isLiked = !restaurant.isLiked))
                        }
                    }
                }

            }

        }
    }
}