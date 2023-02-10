package com.brick.utils

import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.brick.R

object AnimationUtil {
    @JvmStatic
    fun getZoomIn(context: Context?): Animation {
        return AnimationUtils.loadAnimation(context, R.anim.anim_zoom_in)
    }

    @JvmStatic
    fun getSlideInLeft(context: Context?): Animation {
        return AnimationUtils.loadAnimation(context, R.anim.anim_slide_in_left)
    }

    @JvmStatic
    fun getSlideInRight(context: Context?): Animation {
        return AnimationUtils.loadAnimation(context, R.anim.anim_slide_in_right)
    }
}
