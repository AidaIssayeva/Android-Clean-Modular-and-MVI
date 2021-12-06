package com.cupsofcode.feed

import com.cupsofcode.feed.mvi.FeedIntent
import com.cupsofcode.feed.mvi.FeedViewState
import com.cupsofcode.feed.usecase.FeedRestaurantsUseCase
import com.cupsofcode.feed.usecase.RestaurantLikedUseCase
import com.cupsofcode.navigator.Navigator
import com.cupsofcode.ui_commons.wrapper.StringResources
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.reactivex.Observable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class FeedViewModelTest {
    @RelaxedMockK
    private lateinit var useCase: FeedRestaurantsUseCase

    @RelaxedMockK
    private lateinit var likedUseCase: RestaurantLikedUseCase

    @RelaxedMockK
    private lateinit var navigator: Navigator

    @RelaxedMockK
    private lateinit var stringResources: StringResources

    private val testScheduler = TestScheduler()
    private lateinit var feedViewModel: FeedViewModel
    private lateinit var intentSubject: PublishSubject<FeedIntent>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
        feedViewModel = FeedViewModel(useCase, likedUseCase, stringResources, navigator)
        intentSubject = PublishSubject.create<FeedIntent>()
    }

    @Test
    fun `should return an initial viewState when viewmodel is binded`() {
        //given the initialized viewmodel

        //when it gets binded
        val resultsObserver = feedViewModel.bind(intentSubject).test()

        //then
        resultsObserver.assertValue(FeedViewState())
    }

    @Test
    fun `should return a viewstate filled with the list of restaurants`() {
        //given
        val expectedResult = arrayOf(
            FeedViewState(),
            FeedViewState(isLoading = true),
            FeedViewState(restaurants = restaurants.map { it.toUiModel(stringResources) }, toolbarTitle = stringResources.getString(R.string.discover))
        )
        every { useCase.execute() } returns Observable.just(restaurants)

        // When
        val resultsObserver = feedViewModel.bind(intentSubject).test()

        // Then
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        resultsObserver.assertValues(*expectedResult)
    }

    @Test
    fun `should return a viewstate with error when retrieving fails`() {
        //given
        val error = Throwable("error")
        val expectedResult = arrayOf(
            FeedViewState(),
            FeedViewState(isLoading = true),
            FeedViewState(error = error)
        )
        every { useCase.execute() } returns Observable.error(error)

        //when
        val resultsObserver = feedViewModel.bind(intentSubject).test()

        //then
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        resultsObserver.assertValues(*expectedResult)
    }

    @Test
    fun `should show empty results message if backend returns nothing`(){
        //given
        val expectedResult = arrayOf(
            FeedViewState(),
            FeedViewState(isLoading = true),
            FeedViewState(restaurants = emptyList(),
                toolbarTitle = stringResources.getString(R.string.discover),
                emptyListMessage =  stringResources.getString(R.string.no_results_found))
        )
        every { useCase.execute() } returns Observable.just(emptyList())

        //when
        val resultsObserver = feedViewModel.bind(intentSubject).test()

        //then
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        resultsObserver.assertValues(*expectedResult)
    }
}