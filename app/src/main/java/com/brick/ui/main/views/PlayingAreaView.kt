package com.brick.ui.main.views

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.core.content.ContextCompat
import com.brick.BuildConfig
import com.brick.R
import com.brick.Values
import com.brick.Values.COUNT_DOWN_INTERVAL
import com.brick.Values.GAME_OVER_DELAY_IN_MILLIS
import com.brick.Values.LINE_WIDTH
import com.brick.data.Pref
import com.brick.enums.FigureState
import com.brick.figures.Figure
import com.brick.figures.factory.FigureCreator
import com.brick.figures.factory.FigureFactory.getFigure
import com.brick.ui.main.NetManager
import com.brick.ui.main.listeners.OnNetChangedListener
import com.brick.ui.main.listeners.OnPlayingAreaTouch
import com.brick.ui.main.listeners.OnTimerStateChangedListener
import com.brick.ui.main.listeners.OnViewTouchListener

class PlayingAreaView : View, OnNetChangedListener, OnPlayingAreaTouch {
    private var squareWidth = 0
    private var verticalSquareCount = 0
    private var screenHeight = 0
    private var screenWidth = 0
    private var scale = 0
    private var squaresInRowCount = 0
    private var isTimerRunning = false
    private var isGameOver = false
    private var currentFigure: Figure? = null
    private var netManager: NetManager? = null
    private var figureCreator: FigureCreator? = null
    private var scoreView: ScoreView? = null
    private var previewAreaView: PreviewAreaView? = null
    private lateinit var pref: Pref
    private var onViewTouchListener: OnViewTouchListener? = null
    private lateinit var paint: Paint
    private var timer: CountDownTimer? = null
    private lateinit var context: Context
    private var onTimerStateChangedListener: OnTimerStateChangedListener? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        init(context)
    }

    private fun init(context: Context) {
        paint = Paint()
        figureCreator = FigureCreator()
        pref = Pref(getContext())
        onViewTouchListener = OnViewTouchListener(context, this)
        setOnTouchListener(onViewTouchListener)
        squaresInRowCount = pref.squaresCountInRow
        this.context = context
        isTimerRunning = true
        isGameOver = false
    }

    override fun onDraw(canvas: Canvas) {
        paint.color = Color.BLACK
        paint.strokeWidth = LINE_WIDTH
        drawHorizontalLines(canvas)
        drawVerticalLines(canvas)
        if (currentFigure != null && currentFigure?.state === FigureState.MOVING && isTimerRunning) startMoveDown()
        netManager?.let { nm ->
            for (squarePath in nm.getStoppedFiguresPaths()) {
//                    paint.color = resources.getColor(pref.figuresColor)
                paint.color = ContextCompat.getColor(context, pref.figuresColor)
                canvas.drawPath(squarePath, paint)
            }
        }
    }

    override fun onMeasure(
        widthMeasureSpec: Int, heightMeasureSpec: Int
    ) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        squareWidth = MeasureSpec.getSize(widthMeasureSpec) / squaresInRowCount
        verticalSquareCount = MeasureSpec.getSize(heightMeasureSpec) / squareWidth
        scale = squareWidth - MeasureSpec.getSize(heightMeasureSpec) % squareWidth
        screenHeight = MeasureSpec.getSize(heightMeasureSpec)
        screenWidth = MeasureSpec.getSize(widthMeasureSpec)
        onViewTouchListener?.setScreenWidth(screenWidth)
    }

    fun cleanup() {
        scoreView?.score?.let {
            pref.putNewScore(it)
        }
        cancelTimer()
        scoreView?.setStartValue()
        netManager = null
        currentFigure = null
    }

    fun setDependencies(
        scoreView: ScoreView?,
        previewAreaView: PreviewAreaView?,
        onTimerStateChangedListener: OnTimerStateChangedListener?
    ) {
        this.scoreView = scoreView
        this.previewAreaView = previewAreaView
        this.onTimerStateChangedListener = onTimerStateChangedListener
    }

    private fun drawHorizontalLines(canvas: Canvas) {
        for (i in 1..verticalSquareCount) {
            canvas.drawLine(
                /* startX = */ 0f,
                /* startY = */ (screenHeight - squareWidth * i).toFloat(),
                /* stopX = */ screenWidth.toFloat(),
                /* stopY = */ (screenHeight - squareWidth * i).toFloat(),
                /* paint = */ paint
            )
        }
    }

    private fun drawVerticalLines(canvas: Canvas) {
        for (i in 1..squaresInRowCount) {
            if (pref.isHintsEnabled) drawVerticalHints(i)
            canvas.drawLine(
                /* startX = */ (i * squareWidth).toFloat(),
                /* startY = */ 0f,
                /* stopX = */ (i * squareWidth).toFloat(),
                /* stopY = */ screenHeight.toFloat(),
                /* paint = */ paint
            )
        }
    }

    private fun drawVerticalHints(line: Int) {
        currentFigure?.let { f ->
            if (line == f.currentX || line == f.currentX + f.widthInSquare) {
                paint.color = ContextCompat.getColor(context, R.color.colorPrimaryTransparent)
                paint.strokeWidth = LINE_WIDTH * 4
            } else {
                paint.color = Color.BLACK
                paint.strokeWidth = LINE_WIDTH
            }
        }
    }

    fun isTimerRunning(): Boolean {
        return isTimerRunning && !isGameOver
    }

    fun handleTimerState() {
        if (isTimerRunning) {
            cancelTimer()
        } else {
            startTimer()
        }
        isTimerRunning = !isTimerRunning
        if (!isGameOver) onTimerStateChangedListener?.isTimerRunning(isTimerRunning)
    }

    fun startTimer() {
        timer?.start()
    }

    fun cancelTimer() {
        timer?.cancel()
    }

    private fun startMoveDown() {
        if (BuildConfig.DEBUG) {
            netManager?.printNet()
        }
        cancelTimer()
        timer = object : CountDownTimer(pref.figuresSpeed, COUNT_DOWN_INTERVAL.toLong()) {
            override fun onTick(millisUntilFinished: Long) {
                isTimerRunning = true
            }

            override fun onFinish() {
                currentFigure?.let { f ->
                    if (f.state === FigureState.MOVING) {
                        f.moveDown()
                        netManager?.moveDownInNet()
                        invalidate()
                    }
                }
            }
        }
        netManager?.let { nm ->
            if (!nm.isNetFreeToMoveDown()) {
                nm.changeFigureState()
                cancelTimer()
            } else {
                startTimer()
            }
        }
    }

    fun fastMoveDown() {
        currentFigure?.let { f ->
            if (isTimerRunning) {
                cancelTimer()
                while (f.state === FigureState.MOVING) {
                    netManager?.let { nm ->
                        if (!nm.isNetFreeToMoveDown()) {
                            nm.changeFigureState()
                        }
                    }
                    f.moveDown()
                    netManager?.moveDownInNet()
                }
                invalidate()
            }
        }
    }

    private fun moveRightFast() {
        currentFigure?.let { f ->
            if (isTimerRunning) {
                cancelTimer()
                netManager?.let { nm ->
                    while (nm.isNetFreeToMoveRight()) {
                        nm.resetMaskBeforeMoveWithFalse()
                        f.moveRight()
                        nm.moveRightInNet()
                    }
                }
                invalidate()
            }
        }
    }

    private fun moveLeftFast() {
        currentFigure?.let { f ->
            if (isTimerRunning) {
                cancelTimer()
                netManager?.let { nm ->
                    while (nm.isNetFreeToMoveLeft()) {
                        nm.resetMaskBeforeMoveWithFalse()
                        f.moveLeft()
                        nm.moveLeftInNet()
                    }
                }
                invalidate()
            }
        }
    }

    //todo think about the restrictions
    fun rotate() {
        currentFigure?.let { f ->
            if (f.state === FigureState.MOVING && f.rotatedFigure != null && isTimerRunning) {
                val figure = getFigure(
                    figureType = f.rotatedFigure,
                    widthSquare = squareWidth,
                    scale = scale,
                    context = context,
                    point = f.pointOnScreen
                )
                if (figure != null) {
                    figure.initFigureMask()
                    netManager?.let { nm ->
                        if (nm.canRotate(figure)) {
                            currentFigure = figure
                            nm.checkBottomLine()
                            nm.initRotatedFigure(figure)
                        }
                    }
                }
            }
        }
    }

    private fun createFigure() {
        val figure = getFigure(
            figureType = figureCreator?.getCurrentFigureType(),
            widthSquare = squareWidth,
            scale = scale, squaresCountInRow = squaresInRowCount,
            context = context
        )
        if (figure != null) {
            currentFigure = figure
            if (netManager == null) {
                netManager = NetManager(
                    /* onNetChangedListener = */ this,
                    /* verticalSquaresCount = */ verticalSquareCount,
                    /* horizontalSquaresCount = */ squaresInRowCount,
                    /* widthOfSquareSide = */ squareWidth,
                    /* scale = */ scale
                )
            }
            netManager?.let { nm ->
                if (nm.isVerticalLineComplete()) {
                    nm.checkBottomLine()
                    currentFigure?.let {
                        nm.initFigure(it)
                    }
                    if (BuildConfig.DEBUG) {
                        nm.printNet()
                    }
                    invalidate()
                }
            }
        }
    }

    private fun moveLeft() {
        netManager?.let { nm ->
            if (nm.isNetFreeToMoveLeft() && isTimerRunning) {
                nm.resetMaskBeforeMoveWithFalse()
                currentFigure?.moveLeft()
                nm.moveLeftInNet()
                if (BuildConfig.DEBUG) {
                    nm.printNet()
                }
                invalidate()
            }
        }
    }

    private fun moveRight() {
        netManager?.let { nm ->
            if (nm.isNetFreeToMoveRight() && isTimerRunning) {
                nm.resetMaskBeforeMoveWithFalse()
                currentFigure?.moveRight()
                nm.moveRightInNet()
                if (BuildConfig.DEBUG) {
                    nm.printNet()
                }
                invalidate()
            }
        }
    }

    fun createFigureWithDelay() {
        Handler(Looper.getMainLooper()).postDelayed({
            previewAreaView?.drawNextFigure(
                getFigure(
                    figureType = figureCreator?.nextFigureType,
                    widthSquare = squareWidth * squaresInRowCount / Values.SQUARES_COUNT_IN_ROW,
                    context = context
                )
            )
            createFigure()
        }, Values.DELAY_IN_MILLIS)
    }

    override fun onFigureStoppedMove() {
        netManager?.let { nm ->
            if (nm.isVerticalLineComplete()) {
                scoreView?.sumScoreWhenFigureStopped()
                previewAreaView?.drawNextFigure(
                    /* figure = */ getFigure(
                        figureType = figureCreator?.createNextFigure(),
                        widthSquare = squareWidth * squaresInRowCount / Values.SQUARES_COUNT_IN_ROW,
                        context = context
                    )
                )
                createFigure()
            }
        }
    }

    override fun onBottomLineIsTrue() {
        scoreView?.sumScoreWhenBottomLineIsTrue(squaresInRowCount)
    }

    override fun onTopLineHasTrue() {
        isGameOver = true
        cancelTimer()
        //TODO switch to popup
        onTimerStateChangedListener?.disableAllControls()
        Toast.makeText(context, context.getString(R.string.game_over_text), Toast.LENGTH_LONG)
            .show()
        Handler(Looper.getMainLooper()).postDelayed(
            {
                (context as Activity?)?.finish()
            },
            GAME_OVER_DELAY_IN_MILLIS
        )
    }

    override fun onRightMove() {
        moveRight()
    }

    override fun onLeftMove() {
        moveLeft()
    }

    override fun onLongLeftClick() {
        moveLeftFast()
    }

    override fun onLongRightClick() {
        moveRightFast()
    }
}
