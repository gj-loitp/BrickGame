package com.brick.ui.main.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.brick.figures.Figure

var PREVIEW_AREA_WIDTH = 0

class PreviewAreaView : View {
    private var paint: Paint? = null
    private var figure: Figure? = null

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        paint = Paint()
    }

    fun drawNextFigure(figure: Figure?) {
        this.figure = figure
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        PREVIEW_AREA_WIDTH = MeasureSpec.getSize(widthMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        figure?.let { f ->
            val path = f.path
            paint?.color = f.color

            path?.let { _path ->
                paint?.let { _paint ->
                    canvas.drawPath(_path, _paint)
                }
            }
        }
    }
}
