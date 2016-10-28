package de.fha.bwi50101.overview.home;

import de.flhn.cleanboilerplate.presentation.presenters.base.BasePresenter;

/**
 * Created by Florian on 08.10.2016.
 */

public interface HomeFragmentPresenter extends BasePresenter {
    void setView(HomeFragmentPresenter.View view);

    void fetchMostRecentGlucoseValue();

    void handleTimerChanged(int hourOfDay, int min, boolean isEnabled);

    void loadAlarm();

    void handleTimerStateChanged(boolean isEnabled);

    interface View {
        void displayMostRecentValue(String displayString, boolean shouldShowUnitLabel);

        void onError();

        void displayAlarmData(String time, boolean enabled);

        void showMessage(String message);

        void updateAlarmSwitchState(boolean state);
    }
}
