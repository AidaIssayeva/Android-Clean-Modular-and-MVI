package com.cupsofcode.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cupsofcode.feed.dagger.FeedComponent
import com.cupsofcode.feed.epoxy.FeedEpoxyAdapter
import com.cupsofcode.feed.mvi.FeedIntent
import com.cupsofcode.navigator.NavigatorComponent
import com.cupsofcode.respository_restaurant.RestaurantComponent
import com.cupsofcode.ui_commons.wrapper.ResourcesComponent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.feed_fragment.view.*

class FeedFragment : Fragment() {

    companion object {
        fun newInstance() = FeedFragment()
    }

    private val component by lazy {
        val restaurantComponent =
            activity?.applicationContext?.getSystemService(RestaurantComponent::class.java.name)
        val resourcesComponent =
            activity?.applicationContext?.getSystemService(ResourcesComponent::class.java.name)
        val navigatorComponent =
            activity?.applicationContext?.getSystemService(NavigatorComponent::class.java.name)
        FeedComponent.builder()
            .resourceComponent(resourcesComponent!! as ResourcesComponent)
            .restaurantComponent(restaurantComponent!! as RestaurantComponent)
            .navigationComponent(navigatorComponent!! as NavigatorComponent)
            .build()
    }

    private val intentsSubject by lazy {
        PublishSubject.create<FeedIntent>()
    }
    private val viewModel by lazy {
        component.viewModel()
    }

    private val feedAdapter by lazy {
        FeedEpoxyAdapter(intentsSubject, context!!)
    }

    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.feed_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.recyclerview.layoutManager = LinearLayoutManager(context)
        view.recyclerview.adapter = feedAdapter.adapter

        compositeDisposable.add(
            viewModel.bind(intentsSubject)
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe({
                    switchLoading(view, it.isLoading)
                    switchError(it.error)
                    feedAdapter.setData(it)
                }, {
                    //Obs should not error out, because errors being passed as intent to viewstate. If compiler comes here, it means somewhere in vm stream is not being handled properly: check if you're converting to Observable when you get the error from single or completable
                })
        )
    }

    private fun switchLoading(view: View, isLoading: Boolean) {
        if (isLoading) {
            view.loader.visibility = View.VISIBLE
        } else {
            view.loader.visibility = View.GONE
        }

    }

    private fun switchError(error: Throwable?) {
        if (error != null) {
            AlertDialog.Builder(context!!)
                .setTitle(context?.getString(R.string.error_title, error.message))
                .setMessage(R.string.error_message)
                .setOnDismissListener {
                    intentsSubject.onNext(FeedIntent.ErrorDismissed)
                }
                .setPositiveButton(
                    R.string.ok
                ) { _, _ ->
                    intentsSubject.onNext(FeedIntent.ErrorDismissed)
                }.show()
        }
    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }
}