package com.cupsofcode.homeproject.activity

import com.cupsofcode.navigator.NavigatorComponent
import com.google.android.play.core.review.ReviewManager
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [MainActivityModule::class]

)
interface MainActivityComponent: NavigatorComponent {

    fun viewModel(): ActivityViewModel

    fun reviewManager(): ReviewManager

    @Component.Builder
    interface Builder {
        fun activityModule(module: MainActivityModule): Builder
        fun build(): MainActivityComponent
    }

    companion object {
        fun builder(): MainActivityComponent.Builder {
            return DaggerMainActivityComponent.builder()
        }
    }
}