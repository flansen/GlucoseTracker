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
public abstract class LumindSliderHandler implements SliderEventListener {
    private static final String TAG = "LumindSliderHandler";
    private static final int MIN_MAX_TIMERTASK_INTERVAL = 25;
    private float timertaskDelta;
    private float previousSliderValue = -2f;
    private Timer timer;
    private Handler handler;
    private LumindSlider lumindSlider;


    public LumindSliderHandler(float timertaskDelta, LumindSlider slider) {
        this.timertaskDelta = timertaskDelta;
        this.lumindSlider = slider;
        handler = new Handler();
        onHandlerCreated();
    }

    public abstract String getSliderLabelString();

    public abstract void onHandlerCreated();

    public abstract void onUpdate(float delta);

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
        timer.scheduleAtFixedRate(new SliderClippingTimerTask(-timertaskDelta), 0, MIN_MAX_TIMERTASK_INTERVAL);
    }

    @Override
    public void onMinBottom() {
        if (timer != null) return;
        timer = new Timer("minTimer");
        timer.scheduleAtFixedRate(new SliderClippingTimerTask(timertaskDelta), 0, MIN_MAX_TIMERTASK_INTERVAL);
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
