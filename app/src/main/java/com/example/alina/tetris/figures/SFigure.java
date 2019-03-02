package com.example.alina.tetris.figures;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Point;

import com.example.alina.tetris.R;
import com.example.alina.tetris.enums.FigureType;

/**
 * Created by Alina on 02.04.2017.
 */

public class SFigure extends Figure {

    public SFigure(int squareWidth, int scale, Context context) {
        super(squareWidth, scale, context);
    }

    public SFigure(int widthSquare, Context context, Point point) {
        super(widthSquare, context, point);
    }

    public SFigure(int squareWidth, int scale, Context context, Point point) {
        super(squareWidth, scale, context, point);
    }

    @Override
    public void initFigureMask() {
        super.initFigureMask();
        figureMask[0][1] = true;
        figureMask[0][2] = true;
        figureMask[1][0] = true;
        figureMask[1][1] = true;
    }

    @Override
    public FigureType getRotatedFigure() {
        return FigureType.S_SECOND_FIGURE;
    }

    @Override
    public int getWidthInSquare() {
        return 3;
    }

    @Override
    public int getHeightInSquare() {
        return 2;
    }

    @Override
    public Path getPath() {
        Path path = new Path();
        path.moveTo(point.x + squareWidth, point.y - scale);
        path.lineTo(point.x + squareWidth, point.y - squareWidth - scale);
        path.lineTo(point.x + squareWidth * 3, point.y - squareWidth - scale);
        path.lineTo(point.x + squareWidth * 3, point.y - scale);
        path.lineTo(point.x + squareWidth * 2, point.y - scale);
        path.lineTo(point.x + squareWidth * 2, point.y + squareWidth - scale);
        path.lineTo(point.x, point.y + squareWidth - scale);
        path.lineTo(point.x, point.y - scale);
        path.close();
        return path;
    }

    @Override
    public int getColor() {
        return this.context.getResources().getColor(R.color.sFigure);
    }
}
