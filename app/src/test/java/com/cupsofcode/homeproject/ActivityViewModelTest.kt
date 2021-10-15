package com.cupsofcode.homeproject

import android.content.SharedPreferences
import com.cupsofcode.homeproject.activity.ActivityIntent
import com.cupsofcode.homeproject.activity.ActivityViewModel
import com.cupsofcode.homeproject.activity.ViewState
import com.cupsofcode.navigator.Navigator
import com.cupsofcode.navigator.NavigatorPath
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.testing.FakeReviewManager
import com.google.android.play.core.tasks.OnCompleteListener
import com.google.android.play.core.tasks.Task
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.slot
import io.reactivex.Completable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import java.lang.ClassCastException
import java.util.concurrent.TimeUnit


class ActivityViewModelTest {

    @RelaxedMockK
    private lateinit var navigator: Navigator

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
    fun `should return a viewstate without any reviewinfo because number of sessions is not 5,10,20 `() {
        //given
        val expectedViewStates = arrayOf(
            ViewState()
        )
        every { sharedPreferences.getInt("session", 1) } returns 4

        //when
        val resultsObserver = activityViewModel.bind(intentSubject).test()

        //then
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        resultsObserver.assertValues(*expectedViewStates)
    }

    @Test
    fun `should return a viewstate with reviewinfo because number of sessions is 5,10,20 && retrieving reviewInfo was successful `() {
        //given
        val mockReviewInfo = mockk<ReviewInfo>(relaxed = true)
        val mockReviewInstance = mockk<Task<ReviewInfo>>(relaxed = true)

        val expectedViewStates = arrayOf(
            ViewState(),
            ViewState().copy(reviewInfo = mockReviewInfo)
        )

        every { mockReviewInstance.isSuccessful } returns true
        every { mockReviewInstance.result } returns mockReviewInfo
        every { reviewManager.requestReviewFlow() } returns mockReviewInstance
        every { sharedPreferences.getInt("session", 1) } returns 5

        val slot = slot<OnCompleteListener<ReviewInfo>>()
        every { mockReviewInstance.addOnCompleteListener(capture(slot)) } answers {
            slot.captured.onComplete(mockReviewInstance)
            mockReviewInstance
        }

        //when
        val resultsObserver = activityViewModel.bind(intentSubject).test()


        //then
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        resultsObserver.assertValues(*expectedViewStates)
    }

    @Test
    fun `should return a viewstate with reviewinfo because number of sessions is 5,10,20 && retrieving reviewInfo was NOT successful `() {
        //given
        val mockReviewInfo = mockk<ReviewInfo>(relaxed = true)
        val mockReviewInstance = mockk<Task<ReviewInfo>>(relaxed = true)

        val expectedViewStates = arrayOf(
            ViewState()
        )

        every { mockReviewInstance.isSuccessful } returns false
        every { mockReviewInstance.result } returns mockReviewInfo
        every { mockReviewInstance.exception } returns Exception(Unsuccessful_Task)
        every { reviewManager.requestReviewFlow() } returns mockReviewInstance
        every { sharedPreferences.getInt("session", 1) } returns 5

        val slot = slot<OnCompleteListener<ReviewInfo>>()
        every { mockReviewInstance.addOnCompleteListener(capture(slot)) } answers {
            slot.captured.onComplete(mockReviewInstance)
            mockReviewInstance
        }

        //when
        val resultsObserver = activityViewModel.bind(intentSubject).test()


        //then
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        resultsObserver.assertValues(*expectedViewStates)
    }

    @Test
    fun `should successfully navigate to feed after in-app review completed`() {
        //given
        val expectedViewStates = arrayOf(
            ViewState()
        )
        every { navigator.navigateTo(NavigatorPath.Feed) } returns Completable.complete()


        //when
        val resultsObserver = activityViewModel.bind(intentSubject).test()
        intentSubject.onNext(ActivityIntent.InAppReviewCompleted)

        //then
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        resultsObserver.assertValues(*expectedViewStates)
    }

    @Test
    fun `should update viewstate with error after in-app review completed`() {
        //given
        val error = Throwable(Error)
        val expectedViewStates = arrayOf(
            ViewState(),
            ViewState().copy(error = error)
        )
        every { navigator.navigateTo(NavigatorPath.Feed) } returns Completable.error(error)


        //when
        val resultsObserver = activityViewModel.bind(intentSubject).test()
        intentSubject.onNext(ActivityIntent.InAppReviewCompleted)

        //then
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        resultsObserver.assertValues(*expectedViewStates)
    }

    @Test
    fun `save session number successfully`() {
        //given
        val expectedViewStates = arrayOf(
            ViewState()
        )

        //when
        val resultsObserver = activityViewModel.bind(intentSubject).test()
        intentSubject.onNext(ActivityIntent.Session)

        //then
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        resultsObserver.assertValues(*expectedViewStates)
    }

    @Test
    fun `save session number failed`() { //TODO: fix
        //given
        val error = Throwable(Error)
        val expectedViewStates = arrayOf(
            ViewState(),
            ViewState().copy(error = error)
        )


        //when
        val resultsObserver = activityViewModel.bind(intentSubject).test()
        intentSubject.onNext(ActivityIntent.Session)

        //then
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        resultsObserver.assertValues(*expectedViewStates)
    }

    companion object {
        private const val Unsuccessful_Task = "Unsuccessful_Task_From_Play_Core_API"
        private const val Error = "Error"
    }
}