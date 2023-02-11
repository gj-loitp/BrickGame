package com.brick.ui.settings

import com.brick.R
import com.brick.Values.DEFAULT_COLOR
import com.brick.data.Pref
import com.brick.enums.FigureSpeed
import com.brick.utils.Utils.getFiguresSpeedByMillis
import com.brick.utils.Utils.getViewIdByColor

internal class SettingsPresenter(
    private val settingsView: SettingsView?,
    private val pref: Pref
) {
    fun setValues() {
        val figureSpeed = getFiguresSpeedByMillis(pref.figuresSpeed)
        if (settingsView != null) {
            settingsView.markChosenColor(DEFAULT_COLOR, getViewIdByColor(pref.figuresColor))
            settingsView.setSquaresCountInRow(pref.squaresCountInRow)
            settingsView.setSpeedTitle(figureSpeed.speedItemId)
            settingsView.setVerticalHintsChecked(pref.isHintsEnabled)
        }
    }

    fun setSquareCountInRow(newValue: Int) {
        pref.squaresCountInRow = newValue
    }

    fun getEvent(id: Int) {
        when (id) {
            R.id.vLFigureColor -> manageColorPicking(R.color.lFigure, id)
            R.id.vSquareFigureColor -> manageColorPicking(R.color.squareFigure, id)
            R.id.vLongFigureColor -> manageColorPicking(R.color.longFigure, id)
            R.id.vZFigureColor -> manageColorPicking(R.color.zFigure, id)
            R.id.vTFigureColor -> manageColorPicking(R.color.tFigure, id)
            R.id.vJFigureColor -> manageColorPicking(R.color.jFigure, id)
            R.id.sEnableHints -> {
                val isEnabled = pref.isHintsEnabled
                pref.isHintsEnabled = !isEnabled
            }
            R.id.tvVeryFast -> manageSpeedPicking(FigureSpeed.VERY_FAST.figureSpeedInMillis, id)
            R.id.tvFast -> manageSpeedPicking(FigureSpeed.FAST.figureSpeedInMillis, id)
            R.id.tvDefault -> manageSpeedPicking(FigureSpeed.DEFAULT.figureSpeedInMillis, id)
            R.id.tvSlow -> manageSpeedPicking(FigureSpeed.SLOW.figureSpeedInMillis, id)
            R.id.tvVerySlow -> manageSpeedPicking(FigureSpeed.VERY_SLOW.figureSpeedInMillis, id)
            else -> {}
        }
    }

    private fun manageSpeedPicking(
        newSpeed: Long,
        newItemId: Int
    ) {
        pref.figuresSpeed = newSpeed
        settingsView?.setSpeedTitle(newItemId)
    }

    private fun manageColorPicking(
        newColor: Int,
        newItemId: Int
    ) {
        val oldColor = pref.figuresColor
        pref.figuresColor = newColor
        settingsView?.markChosenColor(oldColor, newItemId)
    }
}
