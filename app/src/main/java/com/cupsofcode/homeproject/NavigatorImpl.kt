package com.cupsofcode.homeproject

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.widget.Toast
import com.cupsofcode.navigator.Navigator
import io.reactivex.Completable
import javax.inject.Inject


class NavigatorImpl @Inject constructor(private val context: Context) : Navigator {

    override fun call(phoneNumber: String): Completable {
        if (phoneNumber.isEmpty()) {
            return Completable.complete()
        }
        return Completable.fromAction {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$phoneNumber")
            intent.flags = FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    override fun showToast(message: String): Completable {
        return Completable.fromAction {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}