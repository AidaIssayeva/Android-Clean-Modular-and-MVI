package com.cupsofcode.homeproject.activity

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.FragmentManager
import com.cupsofcode.homeproject.NavigatorImpl
import com.cupsofcode.navigator.Navigator
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class MainActivityModule constructor(
    private val fragmentManager: FragmentManager,
    private val context: Context
) {

    @Provides
    @Singleton
    fun providesNavigator(): Navigator {
        return NavigatorImpl(fragmentManager, context)
    }

    @Provides
    @Singleton
    fun providesReviewManager(): ReviewManager {
        return ReviewManagerFactory.create(context)
    }

    @Provides
    @Singleton
    fun providesSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences("shared", Context.MODE_PRIVATE)
    }
}