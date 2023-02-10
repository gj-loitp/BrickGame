package com.brick.utils

import android.app.Activity
import android.content.Intent
import android.content.pm.ResolveInfo
import android.graphics.Path
import android.net.Uri
import com.brick.R
import com.brick.Values.DEV_NAME
import com.brick.Values.EXTRA_ROWS
import com.brick.enums.FigureSpeed
import java.util.*

object Utils {
    @JvmStatic
    fun createSmallSquareFigure(
        i: Int,
        j: Int,
        squareWidth: Int,
        scale: Int
    ): Path {
        val path = Path()
        val delta: Int = j * squareWidth - (EXTRA_ROWS - 2) * squareWidth - scale
        path.moveTo((i * squareWidth).toFloat(), delta.toFloat())
        path.lineTo((i * squareWidth).toFloat(), (delta - squareWidth).toFloat())
        path.lineTo((i * squareWidth + squareWidth).toFloat(), (delta - squareWidth).toFloat())
        path.lineTo((i * squareWidth + squareWidth).toFloat(), delta.toFloat())
        path.close()
        return path
    }

    @JvmStatic
    fun getViewIdByColor(color: Int): Int {
        var id = 0
        when (color) {
            R.color.lFigure -> id = R.id.vLFigureColor
            R.color.squareFigure -> id = R.id.vSquareFigureColor
            R.color.longFigure -> id = R.id.vLongFigureColor
            R.color.zFigure -> id = R.id.vZFigureColor
            R.color.tFigure -> id = R.id.vTFigureColor
            R.color.jFigure -> id = R.id.vJFigureColor
            else -> {}
        }
        return id
    }

    @JvmStatic
    fun getFiguresSpeedByMillis(speedMillis: Long): FigureSpeed {
        val speed: FigureSpeed = when (speedMillis) {
            FigureSpeed.VERY_FAST.figureSpeedInMillis -> {
                FigureSpeed.VERY_FAST
            }
            FigureSpeed.FAST.figureSpeedInMillis -> {
                FigureSpeed.FAST
            }
            FigureSpeed.DEFAULT.figureSpeedInMillis -> {
                FigureSpeed.DEFAULT
            }
            FigureSpeed.SLOW.figureSpeedInMillis -> {
                FigureSpeed.SLOW
            }
            else -> {
                FigureSpeed.VERY_SLOW
            }
        }
        return speed
    }

    @JvmStatic
    fun openGmail(
        activity: Activity,
        email: Array<String?>?,
        subject: String?
    ): Intent {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.putExtra(Intent.EXTRA_EMAIL, email)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        emailIntent.type = "text/plain"
        val pm = activity.packageManager
        val matches = pm.queryIntentActivities(emailIntent, 0)
        var best: ResolveInfo? = null
        for (info in matches) if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.lowercase(
                Locale.getDefault()
            ).contains("gmail")
        ) best = info
        if (best != null) emailIntent.setClassName(
            best.activityInfo.packageName,
            best.activityInfo.name
        )
        return emailIntent
    }

    @JvmStatic
    fun openMarket(activity: Activity): Intent {
        val uri = Uri.parse("market://details?id=" + activity.packageName)
        return Intent(Intent.ACTION_VIEW, uri)
    }

    @JvmStatic
    fun showMoreApps(): Intent {
        val uri = Uri.parse("market://search?q=pub:$DEV_NAME")
        return Intent(Intent.ACTION_VIEW, uri)
    }
}
