package com.brick.figures

import android.content.Context
import android.graphics.Path
import android.graphics.Point
import androidx.core.content.ContextCompat
import com.brick.R
import com.brick.Values.EXTRA_ROWS
import com.brick.enums.FigureState
import com.brick.enums.FigureType
import java.util.*

abstract class Figure {
    var state: FigureState
    private val context: Context

    @JvmField
    protected val squareWidth: Int

    @JvmField
    var scale: Int

    @JvmField
    val pointOnScreen: Point

    @JvmField
    val pointInNet: Point

    lateinit var figureMask: Array<BooleanArray>

    protected constructor(
        squareWidth: Int,
        scale: Int,
        squaresCountInRow: Int,
        context: Context
    ) {
        this.squareWidth = squareWidth
        pointOnScreen = initPoint(squaresCountInRow)
        this.scale = scale
        state = FigureState.MOVING
        this.context = context
        pointInNet = Point(
            getCoordinateInNet(squareWidth, pointOnScreen.x),
            EXTRA_ROWS - heightInSquare
        )
        initFigureMask()
    }

    protected constructor(
        squareWidth: Int,
        scale: Int,
        context: Context,
        pointOnScreen: Point
    ) {
        this.squareWidth = squareWidth
        this.pointOnScreen = pointOnScreen
        this.scale = scale
        state = FigureState.MOVING
        this.context = context
        pointInNet = Point(
            /* x = */ getCoordinateInNet(squareWidth, pointOnScreen.x),
            /* y = */ getCoordinateInNet(squareWidth, pointOnScreen.y)
        )
    }

    protected constructor(
        widthSquare: Int,
        context: Context,
        pointOnScreen: Point
    ) {
        squareWidth = widthSquare / 2
        this.pointOnScreen = Point(pointOnScreen.x, pointOnScreen.y)
        scale = 0
        state = FigureState.MOVING
        this.context = context
        pointInNet = Point(pointOnScreen.x, pointOnScreen.y)
    }

    private fun initPoint(squaresCountInRow: Int): Point {
        val arrayOfPositions = ArrayList<Int>()
        for (i in 2 until squaresCountInRow - EXTRA_ROWS) {
            arrayOfPositions.add(i * squareWidth)
        }
        val position = Random().nextInt(arrayOfPositions.size)
        return Point(arrayOfPositions[position], 0)
    }

    private fun getCoordinateInNet(
        squareWidth: Int,
        coordinate: Int
    ): Int {
        return coordinate / squareWidth
    }

    val currentX: Int
        get() = pointInNet.x
    val currentY: Int
        get() = pointInNet.y

    open fun initFigureMask() {
        figureMask = Array(heightInSquare) {
            BooleanArray(
                widthInSquare
            )
        }
    }

    fun initMaskWithFalse() {
        for (i in 0 until heightInSquare) {
            for (j in 0 until widthInSquare) {
                figureMask?.let { array ->
                    array[i][j] = false
                }
            }
        }
    }

    fun moveLeft() {
        pointOnScreen[pointOnScreen.x - squareWidth] = pointOnScreen.y
        pointInNet[pointInNet.x - 1] = pointInNet.y
    }

    fun moveRight() {
        pointOnScreen[pointOnScreen.x + squareWidth] = pointOnScreen.y
        pointInNet[pointInNet.x + 1] = pointInNet.y
    }

    fun moveDown() {
        pointOnScreen[pointOnScreen.x] = pointOnScreen.y + squareWidth
        pointInNet[pointInNet.x] = pointInNet.y + 1
    }

    val color: Int get() = ContextCompat.getColor(context, R.color.colorPrimaryTransparent)
    abstract val rotatedFigure: FigureType?
    abstract val widthInSquare: Int
    abstract val heightInSquare: Int
    abstract val path: Path?
}
