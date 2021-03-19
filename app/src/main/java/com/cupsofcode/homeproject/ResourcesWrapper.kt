package com.cupsofcode.homeproject

import android.content.Context
import com.cupsofcode.ui_commons.wrapper.StringResources
import javax.inject.Inject


class ResourcesWrapper @Inject constructor(private val context: Context): StringResources {

    override fun getString(id: Int): String {
       return context.getString(id)
    }

    override fun getString(id: Int, vararg formatted: Any): String {
       return context.getString(id, *formatted)
    }
}