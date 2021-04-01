package com.cupsofcode.homeproject.dagger

import com.cupsofcode.datasource_restaurant.RestaurantDataModule
import com.cupsofcode.navigator.NavigatorComponent
import com.cupsofcode.respository_restaurant.RestaurantComponent
import com.cupsofcode.ui_commons.wrapper.ResourcesComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RestaurantDataModule::class, NetworkModule::class, AppModule::class]
)
interface AppComponent: RestaurantComponent, ResourcesComponent {

    @Component.Builder
    interface Builder {
        fun appModule(applicationModule: AppModule): Builder
        fun build(): AppComponent
    }

    companion object {
        fun builder(): Builder {
            return DaggerAppComponent.builder()
        }
    }
}