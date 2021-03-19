package com.cupsofcode.homeproject.dagger

import android.content.Context
import com.cupsofcode.homeproject.ResourcesWrapper
import com.cupsofcode.ui_commons.wrapper.StringResources
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule constructor(private val appContext: Context) {
    @Provides
    @Singleton
    fun providesContext() = appContext

    @Provides
    @Singleton
    fun providesResourcesWrapper() =
        ResourcesWrapper(appContext)

    @Provides
    @Singleton
    fun providesStringResources(resourcesWrapper: ResourcesWrapper): StringResources {
        return resourcesWrapper
    }
}