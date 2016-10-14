package de.fha.bwi50101.create_edit.slider;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

import de.fha.bwi50101.create_edit.ViewPagerHolder;


/**
 * Created by Florian on 07.05.2016.
 */
public abstract class LumindSliderHandlerImpl implements SliderEventListener, LumindSliderHandler {
    private static final int MIN_MAX_TIMERTASK_INTERVAL = 25;
    private float timerTaskDelta;
    private float previousSliderValue;
    private Timer timer;
    private Handler handler;
    private LumindSlider lumindSlider;


    public LumindSliderHandlerImpl(LumindSlider slider) {
        this.timerTaskDelta = 0.01f;
        this.lumindSlider = slider;
        previousSliderValue = -2f;
        handler = new Handler();
        onLumindSliderHandlerCreated();
    }

    @Override
    public void setSubtitleText(String text) {
        lumindSlider.getRoundRectSliderView().setSubtitleText(text);
    }

    @Override
    public void setSliderColorAndAlpha(int color, int alpha) {
        lumindSlider.setViewColorAndAlpha(color, alpha);
    }

    @Override
    public void setBackgroundColor(int color) {
        lumindSlider.setBackgroundColor(color);
    }

    @Override
    public void onHandlerSet() {
        setSliderLabelString();
    }

    public void setSliderLabelString() {
        lumindSlider.getRoundRectSliderView().setTitleText(getSliderLabelString());
    }

    @Override
    public void onSlide(float deltaMovement) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        float delta = deltaMovement - previousSliderValue;
        onUpdate(delta);
        previousSliderValue = deltaMovement;
        afterUpdate();
    }


    @Override
    public void onMaxTop() {
        if (timer != null) return;
        timer = new Timer("maxTimer");
        timer.scheduleAtFixedRate(new SliderClippingTimerTask(-timerTaskDelta), 0, MIN_MAX_TIMERTASK_INTERVAL);
    }

    @Override
    public void onMinBottom() {
        if (timer != null) return;
        timer = new Timer("minTimer");
        timer.scheduleAtFixedRate(new SliderClippingTimerTask(timerTaskDelta), 0, MIN_MAX_TIMERTASK_INTERVAL);
    }

    @Override
    public void onSliderRelease() {
        if (timer != null) {
            handler.removeCallbacksAndMessages(null);
            timer.cancel();
            timer = null;
        }
        Activity host = lumindSlider.getHostingActivity();
        if (host instanceof ViewPagerHolder) {
            ((ViewPagerHolder) host).bringViewPagerToBackground();
        }
        lumindSlider.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onSliderStart() {
        Activity host = lumindSlider.getHostingActivity();
        if (host instanceof ViewPagerHolder) {
            ((ViewPagerHolder) host).bringViewPagerToFront();
        }
        previousSliderValue = 0.5f;
    }

    protected void afterUpdate() {
        setSliderLabelString();
    }


    private class SliderClippingTimerTask extends TimerTask {
        private final float modValue;

        public SliderClippingTimerTask(float modValue) {
            this.modValue = modValue;
        }

        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onUpdate(modValue);
                    afterUpdate();
                }
            });
        }
    }
}
