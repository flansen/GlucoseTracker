package de.fha.bwi50101.overview.home.impl;

import de.fha.bwi50101.common.AppSettings;
import de.fha.bwi50101.common.Constants;
import de.fha.bwi50101.common.impl.TimeConverter;
import de.fha.bwi50101.overview.home.LumindAlarm;
import de.fha.bwi50101.overview.home.SaveAlarmInteractor;
import de.fha.bwi50101.overview.home.SetAlarmInteractor;
import de.flhn.cleanboilerplate.domain.executor.Executor;
import de.flhn.cleanboilerplate.domain.executor.MainThread;
import de.flhn.cleanboilerplate.domain.interactors.base.AbstractInteractor;

/**
 * Created by Florian on 28.10.2016.
 */

public class SaveAlarmInteractorImpl extends AbstractInteractor implements SaveAlarmInteractor {

    private final AppSettings appSettings;
    private final Callback callback;
    private final SetAlarmInteractor setAlarmInteractor;

    public SaveAlarmInteractorImpl(Executor threadExecutor, MainThread mainThread, Callback callback, AppSettings appSettings, SetAlarmInteractor setAlarmInteractor) {
        super(threadExecutor, mainThread);
        this.appSettings = appSettings;
        this.callback = callback;
        this.setAlarmInteractor = setAlarmInteractor;
    }

    @Override
    public void saveAlarm(LumindAlarm alarm) {
        appSettings.putBoolean(Constants.ALARM_ENABLED_KEY, alarm.isEnabled());
        appSettings.putInt(Constants.ALARM_HOUR_KEY, alarm.getHourOfDay());
        appSettings.putInt(Constants.ALARM_MINUTES_KEY, alarm.getMinutes());

        setAlarmInteractor.setAlarm(TimeConverter.calculateMsUntil(alarm.getHourOfDay(), alarm.getMinutes()), alarm.isEnabled());

        callback.onAlarmSaved(alarm);
    }

    @Override
    public void run() {

    }
}
