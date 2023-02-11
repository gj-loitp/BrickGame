package com.brick.ui.settings

internal interface SettingsView {
    fun markChosenColor(oldColor: Int, newItemId: Int)
    fun setSpeedTitle(newItemId: Int)
    fun setVerticalHintsChecked(hintsEnabled: Boolean)
    fun setSquaresCountInRow(squaresCountInRow: Int)
}
