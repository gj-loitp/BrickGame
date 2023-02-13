package com.brick.ui.start

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.brick.BuildConfig
import com.brick.databinding.ActivityStartBinding
import com.brick.ui.main.MainActivity
import com.brick.ui.score.ScoreActivity
import com.brick.ui.settings.SettingsActivity
import com.brick.utils.AnimationUtil.getSlideInLeft
import com.brick.utils.AnimationUtil.getSlideInRight
import com.brick.utils.AnimationUtil.getZoomIn
import com.brick.utils.openBrowserPolicy
import com.brick.utils.setSafeOnClickListener

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvVersion.text =
            "©🅡🅞🅨➒➌🅖🅡🅞🅤🅟\n🅥🅔🅡🅢🅘🅞🅝\n${BuildConfig.VERSION_NAME}"
        binding.tvPolicy.apply {
            paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
            setSafeOnClickListener {
                this@StartActivity.openBrowserPolicy()
            }
        }
        binding.bStartGame.setSafeOnClickListener {
            startGame()
        }
        binding.bOpenScores.setSafeOnClickListener {
            openScores()
        }
        binding.ivSettings.setSafeOnClickListener {
            openSettings()
        }
    }

    override fun onResume() {
        super.onResume()

        binding.tvGameTitle.startAnimation(getZoomIn(this))
        binding.tvVersion.startAnimation(getZoomIn(this))
        binding.bStartGame.startAnimation(getZoomIn(this))
        binding.bOpenScores.startAnimation(getZoomIn(this))
        binding.ivSettings.startAnimation(getZoomIn(this))
        binding.tvPolicy.startAnimation(getZoomIn(this))
    }

    private fun startGame() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun openScores() {
        startActivity(Intent(this, ScoreActivity::class.java))
    }

    private fun openSettings() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }
}