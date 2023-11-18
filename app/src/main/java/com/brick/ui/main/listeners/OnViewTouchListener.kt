package com.brick.ui.main.listeners

import android.annotation.SuppressLint
import android.content.Context
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import kotlin.math.abs

private const val SWIPE_DISTANCE_THRESHOLD = 100
private const val SWIPE_VELOCITY_THRESHOLD = 100

class OnViewTouchListener(
    context: Context?, onPlayingAreaTouch: OnPlayingAreaTouch?,
) : OnTouchListener {
    private val gestureDetector: GestureDetector
    private val onPlayingAreaTouch: OnPlayingAreaTouch?
    private var halfScreenWidth = 0

    init {
        gestureDetector = GestureDetector(context, GestureListener())
        this.onPlayingAreaTouch = onPlayingAreaTouch
    }

    fun setScreenWidth(screenWidth: Int) {
        halfScreenWidth = screenWidth / 2
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(
        v: View, event: MotionEvent,
    ): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    private inner class GestureListener : SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onLongPress(e: MotionEvent) {
            if (halfScreenWidth != 0 && onPlayingAreaTouch != null) {
                if (e.x <= halfScreenWidth) {
                    onPlayingAreaTouch.onLongLeftClick()
                } else if (e.x > halfScreenWidth) {
                    onPlayingAreaTouch.onLongRightClick()
                }
            }
            super.onLongPress(e)
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            if (halfScreenWidth != 0 && onPlayingAreaTouch != null) {
                if (e.x <= halfScreenWidth) {
                    onPlayingAreaTouch.onLeftMove()
                } else if (e.x > halfScreenWidth) {
                    onPlayingAreaTouch.onRightMove()
                }
            }
            return super.onSingleTapUp(e)
        }

        override fun onFling(
            e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float,
        ): Boolean {
            if (e1 != null) {
                val distanceX = e2.x - e1.x
                val distanceY = e2.y - e1.y
                if (abs(distanceX) > abs(distanceY) && abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && abs(
                        velocityX
                    ) > SWIPE_VELOCITY_THRESHOLD
                ) {
                    if (distanceX > 0) {
                        onPlayingAreaTouch?.onRightMove()
                    } else {
                        onPlayingAreaTouch?.onLeftMove()
                    }
                    return true
                }
            }
            return false
        }
    }
}
