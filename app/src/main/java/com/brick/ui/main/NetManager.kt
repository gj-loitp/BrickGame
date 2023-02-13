package com.brick.ui.main

import android.graphics.Path
import android.util.Log
import com.brick.BuildConfig
import com.brick.Values.EXTRA_ROWS
import com.brick.enums.FigureState
import com.brick.figures.Figure
import com.brick.ui.main.listeners.OnNetChangedListener
import com.brick.utils.Utils.createSmallSquareFigure

class NetManager(
    private val onNetChangedListener: OnNetChangedListener,
    verticalSquaresCount: Int,
    horizontalSquaresCount: Int,
    widthOfSquareSide: Int,
    scale: Int
) {
    private var verticalSquaresCount = 0
    private var horizontalSquaresCount = 0
    private var figure: Figure? = null
    private var net: Array<BooleanArray>? = null
    private lateinit var zeroNet: Array<BooleanArray>
    private var squareWidth = 0
    private var scale = 0

    companion object {
        var combo = 0
    }

    init {
        initNet(
            verticalSquaresCount = verticalSquaresCount,
            horizontalSquaresCount = horizontalSquaresCount,
            widthOfSquareSide = widthOfSquareSide,
            scale = scale
        )
    }

    private fun initNet(
        verticalSquaresCount: Int,
        horizontalSquaresCount: Int,
        widthOfSquareSide: Int,
        scale: Int
    ) {
        net = Array(verticalSquaresCount + EXTRA_ROWS) {
            BooleanArray(horizontalSquaresCount)
        }
        net?.let {
            setFalseNet(it)
        }
        squareWidth = widthOfSquareSide
        this.scale = scale
        this.verticalSquaresCount = verticalSquaresCount
        this.horizontalSquaresCount = horizontalSquaresCount
    }

    fun initRotatedFigure(figure: Figure) {
        this.figure?.initMaskWithFalse()
        copyMaskToNet()
        initFigure(figure)
    }

    @Suppress("unused")
    fun getNetRowCount(): Int {
        return net?.size ?: 0
    }

    @Suppress("unused")
    fun getNetColumnCount(): Int {
        return net?.get(0)?.size ?: 0
    }

    fun initFigure(figure: Figure) {
        this.figure = figure
        zeroNet = Array(figure.heightInSquare) { BooleanArray(1) }
        copyMaskToNet()
    }

    fun canRotate(rotatedFigure: Figure): Boolean {
        var result = false
        if (rotatedFigure.pointInNet.x + rotatedFigure.widthInSquare <= horizontalSquaresCount && rotatedFigure.pointInNet.y + rotatedFigure.heightInSquare < verticalSquaresCount && isNetFreeToMoveDown()) {
            result = true
        }
        return result
    }

    fun resetMaskBeforeMoveWithFalse() {
        figure?.let { f ->
            val falseFigureMast = Array(
                f.heightInSquare
            ) { BooleanArray(f.widthInSquare) }
            for (i in f.figureMask.indices) {
                val startHorizontalPos = getStartHorizontalPosition(f.figureMask[i])
                val endPosition = getEndHorizontalPosition(f.figureMask[i])

                net?.let { n ->
                    System.arraycopy(
                        falseFigureMast[i], startHorizontalPos, n[f.pointInNet.y + i],
                        f.pointInNet.x + startHorizontalPos, endPosition
                    )
                }
            }
        }
    }

    private fun setFalseNet(net: Array<BooleanArray>) {
        for (i in net.indices) {
            for (j in net[0].indices) {
                net[i][j] = false
            }
        }
    }

    private fun copyMaskToNet() {
        figure?.let { f ->
            copyArrays(
                size = f.figureMask.size,
                sourceArray = f.figureMask,
                destinationArray = net,
                destinationPosition = f.currentX,
                length = f.figureMask[0].size
            )
        }
    }

    private fun resetNetAfterMoving(destinationPosition: Int) {
        net?.let { n ->
            figure?.let { f ->
                for (i in zeroNet.indices) {
                    System.arraycopy(
                        zeroNet[i], 0,
                        n[f.pointInNet.y + i], destinationPosition, 1
                    )
                }
            }
        }
    }

    private fun moveFigure() {
        figure?.let { f ->
            net?.let { n ->
                for (i in f.figureMask.indices) {
                    val startHorizontalPos = getStartHorizontalPosition(f.figureMask[i])
                    val endPosition = getEndHorizontalPosition(f.figureMask[i])
                    System.arraycopy(
                        /* p0 = */ f.figureMask[i], /* p1 = */ startHorizontalPos,
                        /* p2 = */ n[f.pointInNet.y + i],
                        /* p3 = */ f.pointInNet.x + startHorizontalPos, /* p4 = */ endPosition
                    )
                }
            }
        }
    }

    private fun copyArrays(
        size: Int,
        sourceArray: Array<BooleanArray>,
        destinationArray: Array<BooleanArray>?,
        destinationPosition: Int,
        length: Int
    ) {
        destinationArray?.let { da ->
            figure?.let { f ->
                for (i in 0 until size) {
                    System.arraycopy(
                        sourceArray[i], 0, da[f.currentY + i],
                        destinationPosition, length
                    )
                }
            }
        }
    }

    private fun getStartVerticalPosition(
        mask: Array<BooleanArray>,
        column: Int
    ): Int {
        var position = 0
        for (i in mask.indices) {
            if (mask[i][column]) {
                position = i
                break
            }
        }
        return position
    }

    private fun getEndVerticalPosition(
        mask: Array<BooleanArray>,
        column: Int
    ): Int {
        var trueCount = 0
        for (aMask in mask) {
            if (aMask[column]) {
                trueCount += 1
            }
        }
        return trueCount
    }

    private fun getStartHorizontalPosition(mask: BooleanArray): Int {
        var position = 0
        for (i in mask.indices) {
            if (mask[i]) {
                position = i
                break
            }
        }
        return position
    }

    private fun getEndHorizontalPosition(mask: BooleanArray): Int {
        var trueCount = 0
        for (aMask in mask) {
            if (aMask) {
                trueCount += 1
            }
        }
        return trueCount
    }

    //todo think of rotated figure
    private fun isFigureBelow(): Boolean {
        var result = false
        figure?.let { f ->
            val coordinateX = f.pointInNet.x
            for (i in f.figureMask.size downTo 1) {
                val startHorizontalPos = getStartHorizontalPosition(f.figureMask[i - 1])
                val endHorizontalPos = getEndHorizontalPosition(f.figureMask[i - 1])
                for (j in coordinateX + startHorizontalPos until coordinateX + endHorizontalPos + startHorizontalPos) {
                    val startVerticalPos =
                        getStartVerticalPosition(f.figureMask, j - coordinateX)
                    val endVerticalPos =
                        getEndVerticalPosition(f.figureMask, j - coordinateX)
                    if (net!![f.pointInNet.y + startVerticalPos + endVerticalPos][j]) {
                        result = true
                        break
                    }
                }
            }
        }
        return result
    }

    private fun isFigureLeft(): Boolean {
        var result = false
        figure?.let { f ->
            val coordinateY = f.pointInNet.y
            val coordinateX = f.pointInNet.x
            for (i in 0 until f.heightInSquare) {
                val startHorizontalPos = getStartHorizontalPosition(f.figureMask[i])
                if (net!![coordinateY + i][coordinateX + startHorizontalPos - 1]) {
                    result = true
                    break
                }
            }
        }
        return result
    }

    private fun isFigureRight(): Boolean {
        var result = false
        figure?.let { f ->
            val coordinateY = f.pointInNet.y
            val coordinateX = f.pointInNet.x
            for (i in 0 until f.heightInSquare) {
                val startHorizontalPos = getStartHorizontalPosition(f.figureMask[i])
                val endHorizontalPos = getEndHorizontalPosition(f.figureMask[i])
                if (net!![coordinateY + i][coordinateX + startHorizontalPos + endHorizontalPos]) {
                    result = true
                    break
                }
            }
        }
        return result
    }

    private fun levelDownNet(level: Int, rowsCount: Int) {
        val tmpNet = Array(verticalSquaresCount + EXTRA_ROWS) {
            BooleanArray(horizontalSquaresCount)
        }
        net?.let { n ->
            for (i in n.indices) {
                System.arraycopy(n[i], 0, tmpNet[i], 0, n[i].size)
            }
            for (i in 0..n.size - level) {
                for (j in n[0].indices) {
                    n[i][j] = false
                }
            }
            for (i in 0 until n.size - level) {
                System.arraycopy(tmpNet[i], 0, n[i + rowsCount], 0, tmpNet[i].size)
            }
        }
    }

    private fun isHorizontalLineTrue(booleans: BooleanArray): Boolean {
        var result = false
        var j = 0
        for (i in 0 until horizontalSquaresCount) {
            if (booleans[i]) {
                j++
            }
        }
        if (j == horizontalSquaresCount) {
            result = true
        }
        return result
    }

    private fun getMaxCountOfTrue(values: Array<IntArray>): Int {
        var max = values[1][0]
        for (value in values) {
            if (value[0] > max) {
                max = value[0]
            }
        }
        return max
    }

    fun getStoppedFiguresPaths(): ArrayList<Path> {
        val paths = ArrayList<Path>()
        for (i in horizontalSquaresCount - 1 downTo 0) {
            for (j in verticalSquaresCount + EXTRA_ROWS - 1 downTo 0) {
                net?.let { n ->
                    if (n[j][i]) {
                        val path = createSmallSquareFigure(
                            i = i,
                            j = j,
                            squareWidth = squareWidth,
                            scale = scale
                        )
                        paths.add(path)
                    }
                }
            }
        }
        return paths
    }

    fun isVerticalLineComplete(): Boolean {
        var result = false
        val values = Array(horizontalSquaresCount) { IntArray(1) }
        for (i in 0 until horizontalSquaresCount) {
            for (j in EXTRA_ROWS - 1 until verticalSquaresCount + EXTRA_ROWS) {
                if (net!![j][i]) {
                    values[i][0] = verticalSquaresCount + EXTRA_ROWS - j
                    break
                }
            }
            if (getMaxCountOfTrue(values) >= verticalSquaresCount + 1) {
                result = true
                figure?.state = FigureState.STOPPED
                onNetChangedListener.onTopLineHasTrue()
                break
            }
        }
        return !result
    }

    fun changeFigureState() {
        if (!isNetFreeToMoveDown()) {
            figure?.state = FigureState.STOPPED
            onNetChangedListener.onFigureStoppedMove()
        }
    }

    fun checkBottomLine() {
        var skippedRows = 0
        var rowsCount = 0
        for (i in verticalSquaresCount + EXTRA_ROWS - 1 downTo 1) {
            net?.let { n ->
                if (isHorizontalLineTrue(n[i])) {
                    rowsCount++
                    skippedRows = verticalSquaresCount + EXTRA_ROWS - i
                }
            }
        }
        if (skippedRows != 0) {
            levelDownNet(skippedRows, rowsCount)
            combo = rowsCount
            onNetChangedListener.onBottomLineIsTrue()
        }
    }

    fun isNetFreeToMoveDown(): Boolean {
        var result = false
        figure?.let { f ->
            if (f.pointInNet.y + f.heightInSquare != verticalSquaresCount + EXTRA_ROWS && !isFigureBelow()) {
                result = true
            }
        }
        return result
    }

    fun isNetFreeToMoveLeft(): Boolean {
        var result = false
        if (figure?.pointInNet?.x != 0 && isNetFreeToMoveDown() && !isFigureLeft()) {
            result = true
        }
        return result
    }

    fun isNetFreeToMoveRight(): Boolean {
        var result = false
        figure?.let { f ->
            if (f.pointInNet.x + f.widthInSquare < horizontalSquaresCount && isNetFreeToMoveDown() && !isFigureRight()) {
                result = true
            }
        }
        return result
    }

    fun moveRightInNet() {
        setFalseNet(zeroNet)
        moveFigure()
        figure?.let { f ->
            resetNetAfterMoving(destinationPosition = f.pointInNet.x - 1)
        }
    }

    fun moveLeftInNet() {
        setFalseNet(zeroNet)
        moveFigure()
        figure?.let { f ->
            resetNetAfterMoving(f.pointInNet.x + f.widthInSquare)
        }
    }

    fun moveDownInNet() {
        try {
            figure?.let { f ->
                val zeroNet = Array(1) {
                    BooleanArray(f.widthInSquare)
                }
                var coordinateY = f.pointInNet.y
                coordinateY--
                for (i in f.figureMask.size downTo 1) {
                    val startPosition = getStartHorizontalPosition(f.figureMask[i - 1])
                    val endPosition = getEndHorizontalPosition(f.figureMask[i - 1])
                    net?.let { n ->
                        System.arraycopy(
                            /* p0 = */ f.figureMask[i - 1],
                            /* p1 = */ startPosition,
                            /* p2 = */ n[coordinateY + i],
                            /* p3 = */ f.pointInNet.x + startPosition,
                            /* p4 = */ endPosition
                        )
                        for (zero in zeroNet) {
                            System.arraycopy(
                                /* p0 = */ zero,
                                /* p1 = */ startPosition,
                                /* p2 = */ n[coordinateY + i - 1],
                                /* p3 = */ f.pointInNet.x + startPosition,
                                /* p4 = */ endPosition
                            )
                        }
                    }
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun printNet() {
        if (net == null) {
            return
        }
        val str = StringBuilder()
        net?.let { n ->
            for (aNet in n) {
                for (j in n[0].indices) {
                    str.append(if (aNet[j]) 1 else 0)
                    str.append(" ")
                }
                str.append('\n')
            }
        }

        if (BuildConfig.DEBUG) Log.d("logNet", str.toString())
    }
}
