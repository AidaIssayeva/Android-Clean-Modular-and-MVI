package com.cupsofcode.feed.usecase

import com.cupsofcode.respository_restaurant.RestaurantRepository
import io.reactivex.Completable
import javax.inject.Inject


class RestaurantLikedUseCase @Inject constructor(private val repo: RestaurantRepository) {

    fun execute(id: String, isLiked: Boolean): Completable {
        return repo.likeUnlike(id, isLiked)
    }
}