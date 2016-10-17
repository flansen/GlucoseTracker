package de.fha.bwi50101.create_edit;

/**
 * Created by Florian on 24.04.2016.
 */
public interface DisableableViewPagerHolder {
    void bringViewPagerToFront();

    void bringViewPagerToBackground();

    void enableViewPaging();

    void disableViewPaging();
}
