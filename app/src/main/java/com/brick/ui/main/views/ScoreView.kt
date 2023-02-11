package com.brick.ui.main.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.brick.Values.DEFAULT_VALUE
import com.brick.Values.FIGURE_STOPPED_SCORE
import com.brick.ui.main.NetManager

class ScoreView : AppCompatTextView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(
        context, attrs
    )

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )

    var score: Int
        get() = text.toString().toInt()
        private set(score) {
            this.text = score.toString()
        }

    fun setStartValue() {
        score = DEFAULT_VALUE
    }

    fun sumScoreWhenFigureStopped() {
        var scoreValue = score
        scoreValue += FIGURE_STOPPED_SCORE
        score = scoreValue
    }

    fun sumScoreWhenBottomLineIsTrue(squaresInRowCount: Int) {
        var scoreValue = score
        scoreValue += squaresInRowCount * FIGURE_STOPPED_SCORE * NetManager.combo
        score = scoreValue
    }
}
