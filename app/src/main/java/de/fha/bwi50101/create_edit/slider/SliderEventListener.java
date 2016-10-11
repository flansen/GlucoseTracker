package de.fha.bwi50101.create_edit.slider;

/**
 * Created by Florian on 18.04.2016.
 */
public interface SliderEventListener {
    void onSlide(float deltaMovement);

    void onMaxTop();

    void onMinBottom();

    void onSliderRelease();

    void onSliderStart();

    void onHandlerSet();
}
