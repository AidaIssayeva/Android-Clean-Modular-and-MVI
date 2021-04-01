package com.cupsofcode.navigator


sealed class NavigatorPath {
    object Feed : NavigatorPath()
    data class Details(val id: String) : NavigatorPath()
}