package com.cupsofcode.datasource_restaurant

import com.cupsofcode.respository_restaurant.RestaurantRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RestaurantDataModule {
    @Binds
    @Singleton
    abstract fun bindsRestaurantRepository(impl: RestaurantRepositoryImpl): RestaurantRepository
}