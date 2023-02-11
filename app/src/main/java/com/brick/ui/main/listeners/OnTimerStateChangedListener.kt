package com.brick.ui.main.listeners

interface OnTimerStateChangedListener {
    fun isTimerRunning(isRunning: Boolean)
    fun disableAllControls()
}
