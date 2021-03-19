package com.cupsofcode.feed

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement


class RxSchedulerRule : TestRule {

    private val scheduler by lazy { Schedulers.trampoline() }

    override fun apply(base: Statement?, description: Description?): Statement =
        object : Statement() {
            override fun evaluate() {
                try {
                    RxJavaPlugins.setIoSchedulerHandler { scheduler }
                    RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler }
                    base?.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
}