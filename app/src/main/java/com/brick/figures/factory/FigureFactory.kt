package com.brick.figures.factory

import android.content.Context
import android.graphics.Point
import com.brick.enums.FigureType
import com.brick.figures.Figure
import com.brick.figures.SquareFigure
import com.brick.figures.figure_j.JFigure
import com.brick.figures.figure_j.JFourthFigure
import com.brick.figures.figure_j.JSecondFigure
import com.brick.figures.figure_j.JThirdFigure
import com.brick.figures.figure_l.LFigure
import com.brick.figures.figure_l.LFourthFigure
import com.brick.figures.figure_l.LSecondFigure
import com.brick.figures.figure_l.LThirdFigure
import com.brick.figures.figure_long.LongFigure
import com.brick.figures.figure_long.LongSecondFigure
import com.brick.figures.figure_s.SFigure
import com.brick.figures.figure_s.SSecondFigure
import com.brick.figures.figure_t.TFigure
import com.brick.figures.figure_t.TFourthFigure
import com.brick.figures.figure_t.TSecondFigure
import com.brick.figures.figure_t.TThirdFigure
import com.brick.figures.figure_z.ZFigure
import com.brick.figures.figure_z.ZSecondFigure
import com.brick.ui.main.views.PreviewAreaView

object FigureFactory {
    fun getFigure(
        figureType: FigureType?,
        widthSquare: Int,
        scale: Int,
        squaresCountInRow: Int,
        context: Context
    ): Figure? {
        return when (figureType) {
            FigureType.S_FIGURE -> SFigure(widthSquare, scale, squaresCountInRow, context)
            FigureType.Z_FIGURE -> ZFigure(widthSquare, scale, squaresCountInRow, context)
            FigureType.S_SECOND_FIGURE -> SSecondFigure(
                /* squareWidth = */ widthSquare,
                /* scale = */ scale,
                /* squaresCountInRow = */ squaresCountInRow,
                /* context = */ context
            )
            FigureType.Z_SECOND_FIGURE -> ZSecondFigure(
                /* squareWidth = */ widthSquare,
                /* scale = */ scale,
                /* squaresCountInRow = */ squaresCountInRow,
                /* context = */ context
            )
            FigureType.L_FIGURE -> LFigure(widthSquare, scale, squaresCountInRow, context)
            FigureType.L_FOURTH_FIGURE -> LFourthFigure(
                /* squareWidth = */ widthSquare,
                /* scale = */ scale,
                /* squaresCountInRow = */ squaresCountInRow,
                /* context = */ context
            )
            FigureType.L_SECOND_FIGURE -> LSecondFigure(
                /* squareWidth = */ widthSquare,
                /* scale = */ scale,
                /* squaresCountInRow = */ squaresCountInRow,
                /* context = */ context
            )
            FigureType.L_THIRD_FIGURE -> LThirdFigure(
                /* squareWidth = */ widthSquare,
                /* scale = */ scale,
                /* squaresCountInRow = */ squaresCountInRow,
                /* context = */ context
            )
            FigureType.J_FIGURE -> JFigure(widthSquare, scale, squaresCountInRow, context)
            FigureType.J_SECOND_FIGURE -> JSecondFigure(
                /* squareWidth = */ widthSquare,
                /* scale = */ scale,
                /* squaresCountInRow = */ squaresCountInRow,
                /* context = */ context
            )
            FigureType.J_FOURTH_FIGURE -> JFourthFigure(
                /* squareWidth = */ widthSquare,
                /* scale = */ scale,
                /* squaresCountInRow = */ squaresCountInRow,
                /* context = */ context
            )
            FigureType.J_THIRD_FIGURE -> JThirdFigure(
                /* squareWidth = */ widthSquare,
                /* scale = */ scale,
                /* squaresCountInRow = */ squaresCountInRow,
                /* context = */ context
            )
            FigureType.SQUARE_FIGURE -> SquareFigure(
                squareWidth = widthSquare,
                scale = scale,
                squaresCountInRow = squaresCountInRow,
                context = context
            )
            FigureType.LONG_SECOND_FIGURE -> LongSecondFigure(
                /* squareWidth = */ widthSquare,
                /* scale = */ scale,
                /* squaresCountInRow = */ squaresCountInRow,
                /* context = */ context
            )
            FigureType.LONG_FIGURE -> LongFigure(widthSquare, scale, squaresCountInRow, context)
            FigureType.T_FIGURE -> TFigure(widthSquare, scale, squaresCountInRow, context)
            FigureType.T_SECOND_FIGURE -> TSecondFigure(
                /* squareWidth = */ widthSquare,
                /* scale = */ scale,
                /* squaresCountInRow = */ squaresCountInRow,
                /* context = */ context
            )
            FigureType.T_THIRD_FIGURE -> TThirdFigure(
                /* squareWidth = */ widthSquare,
                /* scale = */ scale,
                /* squaresCountInRow = */ squaresCountInRow,
                /* context = */ context
            )
            FigureType.T_FOURTH_FIGURE -> TFourthFigure(
                /* squareWidth = */ widthSquare,
                /* scale = */ scale,
                /* squaresCountInRow = */ squaresCountInRow,
                /* context = */ context
            )
            else -> null
        }
    }

    fun getFigure(
        figureType: FigureType?,
        widthSquare: Int,
        scale: Int,
        context: Context,
        point: Point
    ): Figure? {
        var scale = scale
        return when (figureType) {
            FigureType.S_FIGURE -> {
                scale += 2 * widthSquare
                if (point.y > widthSquare) point.y = point.y - widthSquare
                SFigure(widthSquare, scale, context, point)
            }
            FigureType.S_SECOND_FIGURE -> {
                scale += 2 * widthSquare
                if (point.y > widthSquare) point.y = point.y - widthSquare
                SSecondFigure(widthSquare, scale, context, point)
            }
            FigureType.Z_FIGURE -> {
                scale += 3 * widthSquare
                if (point.y > widthSquare) point.y = point.y - widthSquare
                ZFigure(widthSquare, scale, context, point)
            }
            FigureType.Z_SECOND_FIGURE -> {
                scale += 3 * widthSquare
                if (point.y > widthSquare) point.y = point.y - widthSquare
                ZSecondFigure(widthSquare, scale, context, point)
            }
            FigureType.L_FIGURE -> {
                scale += 3 * widthSquare
                if (point.y > 2 * widthSquare) point.y = point.y - 2 * widthSquare
                LFigure(widthSquare, scale, context, point)
            }
            FigureType.L_FOURTH_FIGURE -> {
                scale += 3 * widthSquare
                LFourthFigure(widthSquare, scale, context, point)
            }
            FigureType.L_SECOND_FIGURE -> {
                scale += 2 * widthSquare
                if (point.y > widthSquare) point.y = point.y - widthSquare
                LSecondFigure(widthSquare, scale, context, point)
            }
            FigureType.L_THIRD_FIGURE -> {
                scale += 3 * widthSquare
                if (point.y > widthSquare) point.y = point.y - widthSquare
                LThirdFigure(widthSquare, scale, context, point)
            }
            FigureType.J_FIGURE -> {
                scale += 3 * widthSquare
                if (point.y > 2 * widthSquare) point.y = point.y - 2 * widthSquare
                JFigure(widthSquare, scale, context, point)
            }
            FigureType.J_SECOND_FIGURE -> {
                scale += 3 * widthSquare
                if (point.y > widthSquare) point.y = point.y - widthSquare
                JSecondFigure(widthSquare, scale, context, point)
            }
            FigureType.J_FOURTH_FIGURE -> {
                scale += 3 * widthSquare
                if (point.y > widthSquare) point.y = point.y - widthSquare
                JFourthFigure(widthSquare, scale, context, point)
            }
            FigureType.J_THIRD_FIGURE -> {
                scale += 3 * widthSquare
                if (point.y > 2 * widthSquare) point.y = point.y - 2 * widthSquare
                JThirdFigure(widthSquare, scale, context, point)
            }
            FigureType.SQUARE_FIGURE -> SquareFigure(widthSquare, scale, context, point)
            FigureType.LONG_SECOND_FIGURE -> {
                scale += 3 * widthSquare
                LongSecondFigure(widthSquare, scale, context, point)
            }
            FigureType.LONG_FIGURE -> {
                scale += 3 * widthSquare
                if (point.y > 3 * widthSquare) {
                    point.y -= point.y - 2 * widthSquare
                } else if (point.y > 2 * widthSquare) {
                    point.y = point.y - 2 * widthSquare
                }
                LongFigure(widthSquare, scale, context, point)
            }
            FigureType.T_FIGURE -> {
                scale += widthSquare
                TFigure(widthSquare, scale, context, point)
            }
            FigureType.T_SECOND_FIGURE -> {
                scale += 3 * widthSquare
                if (point.y > widthSquare) point.y = point.y - widthSquare
                TSecondFigure(widthSquare, scale, context, point)
            }
            FigureType.T_THIRD_FIGURE -> {
                scale += 3 * widthSquare
                if (point.y > 2 * widthSquare) point.y = point.y - 2 * widthSquare
                TThirdFigure(widthSquare, scale, context, point)
            }
            FigureType.T_FOURTH_FIGURE -> {
                scale += 2 * widthSquare
                if (point.y > widthSquare) point.y = point.y - widthSquare
                TFourthFigure(widthSquare, scale, context, point)
            }
            else -> null
        }
    }

    @JvmStatic
    fun getFigure(
        figureType: FigureType?,
        widthSquare: Int,
        context: Context
    ): Figure? {
        val center = PreviewAreaView.PREVIEW_AREA_WIDTH / 2
        return when (figureType) {
            FigureType.S_FIGURE -> SFigure(
                /* widthSquare = */ widthSquare,
                /* context = */ context,
                /* point = */ Point(
                    center - widthSquare / 2 - widthSquare / 4, widthSquare + widthSquare / 2
                )
            )
            FigureType.Z_FIGURE -> ZFigure(
                /* widthSquare = */ widthSquare,
                /* context = */ context,
                /* point = */ Point(center - widthSquare / 2 - widthSquare / 4, widthSquare)
            )
            FigureType.S_SECOND_FIGURE -> SSecondFigure(
                /* widthSquare = */ widthSquare,
                /* context = */ context,
                /* point = */ Point(center - widthSquare / 2, widthSquare + widthSquare / 2)
            )
            FigureType.Z_SECOND_FIGURE -> ZSecondFigure(
                /* widthSquare = */ widthSquare,
                /* context = */ context,
                /* point = */ Point(center - widthSquare / 2, widthSquare)
            )
            FigureType.L_FIGURE -> LFigure(
                /* widthSquare = */ widthSquare,
                /* context = */ context,
                /* point = */ Point(center - widthSquare / 4, widthSquare)
            )
            FigureType.L_FOURTH_FIGURE -> LFourthFigure(
                /* widthSquare = */ widthSquare,
                /* context = */ context,
                /* point = */ Point(center - widthSquare / 2 - widthSquare / 4, widthSquare)
            )
            FigureType.L_SECOND_FIGURE -> LSecondFigure(
                /* widthSquare = */ widthSquare,
                /* context = */ context,
                /* point = */ Point(
                    center - widthSquare / 2 - widthSquare / 4, widthSquare + widthSquare / 2
                )
            )
            FigureType.L_THIRD_FIGURE -> LThirdFigure(
                /* widthSquare = */ widthSquare,
                /* context = */ context,
                /* point = */ Point(center - widthSquare / 2, widthSquare)
            )
            FigureType.J_FIGURE -> JFigure(
                /* widthSquare = */ widthSquare,
                /* context = */ context,
                /* point = */ Point(center - widthSquare / 2, widthSquare)
            )
            FigureType.J_SECOND_FIGURE -> JSecondFigure(
                /* widthSquare = */ widthSquare,
                /* context = */ context,
                /* point = */ Point(center - widthSquare / 2 - widthSquare / 4, widthSquare)
            )
            FigureType.J_FOURTH_FIGURE -> JFourthFigure(
                /* widthSquare = */ widthSquare,
                /* context = */ context,
                /* point = */ Point(center - widthSquare / 2 - widthSquare / 4, widthSquare)
            )
            FigureType.J_THIRD_FIGURE -> JThirdFigure(
                /* widthSquare = */ widthSquare,
                /* context = */ context,
                /* point = */ Point(center - widthSquare / 2, widthSquare)
            )
            FigureType.SQUARE_FIGURE -> SquareFigure(
                widthSquare = widthSquare,
                context = context,
                point = Point(center - widthSquare / 2, widthSquare)
            )
            FigureType.LONG_SECOND_FIGURE -> LongSecondFigure(
                /* widthSquare = */ widthSquare,
                /* context = */ context,
                /* point = */ Point(center - widthSquare, widthSquare)
            )
            FigureType.LONG_FIGURE -> LongFigure(
                widthSquare, context, Point(center - widthSquare / 4, widthSquare)
            )
            FigureType.T_FIGURE -> TFigure(
                /* widthSquare = */ widthSquare,
                /* context = */ context,
                /* point = */ Point(center - widthSquare / 2 - widthSquare / 4, widthSquare * 2)
            )
            FigureType.T_SECOND_FIGURE -> TSecondFigure(
                widthSquare, context, Point(center - widthSquare / 2 - widthSquare / 4, widthSquare)
            )
            FigureType.T_THIRD_FIGURE -> TThirdFigure(
                widthSquare, context, Point(center - widthSquare / 2, widthSquare)
            )
            FigureType.T_FOURTH_FIGURE -> TFourthFigure(
                widthSquare, context, Point(center - widthSquare / 2, widthSquare + widthSquare / 2)
            )
            else -> null
        }
    }
}
