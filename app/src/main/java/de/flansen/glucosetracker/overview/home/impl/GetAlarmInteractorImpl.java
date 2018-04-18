package de.flansen.glucosetracker.overview.home.impl;

import de.flansen.glucosetracker.common.AppSettings;
import de.flansen.glucosetracker.common.Constants;
import de.flansen.glucosetracker.overview.home.GetAlarmInteractor;
import de.flansen.glucosetracker.overview.home.LumindAlarm;
import de.flhn.cleanboilerplate.domain.executor.Executor;
import de.flhn.cleanboilerplate.domain.executor.MainThread;
import de.flhn.cleanboilerplate.domain.interactors.base.AbstractInteractor;

/**
 * Created by Florian on 28.10.2016.
 */

public class GetAlarmInteractorImpl extends AbstractInteractor implements GetAlarmInteractor {
    private static final boolean DEFAULT_DISABLED = false;
    private final Callback callback;
    private final AppSettings appSettings;

    public GetAlarmInteractorImpl(Executor threadExecutor, MainThread mainThread, Callback callback, AppSettings appSettings) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.appSettings = appSettings;
    }

    @Override
    public void run() {
        boolean isEnabled = appSettings.getBoolean(Constants.ALARM_ENABLED_KEY, DEFAULT_DISABLED);
        int hourOfDay = appSettings.getInt(Constants.ALARM_HOUR_KEY, 12);
        int min = appSettings.getInt(Constants.ALARM_MINUTES_KEY, 0);

        final LumindAlarm alarm = new LumindAlarm(isEnabled, hourOfDay, min);

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onAlarmFetched(alarm);
            }
        });
    }
}
