package de.fha.bwi50101.create_edit;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import de.fha.bwi50101.R;
import de.fha.bwi50101.create_edit.slider.AbstractLumindSliderHandler;

/**
 * Created by Florian on 18.10.2016.
 */

public abstract class AbstractSliderFragment extends Fragment {

    protected AbstractLumindSliderHandler sliderHandler;

    protected void slidingStarted() {
        ((DisableableViewPagerHolder) getActivity()).disableViewPaging();
    }

    protected void slidingStopped() {
        ((DisableableViewPagerHolder) getActivity()).enableViewPaging();
    }

    protected int getSliderColor() {
        return ContextCompat.getColor(this.getContext(), R.color.colorPrimary);
    }

    public void resetSlider() {
        sliderHandler.deactivate();
        sliderHandler.setSliderLabelString();
    }
}
