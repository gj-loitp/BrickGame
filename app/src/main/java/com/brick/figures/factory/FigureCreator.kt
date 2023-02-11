package com.brick.figures.factory

import com.brick.enums.FigureType
import java.util.*

class FigureCreator {
    private val random: Random = Random()
    private var currentFigureType: FigureType?
    var nextFigureType: FigureType
        private set
    private var mainFigureType: MainFigureType? = null

    init {
        currentFigureType = null
        nextFigureType = newFigure
    }

    private fun initFigures() {
        currentFigureType = nextFigureType
        nextFigureType = newFigure
    }

    fun getCurrentFigureType(): FigureType {
        return currentFigureType ?: newFigure
    }

    fun createNextFigure(): FigureType {
        initFigures()
        return nextFigureType
    }

    private val newFigure: FigureType
        get() {
            val nextType = mainFigureType
            var nextFigure = nextFigureType
            while (nextType == mainFigureType) {
                mainFigureType =
                    MainFigureType.values()[random.nextInt(MainFigureType.values().size)]
                mainFigureType?.let {
                    nextFigure = it.types[random.nextInt(it.types.size)]
                }
            }
            return nextFigure
        }

    private enum class MainFigureType(vararg types: FigureType) {
        LONG_TYPE(
            FigureType.LONG_FIGURE,
            FigureType.LONG_SECOND_FIGURE
        ),
        SQUARE_TYPE(FigureType.SQUARE_FIGURE),
        L_TYPE(
            FigureType.L_FIGURE,
            FigureType.L_SECOND_FIGURE,
            FigureType.L_THIRD_FIGURE,
            FigureType.L_FOURTH_FIGURE
        ),
        T_TYPE(
            FigureType.T_FIGURE,
            FigureType.T_SECOND_FIGURE,
            FigureType.T_THIRD_FIGURE,
            FigureType.T_FOURTH_FIGURE
        ),
        J_TYPE(
            FigureType.J_FIGURE,
            FigureType.J_SECOND_FIGURE,
            FigureType.J_THIRD_FIGURE,
            FigureType.J_FOURTH_FIGURE
        ),
        S_TYPE(FigureType.S_FIGURE, FigureType.S_SECOND_FIGURE), Z_TYPE(
            FigureType.Z_FIGURE,
            FigureType.Z_SECOND_FIGURE
        );

        val types: Array<FigureType>

        init {
            this.types = types as Array<FigureType>
        }
    }
}
