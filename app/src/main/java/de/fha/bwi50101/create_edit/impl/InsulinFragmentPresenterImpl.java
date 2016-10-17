package de.fha.bwi50101.create_edit.impl;

import de.fha.bwi50101.common.model.DiabetesData;
import de.fha.bwi50101.common.model.DiabetesDataType;
import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.create_edit.insulin.InsulinFragmentPresenter;

/**
 * Created by Florian on 14.10.2016.
 */

public class InsulinFragmentPresenterImpl implements InsulinFragmentPresenter {
    private Entry entry;
    private DiabetesData diabetesData;

    public InsulinFragmentPresenterImpl(Entry entry) {
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
}
