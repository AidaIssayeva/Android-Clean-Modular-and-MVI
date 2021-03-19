package com.cupsofcode.homeproject.dagger

import com.cupsofcode.network.restaurants.RestaurantNetwork
import com.cupsofcode.network.restaurants.RestaurantNetworkImpl
import com.cupsofcode.network.restaurants.RestaurantService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @BaseUrl
    fun providesBaseUrl() = "https://api.doordash.com"

    @Provides
    @Singleton
    fun providesRestaurantService(retrofit: Retrofit): RestaurantService {
        return retrofit.create(RestaurantService::class.java)
    }

    @Provides
    @Singleton
    fun providesRestaurantNetworkDataSource(client: RestaurantService): RestaurantNetwork {
        return RestaurantNetworkImpl(client)
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .callTimeout(90, TimeUnit.SECONDS)
            .connectTimeout(90, TimeUnit.SECONDS)
            .readTimeout(90, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(moshi: Moshi, okHttpClient: OkHttpClient, @BaseUrl baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(okHttpClient)
            .build()
    }
    @Provides
    @Singleton
    fun providesMoshi(): Moshi {
        return Moshi.Builder()
            .build()
    }
}