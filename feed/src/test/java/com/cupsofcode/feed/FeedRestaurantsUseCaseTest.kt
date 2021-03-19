package com.cupsofcode.feed

import com.cupsofcode.feed.mvi.FeedIntent
import com.cupsofcode.feed.usecase.FeedRestaurantsUseCase
import com.cupsofcode.respository_restaurant.RestaurantRepository
import com.cupsofcode.respository_restaurant.model.Restaurant
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class FeedRestaurantsUseCaseTest {
    @RelaxedMockK
    lateinit var repository: RestaurantRepository

    @get:Rule
    val rule = RxSchedulerRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should return sorted list of restaurants by open time and then distance`() {
        //given
        val useCase = FeedRestaurantsUseCase(repository)
        val expectedResult = sortedRestaurants
            every { repository.getNearbyRestaurants(37.422740, -122.139956) } returns  Observable.just(restaurants)

        //when
       val test = useCase.execute().test()

        //then
        verify {
            repository.getNearbyRestaurants(37.422740, -122.139956)
        }
        test.assertResult(expectedResult)
    }
}