package com.cupsofcode.datasource_restaurant

import android.content.Context
import com.cupsofcode.network.restaurants.RestaurantNetwork
import com.cupsofcode.respository_restaurant.RestaurantRepository
import com.cupsofcode.respository_restaurant.model.Restaurant
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Observable.just
import io.reactivex.Single
import io.reactivex.rxkotlin.Observables.combineLatest
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
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

    private val memoryCache =
        mutableListOf<Restaurant>() //decided to go with memory cache for this exercise, we can add db if needed as well

    private val publishSubject by lazy {
        BehaviorSubject.createDefault<List<Restaurant>>(memoryCache)
    }

    override fun getNearbyRestaurants(
        lat: Double,
        long: Double,
        forceRefresh: Boolean
    ): Observable<List<Restaurant>> {
        return publishSubject.hide()
            .flatMap {
                val loading = memoryCache.isEmpty() || forceRefresh
                if (loading) {
                    loadDatafromNetwork(lat, long)
                } else {
                    just(it)
                }
            }
            .subscribeOn(Schedulers.io())
    }


    private fun loadDatafromNetwork(
        lat: Double,
        long: Double
    ): Observable<List<Restaurant>> {
        return restaurantService.getRestaurants(lat = lat, long = long, offset = 0, limit = 50)
            .map {
                it.map {
                    it.toRestaurant(sharedPref.getBoolean(it.id.toString(), false))
                }
            }.doOnSuccess {
                memoryCache.addAll(it)
                publishSubject.onNext(it)
            }.toObservable()
    }

    override fun likeUnlike(restaurantId: Int, liked: Boolean): Completable {
        return Completable.fromAction {
            sharedPref.edit()
                .putBoolean(restaurantId.toString(), liked)
                .apply()

            memoryCache.first { it.id == restaurantId }.isLiked = liked
            publishSubject.onNext(memoryCache)
        }
    }

    companion object {
        const val SHARED_NAME = "LIKED"
    }
}