package com.brick.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.brick.R
import com.brick.Values
import com.brick.databinding.ActivityMainBinding
import com.brick.ui.main.listeners.OnTimerStateChangedListener
import com.brick.utils.DebouncedOnClickListener
import com.brick.utils.setSafeOnClickListener

class MainActivity : AppCompatActivity(), OnTimerStateChangedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playingArea.setDependencies(
            scoreView = binding.tvScore,
            previewAreaView = binding.tvNextFigure,
            onTimerStateChangedListener = this
        )
        binding.playingArea.cleanup()
        binding.playingArea.createFigureWithDelay()
//        binding.ivRotate.setOnClickListener(object :
//            DebouncedOnClickListener(Values.DEBOUNCE_DELAY_IN_MILLIS) {
//            override fun onDebouncedClick(v: View?) {
//                binding.playingArea.rotate()
//            }
//        })
        binding.ivRotate.setSafeOnClickListener {
            binding.playingArea.rotate()
        }
        binding.ivMoveDown.setSafeOnClickListener {
            moveDown()
        }
        binding.ivPausePlay.setSafeOnClickListener {
            pausePlay()
        }
        binding.ivBack.setSafeOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        if (binding.playingArea.isTimerRunning()) {
            binding.playingArea.startTimer()
            setControlsEnabled(true)
        }
    }

    override fun onStop() {
        binding.playingArea.cancelTimer()
        super.onStop()
    }

    override fun onDestroy() {
        binding.playingArea.cleanup()
        super.onDestroy()
    }

    private fun setControlsEnabled(isRunning: Boolean) {
        binding.ivRotate.isEnabled = isRunning
        binding.ivMoveDown.isEnabled = isRunning
    }

    private fun moveDown() {
        binding.playingArea.fastMoveDown()
    }

    private fun pausePlay() {
        binding.playingArea.handleTimerState()
    }

    override fun isTimerRunning(isRunning: Boolean) {
        binding.tvPaused.isVisible = !isRunning
        binding.ivPausePlay.setImageResource(if (isRunning) R.drawable.baseline_pause_black_48 else R.drawable.baseline_play_arrow_black_48)
        setControlsEnabled(isRunning)
    }

    override fun disableAllControls() {
        binding.ivPausePlay.isEnabled = false
        setControlsEnabled(false)
    }
}
