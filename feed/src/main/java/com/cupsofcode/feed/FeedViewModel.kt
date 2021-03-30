package com.cupsofcode.feed

import androidx.lifecycle.ViewModel
import com.cupsofcode.feed.mvi.FeedIntent
import com.cupsofcode.feed.mvi.FeedViewState
import com.cupsofcode.feed.usecase.FeedRestaurantsUseCase
import com.cupsofcode.feed.usecase.RestaurantLikedUseCase
import com.cupsofcode.ui_commons.wrapper.StringResources
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    private val feedRestaurantsUseCase: FeedRestaurantsUseCase,
    private val likeUseCase: RestaurantLikedUseCase,
    private val stringResources: StringResources
) :
    ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val initialState = Callable {
        FeedViewState()
    }

    fun bind(intents: Observable<FeedIntent>) = bindIntents(intents)
        .scanWith(initialState, reducer)
        .doOnError { println("error:" + it) }
        .onErrorReturn {
            //can be some sort of custom error model dictated by backend response where header, body and even action buttons coming in
            FeedViewState().copy(error = it)
        }
        .distinctUntilChanged()


    private fun bindIntents(intents: Observable<FeedIntent>): Observable<FeedIntent> {
        val dataIntent = feedRestaurantsUseCase.execute()
            .map<FeedIntent> {
                FeedIntent.Data(restaurants = it.map { it.toUiModel(stringResources) })
            }
            .startWith(FeedIntent.Loading)
            .onErrorReturn {
                FeedIntent.Error(error = it)
            }

        val viewIntents = intents.publish {
            val restaurantClicked = it.ofType(FeedIntent.RestaurantClicked::class.java)
                .flatMapCompletable {
                    //open Details screen -> there will be navigator
                    println("clicked")
                    Completable.complete()
                }.toObservable<FeedIntent>()

            val likeClicked = it.ofType(FeedIntent.LikeClicked::class.java)
                .debounce(300, TimeUnit.MILLISECONDS)
                .switchMap { intent ->
                    likeUseCase.execute(intent.restaurantId, intent.isLiked)
                        .toObservable<FeedIntent>()
                        .startWith(FeedIntent.Loading)
                }

            Observable.merge(
                restaurantClicked,
                likeClicked,
                it.ofType(FeedIntent.ErrorDismissed::class.java)
            )
        }

        return Observable.merge(viewIntents, dataIntent)
    }

    private val reducer =
        BiFunction<FeedViewState, FeedIntent, FeedViewState> { previous, intent ->
            when (intent) {
                is FeedIntent.Data -> {
                    val restaurants = intent.restaurants
                    val emptyListMessage =
                        if (restaurants.isEmpty()) stringResources.getString(R.string.no_results_found) else ""
                    previous.copy(
                        restaurants = restaurants,
                        isLoading = false,
                        toolbarTitle = stringResources.getString(R.string.discover),
                        emptyListMessage = emptyListMessage
                    )
                }
                is FeedIntent.Loading -> previous.copy(isLoading = true)
                is FeedIntent.Error -> previous.copy(isLoading = false, error = intent.error)
                is FeedIntent.ErrorDismissed -> previous.copy(error = null)
                else -> previous
            }
        }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}