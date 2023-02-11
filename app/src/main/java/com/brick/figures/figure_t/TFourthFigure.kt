package com.brick.figures.figure_t

import android.content.Context
import android.graphics.Path
import android.graphics.Point
import com.brick.enums.FigureType
import com.brick.figures.Figure

class TFourthFigure : Figure {
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
    ) {
        this.scale += squareWidth
    }

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
        squareWidth: Int,
        scale: Int,
        context: Context,
        point: Point
    ) : super(
        squareWidth,
        scale,
        context,
        point
    )

    override fun initFigureMask() {
        super.initFigureMask()
        figureMask[0][1] = true
        figureMask[1][0] = true
        figureMask[1][1] = true
        figureMask[2][1] = true
    }

    override val rotatedFigure: FigureType
        get() = FigureType.T_FIGURE
    override val widthInSquare: Int
        get() = 2
    override val heightInSquare: Int
        get() = 3
    override val path: Path
        get() {
            val path = Path()
            path.moveTo(pointOnScreen.x.toFloat(), (pointOnScreen.y - scale).toFloat())
            path.lineTo(
                pointOnScreen.x.toFloat(),
                (pointOnScreen.y + squareWidth - scale).toFloat()
            )
            path.lineTo(
                (pointOnScreen.x + squareWidth).toFloat(),
                (pointOnScreen.y + squareWidth - scale).toFloat()
            )
            path.lineTo(
                (pointOnScreen.x + squareWidth).toFloat(),
                (pointOnScreen.y + squareWidth * 2 - scale).toFloat()
            )
            path.lineTo(
                (pointOnScreen.x + squareWidth * 2).toFloat(),
                (pointOnScreen.y + squareWidth * 2 - scale).toFloat()
            )
            path.lineTo(
                (pointOnScreen.x + squareWidth * 2).toFloat(),
                (pointOnScreen.y - squareWidth - scale).toFloat()
            )
            path.lineTo(
                (pointOnScreen.x + squareWidth).toFloat(),
                (pointOnScreen.y - squareWidth - scale).toFloat()
            )
            path.lineTo(
                (pointOnScreen.x + squareWidth).toFloat(),
                (pointOnScreen.y - scale).toFloat()
            )
            path.close()
            return path
        }
}
