package com.brick.ui.main

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.brick.R
import com.brick.Values
import com.brick.databinding.ActivityMainBinding
import com.brick.ui.main.listeners.OnTimerStateChangedListener
import com.brick.ui.main.views.PlayingAreaView
import com.brick.ui.main.views.PreviewAreaView
import com.brick.ui.main.views.ScoreView
import com.brick.utils.DebouncedOnClickListener

class MainActivity : AppCompatActivity(), OnTimerStateChangedListener {
    @JvmField
    @BindView(R.id.playingArea)
    var playingAreaView: PlayingAreaView? = null

    @JvmField
    @BindView(R.id.tvScore)
    var scoreView: ScoreView? = null

    @JvmField
    @BindView(R.id.tvNextFigure)
    var previewAreaView: PreviewAreaView? = null

    @JvmField
    @BindView(R.id.ivPausePlay)
    var playPauseImage: ImageView? = null

    @JvmField
    @BindView(R.id.ivRotate)
    var rotateImage: ImageView? = null

    @JvmField
    @BindView(R.id.ivMoveDown)
    var moveDownImage: ImageView? = null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)
        playingAreaView!!.setDependencies(scoreView, previewAreaView, this)
        playingAreaView!!.cleanup()
        playingAreaView!!.createFigureWithDelay()
        val rotate = findViewById<ImageView>(R.id.ivRotate)
        rotate.setOnClickListener(object :
            DebouncedOnClickListener(Values.DEBOUNCE_DELAY_IN_MILLIS) {
            override fun onDebouncedClick(v: View?) {
                playingAreaView!!.rotate()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (playingAreaView!!.isTimerRunning()) {
            playingAreaView!!.startTimer()
            setControlsEnabled(true)
        }
    }

    override fun onStop() {
        playingAreaView!!.cancelTimer()
        super.onStop()
    }

    override fun onDestroy() {
        playingAreaView!!.cleanup()
        super.onDestroy()
    }

    private fun setControlsEnabled(isRunning: Boolean) {
        rotateImage!!.isEnabled = isRunning
        moveDownImage!!.isEnabled = isRunning
    }

    @OnClick(R.id.ivMoveDown)
    fun moveDown() {
        playingAreaView!!.fastMoveDown()
    }

    @OnClick(R.id.ivPausePlay)
    fun pausePlay() {
        playingAreaView!!.handleTimerState()
    }

    override fun isTimerRunning(isRunning: Boolean) {
        playPauseImage!!.setImageResource(if (isRunning) R.drawable.ic_pause else R.drawable.ic_resume)
        setControlsEnabled(isRunning)
    }

    override fun disableAllControls() {
        playPauseImage!!.isEnabled = false
        setControlsEnabled(false)
    }
}