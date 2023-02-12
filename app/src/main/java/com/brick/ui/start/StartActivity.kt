package com.brick.ui.start

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.brick.BuildConfig
import com.brick.R
import com.brick.databinding.ActivityStartBinding
import com.brick.ui.main.MainActivity
import com.brick.ui.score.ScoreActivity
import com.brick.ui.settings.SettingsActivity
import com.brick.utils.AnimationUtil.getSlideInLeft
import com.brick.utils.AnimationUtil.getSlideInRight
import com.brick.utils.AnimationUtil.getZoomIn

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTitleAnimation()
        setButtonAnimation()

        binding.tvVersion.text = "©Roy93Group\nVersion ${BuildConfig.VERSION_NAME}"
        binding.bStartGame.setOnClickListener {
            startGame()
        }
        binding.bOpenScores.setOnClickListener {
            openScores()
        }
        binding.ivSettings.setOnClickListener {
            openSettings()
        }
    }

    override fun onResume() {
        super.onResume()
        setTitleAnimation()
    }

    private fun setTitleAnimation() {
        binding.tvGameTitle.startAnimation(getZoomIn(this))
    }

    private fun setButtonAnimation() {
        binding.bStartGame.startAnimation(getSlideInLeft(this))
        binding.bOpenScores.startAnimation(getSlideInRight(this))
    }

    private fun startGame() {
        this.startActivity(Intent(this, MainActivity::class.java))
    }

    private fun openScores() {
        this.startActivity(Intent(this, ScoreActivity::class.java))
    }

    private fun openSettings() {
        this.startActivity(Intent(this, SettingsActivity::class.java))
    }
}