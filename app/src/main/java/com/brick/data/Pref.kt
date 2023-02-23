package com.brick.data

import android.content.Context
import android.content.SharedPreferences
import com.brick.Values.DEFAULT_COLOR
import com.brick.Values.DEFAULT_SPEED
import com.brick.Values.DEFAULT_VALUE
import com.brick.Values.ENABLED_HINTS
import com.brick.Values.ENABLE_HINTS_KEY
import com.brick.Values.FIGURE_COLOR_KEY
import com.brick.Values.FIGURE_SPEED_KEY
import com.brick.Values.FIRST_VALUE_KEY
import com.brick.Values.PREFERENCES_KEY
import com.brick.Values.SECOND_VALUE_KEY
import com.brick.Values.SQUARES_COUNT_IN_ROW
import com.brick.Values.SQUARES_COUNT_IN_ROW_KEY
import com.brick.Values.THIRD_VALUE_KEY
import com.brick.utils.NotificationUtil

class Pref(private val context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor? = null

    fun putNewScore(newScore: Int) {
        editor = preferences.edit()
        val firstValue = preferences.getInt(FIRST_VALUE_KEY, DEFAULT_VALUE)
        val secondValue = preferences.getInt(SECOND_VALUE_KEY, DEFAULT_VALUE)
        val thirdValue = preferences.getInt(THIRD_VALUE_KEY, DEFAULT_VALUE)
        if (newScore > firstValue && newScore != DEFAULT_VALUE) {
            editor?.apply {
                putInt(FIRST_VALUE_KEY, newScore)
                putInt(SECOND_VALUE_KEY, firstValue)
                putInt(THIRD_VALUE_KEY, secondValue)
            }
            NotificationUtil(context, newScore).createNotification()
        } else if (newScore > secondValue && newScore != DEFAULT_VALUE) {
            editor?.apply {
                putInt(SECOND_VALUE_KEY, newScore)
                putInt(THIRD_VALUE_KEY, secondValue)
            }
            NotificationUtil(context, newScore).createNotification()
        } else if (newScore > thirdValue && newScore != DEFAULT_VALUE) {
            editor?.putInt(THIRD_VALUE_KEY, newScore)
            NotificationUtil(context, newScore).createNotification()
        }
        editor?.apply {
            apply()
//            commit()
        }
    }

    var figuresColor: Int
        get() = preferences.getInt(FIGURE_COLOR_KEY, DEFAULT_COLOR)
        set(color) {
            editor = preferences.edit()
            editor?.apply {
                putInt(FIGURE_COLOR_KEY, color)
                apply()
//                commit()
            }
        }

    var squaresCountInRow: Int
        get() = preferences.getInt(SQUARES_COUNT_IN_ROW_KEY, SQUARES_COUNT_IN_ROW)
        set(count) {
            editor = preferences.edit()
            editor?.apply {
                putInt(SQUARES_COUNT_IN_ROW_KEY, count)
                apply()
//                commit()
            }
        }

    var isHintsEnabled: Boolean
        get() = preferences.getBoolean(ENABLE_HINTS_KEY, ENABLED_HINTS)
        set(isEnabled) {
            editor = preferences.edit()
            editor?.apply {
                putBoolean(ENABLE_HINTS_KEY, isEnabled)
                apply()
//                commit()
            }
        }

    var figuresSpeed: Long
        get() = preferences.getLong(FIGURE_SPEED_KEY, DEFAULT_SPEED)
        set(speed) {
            editor = preferences.edit()
            editor?.apply {
                putLong(FIGURE_SPEED_KEY, speed)
                apply()
//                commit()
            }
        }

    val firstValue: String
        get() = preferences.getInt(FIRST_VALUE_KEY, DEFAULT_VALUE).toString()

    val secondValue: String
        get() = preferences.getInt(SECOND_VALUE_KEY, DEFAULT_VALUE).toString()

    val thirdValue: String
        get() = preferences.getInt(THIRD_VALUE_KEY, DEFAULT_VALUE).toString()
}
