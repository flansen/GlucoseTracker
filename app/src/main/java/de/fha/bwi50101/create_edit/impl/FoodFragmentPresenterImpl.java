package de.fha.bwi50101.create_edit.impl;

import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.create_edit.food.FoodFragmentPresenter;

/**
 * Created by Florian on 14.10.2016.
 */

public class FoodFragmentPresenterImpl implements FoodFragmentPresenter {
    private Entry entry;

    public FoodFragmentPresenterImpl(Entry entry) {
        this.entry = entry;

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
