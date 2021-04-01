package com.cupsofcode.datasource_restaurant

import android.content.Context
import com.cupsofcode.cache.InMemoryCache
import com.cupsofcode.network.restaurants.RestaurantNetwork
import com.cupsofcode.respository_restaurant.RestaurantRepository
import com.cupsofcode.respository_restaurant.model.Restaurant
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Observable.just
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RestaurantRepositoryImpl @Inject constructor(
    private val restaurantService: RestaurantNetwork,
    private val context: Context
) :
    RestaurantRepository {

    private val sharedPref by lazy {
        context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
    }

    private val cache by lazy {
        InMemoryCache<Restaurant>()
    }

    override fun getNearbyRestaurants(
        lat: Double,
        long: Double,
        forceRefresh: Boolean
    ): Observable<List<Restaurant>> {
        return cache.isEmpty()
            .flatMapObservable { empty ->
                val loading = empty || forceRefresh
                if (loading) {
                    pullFromApi(lat, long)
                        .andThen(cache.allObservable())
                } else {
                    cache.allObservable()
                }
            }.subscribeOn(Schedulers.io())
    }


    private fun pullFromApi(
        lat: Double,
        long: Double
    ): Completable {
        return restaurantService.getRestaurants(lat = lat, long = long, offset = 0, limit = 50)
            .map {
                it.map {
                    it.toRestaurant(sharedPref.getBoolean(it.id.toString(), false))
                }
            }.flatMapCompletable {
                cache.putAll(it) { it.id.toString() }
            }
    }

    override fun likeUnlike(restaurantId: Int, liked: Boolean): Completable {
        return Completable.fromAction {
            sharedPref.edit()
                .putBoolean(restaurantId.toString(), liked)
                .apply()
        }.mergeWith(
            cache.oneSingle(restaurantId.toString())
                .flatMapCompletable { restaurant ->
                    cache.put(restaurantId.toString(), restaurant.copy(isLiked = liked))
                }
        )
    }

    companion object {
        const val SHARED_NAME = "LIKED"
    }
}