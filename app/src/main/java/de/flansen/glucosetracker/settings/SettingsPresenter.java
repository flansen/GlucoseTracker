package de.flansen.glucosetracker.settings;

import de.flhn.cleanboilerplate.presentation.presenters.base.BasePresenter;

/**
 * Created by Florian on 26.10.2016.
 */

public interface SettingsPresenter extends BasePresenter {
    boolean onUpperBoundChanged(int newValue);

    boolean onLowerBoundChange(int lowerBound);

    boolean onAlarmIntervalChanged(int interval);

    interface View {
        void showMessage(String m);
    }
}
