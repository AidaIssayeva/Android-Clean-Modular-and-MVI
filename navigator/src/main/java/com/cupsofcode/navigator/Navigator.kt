package com.cupsofcode.navigator

import io.reactivex.Completable

interface Navigator {
    fun showToast(message: String): Completable
    fun call(phoneNumber: String): Completable
}