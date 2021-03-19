package com.cupsofcode.feed.usecase

import com.cupsofcode.respository_restaurant.RestaurantRepository
import com.cupsofcode.respository_restaurant.model.Restaurant
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject


class FeedRestaurantsUseCase @Inject constructor(private val restaurantRepository: RestaurantRepository) {

    fun execute(): Observable<List<Restaurant>> {
        return restaurantRepository.getNearbyRestaurants(37.422740, -122.139956) //later you can pass any other lat&long from search feature that can be added later
                    .map { list ->
                        list.sortedWith(
                            compareByDescending<Restaurant>{ it.isOpen() } //we can also sort by asap arrival time for places that are open, and display it.
                                .thenBy { it.distanceFromUser }
                        )
                    }.observeOn(AndroidSchedulers.mainThread())
    }
}