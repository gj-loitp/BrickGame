package com.brick

import com.brick.enums.FigureSpeed

object Values {
    private const val NAMESPACE = "db"
    const val EXTRA_ROWS = 4
    const val FIGURE_STOPPED_SCORE = 10
    const val COUNT_DOWN_INTERVAL = 750
    const val DELAY_IN_MILLIS: Long = 1500
    const val DEBOUNCE_DELAY_IN_MILLIS: Long = 450
    const val GAME_OVER_DELAY_IN_MILLIS: Long = 4000
    const val LINE_WIDTH = 1f

    const val PLAY_MARKET_URL = "https://play.google.com/store/apps/details?id="
    const val SHARE_INTENT_TYPE = "text/plain"
    const val DEV_NAME = "Roy93Group"

    /*PREFERENCES*/
    const val PREFERENCES_KEY = NAMESPACE + "PREFERENCE_KEY"
    const val FIRST_VALUE_KEY = "first_value"
    const val SECOND_VALUE_KEY = "second_value"
    const val THIRD_VALUE_KEY = "third_value"
    const val DEFAULT_VALUE = 0
    const val FIGURE_COLOR_KEY = "default_color"
    const val DEFAULT_COLOR = R.color.zFigure
    const val FIGURE_SPEED_KEY = "default_speed"

    @JvmField
    val DEFAULT_SPEED = FigureSpeed.DEFAULT.figureSpeedInMillis
    const val ENABLE_HINTS_KEY = "enable_hints_key"
    const val ENABLED_HINTS = true
    const val SQUARES_COUNT_IN_ROW_KEY = "default_squares_in_row"
    const val SQUARES_COUNT_IN_ROW = 10

    /*NOTIFICATIONS*/
    const val NOTIFICATION_ID = 123
    const val CHANNEL_NAME = "SCORES"
    const val SCORE_CHANNEL = "$NAMESPACE.scores"
}
