package com.cupsofcode.datasource_restaurant

import com.cupsofcode.network.restaurants.RestaurantNetwork
import com.cupsofcode.respository_restaurant.RestaurantRepository
import com.cupsofcode.respository_restaurant.model.Restaurant
import io.reactivex.Observable
import io.reactivex.Observable.just
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestaurantRepositoryImpl @Inject constructor(private val restaurantService: RestaurantNetwork) :
    RestaurantRepository {

    private val memoryCache =
        mutableListOf<Restaurant>() //decided to go with memory cache for this exercise, we can add db if needed as well

    override fun getNearbyRestaurants(
        lat: Double,
        long: Double,
        forceRefresh: Boolean
    ): Observable<List<Restaurant>> {
        return Observable.defer {
            val loading = memoryCache.isEmpty() || forceRefresh
            Observable.just(memoryCache)
                .flatMap {
                    if (loading) {
                        loadDatafromNetwork(lat, long)
                    } else {
                        just(it)
                    }
                }
                .distinctUntilChanged()

        }.subscribeOn(Schedulers.io())
    }

    private fun loadDatafromNetwork(
        lat: Double,
        long: Double
    ): Observable<List<Restaurant>> {
        return restaurantService.getRestaurants(lat = lat, long = long, offset = 0, limit = 50)
            .map {
                it.map {
                    it.toRestaurant()
                }
            }.doOnSuccess {
                memoryCache.addAll(it)
            }.toObservable()
    }

}