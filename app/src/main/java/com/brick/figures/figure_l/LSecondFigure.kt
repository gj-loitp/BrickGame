package com.brick.figures.figure_l

import android.content.Context
import android.graphics.Path
import android.graphics.Point
import com.brick.enums.FigureType
import com.brick.figures.Figure

class LSecondFigure : Figure {
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
        figureMask[0][2] = true
        figureMask[1][0] = true
        figureMask[1][1] = true
        figureMask[1][2] = true
    }

    override val rotatedFigure: FigureType
        get() = FigureType.L_FIGURE
    override val widthInSquare: Int
        get() = 3
    override val heightInSquare: Int
        get() = 2
    override val path: Path
        get() {
            val path = Path()
            path.moveTo(pointOnScreen.x.toFloat(), (pointOnScreen.y - scale).toFloat())
            path.lineTo(
                pointOnScreen.x.toFloat(),
                (pointOnScreen.y + squareWidth - scale).toFloat()
            )
            path.lineTo(
                (pointOnScreen.x + squareWidth * 3).toFloat(),
                (pointOnScreen.y + squareWidth - scale).toFloat()
            )
            path.lineTo(
                (pointOnScreen.x + squareWidth * 3).toFloat(),
                (pointOnScreen.y - squareWidth - scale).toFloat()
            )
            path.lineTo(
                (pointOnScreen.x + squareWidth * 2).toFloat(),
                (pointOnScreen.y - squareWidth - scale).toFloat()
            )
            path.lineTo(
                (pointOnScreen.x + squareWidth * 2).toFloat(),
                (pointOnScreen.y - scale).toFloat()
            )
            path.close()
            return path
        }
}
