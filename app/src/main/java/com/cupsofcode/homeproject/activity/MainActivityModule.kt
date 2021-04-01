package com.cupsofcode.homeproject.activity

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.cupsofcode.homeproject.NavigatorImpl
import com.cupsofcode.navigator.Navigator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class MainActivityModule constructor(private val fragmentManager: FragmentManager, private val context: Context) {

    @Provides
    @Singleton
    fun providesNavigator(): Navigator {
        return NavigatorImpl(fragmentManager, context)
    }
}