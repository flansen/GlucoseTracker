package de.fha.bwi50101.create_edit.impl;

import de.fha.bwi50101.common.model.DiabetesData;
import de.fha.bwi50101.common.model.DiabetesDataType;
import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.create_edit.glucose.GlucoseFragmentPresenter;
import de.fha.bwi50101.create_edit.slider.AbstractLumindSliderHandler;

/**
 * Created by Florian on 14.10.2016.
 */

public class GlucoseFragmentPresenterImpl implements GlucoseFragmentPresenter {
    private static final float DEFAULT_VALUE = 100;
    private static final float MIN_VALUE = 30;
    private static final float MAX_VALUE = 500;
    private DiabetesData diabetesData;
    private Entry entry;
    private View view;
    private AbstractLumindSliderHandler sliderHandler;

    public GlucoseFragmentPresenterImpl(Entry entry) {
        this.entry = entry;
        if (entry.hasDiabetesDataOfType(DiabetesDataType.Glucose)) {
            diabetesData = entry.getDiabetesDataOfType(DiabetesDataType.Glucose);
        } else {
            diabetesData = new DiabetesData();
            diabetesData.setType(DiabetesDataType.Glucose);
            diabetesData.setValue(DEFAULT_VALUE);
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
    }

    @Override
    public void resetClicked() {
        resetModel();
        view.resetSlider();
    }

    @Override
    public float getSliderValue() {
        return diabetesData.getValue();
    }

    @Override
    public void sliderValueChanged(float delta) {
        float newValue = Math.max(Math.min(diabetesData.getValue() - delta, MAX_VALUE), MIN_VALUE);
        diabetesData.setValue(newValue);
        diabetesData.setActive(true);
    }

    private void resetSlider() {
        sliderHandler.deactivate();
        sliderHandler.setSliderLabelString();
    }

    private void resetModel() {
        diabetesData.setActive(false);
        diabetesData.setValue(DEFAULT_VALUE);
    }
}

