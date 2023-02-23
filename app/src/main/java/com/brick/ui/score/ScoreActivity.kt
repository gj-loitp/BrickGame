package com.brick.ui.score

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.brick.R
import com.brick.Values
import com.brick.data.Pref
import com.brick.databinding.ActivityScoreBinding
import com.brick.utils.AnimationUtil.getZoomIn
import com.brick.utils.setSafeOnClickListener

class ScoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScoreBinding
    private var pref: Pref? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = Pref(applicationContext)
        pref?.let { p ->
            binding.tvFirstScore.text = p.firstValue
            binding.tvSecondScore.text = p.secondValue
            binding.tvThirdScore.text = p.thirdValue
        }

        binding.llc.startAnimation(getZoomIn(this))
        binding.ivShare.startAnimation(getZoomIn(this))
        binding.ivBack.startAnimation(getZoomIn(this))

        binding.ivShare.setSafeOnClickListener {
            share()
        }
        binding.ivBack.setSafeOnClickListener {
            finish()
        }
    }

    private fun share() {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = Values.SHARE_INTENT_TYPE
        val shareBody =
            """${resources.getString(R.string.share_body_part_one)}${pref?.firstValue} ${
                resources.getString(R.string.share_body_part_second)
            }

${Values.PLAY_MARKET_URL}${packageName}"""
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.app_name))
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(
            Intent.createChooser(
                sharingIntent, resources.getString(R.string.share_via_text)
            )
        )
    }
}
