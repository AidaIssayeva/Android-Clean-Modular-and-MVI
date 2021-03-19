package com.cupsofcode.feed.dagger

import com.cupsofcode.feed.FeedViewModel
import com.cupsofcode.respository_restaurant.RestaurantComponent
import com.cupsofcode.ui_commons.wrapper.ResourcesComponent
import dagger.Component


@Component(
    dependencies = [RestaurantComponent::class, ResourcesComponent::class]
)
interface FeedComponent {

    fun viewModel(): FeedViewModel

    @Component.Builder
    interface Builder {
        fun resourceComponent(resourceComponent: ResourcesComponent): Builder
        fun restaurantComponent(component: RestaurantComponent): Builder
        fun build(): FeedComponent
    }

    companion object {
        fun builder(): Builder {
            return DaggerFeedComponent.builder()
        }
    }

}