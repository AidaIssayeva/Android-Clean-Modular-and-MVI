package com.cupsofcode.homeproject

import android.content.Context
import android.content.SharedPreferences
import com.cupsofcode.feed.mvi.FeedViewState
import com.cupsofcode.homeproject.activity.ActivityIntent
import com.cupsofcode.homeproject.activity.ActivityViewModel
import com.cupsofcode.homeproject.activity.ViewState
import com.cupsofcode.navigator.Navigator
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.testing.FakeReviewManager
import com.google.android.play.core.tasks.Task
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit


class ActivityViewModelTest {

    @RelaxedMockK
    private lateinit var navigator: Navigator

    private val context = mockk<Context>(relaxed = true)

    private val reviewManager: FakeReviewManager = mockk(relaxed = true)

    @RelaxedMockK
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var activityViewModel: ActivityViewModel

    private lateinit var intentSubject: PublishSubject<ActivityIntent>

    private val testScheduler = TestScheduler()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }

        activityViewModel = ActivityViewModel(
            navigator = navigator,
            reviewManager = reviewManager,
            sharedPreferences = sharedPreferences
        )
        intentSubject = PublishSubject.create<ActivityIntent>()

    }

    @Test
    fun `should return an initial viewState when viewmodel is binded`() {
        //given the initialized viewmodel

        //when it gets binded
        val resultsObserver = activityViewModel.bind(intentSubject).test()

        //then
        resultsObserver.assertValue(ViewState())
    }

    @Test
    fun `should return a viewstate with reviewInfo to launch `() {
        //given
        val mockRequestInfo = mockk<Task<ReviewInfo>>(relaxed = true)

        val viewstates = arrayOf(
            ViewState(),
            ViewState().copy(
                reviewInfo = mockRequestInfo.result
            )
        )
        every { reviewManager.requestReviewFlow() } returns mockRequestInfo
        every { sharedPreferences.getInt("session", 1) } returns 5

        //when
        val resultsObserver = activityViewModel.bind(intentSubject).test()


        //then
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        resultsObserver.assertValues(*viewstates)
    }
}