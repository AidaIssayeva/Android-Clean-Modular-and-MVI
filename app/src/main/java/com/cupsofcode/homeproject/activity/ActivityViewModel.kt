package com.cupsofcode.homeproject.activity

import androidx.lifecycle.ViewModel
import com.cupsofcode.navigator.Navigator
import com.cupsofcode.navigator.NavigatorPath
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import java.util.concurrent.Callable
import javax.inject.Inject


class ActivityViewModel @Inject constructor(
    private val navigator: Navigator,
    private val reviewManager: ReviewManager
) : ViewModel() {

    private val initialState = Callable {
        ViewState()
    }

    fun bind(intents: Observable<ActivityIntent>) = bindIntents(intents)
        .scanWith(initialState, reducer)
        .doOnError { println("error:" + it) }
        .onErrorReturn {
            ViewState().copy(error = it)
        }
        .distinctUntilChanged()

    private fun bindIntents(intents: Observable<ActivityIntent>): Observable<ActivityIntent> {

        val obtainReviewInfo = Single.create<ReviewInfo> { emitter ->
            val reviewInfo = reviewManager.requestReviewFlow()
            reviewInfo.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    emitter.onSuccess(task.result)
                } else {
                    emitter.onError(Throwable(task.exception))
                }

            }
        }.map<ActivityIntent> {
            ActivityIntent.InAppReviewRequested(it)
        }.onErrorReturn { error ->
            //If this throws an error, do you need to display it to a user? Not really.
            // We can just log an error.
            ActivityIntent.NoOp
        }.toObservable()

        val viewIntents = intents.publish {
            val inAppReviewCompleted = it.ofType(ActivityIntent.InAppReviewCompleted::class.java)
                .flatMapCompletable {

                    //after received a signal that review flow ended, we proceed with regular flow
                    navigator.navigateTo(NavigatorPath.Feed)
                }.toObservable<ActivityIntent>()

            Observable.merge(inAppReviewCompleted, obtainReviewInfo)
        }

        val ass = Observable.just(ActivityIntent.NoOp)
        return Observable.merge(ass, viewIntents)
    }

    private val reducer =
        BiFunction<ViewState, ActivityIntent, ViewState> { previous, intent ->
            when (intent) {
                is ActivityIntent.InAppReviewRequested -> {
                    previous.copy(reviewInfo = intent.reviewInfo)
                }
                is ActivityIntent.Error -> {
                    previous.copy(error = intent.throwable, reviewInfo = null)
                }
                else -> previous.copy(reviewInfo = null)
            }
        }


}