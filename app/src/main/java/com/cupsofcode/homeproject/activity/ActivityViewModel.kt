package com.cupsofcode.homeproject.activity

import androidx.lifecycle.ViewModel
import com.cupsofcode.navigator.Navigator
import com.cupsofcode.navigator.NavigatorPath
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import java.util.concurrent.Callable
import javax.inject.Inject


class ActivityViewModel @Inject constructor(private val navigator: Navigator) : ViewModel() {

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
        val loadFeed = navigator.navigateTo(NavigatorPath.Feed)
            .toObservable<ActivityIntent>()


        return loadFeed
    }

    private val reducer =
        BiFunction<ViewState, ActivityIntent, ViewState> { previous, intent ->
            previous
        }


}