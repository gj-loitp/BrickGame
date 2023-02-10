package com.brick.ui.settings;

import com.brick.R;
import com.brick.data.Pref;
import com.brick.enums.FigureSpeed;
import com.brick.utils.Utils;

import static com.brick.Values.DEFAULT_COLOR;
import static com.brick.enums.FigureSpeed.DEFAULT;
import static com.brick.enums.FigureSpeed.FAST;
import static com.brick.enums.FigureSpeed.SLOW;
import static com.brick.enums.FigureSpeed.VERY_FAST;
import static com.brick.enums.FigureSpeed.VERY_SLOW;

class SettingsPresenter {

    private final Pref pref;
    private final SettingsView settingsView;

    SettingsPresenter(SettingsView settingsView, Pref pref) {
        this.settingsView = settingsView;
        this.pref = pref;
    }

    void setValues() {
        FigureSpeed figureSpeed = Utils.getFiguresSpeedByMillis(pref.getFiguresSpeed());
        if (settingsView != null) {
            settingsView.markChosenColor(DEFAULT_COLOR, Utils.getViewIdByColor(pref.getFiguresColor()));
            settingsView.setSquaresCountInRow(pref.getSquaresCountInRow());
            settingsView.setSpeedTitle(figureSpeed.getSpeedItemId());
            settingsView.setVerticalHintsChecked(pref.isHintsEnabled());
        }
    }

    void setSquareCountInRow(int newValue) {
        pref.setSquaresCountInRow(newValue);
    }

    void getEvent(int id) {
        switch (id) {
            case R.id.vLFigureColor:
                manageColorPicking(R.color.lFigure, id);
                break;
            case R.id.vSquareFigureColor:
                manageColorPicking(R.color.squareFigure, id);
                break;
            case R.id.vLongFigureColor:
                manageColorPicking(R.color.longFigure, id);
                break;
            case R.id.vZFigureColor:
                manageColorPicking(R.color.zFigure, id);
                break;
            case R.id.vTFigureColor:
                manageColorPicking(R.color.tFigure, id);
                break;
            case R.id.vJFigureColor:
                manageColorPicking(R.color.jFigure, id);
                break;
            case R.id.sEnableHints:
                boolean isEnabled = pref.isHintsEnabled();
                pref.setHintsEnabled(!isEnabled);
                break;
            case R.id.tvVeryFast:
                manageSpeedPicking(VERY_FAST.getFigureSpeedInMillis(), id);
                break;
            case R.id.tvFast:
                manageSpeedPicking(FAST.getFigureSpeedInMillis(), id);
                break;
            case R.id.tvDefault:
                manageSpeedPicking(DEFAULT.getFigureSpeedInMillis(), id);
                break;
            case R.id.tvSlow:
                manageSpeedPicking(SLOW.getFigureSpeedInMillis(), id);
                break;
            case R.id.tvVerySlow:
                manageSpeedPicking(VERY_SLOW.getFigureSpeedInMillis(), id);
                break;
            default:
                break;
        }
    }

    private void manageSpeedPicking(long newSpeed, int newItemId) {
        pref.setFiguresSpeed(newSpeed);
        settingsView.setSpeedTitle(newItemId);
    }

    private void manageColorPicking(int newColor, int newItemId) {
        int oldColor = pref.getFiguresColor();
        pref.setFiguresColor(newColor);
        settingsView.markChosenColor(oldColor, newItemId);
    }
}
