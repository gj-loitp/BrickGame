package com.brick.figures

import android.content.Context
import android.graphics.Path
import android.graphics.Point
import com.brick.enums.FigureType

class SquareFigure : Figure {
    constructor(
        squareWidth: Int,
        scale: Int,
        squaresCountInRow: Int,
        context: Context?
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
        context: Context?,
        point: Point?
    ) : super(
        widthSquare,
        context,
        point
    )

    constructor(
        squareWidth: Int,
        scale: Int,
        context: Context?,
        point: Point?
    ) : super(
        squareWidth,
        scale,
        context,
        point
    )

    override fun initFigureMask() {
        super.initFigureMask()
        figureMask[0][0] = true
        figureMask[0][1] = true
        figureMask[1][0] = true
        figureMask[1][1] = true
    }

    override fun getRotatedFigure(): FigureType? {
        return null
    }

    override fun getWidthInSquare(): Int {
        return 2
    }

    override fun getHeightInSquare(): Int {
        return 2
    }

    override fun getPath(): Path {
        val path = Path()
        path.moveTo(pointOnScreen.x.toFloat(), (pointOnScreen.y - scale).toFloat())
        path.lineTo(
            (pointOnScreen.x + squareWidth * 2).toFloat(),
            (pointOnScreen.y - scale).toFloat()
        )
        path.lineTo(
            (pointOnScreen.x + squareWidth * 2).toFloat(),
            (pointOnScreen.y + squareWidth * 2 - scale).toFloat()
        )
        path.lineTo(
            pointOnScreen.x.toFloat(),
            (pointOnScreen.y + squareWidth * 2 - scale).toFloat()
        )
        path.close()
        return path
    }
}
