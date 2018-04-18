package de.flansen.glucosetracker.settings.impl;

import de.flansen.glucosetracker.common.AppSettings;
import de.flansen.glucosetracker.common.Constants;
import de.flansen.glucosetracker.settings.SettingsPresenter;
import de.flhn.cleanboilerplate.domain.executor.Executor;
import de.flhn.cleanboilerplate.domain.executor.MainThread;
import de.flhn.cleanboilerplate.presentation.presenters.base.AbstractPresenter;

/**
 * Created by Florian on 26.10.2016.
 */

public class SettingsPresenterImpl extends AbstractPresenter implements SettingsPresenter {
    private final AppSettings appSettings;
    private final View view;

    public SettingsPresenterImpl(Executor executor, MainThread mainThread, AppSettings appSettings, View view) {
        super(executor, mainThread);
        this.appSettings = appSettings;
        this.view = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public boolean onUpperBoundChanged(int newUpperVal) {
        int upperValueLowerBound = Constants.GLUCOSE_UPPER_LIMIT_LOWER_BOUND;
        int upperValueUpperBound = Constants.GLUCOSE_UPPER_LIMIT_UPPER_BOUND;

        String lowerValueStr = appSettings.getString(Constants.SETTINGS_KEY_LOWER_BOUND);
        int lowerValue = Integer.valueOf(lowerValueStr);
        if (newUpperVal > upperValueUpperBound || newUpperVal < upperValueLowerBound || newUpperVal < lowerValue) {
            view.showMessage("Upper value must be between " + upperValueLowerBound + "  and " + upperValueUpperBound + " and greater than the lower bound.");
            return false;
        } else {
            appSettings.putString(Constants.SETTINGS_KEY_UPPER_BOUND, Integer.toString(newUpperVal));
            return true;
        }
    }

    @Override
    public boolean onLowerBoundChange(int newLowerVal) {
        int lowerValueLowerBound = Constants.GLUCOSE_LOWER_LIMIT_LOWER_BOUND;
        int lowerValueUpperBound = Constants.GLUCOSE_LOWER_LIMIT_UPPER_BOUND;

        String upperValueStr = appSettings.getString(Constants.SETTINGS_KEY_UPPER_BOUND);
        int upperValue = Integer.valueOf(upperValueStr);
        if (newLowerVal < lowerValueLowerBound || newLowerVal > lowerValueUpperBound || newLowerVal > upperValue) {
            view.showMessage("Lower value must be between " + lowerValueLowerBound + " and " + lowerValueUpperBound + " and lower than the lower value.");
            return false;
        } else {
            appSettings.putString(Constants.SETTINGS_KEY_LOWER_BOUND, Integer.toString(newLowerVal));
            return true;
        }
    }

    @Override
    public boolean onAlarmIntervalChanged(int interval) {
        appSettings.putInt(Constants.SETTINGS_KEY_ALARM_INTERVAL, interval);
        return true;
    }
}
