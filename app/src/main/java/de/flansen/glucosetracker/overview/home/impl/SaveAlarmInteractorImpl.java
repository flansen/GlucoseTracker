package de.flansen.glucosetracker.overview.home.impl;

import de.flansen.glucosetracker.common.AppSettings;
import de.flansen.glucosetracker.common.Constants;
import de.flansen.glucosetracker.common.impl.TimeConverter;
import de.flansen.glucosetracker.overview.home.LumindAlarm;
import de.flansen.glucosetracker.overview.home.SaveAlarmInteractor;
import de.flansen.glucosetracker.overview.home.SetAlarmInteractor;
import de.flhn.cleanboilerplate.domain.executor.Executor;
import de.flhn.cleanboilerplate.domain.executor.MainThread;
import de.flhn.cleanboilerplate.domain.interactors.base.AbstractInteractor;

/**
 * Created by Florian on 28.10.2016.
 */

public class SaveAlarmInteractorImpl extends AbstractInteractor implements SaveAlarmInteractor {

    private final AppSettings appSettings;
    private final SetAlarmInteractor setAlarmInteractor;
    private Callback callback;

    public SaveAlarmInteractorImpl(Executor threadExecutor, MainThread mainThread, AppSettings appSettings, SetAlarmInteractor setAlarmInteractor) {
        super(threadExecutor, mainThread);
        this.appSettings = appSettings;
        this.setAlarmInteractor = setAlarmInteractor;
    }

    @Override
    public void saveAlarm(LumindAlarm alarm) {
        appSettings.putBoolean(Constants.ALARM_ENABLED_KEY, alarm.isEnabled());
        appSettings.putInt(Constants.ALARM_HOUR_KEY, alarm.getHourOfDay());
        appSettings.putInt(Constants.ALARM_MINUTES_KEY, alarm.getMinutes());

        setAlarmInteractor.setAlarm(TimeConverter.calculateMsUntil(alarm.getHourOfDay(), alarm.getMinutes()), alarm.isEnabled());

        if (callback != null)
            callback.onAlarmSaved(alarm);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void run() {

    }
}
