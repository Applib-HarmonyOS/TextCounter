package com.github.premnirmal.textcounter;

import ohos.eventhandler.EventHandler;

/**
 * Created by prem on 10/28/14.
 *
 * <p>Class that handles the counting up/down of the text value
 */
class Counter implements Runnable {
    final CounterView view;
    final float increment;
    final float startValue;
    final float endValue;
    final long interval;
    float currentValue;
    float newValue;
    EventHandler mHandler;

    Counter(CounterView view, float startValue, float endValue, long interval, float increment, EventHandler handler) {
        this.view = view;
        this.startValue = startValue;
        this.endValue = endValue;
        this.interval = interval;
        this.increment = increment;
        this.newValue = this.startValue;
        this.currentValue = this.startValue - increment;
        this.mHandler = handler;
    }

    public void runNew() {
        if (mHandler != null) {
            mHandler.removeTask(Counter.this);
            mHandler.postTask(Counter.this, interval);
        }
    }

    @Override
    public void run() {
        if (valuesAreCorrect()) {
            final float valueToSet;
            if (increment > 0) {
                if (newValue <= endValue) {
                    valueToSet = newValue;
                } else {
                    valueToSet = endValue;
                }
            } else if (increment < 0) {
                if (newValue >= endValue) {
                    valueToSet = newValue;
                } else {
                    valueToSet = endValue;
                }
            } else {
                return;
            }
            view.setCurrentTextValue(valueToSet);
            currentValue = newValue;
            newValue += increment;
            runNew();
        } else {
            view.setCurrentTextValue(endValue);
        }
    }

    private boolean valuesAreCorrect() {
        if (increment > 0) {
            return newValue >= currentValue && newValue < endValue;
        } else if (increment < 0) {
            return newValue < currentValue && newValue > endValue;
        } else {
            return false;
        }
    }
}