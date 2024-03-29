package com.cupsofcode.homeproject.activity

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.cupsofcode.homeproject.R
import com.cupsofcode.navigator.NavigatorComponent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    private val component by lazy {
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

    private val reviewManager by lazy {
        component.reviewManager()
    }

    private val alertDialog by lazy {
        AlertDialog.Builder(this@MainActivity)
            .setOnDismissListener {
                intentsSubject.onNext(ActivityIntent.DialogDismissed)
            }
            .setMessage(R.string.error_message)
            .setPositiveButton(
                R.string.ok
            ) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        compositeDisposable.add(
            viewModel.bind(intentsSubject.hide())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe({ viewState ->
                    viewState.reviewInfo?.run {
                        val review = reviewManager.launchReviewFlow(this@MainActivity, this)
                        review.addOnCompleteListener {
                            intentsSubject.onNext(ActivityIntent.InAppReviewCompleted)
                        }
                    }

                    viewState.error?.run {
                        alertDialog.setTitle(
                            getString(
                                R.string.error_title,
                                viewState.error.message
                            )
                        )
                        alertDialog.show()
                    } ?: alertDialog.dismiss()
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

    override fun onStop() {
        intentsSubject.onNext(ActivityIntent.Session)
        super.onStop()
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}