package com.cupsofcode.homeproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cupsofcode.feed.FeedFragment

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    addFragment(FeedFragment.newInstance())
  }

  private fun addFragment(fragment: Fragment){
    supportFragmentManager
      .beginTransaction()
      .replace(R.id.fragment_container, fragment)
      .addToBackStack(fragment::class.java.name)
      .commit()
  }

}