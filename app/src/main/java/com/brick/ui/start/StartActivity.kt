package com.brick.ui.start

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd
import com.brick.BuildConfig
import com.brick.R
import com.brick.databinding.ActivityStartBinding
import com.brick.ui.main.MainActivity
import com.brick.ui.score.ScoreActivity
import com.brick.ui.settings.SettingsActivity
import com.brick.utils.AnimationUtil.getZoomIn
import com.brick.utils.openBrowserPolicy
import com.brick.utils.setSafeOnClickListener
import java.util.concurrent.TimeUnit
import kotlin.math.min
import kotlin.math.pow

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    private var interstitialAd: MaxInterstitialAd? = null
    private var retryAttempt = 0

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
            showAd {
                startGame()
            }
        }
        binding.bOpenScores.setSafeOnClickListener {
            showAd {
                openScores()
            }
        }
        binding.ivSettings.setSafeOnClickListener {
            showAd {
                openSettings()
            }
        }
        createAdInter()
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

    private fun createAdInter() {
        val enableAdInter = getString(R.string.EnableAdInter) == "true"
        if (enableAdInter) {
            interstitialAd = MaxInterstitialAd(getString(R.string.INTER), this)
            interstitialAd?.let { ad ->
                ad.setListener(object : MaxAdListener {
                    override fun onAdLoaded(p0: MaxAd?) {
//                        logI("onAdLoaded")
                        retryAttempt = 0
                    }

                    override fun onAdDisplayed(p0: MaxAd?) {
//                        logI("onAdDisplayed")
                    }

                    override fun onAdHidden(p0: MaxAd?) {
//                        logI("onAdHidden")
                        // Interstitial Ad is hidden. Pre-load the next ad
                        interstitialAd?.loadAd()
                    }

                    override fun onAdClicked(p0: MaxAd?) {
//                        logI("onAdClicked")
                    }

                    override fun onAdLoadFailed(p0: String?, p1: MaxError?) {
//                        logI("onAdLoadFailed")
                        retryAttempt++
                        val delayMillis =
                            TimeUnit.SECONDS.toMillis(2.0.pow(min(6, retryAttempt)).toLong())

                        Handler(Looper.getMainLooper()).postDelayed(
                            {
                                interstitialAd?.loadAd()
                            }, delayMillis
                        )
                    }

                    override fun onAdDisplayFailed(p0: MaxAd?, p1: MaxError?) {
//                        logI("onAdDisplayFailed")
                        // Interstitial ad failed to display. We recommend loading the next ad.
                        interstitialAd?.loadAd()
                    }

                })
                ad.setRevenueListener {
//                    logI("onAdDisplayed")
                }

                // Load the first ad.
                ad.loadAd()
            }
        }
    }

    private fun showAd(runnable: Runnable? = null) {
        val enableAdInter = getString(R.string.EnableAdInter) == "true"
        if (enableAdInter) {
            if (interstitialAd == null) {
                runnable?.run()
            } else {
                interstitialAd?.let { ad ->
                    if (ad.isReady) {
                        ad.showAd()
                        runnable?.run()
                    } else {
                        runnable?.run()
                    }
                }
            }
        } else {
            Toast.makeText(this, "Applovin show ad Inter in debug mode", Toast.LENGTH_SHORT).show()
            runnable?.run()
        }
    }
}