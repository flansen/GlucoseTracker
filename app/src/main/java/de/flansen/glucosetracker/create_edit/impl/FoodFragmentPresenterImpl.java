package de.flansen.glucosetracker.create_edit.impl;

import java.util.Date;

import de.flansen.glucosetracker.common.Constants;
import de.flansen.glucosetracker.common.model.DiabetesData;
import de.flansen.glucosetracker.common.model.DiabetesDataType;
import de.flansen.glucosetracker.common.model.Entry;
import de.flansen.glucosetracker.create_edit.food.FoodFragmentPresenter;

/**
 * Created by Florian on 14.10.2016.
 */

public class FoodFragmentPresenterImpl implements FoodFragmentPresenter {
    private static final int DEFAULT_VALUE = 8;
    private static final float MIN_VALUE = 0;
    private static final float MAX_VALUE = 50;
    private Entry entry;
    private DiabetesData diabetesData;
    private View view;


    public FoodFragmentPresenterImpl(Entry entry) {
        this.entry = entry;
        if (entry.hasDiabetesDataOfType(DiabetesDataType.Food)) {
            diabetesData = entry.getDiabetesDataOfType(DiabetesDataType.Food);
        } else {
            diabetesData = new DiabetesData();
            diabetesData.setType(DiabetesDataType.Food);
            diabetesData.setValue(DEFAULT_VALUE);
            diabetesData.setDate(new Date());
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
        if (diabetesData.getId() != Constants.NO_ID) {
            view.recreateStateForEditing();
        }
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

    private void resetModel() {
        diabetesData.setActive(false);
        diabetesData.setValue(DEFAULT_VALUE);
    }

}
