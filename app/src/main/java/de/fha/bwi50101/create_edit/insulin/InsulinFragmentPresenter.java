package de.fha.bwi50101.create_edit.insulin;

import de.flhn.cleanboilerplate.presentation.presenters.base.BasePresenter;

/**
 * Created by Florian on 14.10.2016.
 */

public interface InsulinFragmentPresenter extends BasePresenter {
    void setView(View view);

    void resetClicked();

    float getSliderValue();

    void sliderValueChanged(float delta);

    interface View {
        void resetSlider();
    }

}
