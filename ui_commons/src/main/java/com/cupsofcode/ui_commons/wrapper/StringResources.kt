package com.cupsofcode.ui_commons.wrapper

import androidx.annotation.StringRes

interface StringResources {
    fun getString(@StringRes id: Int): String
    fun getString(@StringRes id: Int, vararg formatted: Any): String
}