package de.fha.bwi50101.create_edit.impl;

import android.graphics.Color;

import de.fha.bwi50101.common.model.DiabetesData;
import de.fha.bwi50101.common.model.DiabetesDataType;
import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.create_edit.glucose.GlucoseFragmentPresenter;
import de.fha.bwi50101.create_edit.slider.LumindSlider;
import de.fha.bwi50101.create_edit.slider.LumindSliderHandlerImpl;

/**
 * Created by Florian on 14.10.2016.
 */

public class GlucoseFragmentPresenterImpl implements GlucoseFragmentPresenter {
    private DiabetesData diabetesData;
    private Entry entry;
    private View view;

    public GlucoseFragmentPresenterImpl(Entry entry) {
        this.entry = entry;
        if (entry.hasDiabetesDataOfType(DiabetesDataType.Glucose)) {
            diabetesData = entry.getDiabetesDataOfType(DiabetesDataType.Glucose);
        } else {
            diabetesData = new DiabetesData();
            diabetesData.setType(DiabetesDataType.Glucose);
            entry.addOrReplaceDiabetesData(diabetesData);
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void setView(View view) {
        this.view = view;
        LumindSlider slider = view.getSlider();
        slider.setHandler(new LumindSliderHandlerImpl(slider) {
            private static final float SLIDER_TO_VALUE_FACTOR = 90;

            @Override
            public String getSliderLabelString() {
                return String.format("%.0f", diabetesData.getValue());
            }

            @Override
            public void onLumindSliderHandlerCreated() {
                setSubtitleText(" mg/dl");

            }

            @Override
            public void onUpdate(float delta) {
                float newValue = Math.max(Math.min(diabetesData.getValue() - delta * SLIDER_TO_VALUE_FACTOR, 500), 30);
                diabetesData.setValue(newValue);
                diabetesData.setActive(true);
            }

            @Override
            public void onSliderStart() {
                super.onSliderStart();
                GlucoseFragmentPresenterImpl.this.view.slidingStarted();
                setBackgroundColor(Color.RED);
            }

            @Override
            public void onSliderRelease() {
                super.onSliderRelease();
                GlucoseFragmentPresenterImpl.this.view.slidingStopped();
            }
        });
    }
}
