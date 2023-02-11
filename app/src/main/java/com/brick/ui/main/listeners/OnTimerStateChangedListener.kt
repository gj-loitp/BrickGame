package com.brick.ui.main.listeners;

public interface OnTimerStateChangedListener {

    void isTimerRunning(boolean isRunning);

    void disableAllControls();
}
