package com.cupsofcode.homeproject.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cupsofcode.feed.FeedFragment
import com.cupsofcode.feed.mvi.FeedIntent
import com.cupsofcode.homeproject.R
import com.cupsofcode.navigator.NavigatorComponent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    private val component by lazy {
        val navigatorComponent =
            applicationContext?.getSystemService(NavigatorComponent::class.java.name)
        MainActivityComponent.builder()
            .activityModule(MainActivityModule(supportFragmentManager, applicationContext))
            .build()
    }
    private val viewModel by lazy {
        component.viewModel()
    }
    private val intentsSubject by lazy {
        PublishSubject.create<ActivityIntent>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        compositeDisposable.add(
            viewModel.bind(intentsSubject.hide())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe({

                }, {

                })
        )
    }

    override fun getSystemService(name: String): Any? {
        return if (name == NavigatorComponent::class.java.name) {
            component
        } else {
            return super.getSystemService(name)
        }
    }

}