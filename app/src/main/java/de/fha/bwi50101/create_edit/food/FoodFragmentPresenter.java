package de.fha.bwi50101.create_edit.food;

import de.fha.bwi50101.create_edit.slider.LumindSlider;
import de.flhn.cleanboilerplate.presentation.presenters.base.BasePresenter;

/**
 * Created by Florian on 14.10.2016.
 */

public interface FoodFragmentPresenter extends BasePresenter {
    void setView(View view);

    void resetClicked();

    interface View {
        LumindSlider getSlider();

        void slidingStarted();

        void slidingStopped();

        int getSliderColor();
    }
}
