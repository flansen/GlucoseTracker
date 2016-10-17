package de.fha.bwi50101.create_edit.impl;

import de.fha.bwi50101.common.Constants;
import de.fha.bwi50101.common.impl.ColorHelper;
import de.fha.bwi50101.common.model.DiabetesData;
import de.fha.bwi50101.common.model.DiabetesDataType;
import de.fha.bwi50101.common.model.Entry;
import de.fha.bwi50101.create_edit.glucose.GlucoseFragmentPresenter;
import de.fha.bwi50101.create_edit.slider.AbstractLumindSliderHandler;
import de.fha.bwi50101.create_edit.slider.LumindSlider;

/**
 * Created by Florian on 14.10.2016.
 */

public class GlucoseFragmentPresenterImpl implements GlucoseFragmentPresenter {
    private static final float DEFAULT_VALUE = 100;
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
        LumindSlider slider = view.getSlider();
        sliderHandler = new GlucoseSliderHandler(slider);
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


    class GlucoseSliderHandler extends AbstractLumindSliderHandler {
        private static final float SLIDER_TO_VALUE_FACTOR = 90;
        private static final float MIN_GLUCOSE = 30;
        private static final float MAX_GLUCOSE = 500;
        private static final String SUBTITLE = " mg/dl";

        public GlucoseSliderHandler(LumindSlider slider) {
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
            float newValue = Math.max(Math.min(diabetesData.getValue() - delta * SLIDER_TO_VALUE_FACTOR, MAX_GLUCOSE), MIN_GLUCOSE);
            diabetesData.setValue(newValue);
        }

        @Override
        public void onSliderStart() {
            super.onSliderStart();
            GlucoseFragmentPresenterImpl.this.view.slidingStarted();
            diabetesData.setActive(true);
            setSliderColorAndAlpha(Constants.COLORS.GREY, Constants.COLORS.QUARTER_OPAC);
            afterUpdate();
        }

        @Override
        public void onSliderRelease() {
            super.onSliderRelease();
            GlucoseFragmentPresenterImpl.this.view.slidingStopped();
            setSliderColorAndAlpha(ColorHelper.calculateColor(diabetesData.getValue()), Constants.COLORS.FULL_OPAC);
        }

        @Override
        protected void afterUpdate() {
            super.afterUpdate();
            int[] gradientColors = ColorHelper.calculateColors(diabetesData.getValue());
            ColorHelper.setViewBackgroundToGradient(lumindSlider, gradientColors[0], gradientColors[1]);
        }
    }
}

