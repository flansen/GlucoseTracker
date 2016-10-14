package de.fha.bwi50101.create_edit.slider;

/**
 * Created by Florian on 14.10.2016.
 */

public interface LumindSliderHandler {

    String getSliderLabelString();

    void onLumindSliderHandlerCreated();

    void onUpdate(float delta);

    void setSubtitleText(String text);

    void setSliderColorAndAlpha(int color, int alpha);

    void setBackgroundColor(int color);
}
