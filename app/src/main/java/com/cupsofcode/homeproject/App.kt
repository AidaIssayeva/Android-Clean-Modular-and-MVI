package com.cupsofcode.homeproject

import android.app.Application
import com.cupsofcode.homeproject.dagger.AppComponent
import com.cupsofcode.homeproject.dagger.AppModule
import com.jakewharton.threetenabp.AndroidThreeTen


class App : Application() {
    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = AppComponent.builder()
            .appModule(AppModule(this))
            .build()

        AndroidThreeTen.init(this)
    }

    private fun flattenInterface(clazz: Class<*>): Set<Class<*>> {
        return clazz.interfaces
            .flatMap {
                flattenInterface(it) + it
            }
            .toSet()
    }

    private val componentInterfaces = flattenInterface(AppComponent::class.java)

    override fun getSystemService(name: String): Any? {
        componentInterfaces.forEach {
            if (it.name == name) {
                return component
            }
        }
        return super.getSystemService(name)
    }
}