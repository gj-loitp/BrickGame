package com.brick.figures.figure_long

import android.content.Context
import android.graphics.Path
import android.graphics.Point
import com.brick.enums.FigureType
import com.brick.figures.Figure

class LongSecondFigure : Figure {
    constructor(
        squareWidth: Int,
        scale: Int,
        squaresCountInRow: Int,
        context: Context
    ) : super(
        squareWidth,
        scale,
        squaresCountInRow,
        context
    )

    constructor(
        widthSquare: Int,
        context: Context,
        point: Point
    ) : super(
        widthSquare,
        context,
        point
    )

    constructor(
        widthSquare: Int,
        scale: Int,
        context: Context,
        point: Point
    ) : super(
        widthSquare,
        scale,
        context,
        point
    )

    override fun initFigureMask() {
        super.initFigureMask()
        figureMask[0][0] = true
        figureMask[0][1] = true
        figureMask[0][2] = true
        figureMask[0][3] = true
    }

    override val rotatedFigure: FigureType
        get() = FigureType.LONG_FIGURE
    override val widthInSquare: Int
        get() = 4
    override val heightInSquare: Int
        get() = 1
    override val path: Path
        get() {
            val path = Path()
            path.moveTo(pointOnScreen.x.toFloat(), (pointOnScreen.y - scale).toFloat())
            path.lineTo(
                pointOnScreen.x.toFloat(),
                (pointOnScreen.y + squareWidth - scale).toFloat()
            )
            path.lineTo(
                (pointOnScreen.x + squareWidth * 4).toFloat(),
                (pointOnScreen.y + squareWidth - scale).toFloat()
            )
            path.lineTo(
                (pointOnScreen.x + squareWidth * 4).toFloat(),
                (pointOnScreen.y - scale).toFloat()
            )
            path.close()
            return path
        }
}
