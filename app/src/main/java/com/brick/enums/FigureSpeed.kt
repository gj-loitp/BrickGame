package com.brick.enums

import com.brick.R

enum class FigureSpeed(
    val speedItemId: Int,
    val figureSpeedInMillis: Long
) {
    VERY_FAST(speedItemId = R.id.tvVeryFast, figureSpeedInMillis = 500),
    FAST(speedItemId = R.id.tvFast, figureSpeedInMillis = 750),
    DEFAULT(speedItemId = R.id.tvDefault, figureSpeedInMillis = 1000),
    SLOW(speedItemId = R.id.tvSlow, figureSpeedInMillis = 1250),
    VERY_SLOW(speedItemId = R.id.tvVerySlow, figureSpeedInMillis = 1500);

}
