package de.fha.bwi50101.create_edit.impl;

import de.fha.bwi50101.common.model.DiabetesData;
import de.fha.bwi50101.common.model.DiabetesDataType;
import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.create_edit.food.FoodFragmentPresenter;

/**
 * Created by Florian on 14.10.2016.
 */

public class FoodFragmentPresenterImpl implements FoodFragmentPresenter {
    private Entry entry;
    private DiabetesData diabetesData;

    public FoodFragmentPresenterImpl(Entry entry) {
        this.entry = entry;
        if (entry.hasDiabetesDataOfType(DiabetesDataType.Food)) {
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
}
