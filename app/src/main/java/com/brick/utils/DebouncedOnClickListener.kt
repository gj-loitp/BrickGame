package com.brick.utils

import android.os.SystemClock
import android.view.View
import java.util.*
import kotlin.math.abs

abstract class DebouncedOnClickListener(private val minimumIntervalMillis: Long) :
    View.OnClickListener {
    private val lastClickMap: MutableMap<View, Long>
    abstract fun onDebouncedClick(v: View?)

    init {
        lastClickMap = WeakHashMap()
    }

    override fun onClick(clickedView: View) {
        val previousClickTimestamp = lastClickMap[clickedView]
        val currentTimestamp = SystemClock.uptimeMillis()
        lastClickMap[clickedView] = currentTimestamp
        if (previousClickTimestamp == null || abs(currentTimestamp - previousClickTimestamp) > minimumIntervalMillis) {
            onDebouncedClick(clickedView)
        }
    }
}
