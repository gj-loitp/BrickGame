package com.brick.ui.settings

import android.content.ActivityNotFoundException
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.brick.R
import com.brick.data.Pref
import com.brick.databinding.ActivitySettingsBinding
import com.brick.utils.Utils
import com.brick.utils.Utils.getViewIdByColor
import com.brick.utils.openBrowserPolicy
import com.brick.utils.rateApp
import com.shawnlin.numberpicker.NumberPicker

class SettingsActivity : AppCompatActivity(), SettingsView {
    private var speedItems = ArrayList<TextView>()

    private var settingsPresenter: SettingsPresenter? = null
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingsPresenter = SettingsPresenter(
            settingsView = this,
            pref = Pref(applicationContext)
        )

        speedItems.add(binding.tvVeryFast)
        speedItems.add(binding.tvFast)
        speedItems.add(binding.tvDefault)
        speedItems.add(binding.tvSlow)
        speedItems.add(binding.tvVerySlow)

        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.squaresCountNumberPicker.setOnValueChangedListener { _: NumberPicker?, _: Int, newVal: Int ->
            settingsPresenter?.setSquareCountInRow(newVal)
        }
        binding.flRate.setOnClickListener {
            this@SettingsActivity.rateApp(packageName)
        }
        binding.flMoreApps.setOnClickListener {
            showMoreApps()
        }
        binding.flPrivacyPolicy.setOnClickListener {
            openPrivacyPolicy()
        }
        binding.sEnableHints.setOnClickListener {
            enableHints()
        }
        binding.vLFigureColor.setOnClickListener {
            chooseColorFirst()
        }
        binding.vSquareFigureColor.setOnClickListener {
            chooseColorSecond()
        }
        binding.vLongFigureColor.setOnClickListener {
            chooseColorThird()
        }
        binding.vZFigureColor.setOnClickListener {
            chooseColorFourth()
        }
        binding.vTFigureColor.setOnClickListener {
            chooseColorFifth()
        }
        binding.vJFigureColor.setOnClickListener {
            chooseColorSixth()
        }
        binding.tvVerySlow.setOnClickListener {
            chooseVerySlowSpeed()
        }
        binding.tvSlow.setOnClickListener {
            chooseSlowSpeed()
        }
        binding.tvDefault.setOnClickListener {
            chooseDefaultSpeed()
        }
        binding.tvFast.setOnClickListener {
            chooseFastSpeed()
        }
        binding.tvVeryFast.setOnClickListener {
            chooseVeryFastSpeed()
        }
    }

    override fun onResume() {
        super.onResume()
        settingsPresenter?.setValues()
    }

    override fun markChosenColor(
        oldColor: Int,
        newItemId: Int
    ) {
        val oldImageView = findViewById<ImageView>(getViewIdByColor(oldColor))
        oldImageView?.setImageDrawable(null)
        var newImageView = findViewById<ImageView>(newItemId)
        if (newImageView == null) {
            newImageView = findViewById(R.id.vZFigureColor)
        }
        newImageView?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_ok))
    }

    override fun setSpeedTitle(newItemId: Int) {
        for (item in speedItems) {
            val wrappedDrawable = getDrawable(item, R.color.white)
            item.background = wrappedDrawable
            item.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        }
        val newItem = findViewById<TextView>(newItemId)
        if (newItem != null) {
            val wrappedDrawable = getDrawable(newItem, R.color.colorPrimary)
            newItem.background = wrappedDrawable
            newItem.setTextColor(ContextCompat.getColor(this, R.color.white))
        }
    }

    private fun getDrawable(
        item: TextView,
        colorId: Int
    ): Drawable {
        val wrappedDrawable = DrawableCompat.wrap(item.background)
        DrawableCompat.setTintList(
            /* drawable = */ wrappedDrawable,
            /* tint = */ ColorStateList.valueOf(ContextCompat.getColor(this, colorId))
        )
        return wrappedDrawable
    }

    override fun setVerticalHintsChecked(hintsEnabled: Boolean) {
        binding.sEnableHints.isChecked = hintsEnabled
    }

    override fun setSquaresCountInRow(squaresCountInRow: Int) {
        binding.squaresCountNumberPicker.value = squaresCountInRow
    }

    private fun showMoreApps() {
        try {
            startActivity(Utils.showMoreApps())
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                this,
                resources.getString(R.string.cannot_open_market_error_text),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun openPrivacyPolicy() {
        this@SettingsActivity.openBrowserPolicy()
    }

    private fun enableHints() {
        settingsPresenter?.getEvent(R.id.sEnableHints)
    }

    private fun chooseColorFirst() {
        settingsPresenter?.getEvent(R.id.vLFigureColor)
    }

    private fun chooseColorSecond() {
        settingsPresenter?.getEvent(R.id.vSquareFigureColor)
    }

    private fun chooseColorThird() {
        settingsPresenter?.getEvent(R.id.vLongFigureColor)
    }

    private fun chooseColorFourth() {
        settingsPresenter?.getEvent(R.id.vZFigureColor)
    }

    private fun chooseColorFifth() {
        settingsPresenter?.getEvent(R.id.vTFigureColor)
    }

    private fun chooseColorSixth() {
        settingsPresenter?.getEvent(R.id.vJFigureColor)
    }

    private fun chooseVerySlowSpeed() {
        settingsPresenter?.getEvent(R.id.tvVerySlow)
    }

    private fun chooseSlowSpeed() {
        settingsPresenter?.getEvent(R.id.tvSlow)
    }

    private fun chooseDefaultSpeed() {
        settingsPresenter?.getEvent(R.id.tvDefault)
    }

    private fun chooseFastSpeed() {
        settingsPresenter?.getEvent(R.id.tvFast)
    }

    private fun chooseVeryFastSpeed() {
        settingsPresenter?.getEvent(R.id.tvVeryFast)
    }
}
