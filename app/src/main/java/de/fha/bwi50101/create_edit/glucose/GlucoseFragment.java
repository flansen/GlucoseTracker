package de.fha.bwi50101.create_edit.glucose;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.fha.bwi50101.R;
import de.fha.bwi50101.common.Constants;
import de.fha.bwi50101.common.impl.ColorHelper;
import de.fha.bwi50101.create_edit.AbstractSliderFragment;
import de.fha.bwi50101.create_edit.EntryProvider;
import de.fha.bwi50101.create_edit.impl.GlucoseFragmentPresenterImpl;
import de.fha.bwi50101.create_edit.slider.AbstractLumindSliderHandler;
import de.fha.bwi50101.create_edit.slider.LumindSlider;

/**
 * Created by Florian on 09.10.2016.
 */

public class GlucoseFragment extends AbstractSliderFragment implements GlucoseFragmentPresenter.View {
    @BindView(R.id.glucose_slider)
    LumindSlider slider;
    @BindView(R.id.glucose_reset)
    Button resetButton;

    private GlucoseFragmentPresenter presenter;

    public static GlucoseFragment newInstance() {
        return new GlucoseFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new GlucoseFragmentPresenterImpl(((EntryProvider) getActivity()).getEntry());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_glucose, container, false);
        ButterKnife.bind(this, view);
        presenter.setView(this);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.resetClicked();
            }
        });
        sliderHandler = new GlucoseSliderHandler(slider);
        slider.setHandler(sliderHandler);
        return view;
    }

    private class GlucoseSliderHandler extends AbstractLumindSliderHandler {
        private static final float SLIDER_TO_VALUE_FACTOR = 90;

        private static final String SUBTITLE = " mg/dl";

        private GlucoseSliderHandler(LumindSlider slider) {
            super(slider);
        }

        @Override
        public String getSliderLabelString() {
            return String.format("%.0f", presenter.getSliderValue());
        }

        @Override
        public void onLumindSliderHandlerCreated() {
            setSubtitleText(SUBTITLE);
        }

        @Override
        public void onUpdate(float delta) {
            presenter.sliderValueChanged(delta * SLIDER_TO_VALUE_FACTOR);
        }

        @Override
        public void onSliderStart() {
            super.onSliderStart();
            slidingStarted();
            setSliderColorAndAlpha(Constants.COLORS.GREY, Constants.COLORS.QUARTER_OPAC);
            afterUpdate();
        }

        @Override
        public void onSliderRelease() {
            super.onSliderRelease();
            slidingStopped();
            setSliderColorAndAlpha(ColorHelper.calculateColor(presenter.getSliderValue()), Constants.COLORS.FULL_OPAC);
        }

        @Override
        protected void afterUpdate() {
            super.afterUpdate();
            int[] gradientColors = ColorHelper.calculateColors(presenter.getSliderValue());
            ColorHelper.setViewBackgroundToGradient(lumindSlider, gradientColors[0], gradientColors[1]);
        }
    }
}
