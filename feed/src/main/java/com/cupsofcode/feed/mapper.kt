package com.cupsofcode.feed

import com.cupsofcode.feed.mvi.RestaurantUiModel
import com.cupsofcode.respository_restaurant.model.Restaurant
import com.cupsofcode.ui_commons.wrapper.StringResources
import java.text.DecimalFormat


fun Restaurant.toUiModel(stringsResources: StringResources): RestaurantUiModel {
   return RestaurantUiModel(
        id = this.id,
       status = if (isOpen()) stringsResources.getString(R.string.open) else stringsResources.getString(R.string.closed) ,
       name = this.name,
       logo = this.logo,
       description = this.description,
       distance =  stringsResources.getString(
           R.string.miles,
           DecimalFormat("#.##").format(distanceFromUser)
       ),
       isLiked = this.isLiked
    )
}