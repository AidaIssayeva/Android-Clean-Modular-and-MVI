package com.cupsofcode.homeproject

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.cupsofcode.feed.FeedFragment
import com.cupsofcode.navigator.Navigator
import com.cupsofcode.navigator.NavigatorPath
import io.reactivex.Completable
import javax.inject.Inject


class NavigatorImpl @Inject constructor(
    private val fragmentManager: FragmentManager,
    private val context: Context
) : Navigator {

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

    override fun navigateTo(path: NavigatorPath): Completable {
        return Completable.fromAction {
            val fragment: Fragment? = when (path) {
                is NavigatorPath.Feed -> FeedFragment.newInstance()
                is NavigatorPath.Details -> null
            }
            if (fragment != null) {
                fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(fragment::class.java.name)
                    .commit()
            }
        }
    }
}