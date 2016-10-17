package de.fha.bwi50101.create_edit.impl;

import de.fha.bwi50101.common.Constants;
import de.fha.bwi50101.common.model.DiabetesData;
import de.fha.bwi50101.common.model.DiabetesDataType;
import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.create_edit.insulin.InsulinFragmentPresenter;
import de.fha.bwi50101.create_edit.slider.AbstractLumindSliderHandler;
import de.fha.bwi50101.create_edit.slider.LumindSlider;

/**
 * Created by Florian on 14.10.2016.
 */

public class InsulinFragmentPresenterImpl implements InsulinFragmentPresenter {
    private static final float DEFAULT_VALUE = 8;
    private Entry entry;
    private DiabetesData diabetesData;
    private View view;
    private AbstractLumindSliderHandler sliderHandler;

    public InsulinFragmentPresenterImpl(Entry entry) {
        this.entry = entry;
        if (entry.hasDiabetesDataOfType(DiabetesDataType.StandardInsulin)) {
            diabetesData = entry.getDiabetesDataOfType(DiabetesDataType.StandardInsulin);
        } else {
            diabetesData = new DiabetesData();
            diabetesData.setType(DiabetesDataType.StandardInsulin);
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
        LumindSlider slider = view.getSlider();
        sliderHandler = new InsulinSliderHandler(slider);
        slider.setHandler(sliderHandler);
    }

    @Override
    public void resetClicked() {
        resetModel();
        resetSlider();
    }

    private void resetSlider() {
        sliderHandler.deactivate();
        sliderHandler.setSliderLabelString();
    }

    private void resetModel() {
        diabetesData.setActive(false);
        diabetesData.setValue(DEFAULT_VALUE);
    }

    class InsulinSliderHandler extends AbstractLumindSliderHandler {
        private static final float SLIDER_TO_VALUE_FACTOR = 15;
        private static final float MIN_VALUE = 0;
        private static final float MAX_VALUE = 50;
        private static final String SUBTITLE = " units";

        public InsulinSliderHandler(LumindSlider slider) {
            super(slider);
        }

        @Override
        public String getSliderLabelString() {
            return String.format("%.0f", diabetesData.getValue());
        }

        @Override
        public void onLumindSliderHandlerCreated() {
            setSubtitleText(SUBTITLE);
        }

        @Override
        public void onUpdate(float delta) {
            float newValue = Math.max(Math.min(diabetesData.getValue() - delta * SLIDER_TO_VALUE_FACTOR, MAX_VALUE), MIN_VALUE);
            diabetesData.setValue(newValue);
        }

        @Override
        public void onSliderStart() {
            super.onSliderStart();
            InsulinFragmentPresenterImpl.this.view.slidingStarted();
            diabetesData.setActive(true);
            setSliderColorAndAlpha(Constants.COLORS.GREY, Constants.COLORS.QUARTER_OPAC);
            afterUpdate();
        }

        @Override
        public void onSliderRelease() {
            super.onSliderRelease();
            InsulinFragmentPresenterImpl.this.view.slidingStopped();
            setSliderColorAndAlpha(InsulinFragmentPresenterImpl.this.view.getSliderColor(), Constants.COLORS.FULL_OPAC);
        }

        @Override
        protected void afterUpdate() {
            super.afterUpdate();
            lumindSlider.setBackgroundColor(view.getSliderColor());
        }
    }
}
