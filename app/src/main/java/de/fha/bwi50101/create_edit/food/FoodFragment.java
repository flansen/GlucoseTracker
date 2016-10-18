package de.fha.bwi50101.create_edit.food;

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
import de.fha.bwi50101.create_edit.AbstractSliderFragment;
import de.fha.bwi50101.create_edit.EntryProvider;
import de.fha.bwi50101.create_edit.impl.FoodFragmentPresenterImpl;
import de.fha.bwi50101.create_edit.slider.AbstractLumindSliderHandler;
import de.fha.bwi50101.create_edit.slider.LumindSlider;

/**
 * Created by Florian on 09.10.2016.
 */

public class FoodFragment extends AbstractSliderFragment implements FoodFragmentPresenter.View {
    @BindView(R.id.food_slider)
    LumindSlider slider;
    @BindView(R.id.food_reset)
    Button resetButton;
    private FoodFragmentPresenter presenter;

    public static FoodFragment newInstance() {
        return new FoodFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new FoodFragmentPresenterImpl(((EntryProvider) getActivity()).getEntry());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        ButterKnife.bind(this, view);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.resetClicked();
            }
        });
        presenter.setView(this);
        sliderHandler = new FoodSliderHandler(slider);
        slider.setHandler(sliderHandler);
        return view;
    }

    private class FoodSliderHandler extends AbstractLumindSliderHandler {
        private static final float SLIDER_TO_VALUE_FACTOR = 15;
        private static final String SUBTITLE = " bread units";

        private FoodSliderHandler(LumindSlider slider) {
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
            setSliderColorAndAlpha(getSliderColor(), Constants.COLORS.FULL_OPAC);
        }

        @Override
        protected void afterUpdate() {
            super.afterUpdate();
            lumindSlider.setBackgroundColor(getSliderColor());
        }
    }
}
